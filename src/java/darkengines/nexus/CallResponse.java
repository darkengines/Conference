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
public class CallResponse {
    private long callerId;
    private long calleeId;
    private String uuid;    
    private RTCSessionDescription localDescription;
    
    public CallResponse() {
	
    }
    
    public CallResponse(long callerId, long calleeId, String uuid, RTCSessionDescription localDescription) {
	this.callerId = callerId;
	this.calleeId = calleeId;
	this.uuid = uuid;
	this.localDescription = localDescription;
    }
    
    public String getUuid() {
	return uuid;
    }

    public void setUuid(String uuid) {
	this.uuid = uuid;
    }
    
    public long getCallerId() {
	return callerId;
    }

    public void setCallerId(long callerId) {
	this.callerId = callerId;
    }

    public long getCalleeId() {
	return calleeId;
    }

    public void setCalleeId(long calleeId) {
	this.calleeId = calleeId;
    }

    public RTCSessionDescription getLocalDescription() {
	return localDescription;
    }

    public void setLocalDescription(RTCSessionDescription localDescription) {
	this.localDescription = localDescription;
    }
}
