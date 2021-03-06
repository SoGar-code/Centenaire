package org.centenaire.entity.taglike;

import org.centenaire.entity.EntityEnum;

/**
 * POJO for the "Tag" entity.
 * 
 * <p>
 * This class is essentially a carrier 
 * for specializing the TagLike entity to the Tag table in the database.
 * <p>
 * 
 * @author Olivier GABRIEL
 */
public class Tag extends TagLike {
	
	public Tag(String name) {
		super(name);
		this.classIndex = EntityEnum.TAG.getValue();
	}

	public Tag(int index, String name) {
		super(index, name);
		this.classIndex = EntityEnum.TAG.getValue();
	}
	
}

