package org.centenaire.general.observer;

import java.util.LinkedList;

import org.centenaire.general.Entity;

/**
 * An interface to implement the observer pattern, observer side.
 */
public interface Observer {

	
	public void updateObserver(LinkedList<Entity> currentData);
}
