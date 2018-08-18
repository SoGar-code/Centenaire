/**
 * 
 */
package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * Implementation of 'thematic taxonomy' Entity class.
 *
 */
public class TaxTheme extends TagLike {
	/**
	 * @param index
	 * @param name
	 */
	public TaxTheme(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.TAXTHEME.getValue();
	}

}
