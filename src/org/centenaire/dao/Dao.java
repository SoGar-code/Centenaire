package org.centenaire.dao;

import java.sql.Connection;
import java.util.LinkedList;

import org.centenaire.util.GeneralController;
import org.centenaire.util.pubsub.Channel;
import org.centenaire.util.pubsub.Publisher;

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
	 * Recover all elements in the DB with chosen type.
	 * 
	 * @return list of elements in the DB with chosen type.
	 */
	abstract public LinkedList<T> findAll();

	/**
	 * Method to implement the Publisher interface of the Publisher-Subscriber pattern.
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 */
	public void publish(int channelIndex) {
		// Get the requested channel from Dispatcher
		Channel channel = gc.getChannel(channelIndex);
		
		// Publish on that channel
		channel.publish(channelIndex);
	}
}
