/**
 * 
 */
package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * Implementation of 'Local support type' Entity class.
 *
 */
public class LocalType extends TagLike {
	/**
	 * @param index
	 * @param name
	 */
	public LocalType(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.LOCALISATIONTYPE.getValue();
	}

}
