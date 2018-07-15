/**
 * 
 */
package org.centenaire.edition.entities;

import org.centenaire.edition.entities.taglike.TagLike;

/**
 *
 */
public class ItemTypes extends TagLike {

	/**
	 * @param name
	 */
	public ItemTypes(String name) {
		super(name);
	}

	/**
	 * @param index
	 * @param name
	 */
	public ItemTypes(int index, String name) {
		super(index, name);
	}
	
	public static ItemTypes defaultElement() {
		return new ItemTypes("default item types value");
	}

}
