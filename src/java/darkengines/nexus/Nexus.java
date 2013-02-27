/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.nexus;

/**
 *
 * @author Quicksort
 */
public class Nexus {
    private static Nexus instance = null;
    public Nexus getInstance() {
	if (instance == null) {
	    instance = new Nexus();
	}
	return instance;
    }
}
