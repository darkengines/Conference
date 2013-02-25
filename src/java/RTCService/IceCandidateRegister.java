/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package RTCService;
import WRTC.PeerToPeerConnection.RTCIceCandidate;
import java.util.ArrayList;
/**
 *
 * @author Quicksort
 */
public class IceCandidateRegister {
    private static IceCandidateRegister instance;
    public static IceCandidateRegister getInstance() {
        if (IceCandidateRegister.instance != null) {
            return instance;
        } else {
            IceCandidateRegister.instance = new IceCandidateRegister();
            return IceCandidateRegister.instance;
        }
    }
    private ArrayList<RTCIceCandidate> register;
    private IceCandidateRegister() {
        this.register = new ArrayList<RTCIceCandidate>() {};
    }
    public ArrayList<RTCIceCandidate> getRegister() {
        return this.register;
    }
}
