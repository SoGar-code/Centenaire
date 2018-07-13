package org.centenaire.edition.entities;

import org.centenaire.general.Entity;

/**
 * POJO for the different entities which behave like a tag.
 * 
 * <p>
 * These entities are those which only attach a name to another Entity.
 * See Direct Known Subclasses for a list of those entities.
 * <p>
 * 
 * @see Tag#Tag
 * 
 * @author Olivier GABRIEL
 */
public class TagLike extends Entity {
	
	/**
	 * Name of the TagLike element. This is its only property.
	 * 
	 * @see TagLike#getName()
	 * @see TagLike#setName(String)
	 */
	protected String name;

	public TagLike(String name) {
		super();
		this.name = name;
	}

	public TagLike(int index, String name) {
		super(index);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getEntry(int i) {
		switch(i){
			case 0:
				return name;
			default:
				return "-";
		}
	}

	@Override
	public void setEntry(int i, Object obj) {
		switch(i){
			case 0:
				this.name = (String) obj;
				break;
			default:
				System.out.println("Semester.setEntry - got int i="+i);
		}

	}

	public static TagLike defaultElement() {
		return new TagLike("default semester");
	}

	public String toString(){
		return name;
	}
}
