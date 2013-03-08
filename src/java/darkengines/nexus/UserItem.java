/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import darkengines.user.User;

/**
 *
 * @author Quicksort
 */
public class UserItem {
    private long id;
    private String displayName;
    
    public UserItem() {
	
    }
    
    public UserItem (User user) {
	id = user.getId();
	displayName = user.getDisplayName();
    }
    
    public UserItem(long id, String displayName) {
	this.id = id;
	this.displayName = displayName;
    }
    
    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }
    
}
