/**
 * 
 */
package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * 
 *
 */
public class Discipline extends TagLike {
	/**
	 * @param name
	 */
	public Discipline(String name) {
		super(name);
		this.classIndex = EntityEnum.DISCIPLINES.getValue();
	}

	/**
	 * @param index
	 * @param name
	 */
	public Discipline(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.DISCIPLINES.getValue();
	}
}
