/**
 * 
 */
package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * Implementation of 'Département' Entity class.
 *
 */
public class Departement extends TagLike {
	/**
	 * @param index
	 * @param name
	 */
	public Departement(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.DEPT.getValue();
	}

}
