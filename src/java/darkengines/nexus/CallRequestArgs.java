/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

/**
 *
 * @author Quicksort
 */
public class CallRequestArgs {
    private long calleeId;


    public void setCalleeId(long callerId) {
	this.calleeId = callerId;
    }

    long getCalleeId() {
	return calleeId;
    }
}
