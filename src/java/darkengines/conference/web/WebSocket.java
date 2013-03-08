/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.nexus.NexusWebSocket;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 *
 * @author Quicksort
 */
public class WebSocket extends WebSocketServlet {

    @Override
    public void configure(WebSocketServletFactory wssf) {
	wssf.getPolicy().setIdleTimeout(10000);
	wssf.register(NexusWebSocket.class);
    }

  
}
