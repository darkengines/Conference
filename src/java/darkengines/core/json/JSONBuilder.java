/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.json;

import com.google.gson.Gson;
import darkengines.core.service.exception.PublicError;
import darkengines.core.service.exception.PublicException;

/**
 *
 * @author Quicksort
 */
public class JSONBuilder {
    public static String buildError(PublicException e) {
	Gson gson = new Gson();
	return gson.toJson(new PublicError(e));
    }
    public static String buildError(Exception e) {
	Gson gson =new Gson();
	return gson.toJson(new PublicError(e));
    }
}
