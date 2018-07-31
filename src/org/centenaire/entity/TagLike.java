package org.centenaire.entity;

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
public class TagLike extends Entity{
	
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
				System.out.println("TagLike.setEntry - got int i="+i);
		}

	}

	public static TagLike defaultElement() {
		return new TagLike("default taglike value");
	}

	public String toString(){
		return name;
	}
	
	public static TagLike newElement(int index, String name, int classIndex) {
		if (classIndex == EntityEnum.ITEMTYPE.getValue()) {
			ItemType tl = new ItemType(index, name);
			return tl;
		} else if (classIndex == EntityEnum.EVENTTYPE.getValue()) {
			EventType tl = new EventType(index, name);
			return tl;
		} else if (classIndex == EntityEnum.INSTITTYPE.getValue()) {
			InstitutionType tl = new InstitutionType(index, name);
			return tl;
		} else if (classIndex == EntityEnum.TAG.getValue()) {
			Tag tag = new Tag(index, name);
			return tag;
		} else if (classIndex == EntityEnum.DISCIPLINES.getValue()) {
			Discipline tl = new Discipline(index, name);
			return tl;
		} else if (classIndex == EntityEnum.INSTITSTATUS.getValue()) {
			InstitStatus tl = new InstitStatus(index, name);
			return tl;
		} else if (classIndex == EntityEnum.LOCALISATIONTYPE.getValue()) {
			LocalType tl = new LocalType(index, name);
			return tl;
		} else {
			System.out.println("TagLike.newElement -- TagLike classIndex not found!");
			return null;
		}
	}

}
