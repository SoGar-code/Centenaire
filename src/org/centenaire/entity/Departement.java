/**
 * 
 */
package org.centenaire.entity;

/**
 * Implementation of 'D�partement' Entity class.
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
