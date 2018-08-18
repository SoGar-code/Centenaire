/**
 * 
 */
package org.centenaire.entity.typelike;

import java.util.logging.Logger;

import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;

/**
 * A base class to describe the different types of Items and Events.
 *
 */
public class TypeLike extends Entity {
	protected final static Logger LOGGER = Logger.getLogger(TypeLike.class.getName());
	
	private String name;
	private CatEnum category;
	
	/**
	 * Constructor based on name, index and category
	 * 
	 * @param index
	 * 			index of the specific instance under consideration.
	 * @param name
	 * 			name used to describe the instance at hand.
	 * @param category
	 * 			category in which this specific type falls.
	 */
	public TypeLike(int index, String name, CatEnum category) {
		super(index);
		this.name = name;
		this.category = category;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public CatEnum getCategory() {
		return category;
	}



	public void setCategory(CatEnum category) {
		this.category = category;
	}

	public String toString() {
		return this.name;
	}

	/**
	 * Method used in relation with GTable.
	 * 
	 * @see org.centenaire.entity.Entity#getEntry(int)
	 */
	public Object getEntry(int i) {
		switch(i){
			case 0:
				return name;
			case 1: 
				return category;
			default:
				return "-";
		}
	}

	/**
	 * @see org.centenaire.entity.Entity#setEntry(int, java.lang.Object)
	 */
	@Override
	public void setEntry(int i, Object obj) {
		// TODO Auto-generated method stub

	}
	
	public static TypeLike newElement(int index, String name, CatEnum category, int classIndex) {
		if (classIndex == EntityEnum.ITEMTYPE.getValue()) {
			ItemType tl = new ItemType(index, name, category);
			return tl;
		} else if (classIndex == EntityEnum.EVENTTYPE.getValue()) {
			EventType tl = new EventType(index, name, category);
			return tl;
		} else {
			String msg = String.format("TypeLike.newElement -- Type classIndex '%s' not found!", classIndex);
			LOGGER.warning(msg);
			return null;
		}
	}

}
