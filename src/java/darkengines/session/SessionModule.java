/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.session;

/**
 *
 * @author Quicksort
 */
public class SessionModule {
    private static SessionRepository sessionRepository = null;
    public static SessionRepository getSessionRepository() {
	if (sessionRepository == null) {
	    sessionRepository = new SessionRepository();
	}
	return sessionRepository;
    }
}
