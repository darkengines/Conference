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
public class NexusChatMessage {
    private User author;
    private String content;

    public User getAuthor() {
	return author;
    }

    public void setAuthor(User author) {
	this.author = author;
    }

    public String getContent() {
	return content;
    }

    public void setContent(String content) {
	this.content = content;
    }
}
