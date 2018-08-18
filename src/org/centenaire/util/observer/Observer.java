package org.centenaire.util.observer;

import java.util.List;

import org.centenaire.entity.Entity;

/**
 * An interface to implement the observer pattern, observer side.
 */
public interface Observer {

	
	public void updateObserver(List<Entity> currentData);
}
