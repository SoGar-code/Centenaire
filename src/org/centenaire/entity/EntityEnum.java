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
	TAG(4);
	
	private int classIndex;
	
	EntityEnum(int classIndex){
		this.classIndex = classIndex;
	}
	
	public int getValue() {
		return classIndex;
	}
}
