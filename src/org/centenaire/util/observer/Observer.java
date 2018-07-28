package org.centenaire.util.observer;

import java.util.LinkedList;

import org.centenaire.entity.Entity;

/**
 * An interface to implement the observer pattern, observer side.
 */
public interface Observer {

	
	public void updateObserver(LinkedList<Entity> currentData);
}
