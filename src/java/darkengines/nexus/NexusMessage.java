/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

import com.google.gson.JsonElement;
/**
 *
 * @author Quicksort
 */
public class NexusMessage {
    private NexusMessageType type;
    private JsonElement data;

    public NexusMessage() {
	
    }
    
    public NexusMessage(NexusMessageType type, JsonElement data) {
	this.type = type;
	this.data = data;
    }
    
    public NexusMessageType getType() {
        return type;
    }

    public void setType(NexusMessageType type) {
        this.type = type;
    }

    public JsonElement getData() {
        return data;
    }

    public void setData(JsonElement data) {
        this.data = data;
    }
}
