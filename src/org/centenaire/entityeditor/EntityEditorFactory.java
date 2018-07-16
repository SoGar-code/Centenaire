package org.centenaire.entityeditor;

import org.centenaire.entity.EntityFactory;

/**
 * Factory class for EntityEditor
 * 
 * <p>Entity classes are labeled by their <it>classIndex</it>.
 * In the methods below, the goal is to assign an EntityEditor
 * to each Entity class... Several Entity classes could be covered
 * by a single EntityEditor!
 * 
 * @see Entity#classIndex
 *
 */
public class EntityEditorFactory {

	/**
	 * Given classIndex, return the suitable EntityEditor
	 * 
	 * @param classIndex
	 * 				parameter used to label Entity classes.
	 * @return the class of the associated EntityEditor.
	 */
	public static Class getEntityEditorClass(int classIndex) {
		switch(classIndex) {
			case 0:
				return EntityEditor.class;
			case 1:
				return IndividualEditor.class;
			case 4:
				return TagLikeEditor.class;
			default:
				System.out.println("EntityEditorFactory.getEntityClass -- entity not found!");
				return null;
		}
				
	}
	
	public static EntityEditor getEntityEditor(int classIndex) {
		switch(classIndex) {
			case 0:
				String msg = "EntityEditorFactory.getEntityEditor -- classIndex 0 is for "
						+ "the abstract Entity class! So no defaultElement...";
				System.out.println(msg);
				return null;
			case 1:
				return new IndividualEditor();
			case 4:
				return new TagLikeEditor();
			default:
				System.out.println("EntityEditorFactory.getEntityEditor -- entity not found!");
				return null;
		}
				
	}
}
