package org.centenaire.dao;

import java.sql.Connection;
import java.util.LinkedList;

import org.centenaire.general.GeneralController;
import org.centenaire.general.pubsub.Channel;
import org.centenaire.general.pubsub.Publisher;

/**
 * Abstract class including the different possible actions on POJOs.
 */
public abstract class Dao<T> implements Publisher{
	protected static Connection conn;
	private static GeneralController gc = GeneralController.getInstance();

	abstract public boolean create(T obj);
	
	abstract public boolean update(T obj);
	
	abstract public boolean delete(T obj);
	
	abstract public T find(int index);
	
	/**
	 * Provide a fully initialized element (in terms of index)
	 * 
	 * @return fully initialized element.
	 */
	abstract public T newElement();
	
	/**
	 * Return either an existing element (from DB) or creates a new one.
	 * 
	 * @return Entity object 
	 */
	abstract public T anyElement();
	
	/**
	 * Recover all elements in the DB with chosen type.
	 * 
	 * @return list of elements in the DB with chosen type.
	 */
	abstract public LinkedList<T> findAll();

	/**
	 * Method to implement the Publisher interface of the Publisher-Subscriber pattern.
	 * 
	 * @see org.centenaire.general.pubsub.Subscriber
	 */
	public void publish(int channelIndex) {
		String msg = String.format("==> requested channelIndex: %s", channelIndex);
		System.out.println(msg);
		Channel channel = gc.getChannel(channelIndex);
		
		String msg2 = String.format("==> is channel null? %s", (channel==null));
		System.out.println(msg2);
		
		System.out.println("Could get Channel! Trying to publish...");
		channel.publish();
	}
}
