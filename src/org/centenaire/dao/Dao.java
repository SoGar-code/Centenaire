package org.centenaire.dao;

import java.sql.Connection;
import java.util.LinkedList;

/**
 * Abstract class including the different possible actions on POJOs.
 */
public abstract class Dao<T>{
	protected static Connection conn;

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

}
