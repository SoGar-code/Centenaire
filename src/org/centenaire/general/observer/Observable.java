package org.centenaire.general.observer;

import java.util.LinkedList;

import org.centenaire.general.Entity;

/**
 * An interface to implement the observer pattern, observable side
 */
public interface Observable{

	public void addObserver(Observer obs);
	
	public void updateObservable(LinkedList<Entity> currentData);
	
	// the method deleteObserver is not needed in our case
}
