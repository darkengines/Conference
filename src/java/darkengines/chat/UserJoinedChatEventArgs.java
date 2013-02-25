/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.chat;

import darkengines.user.User;

/**
 *
 * @author Quicksort
 */
public class UserJoinedChatEventArgs {
    private User user;
    public UserJoinedChatEventArgs(User user) {
	this.user = user;
    }
    public User getUser() {
	return user;
    }
}
