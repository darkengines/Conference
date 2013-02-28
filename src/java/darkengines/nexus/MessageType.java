/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

/**
 *
 * @author Quicksort
 */
public enum MessageType {

    QUERY(0x1);
    private int code;

    MessageType(int code) {
        this.code = code;
    }
    public int getCode() {
        return code;
    }
}
