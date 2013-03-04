/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonObject;
import java.lang.reflect.Type;
/**
 *
 * @author Quicksort
 */
public class NexusMessageSerializer implements JsonSerializer<NexusMessage> {

    @Override
    public JsonElement serialize(NexusMessage src, Type typeOfSrc, JsonSerializationContext context) {
	JsonObject element = new JsonObject();
	element.addProperty("type", src.getType().toString());
	element.add("data", src.getData());
	return element;
    }
    
}
