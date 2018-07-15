/**
 * 
 */
package org.centenaire.edition.entities;

import org.centenaire.edition.entities.taglike.TagLike;

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
