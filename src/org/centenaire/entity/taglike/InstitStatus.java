/**
 * 
 */
package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * Implementation of 'Institutional Status' Entity class.
 *
 */
public class InstitStatus extends TagLike {
	/**
	 * @param index
	 * @param name
	 */
	public InstitStatus(int index, String name) {
		super(index, name);
		classIndex = EntityEnum.INSTITSTATUS.getValue();
	}

}
