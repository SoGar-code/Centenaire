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
}
