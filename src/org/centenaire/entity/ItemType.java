/**
 * 
 */
package org.centenaire.entity;

/**
 *
 */
public class ItemType extends TagLike {

	/**
	 * Constructor of ItemType object.
	 * 
	 * @param name
	 */
	public ItemType(String name) {
		super(name);
	}

	/**
	 * @param index
	 * @param name
	 */
	public ItemType(int index, String name) {
		super(index, name);
	}
	
	public static ItemType defaultElement() {
		return new ItemType("default item types value");
	}

}
