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
        sockets.add(socket);
    }

    public void removeSocket(NexusWebSocket socket) {
        sockets.remove(socket);
    }

    public void processMessage(NexusWebSocket socket, String message) {
        Gson gson = new Gson();
        NexusMessage nMessage = gson.fromJson(message, NexusMessage.class);
        switch (nMessage.getType()) {
            case 1: {
            try {
                processQuery(socket, (String) nMessage.getData());
                break;
            } catch (IOException ex) {
                Logger.getLogger(Nexus.class.getName()).log(Level.SEVERE, null, ex);
            }
            }
            default: {
            }
        }
    }

    private void processQuery(NexusWebSocket socket, String string) throws IOException {
        Gson gson = new Gson();
        if (string.equals("getUserList")) {
            ArrayList<User> users = new ArrayList<User>();
            for (NexusWebSocket s : sockets) {
                if (!users.contains(s.getSocketUser())) {
                    users.add(s.getSocketUser());
                }
            }
            int len = users.size();
            Object[] result = new Object[users.size()];
            int i = 0;
            while (i < len) {
                User user = users.get(i);
                result[i] = new UserListItem(user.getId(), user.getDisplayName());
                i++;
            }
            socket.getRemote().sendString(gson.toJson(result));
        }
    }
}
