/**
 * 
 */
package org.centenaire.general.entities;

import org.centenaire.general.entities.taglike.TagLike;

/**
 * @author Olivier GABRIEL
 *
 */
public class EventType extends TagLike {

	/**
	 * @param name
	 */
	public EventType(String name) {
		super(name);
	}

	/**
	 * @param index
	 * @param name
	 */
	public EventType(int index, String name) {
		super(index, name);
	}

}
