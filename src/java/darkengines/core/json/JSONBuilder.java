/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.json;

import com.google.gson.Gson;

/**
 *
 * @author Quicksort
 */
public class JSONBuilder {
    public static String buildError(Exception e) {
	return String.format("{\"message\":\"%s\"}", e.getMessage());
    }
}
