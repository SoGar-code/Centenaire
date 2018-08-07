/**
 * 
 */
package org.centenaire.dao;

import java.sql.Connection;
import java.util.List;

import org.centenaire.entity.Entity;
import org.centenaire.util.GeneralController;
import org.centenaire.util.pubsub.Channel;
import org.centenaire.util.pubsub.Publisher;

/**
 * Abstract class used as model for Relation DAO.
 *
 *<p>This class is useful to implement "relations" between different
 * "entities" in the database. In particular, we assume that only 
 * one or zero "relation" can exist between two prescribed objects 
 * *T objT* and *U objU*.</p>
 * 
 * <p>In the database at hand, this is translated very concretely by 
 * the fact that the could *indexT*, *indexU* of the two previous object
 * serves as primary key of the "relation".</p>
 */
public abstract class RelationDao<T extends Entity, U extends Entity> implements Publisher{
	protected static Connection conn;
	protected static GeneralController gc = GeneralController.getInstance();
	
	/**
	 * Create a relation between *objT* and *objU*.
	 * 
	 * @param objT
	 * @param objU
	 * 
	 * @return True if the creation was successful.
	 */
	public abstract boolean create(T objT, U objU);
	
	/**
	 * Delete the relation between *objT* and *objU*.
	 * 
	 * @param objT
	 * @param objU
	 * 
	 * @return True if the deletion was successful.
	 */
	public abstract boolean delete(T objT, U objU);
	
	/**
	 * Find all *objU* associated to a prescribed *objT*.
	 * 
	 * @param objT
	 * @return list of elements *objU* linked to *objT* by this relation.
	 */
	public abstract List<U> findAll(T objT);
	
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
