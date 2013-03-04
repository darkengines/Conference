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
class ClientUser {
    private long id;
    private String displayName;
    public ClientUser(User user) {
        this.id = user.getId();
        this.displayName = user.getDisplayName();
    }
    public ClientUser(long id, String displayName) {
        this.id = id;
        this.displayName = displayName;
    }
    
}
