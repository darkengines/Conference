/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import WRTC.PeerToPeerConnection.RTCIceCandidate;

/**
 *
 * @author Quicksort
 */
public class IceCandidate {
    private UserItem author;
    private UserItem recipient;
    private RTCIceCandidate iceCandidate;

    public UserItem getAuthor() {
	return author;
    }

    public void setAuthor(UserItem author) {
	this.author = author;
    }

    public UserItem getRecipient() {
	return recipient;
    }

    public void setRecipient(UserItem recipient) {
	this.recipient = recipient;
    }

    public RTCIceCandidate getIceCandidate() {
	return iceCandidate;
    }

    public void RTCIceCandidate(RTCIceCandidate iceCandidate) {
	this.iceCandidate = iceCandidate;
    }
    
}
