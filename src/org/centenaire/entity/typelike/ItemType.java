/**
 * 
 */
package org.centenaire.entity.typelike;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.TagLike;

/**
 *
 */
public class ItemType extends TypeLike {
	
	/**
	 * Constructor based on name, index and category
	 * 
	 * @param index
	 * 			index of the specific instance under consideration.
	 * @param name
	 * 			name used to describe the instance at hand.
	 * @param category
	 * 			category in which this specific type falls.
	 */
	public ItemType(int index, String name, CatEnum category) {
		super(index, name, category);
		this.classIndex = EntityEnum.ITEMTYPE.getValue();
	}

}
