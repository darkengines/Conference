/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTCService;

import RTCService.IceCandidateRegister;
import WRTC.PeerToPeerConnection.RTCIceCandidate;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Quicksort
 */
public class IceCandidate extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Gson gson = new Gson();
        RTCIceCandidate candidate = gson.fromJson(request.getParameter("RTCIceCandidate"), RTCIceCandidate.class);
        IceCandidateRegister.getInstance().getRegister().add(candidate);
    }
}