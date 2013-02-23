/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.session;

import java.util.UUID;

/**
 *
 * @author Quicksort
 */
public class Session {
    private long id;
    private long userId;
    private UUID uuid;
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public long getUserId() {
	return userId;
    }

    public void setUserId(long userId) {
	this.userId = userId;
    }

    public UUID getUuid() {
	return uuid;
    }

    public void setUuid(UUID uuid) {
	this.uuid = uuid;
    }
}
