/**
 * 
 */
package org.centenaire.entity;

/**
 * An enumeration to keep track of the different 'Entity' classes in the system.
 * 
 * <p>With this enumeration, it is not necessary to remember the <it>classIndex</it>
 * of each 'Entity' class.</p>
 *
 */
public enum EntityEnum {
	ENTITY(0),
	INDIV(1),
	ITEM(2),
	EVENTS(3),
	INSTIT(4),
	ITEMTYPE(5),
	EVENTTYPE(6),
	INSTITTYPE(7),
	TAG(8),
	DISCIPLINES(9),
	INSTITSTATUS(10),
	LOCALISATIONTYPE(11);
	
	private final int classIndex;
	
	EntityEnum(int classIndex){
		this.classIndex = classIndex;
	}
	
	public int getValue() {
		return classIndex;
	}
}
