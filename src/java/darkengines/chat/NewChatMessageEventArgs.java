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
public class NewChatMessageEventArgs {
    private User user;
    private String message;
    public NewChatMessageEventArgs(User user, String message) {
	this.user = user;
	this.message = message;
    }
    public User getUser() {
	return user;
    }
    public String getMessage() {
	return message;
    }
}
