/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.lang.reflect.Type;

/**
 *
 * @author Quicksort
 */
public class NexusMessageDeserializer implements JsonDeserializer<NexusMessage> {

    @Override
    public NexusMessage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	NexusMessage message = new NexusMessage();
	message.setType(NexusMessageType.valueOf(json.getAsJsonObject().get("type").getAsString()));
	if (json.getAsJsonObject().has("data") && json.getAsJsonObject().get("data") != null) {
	    message.setData(json.getAsJsonObject().get("data"));
	}
	return message;
    }

}
