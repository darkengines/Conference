/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

/**
 *
 * @author Quicksort
 */
public class NexusMessage {
    private NexusMessageType type;
    private Object data;

    public NexusMessageType getType() {
        return type;
    }

    public void setType(NexusMessageType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
