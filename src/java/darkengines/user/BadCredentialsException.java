/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.user;

import darkengines.core.service.exception.PublicException;

/**
 *
 * @author Quicksort
 */
public class BadCredentialsException extends PublicException {
    
    private String email;
    
    public BadCredentialsException(String email) {
	code = 101;
	description = "Occurs when user try to login with invalid credentials.";
	this.email = email;
    }
    
    @Override
    public String getMessage() {
	return String.format("Bad credentials for user %s.", email);
    }
}
