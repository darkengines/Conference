/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.service.exception;

/**
 *
 * @author Quicksort
 */
public class PublicException extends Throwable {
    protected int code;
    protected String description;
    public PublicException() {
	code = 999;
	description = "No description";
    }

    public int getCode() {
	return code;
    }

    public String getDescription() {
	return description;
    }
}
