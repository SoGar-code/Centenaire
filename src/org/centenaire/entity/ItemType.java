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
		this.classIndex = EntityEnum.ITEMTYPE.getValue();
	}

	/**
	 * @param index
	 * @param name
	 */
	public ItemType(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.ITEMTYPE.getValue();
	}
}
