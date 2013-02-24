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
public class JSONResponse {
    private int code;
    private Object content;
    public JSONResponse(int code, Object content) {
	this.code = code;
	this.content = content;
    }
    @Override
    public String toString() {
	Gson gson = new Gson();
	return gson.toJson(this);
    }
}
