package gestionBilicence.dao;

import java.sql.Connection;
import java.util.LinkedList;

import gestionBilicence.general.Entity;

public abstract class Dao<T>{
	protected static Connection conn;

	abstract public boolean create(T obj);
	
	abstract public boolean update(T obj);
	
	abstract public boolean delete(T obj);
	
	abstract public T find(int index);
	
	// provides a fully initialized element (in terms of index)
	abstract public T newElement();
	
	// returns either an existing element (of the database)
	// or creates a new one.
	abstract public T anyElement();
	
	abstract public LinkedList<T> getData();

}
