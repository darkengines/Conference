/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.session.Session;
import darkengines.session.SessionModule;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.comet.CometEvent;
import org.apache.catalina.comet.CometEvent.EventType;
import org.apache.catalina.comet.CometProcessor;

/**
 *
 * @author Quicksort
 */
public class Chat implements CometProcessor  {

    private ArrayList<HttpServletResponse> connections = null;
    
    @Override
    public void event(CometEvent ce) throws IOException, ServletException, UnsupportedEncodingException {
	try {
	    EventType eventType = ce.getEventType();
	    HttpServletRequest request = ce.getHttpServletRequest();
	    HttpServletResponse response = ce.getHttpServletResponse();
	    
	    String suuid = request.getParameter("uuid");
	    if (suuid == null) {
		fuckYou(ce);
		return;
	    }
	    UUID uuid = UUID.fromString(suuid);		
	    Session session = null;
	    session = SessionModule.getSessionRepository().getSessionByUuid(uuid);
	    if (session == null) {
		fuckYou(ce);
		return;
	    }
	    User user = null;
	    user = UserModule.getUserRepository().getUserById(session.getUserId());
	    
	    if (user == null) {
		fuckYou(ce);
		return;
	    }
	    
	    switch (eventType) {
		case BEGIN: {
		    connections.add(response);
		}
		    
	    }
	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NamingException ex) {
	    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
	} catch (SQLException ex) {
	    Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
	connections = new ArrayList<HttpServletResponse>();
	Thread thread = new Thread(new ChatThread(), "test");
	thread.setDaemon(true);
	thread.start();
    }

    @Override
    public ServletConfig getServletConfig() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getServletInfo() {
	throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroy() {
	throw new UnsupportedOperationException("Not supported yet.");
    }
    
    private void fuckYou(CometEvent ce) throws IOException {
	HttpServletRequest request = ce.getHttpServletRequest();
	HttpServletResponse response = ce.getHttpServletResponse();
	response.getWriter().write("FUCK YOU !");
	response.flushBuffer();
	ce.close();
    }
    
    public class ChatThread implements Runnable {
	
	protected boolean running = true;
	@Override
	public void run() {
	    while (running) {
		for (HttpServletResponse connection: connections) {
		    try {
			connection.getWriter().write("<p>caca<p>");
		    } catch (IOException ex) {
			Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
		    }
		}
	    }
	}
	
    }
    
}
