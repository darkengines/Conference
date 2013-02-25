/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import WRTC.PeerToPeerConnection.RTCSessionDescription;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;
import org.apache.catalina.websocket.WsOutbound;

/**
 *
 * @author Quicksort
 */
public class WebSocket extends WebSocketServlet {
    
    private ArrayList<WsOutbound> connections = null;
    
    @Override
    public void init() {
	connections = new ArrayList<WsOutbound>();
    }
    
    @Override
    protected StreamInbound createWebSocketInbound(String string, HttpServletRequest hsr) {
	return new TheWebSocket();
    }
    
    private class TheWebSocket extends MessageInbound {

	@Override
	protected void onBinaryMessage(ByteBuffer bb) throws IOException {
	    throw new UnsupportedOperationException("Not supported yet.");
	}

	@Override
	protected void onTextMessage(CharBuffer cb) throws IOException {
	    Gson gson = new Gson();
	    UserInfos userInfos = gson.fromJson(cb.toString(), UserInfos.class);
	    for (WsOutbound connection: connections) {
		connection.writeTextMessage(CharBuffer.wrap(gson.toJson(userInfos)));
	    }
	}
	
	@Override
	protected void onOpen(WsOutbound outbound) {
	    try {
		connections.add(outbound);
		outbound.writeTextMessage(CharBuffer. wrap("hello world !"));
	    } catch (IOException ex) {
		Logger.getLogger(WebSocket.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    public class UserInfos {
	private String uuid;
	private RTCSessionDescription sessionDescription;

	public String getUuid() {
	    return uuid;
	}

	public void setUuid(String uuid) {
	    this.uuid = uuid;
	}

	public RTCSessionDescription getSessionDescription() {
	    return sessionDescription;
	}

	public void setSessionDescription(RTCSessionDescription sessionDescription) {
	    this.sessionDescription = sessionDescription;
	}
	
    }
}
