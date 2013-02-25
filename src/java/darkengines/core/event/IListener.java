/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package darkengines.core.event;

/**
 *
 * @author Quicksort
 */
public interface IListener<T> {
    public void callback(Object sender, T eventArgs);
}
