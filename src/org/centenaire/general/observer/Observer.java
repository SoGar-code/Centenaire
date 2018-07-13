package gestionBilicence.general.observer;

import java.util.LinkedList;

import gestionBilicence.general.Entity;

public interface Observer {
	/*
	 * An interface to implement the observer pattern, observer side
	 */
	
	public void updateObserver(LinkedList<Entity> currentData);
}
