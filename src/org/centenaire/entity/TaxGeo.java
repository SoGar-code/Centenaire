/**
 * 
 */
package org.centenaire.entity;

/**
 * Implementation of 'geographical taxonomy' Entity class.
 *
 */
public class TaxGeo extends TagLike {
	/**
	 * @param index
	 * @param name
	 */
	public TaxGeo(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.TAXGEO.getValue();
	}

}
