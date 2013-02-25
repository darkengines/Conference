/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.conference.Application;
import darkengines.session.Session;
import darkengines.session.SessionModule;
import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quicksort
 */
public class SendChatMessage extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    String suuid = request.getParameter("uuid");
	    UUID uuid = UUID.fromString(suuid);
	    Session session = SessionModule.getSessionRepository().getSessionByUuid(uuid);
	    User user = UserModule.getUserRepository().getUserById(session.getUserId());
	    String message = request.getParameter("message");
	    Application.getChat().addMessage(user, message);
	} catch (UnsupportedEncodingException ex) {
	    Logger.getLogger(SendChatMessage.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ClassNotFoundException ex) {
	    Logger.getLogger(SendChatMessage.class.getName()).log(Level.SEVERE, null, ex);
	} catch (NamingException ex) {
	    Logger.getLogger(SendChatMessage.class.getName()).log(Level.SEVERE, null, ex);
	} catch (SQLException ex) {
	    Logger.getLogger(SendChatMessage.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>
}
