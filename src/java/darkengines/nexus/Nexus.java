/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import WRTC.PeerToPeerConnection.RTCSessionDescription;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import darkengines.user.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Quicksort
 */
public class Nexus {

    private static Nexus instance = null;
    protected ArrayList<NexusWebSocket> sockets = null;
    protected Gson gson = null;
    protected Nexus() {
	GsonBuilder gsonBuilder = new GsonBuilder();
	gsonBuilder.registerTypeAdapter(NexusMessage.class, new NexusMessageDeserializer());
	gsonBuilder.registerTypeAdapter(NexusMessage.class, new NexusMessageSerializer());
	gson = gsonBuilder.create();
	sockets = new ArrayList<NexusWebSocket>();
    }

    public static Nexus getInstance() {
	if (instance == null) {
	    instance = new Nexus();
	}
	return instance;
    }

    public void addSocket(NexusWebSocket socket) {
	boolean found = false;
	int size = sockets.size();
	while (!found && size-- != 0) {
	    found = sockets.get(size).getSocketUser().equals(socket.getSocketUser());
	}
	sockets.add(socket);
	if (!found) {
	    try {
		sendOnlineUser(socket.getSocketUser());
	    } catch (IOException ex) {
		Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }

    public void removeSocket(NexusWebSocket socket) {

	sockets.remove(socket);
	try {
	    sendOfflineUser(socket.getSocketUser());
	} catch (IOException ex) {
	    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    public void processMessage(NexusWebSocket socket, String message) {
	NexusMessage nexusMessage = gson.fromJson(message, NexusMessage.class);
	NexusMessageType type = nexusMessage.getType();
	JsonElement json = nexusMessage.getData();

	switch (type) {
	    case KEEP_ALIVE: {

		break;
	    }
	    case GET_ONLINE_USERS: {
		try {
		    sendOnlineUsers(socket, nexusMessage.getData());
		} catch (IOException ex) {
		    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
		}
		break;
	    }
	    case CHAT_MESSAGE: {
		try {
		    sendChatMessage(socket, json);
		} catch (IOException ex) {
		    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
		}
		break;
	    }
	    case CALL_REQUEST: {
		try {
		    sendCallRequest(socket, json);
		} catch (IOException ex) {
		    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
		}
		break;
	    }
	    case CALL_RESPONSE: {
		try {
		    sendCallResponse(socket, json);
		} catch (IOException ex) {
		    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
		}
		break;
	    }
	    case ICE_CANDIDATE: {
		try {
		    sendIceCandidate(socket, json);
		} catch (IOException ex) {
		    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
		}
		break;
	    }
	    default: {

		break;
	    }
	}
    }

    private void sendOnlineUsers(NexusWebSocket socket, JsonElement data) throws IOException {
	ArrayList<User> users = new ArrayList<User>();
	for (NexusWebSocket s : sockets) {
	    if (!users.contains(s.getSocketUser())) {
		users.add(s.getSocketUser());
	    }
	}
	int len = users.size();
	ClientUser[] result = new ClientUser[users.size()];
	int i = 0;
	while (i < len) {
	    User user = users.get(i);
	    result[i] = new ClientUser(user.getId(), user.getDisplayName());
	    i++;
	}
	
	NexusMessage nexusMessage = new NexusMessage();
	nexusMessage.setType(NexusMessageType.GET_ONLINE_USERS);
	
	nexusMessage.setData(gson.toJsonTree(result));
	socket.getSession().getRemote().sendString(gson.toJson(nexusMessage));
    }

    private void sendOfflineUser(User user) throws IOException {
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.OFFLINE_USER);
	message.setData(gson.toJsonTree(new ClientUser(user.getId(), null)));
	for (NexusWebSocket s : sockets) {
	    s.getSession().getRemote().sendString(gson.toJson(message));
	}
    }

    private void sendOnlineUser(User user) throws IOException {
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.ONLINE_USER);
	message.setData(gson.toJsonTree(new ClientUser(user.getId(), user.getDisplayName())));
	for (NexusWebSocket s : sockets) {
	    if (!s.getSocketUser().equals(user)) {
		s.getSession().getRemote().sendString(gson.toJson(message));
	    }
	}
    }

    private void sendChatMessage(NexusWebSocket socket, JsonElement json) throws IOException {
	NexusChatMessage chatMessage = gson.fromJson(json, NexusChatMessage.class);
	chatMessage.setAuthor(socket.getSocketUser());
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.CHAT_MESSAGE);
	message.setData(gson.toJsonTree(chatMessage));
	for (NexusWebSocket s : sockets) {
	    s.getSession().getRemote().sendString(gson.toJson(message));
	}
    }

    private void sendCallRequest(NexusWebSocket socket, JsonElement json) throws IOException {
	CallRequestArgs callRequestArgs = gson.fromJson(json, CallRequestArgs.class);
	NexusWebSocket target = null;
	boolean found = false;
	int size = sockets.size();
	while (!found && size-- > 0) {
	    found = sockets.get(size).getSocketUser().getId() == callRequestArgs.getCalleeId();
	}
	if (found) {
	    target = sockets.get(size);
	}
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.CALL_REQUEST);
	message.setData(gson.toJsonTree(new CallRequest(socket.getSocketUser(), target.getSocketUser())));
	target.getSession().getRemote().sendString(new Gson().toJson(message));
	socket.getSession().getRemote().sendString(new Gson().toJson(message));
    }

    private void sendCallResponse(NexusWebSocket socket, JsonElement json) throws IOException {
	CallResponse description = gson.fromJson(json, CallResponse.class);
	NexusWebSocket target = null;
	boolean found = false;
	int size = sockets.size();
	while (!found && size-- > 0) {
	    found = sockets.get(size).getSocketUser().getId() == description.getCallerId();
	}
	if (found) {
	    target = sockets.get(size);
	}
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.CALL_RESPONSE);
	message.setData(json);
	target.getSession().getRemote().sendString(gson.toJson(message).toString());
    }

    private void sendIceCandidate(NexusWebSocket socket, JsonElement json) throws IOException {
	IceCandidateRequest iceCandidateRequest = gson.fromJson(json, IceCandidateRequest.class);
	NexusWebSocket target = null;
	boolean found = false;
	int size = sockets.size();
	while (!found && size-- > 0) {
	    found = sockets.get(size).getSocketUser().getId() == iceCandidateRequest.getUserId();
	}
	if (found) {
	    target = sockets.get(size);
	}
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.ICE_CANDIDATE);
	message.setData(json);
	target.getSession().getRemote().sendString(gson.toJson(message).toString());
    }
}
