/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.service.exception;

/**
 *
 * @author Quicksort
 */
public class PublicError {
    private int code;
    private String message;
    private String description;
    public PublicError(PublicException pe) {
	this.code = pe.getCode();
	this.message = pe.getMessage();
	this.description = pe.getDescription();
    }
    
    public PublicError(Exception e) {
	this.code = 999;
	this.message = e.getMessage();
	this.description = "No description";
    }

    public int getCode() {
	return code;
    }

    public void setCode(int code) {
	this.code = code;
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }
    
}
