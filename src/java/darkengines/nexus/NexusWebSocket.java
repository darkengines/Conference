/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import darkengines.session.SessionModule;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.RemoteEndpoint;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

/**
 *
 * @author Quicksort
 */
public class NexusWebSocket implements WebSocketListener {
    
    private User socketUser = null;
    private Session session = null;
    private Long lastPong = null;
    public Long getLastPong() {
	return lastPong;
    }
    
    public Session getSession() {
        return session;
    }
    
    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {
	lastPong = Calendar.getInstance().getTimeInMillis();
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
                        this.session = sn;
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
	lastPong = Calendar.getInstance().getTimeInMillis();
	Nexus.getInstance().processMessage(this, string);
    }
    public User getSocketUser() {
        return socketUser;
    }
}
