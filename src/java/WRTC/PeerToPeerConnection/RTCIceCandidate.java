/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package WRTC.PeerToPeerConnection;

/**
 *
 * @author Quicksort
 */
public class RTCIceCandidate {
    private String candidate;

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }
    
    private String sdpMid;

    public String getSdpMid() {
        return sdpMid;
    }

    public void setSdpMid(String sdpMid) {
        this.sdpMid = sdpMid;
    }
    
    private Integer sdpMLineIndex;

    public Integer getSdpMLineIndex() {
        return sdpMLineIndex;
    }

    public void setSdpMLineIndex(Integer sdpMLineIndex) {
        this.sdpMLineIndex = sdpMLineIndex;
    }
    
}
