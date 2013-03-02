/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import com.google.gson.Gson;
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

    protected Nexus() {
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
	Gson gson = new Gson();
	NexusMessage nexusMessage = gson.fromJson(message, NexusMessage.class);
	NexusMessageType type = nexusMessage.getType();

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
		    sendChatMessage(socket, (String)nexusMessage.getData());
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

    private void sendOnlineUsers(NexusWebSocket socket, Object data) throws IOException {
	Gson gson = new Gson();
	ArrayList<User> users = new ArrayList<User>();
	for (NexusWebSocket s : sockets) {
	    if (!users.contains(s.getSocketUser())) {
		users.add(s.getSocketUser());
	    }
	}
	int len = users.size();
	UserListItem[] result = new UserListItem[users.size()];
	int i = 0;
	while (i < len) {
	    User user = users.get(i);
	    result[i] = new UserListItem(user.getId(), user.getDisplayName());
	    i++;
	}
	NexusMessage nexusMessage = new NexusMessage();
	nexusMessage.setType(NexusMessageType.GET_ONLINE_USERS);
	nexusMessage.setData(result);
	socket.getSession().getRemote().sendString(gson.toJson(nexusMessage));
    }

    private void sendOfflineUser(User user) throws IOException {
	Gson gson = new Gson();
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.OFFLINE_USER);
	message.setData(new UserListItem(user.getId(), null));
	for (NexusWebSocket s : sockets) {
	    s.getSession().getRemote().sendString(gson.toJson(message));
	}
    }

    private void sendOnlineUser(User user) throws IOException {
	Gson gson = new Gson();
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.ONLINE_USER);
	message.setData(new UserListItem(user.getId(), user.getDisplayName()));
	for (NexusWebSocket s : sockets) {
	    if (!s.getSocketUser().equals(user)) {
		s.getSession().getRemote().sendString(gson.toJson(message));
	    }
	}
    }

    private void sendChatMessage(NexusWebSocket socket, String content) throws IOException {
	Gson gson = new Gson();
	NexusChatMessage chatMessage = new NexusChatMessage();
	chatMessage.setAuthor(socket.getSocketUser());
	chatMessage.setContent(content);
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.CHAT_MESSAGE);
	message.setData(chatMessage);
	for (NexusWebSocket s : sockets) {
	    if (!s.getSocketUser().equals(socket.getSocketUser())) {
		s.getSession().getRemote().sendString(gson.toJson(message));
	    }
	}
    }
}
