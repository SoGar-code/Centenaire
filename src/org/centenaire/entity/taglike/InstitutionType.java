/**
 * 
 */
package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * 
 *
 */
public class InstitutionType extends TagLike {
	
	public InstitutionType(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.INSTITTYPE.getValue();
	}
}
