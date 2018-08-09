/**
 * 
 */
package org.centenaire.entity;

/**
 * Implementation of 'chronological taxonomy' Entity class.
 *
 */
public class TaxChrono extends TagLike {
	/**
	 * @param index
	 * @param name
	 */
	public TaxChrono(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.TAXCHRONO.getValue();
	}

}
