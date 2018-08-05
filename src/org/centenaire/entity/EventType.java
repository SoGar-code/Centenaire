/**
 * 
 */
package org.centenaire.entity;

/**
 * Implementation of 'EventType' Entity class
 */
public class EventType extends TagLike {
	/**
	 * @param name
	 */
	public EventType(String name) {
		super(name);
		this.classIndex = EntityEnum.EVENTTYPE.getValue();
	}

	/**
	 * @param index
	 * @param name
	 */
	public EventType(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.EVENTTYPE.getValue();
	}

}
