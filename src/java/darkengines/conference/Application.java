/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.conference;

import darkengines.chat.Chat;

/**
 *
 * @author Quicksort
 */
public class Application {
    private static Chat chat = null;
    public static Chat getChat() {
	if (chat == null) {
	    chat = new Chat();
	}
	return chat;
    }
}
