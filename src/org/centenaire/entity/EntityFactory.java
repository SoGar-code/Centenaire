package org.centenaire.entity;

/**
 * Factory class for Entity
 * 
 * <p>Entity classes are labeled by their <it>classIndex</it>.
 * 
 * @see Entity#classIndex
 *
 */
public class EntityFactory {

	public static Class getEntityClass(int i) {
		switch(i) {
			case 0:
				return Entity.class;
			case 1:
				return Individual.class;
			case 4:
				return Tag.class;
			default:
				System.out.println("EntityFactory.getEntityClass -- entity not found!");
				return null;
		}
				
	}
	
	public static Entity getDefaultElement(int i) {
		if (i == EntityEnum.ENTITY.getValue()) {
			String msg = "EntityFactory.getDefaultElement -- classIndex 0 is for "
					+ "the abstract Entity class! So no defaultElement...";
			System.out.println(msg);
			return null;
		} else if (i == EntityEnum.INDIV.getValue()) {
			return Individual.defaultElement();
		} else if (i == EntityEnum.TAG.getValue()) {
			return Tag.defaultElement();
		} else {
			System.out.println("EntityFactory.getDefaultElement -- entity not found!");
			return null;
		}
				
	}
}
