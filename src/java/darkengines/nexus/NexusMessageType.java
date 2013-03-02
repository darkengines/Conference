/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

/**
 *
 * @author Quicksort
 */
public enum NexusMessageType {
    
    KEEP_ALIVE(0x0),
    GET_ONLINE_USERS(0x1),
    OFFLINE_USER(0x2),
    ONLINE_USER(0x3), 
    CHAT_MESSAGE(0x4);
    
    private int code;

    NexusMessageType(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}