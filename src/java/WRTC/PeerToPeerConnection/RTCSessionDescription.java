/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WRTC.PeerToPeerConnection;

/**
 *
 * @author Quicksort
 */
public class RTCSessionDescription {
    private RTCType type;
    private String sdp;
    public void setType(RTCType type) {
        this.type = type;
    }
    public void setSdp(String sdp) {
        this.sdp = sdp;
    }
    public RTCType getType() {
        return this.type;
    }
    public String getSdp() {
        return this.sdp;
    }
    public enum RTCType {
        offer,
        pranswer,
        answer
    }
}
