/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import WRTC.PeerToPeerConnection.RTCSessionDescription;

/**
 *
 * @author Quicksort
 */
public class Offer {
    private UserItem caller;
    private UserItem callee;
    private RTCSessionDescription description;

    public UserItem getCaller() {
	return caller;
    }

    public void setCaller(UserItem caller) {
	this.caller = caller;
    }

    public UserItem getCallee() {
	return callee;
    }

    public void setCallee(UserItem callee) {
	this.callee = callee;
    }

    public RTCSessionDescription getDescription() {
	return description;
    }

    public void setDescription(RTCSessionDescription description) {
	this.description = description;
    }
    
    
}
