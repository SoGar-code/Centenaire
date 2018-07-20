/**
 * 
 */
package org.centenaire.general.pubsub;

import java.util.LinkedList;
import java.util.List;

/**
 * Class corresponding to a news channel.
 * 
 * <p>Part of the Publisher-Subscriber pattern.</p>
 * 
 * @see org.centenaire.general.Dispatcher
 */
public class Channel {
	private List<Subscriber> listSubscribers;
	
	public Channel(){
		listSubscribers = new LinkedList<Subscriber>();
	}
	
	public void addSubscriber(Subscriber sub) {
		listSubscribers.add(sub);
	}
	
	public void publish() {
		for (Subscriber sub: listSubscribers) {
			sub.updateSubscriber();
		}
	}
}