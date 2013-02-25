/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.chat;

import darkengines.core.event.EventHandler;
import darkengines.user.User;
import java.util.ArrayList;

/**
 *
 * @author Quicksort
 */
public class Chat {
    private ArrayList<User> users = null;
    public EventHandler<UserJoinedChatEventArgs> userJoined = null;
    public EventHandler<NewChatMessageEventArgs> newMessage = null;
    
    public Chat() {
	users = new ArrayList<User>();
	userJoined = new EventHandler<UserJoinedChatEventArgs>();
	newMessage = new EventHandler<NewChatMessageEventArgs>();
    }
    
    public void addUser(User user) {
	if (!users.contains(user)) {
	    users.add(user);
	}
	userJoined.execute(this, new UserJoinedChatEventArgs(user));
    }
    
    public void addMessage(User user, String message) {
	newMessage.execute(this, new NewChatMessageEventArgs(user, message));
    }
}
