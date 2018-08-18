/**
 * 
 */
package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * Implementation of 'Country' Entity class.
 *
 */
public class Country extends TagLike {
	/**
	 * @param index
	 * @param name
	 */
	public Country(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.COUNTRY.getValue();
	}

}
