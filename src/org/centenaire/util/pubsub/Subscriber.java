package org.centenaire.util.pubsub;

/**
 * Class implementing Subscriber of a Publisher-Subscriber pattern.
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
 * @see Publisher
 */
public interface Subscriber {
	
	/**
	 * Method called when a new piece of news is published on registered channel.
	 * 
	 * @param channelIndex
	 * 				index of the news channel publishing the piece of news.
	 */
	public void updateSubscriber(int indexClass);
}
