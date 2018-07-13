package org.centenaire.general.observer;

import java.util.LinkedList;

import org.centenaire.general.Entity;

public interface Observer {
	/*
	 * An interface to implement the observer pattern, observer side
	 */
	
	public void updateObserver(LinkedList<Entity> currentData);
}
