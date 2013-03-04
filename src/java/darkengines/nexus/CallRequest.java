/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import darkengines.user.User;
import java.util.UUID;

/**
 *
 * @author Quicksort
 */
public class CallRequest {
    private User caller;
    private User callee;
    private String uuid;

    public CallRequest(User caller, User callee) {
	this.caller = caller;
	this.callee = callee;
	this.uuid = UUID.randomUUID().toString();
    }

    public String getUuid() {
	return uuid;
    }

    public void setUuid(String uuid) {
	this.uuid = uuid;
    }
    
    public User getCaller() {
	return caller;
    }

    public void setCaller(User caller) {
	this.caller = caller;
    }

    public User getCallee() {
	return callee;
    }

    public void setCallee(User callee) {
	this.callee = callee;
    }
}
