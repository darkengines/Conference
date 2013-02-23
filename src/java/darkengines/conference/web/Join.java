/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference.web;

import darkengines.user.User;
import darkengines.user.UserModule;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
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
public class Join extends HttpServlet {

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
	    throws ServletException, IOException, UnsupportedEncodingException, ClassNotFoundException, NamingException, SQLException, Exception {
	String email = request.getParameter("email");
	String password = request.getParameter("password");
	String displayName = request.getParameter("display_name");
	User user = UserModule.getUserRepository().getUserByEmail(email);
	
	if (user != null) {
	    throw new Exception(String.format("Email %s already used.", email));
	} else {
	    user = new User();
	    user.setEmail(email);
	    user.setPassword(password);
	    user.setDisplayName(displayName);
	    UserModule.getUserRepository().insertUser(user);
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
	    throws ServletException, IOException, UnsupportedEncodingException {
	try {
	    processRequest(request, response);
	} catch (Exception e) {
	    response.getWriter().write(e.getMessage());
	}
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
	    throws ServletException, IOException, UnsupportedEncodingException {
	try {
	    processRequest(request, response);
	} catch (Exception e) {
	    response.getWriter().write(e.getMessage());
	}
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
