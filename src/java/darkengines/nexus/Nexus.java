/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	    case OFFER: {
		try {
		    sendOffer(socket, json);
		} catch (IOException ex) {
		    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
		}
		break;
	    }
	    case ANSWER: {
		try {
		    sendAnswer(socket, json);
		} catch (IOException ex) {
		    Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
		}
		break;
	    }
		case HANGUP: {
		try {
		    sendHangUp(socket, json);
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

	NexusMessage nexusMessage = new NexusMessage(NexusMessageType.GET_ONLINE_USERS, gson.toJsonTree(result));
	socket.getSession().getRemote().sendString(gson.toJson(nexusMessage));
    }

    private void sendOfflineUser(User user) throws IOException {
	NexusMessage message = new NexusMessage(NexusMessageType.OFFLINE_USER, gson.toJsonTree(new ClientUser(user.getId(), null)));
	for (NexusWebSocket s : sockets) {
	    s.getSession().getRemote().sendString(gson.toJson(message));
	}
    }

    private void sendOnlineUser(User user) throws IOException {
	NexusMessage message = new NexusMessage(NexusMessageType.ONLINE_USER, gson.toJsonTree(new ClientUser(user.getId(), user.getDisplayName())));
	for (NexusWebSocket s : sockets) {
	    if (!s.getSocketUser().equals(user)) {
		s.getSession().getRemote().sendString(gson.toJson(message));
	    }
	}
    }

    private void sendChatMessage(NexusWebSocket socket, JsonElement json) throws IOException {
	NexusChatMessage chatMessage = gson.fromJson(json, NexusChatMessage.class);
	chatMessage.setAuthor(new UserItem(socket.getSocketUser()));
	NexusMessage message = new NexusMessage(NexusMessageType.CHAT_MESSAGE, gson.toJsonTree(chatMessage));
	int length = sockets.size();
	boolean found = false;
	while (!found && length-- > 0) {
	    found = sockets.get(length).getSocketUser().getId() == chatMessage.getRecipient().getId();
	}
	if (found) {
	    sockets.get(length).getSession().getRemote().sendString(gson.toJson(message));
	    socket.getSession().getRemote().sendString(gson.toJson(message));
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
	NexusMessage message = new NexusMessage(NexusMessageType.CALL_REQUEST, gson.toJsonTree(new CallRequest(socket.getSocketUser(), target.getSocketUser())));
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
	NexusMessage message = new NexusMessage(NexusMessageType.CALL_RESPONSE, json);
	target.getSession().getRemote().sendString(gson.toJson(message).toString());
    }

    private void sendIceCandidate(NexusWebSocket socket, JsonElement json) throws IOException {
	IceCandidate iceCandidate = gson.fromJson(json, IceCandidate.class);
	NexusWebSocket target = null;
	boolean found = false;
	int size = sockets.size();
	while (!found && size-- > 0) {
	    found = sockets.get(size).getSocketUser().getId() == iceCandidate.getRecipient().getId();
	}
	if (found) {
	    target = sockets.get(size);
	    iceCandidate.setAuthor(new UserItem(socket.getSocketUser()));
	}
	NexusMessage message = new NexusMessage(NexusMessageType.ICE_CANDIDATE, gson.toJsonTree(iceCandidate));
	target.getSession().getRemote().sendString(gson.toJson(message).toString());
    }

    private void sendOffer(NexusWebSocket socket, JsonElement json) throws IOException {
	Offer offer = gson.fromJson(json, Offer.class);
	NexusWebSocket target = null;
	boolean found = false;
	int size = sockets.size();
	while (!found && size-- > 0) {
	    found = sockets.get(size).getSocketUser().getId() == offer.getCallee().getId();
	}
	if (found) {
	    target = sockets.get(size);
	}
	NexusMessage message = new NexusMessage(NexusMessageType.OFFER, json);
	target.getSession().getRemote().sendString(new Gson().toJson(message));
    }
    private void sendAnswer(NexusWebSocket socket, JsonElement json) throws IOException {
	Offer offer = gson.fromJson(json, Offer.class);
	NexusWebSocket target = null;
	boolean found = false;
	int size = sockets.size();
	while (!found && size-- > 0) {
	    found = sockets.get(size).getSocketUser().getId() == offer.getCaller().getId();
	}
	if (found) {
	    target = sockets.get(size);
	}
	NexusMessage message = new NexusMessage(NexusMessageType.ANSWER, json);
	target.getSession().getRemote().sendString(new Gson().toJson(message));
    }

    private void sendHangUp(NexusWebSocket socket, JsonElement json) throws IOException {
	HangUp hangUp = gson.fromJson(json, HangUp.class);
	hangUp.setAuthor(new UserItem(socket.getSocketUser()));
	NexusWebSocket target = findSocketByUserId(hangUp.getRecipient().getId());
	if (target != null) {
	    NexusMessage message = new NexusMessage(NexusMessageType.HANGUP, gson.toJsonTree(hangUp));
	    target.getSession().getRemote().sendString(gson.toJson(message));
	}
    }
    
    private NexusWebSocket findSocketByUserId(long id) {
	NexusWebSocket result = null;
	int size = sockets.size();
	while (result == null && size-- > 0) {
	    if (sockets.get(size).getSocketUser().getId() == id) {
		result = sockets.get(size);
	    }
	}
	return result;
    }
}
