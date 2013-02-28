/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import darkengines.session.SessionModule;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jasper.tagplugins.jstl.core.Out;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

/**
 *
 * @author Quicksort
 */
public class NexusWebSocket implements WebSocketListener {
    
    private User socketUser = null;
    private RemoteEndpoint remote = null;
    
    public RemoteEndpoint getRemote() {
        return remote;
    }
    
    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketClose(int i, String string) {
	Nexus.getInstance().removeSocket(this);
    }

    @Override
    public void onWebSocketConnect(Session sn) {
        try {
            String suuid = sn.getUpgradeRequest().getParameterMap().get("uuid")[0];
            if (suuid == null) {
                sn.disconnect();
            } else {
                darkengines.session.Session session = SessionModule.getSessionRepository().getSessionByUuid(UUID.fromString(suuid));
                if (session == null) {
                    sn.disconnect();
                } else {
                    User user = UserModule.getUserRepository().getUserById(session.getUserId());
                    if (user == null) {
                        sn.disconnect();
                    } else {
                        socketUser = user;
                        remote = sn.getRemote();
                        Nexus.getInstance().addSocket(this);
                    }
                }
            }
        } catch (Exception e) {
            try {
                Logger.getLogger(NexusWebSocket.class.getName()).log(Level.SEVERE, null, e);
                sn.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(NexusWebSocket.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void onWebSocketError(Throwable thrwbl) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketText(String string) {
	Nexus.getInstance().processMessage(this, string);
    }
    public User getSocketUser() {
        return socketUser;
    }
}
