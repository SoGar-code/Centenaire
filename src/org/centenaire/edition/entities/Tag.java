package org.centenaire.edition.entities;

import org.centenaire.edition.entities.taglike.TagLike;

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
	}

	public Tag(int index, String name) {
		super(index, name);
	}
	
	public static Tag defaultElement() {
		return new Tag("default tag value");
	}
	
}

