/**
 * Package implementing a Pub-Sub (Publisher-Subscriber) pattern.
 */
package org.centenaire.util.pubsub;

/**
 * Class implementing Dispatcher of a Publisher-Subscriber pattern.
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
 * <p>The pattern is suppose to work in the following way:
 * <ul>
 * <li>Subscriber register to a given news channel with:
 * <pre>
 * {@code
 * dispatcher.getChannel(j).addSubscriber(subscriber);
 * }
 * </pre>
 * </li>
 * <li>Publishers send their updates specifying the news channel with:
 * <pre>
 * {@code
 * dispatcher.publish(j);
 * }
 * </pre>
 * </li>
  * <li>The previous code is equivalent to:
 * <pre>
 * {@code
 * dispatcher.getChannel(j).publish();
 * }
 * </pre>
 * </li>
 * </ul>
 * For simplicity, this is just a notification system, no meaningful data 
 * is carried <it>via</it> these notifications.
 * </p>
 * 
 * <p>The 'level' where the update should be included is dictated 
 * by the very simple update function: the subscribers react in only one way 
 * whatever the news channel.</p>
 * 
 * @see Publisher
 * @see Subscriber
 */
public interface Dispatcher {	
	
	/**
	 * Method to reach a given channel.
	 * 
	 * @param channelIndex
	 * 				index of the news channel to get.
	 * @return the requested news channel.
	 */
	public Channel getChannel(int channelIndex);

}
