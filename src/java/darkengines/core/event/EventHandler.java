/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.event;

import java.util.ArrayList;

/**
 *
 * @author Quicksort
 */
public class EventHandler<T> {
    private ArrayList<IListener<T>> listeners;
    public EventHandler() {
	listeners = new ArrayList<IListener<T>>();
    }
    public void addListener(IListener<T> listener) {
	listeners.add(listener);
    }
    public void removeListener(IListener<T> listener) {
	listeners.remove(listener);
    }
    public void execute(Object sender, T eventArgs) {
	for (IListener<T> listener: listeners) {
	    listener.callback(sender, eventArgs);
	}
    }
}
