/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.user;

/**
 *
 * @author Quicksort
 */
public class UserModule {
    private static UserRepository userRepository = null;
    public static UserRepository getUserRepository() {
	if (userRepository == null) {
	    userRepository = new UserRepository();
	}
	return userRepository;
    }
}
