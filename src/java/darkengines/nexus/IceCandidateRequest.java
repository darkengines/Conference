/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import RTCService.IceCandidate;

/**
 *
 * @author Quicksort
 */
public class IceCandidateRequest {
    private String uuid;
    private long userId;
    private IceCandidate iceCandidate;

    public String getUuid() {
	return uuid;
    }

    public void setUuid(String uuid) {
	this.uuid = uuid;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public IceCandidate getIceCandidate() {
	return iceCandidate;
    }

    public void setIceCandidate(IceCandidate iceCandidate) {
	this.iceCandidate = iceCandidate;
    }
    
}
