/**
 * 
 */
package org.centenaire.general.pubsub;

/**
 * Class implementing Publisher of a Publisher-Subscriber pattern.
 * 
 * <p>The Publisher-Dispatcher-Subscriber (or Pub-Sub) pattern 
 * is a variation on the theme of the Observer pattern. Here, we use 
 * an extra interface, 'Dispatcher', which register events from publishers 
 * and send them to the subscriber.</p>
 * 
 * <p>In this pattern (as opposed to the Observer pattern), Publishers and 
 * Subscribers do not need to know each other (indeed, they do not even need 
 * to be initialized simultaneously).</p>
 *
 * <p>This approach also provides a sort of "multi-channel" Observer.</p>
 * 
 * @see Dispatcher
 * @see Subscriber
 */
public interface Publisher {
	
	public void publish(int channelIndex);
}
