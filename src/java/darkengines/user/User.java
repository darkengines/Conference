/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.user;

/**
 *
 * @author Quicksort
 */
public class User {
    private long id;
    private String email;
    private String password;
    private String displayName;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }

    public String getDisplayName() {
	return displayName;
    }

    public void setDisplayName(String displayName) {
	this.displayName = displayName;
    }
    @Override
    public boolean equals(Object user) {
	return user != null && user instanceof User && ((User)user).id == id;
    }
}
