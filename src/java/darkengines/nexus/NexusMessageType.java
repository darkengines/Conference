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
    CHAT_MESSAGE(0x4),
    CALL_REQUEST(0x4),
    CALL_RESPONSE(0x5),
    ICE_CANDIDATE(0x6),
    OFFER(0x7),
    ANSWER(0x8),
    HANGUP(0x9);
    
    private int code;

    NexusMessageType(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
