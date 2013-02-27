/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketListener;

/**
 *
 * @author Quicksort
 */
public class NexusWebSocket implements WebSocketListener {

    @Override
    public void onWebSocketBinary(byte[] bytes, int i, int i1) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketClose(int i, String string) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketConnect(Session sn) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketError(Throwable thrwbl) {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onWebSocketText(String string) {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
