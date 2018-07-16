package org.centenaire.general;

/**
 * Provide an Entity class from a <it>classIndex</it>
 * 
 * @see Entity#classIndex
 *
 */
public class EntityClassIndex {

	public static Class getEntityClass(int i) {
		switch(i) {
			case 0:
				return Entity.class;
			case 1:
				return Individual.class;
			case 4:
				return Tag.class;
		}
				
	}
}
