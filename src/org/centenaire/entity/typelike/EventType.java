/**
 * 
 */
package org.centenaire.entity.typelike;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.taglike.TagLike;

/**
 * Implementation of 'EventType' Entity class
 */
public class EventType extends TypeLike {

	/**
	 * @param index
	 * @param name
	 */
	public EventType(int index, String name, CatEnum category) {
		super(index, name, category);
		this.classIndex = EntityEnum.EVENTTYPE.getValue();
	}

}
