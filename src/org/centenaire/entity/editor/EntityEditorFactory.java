package org.centenaire.entity.editor;

import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;

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
		if (classIndex == EntityEnum.ENTITY.getValue()) {
			return EntityEditor.class;
		} else if (classIndex == EntityEnum.INDIV.getValue()) {
			return IndividualEditor.class;
		} else if (classIndex == EntityEnum.TAG.getValue()) {
			return TagLikeEditor.class;
		} else {
			System.out.println("EntityEditorFactory.getEntityClass -- entity not found!");
			return null;
		}
				
	}
	
	public static EntityEditor getEntityEditor(int classIndex) {
		if (classIndex == EntityEnum.ENTITY.getValue()) {
			String msg = "EntityEditorFactory.getEntityEditor -- classIndex 0 is for "
					+ "the abstract Entity class! So no defaultElement...";
			System.out.println(msg);
			return null;
		} else if (classIndex == EntityEnum.INDIV.getValue()) {
			return new IndividualEditor();
		} else if (classIndex == EntityEnum.ITEM.getValue()) {
			return new ItemEditor();
		} else if (classIndex == EntityEnum.EVENTS.getValue()) {
			return new EventEditor();
		} else if (classIndex == EntityEnum.INSTIT.getValue()) {
			return new InstitutionEditor();
		} else if (classIndex == EntityEnum.ITEMTYPE.getValue()) {
			return new TypeEditor();
		} else if (classIndex == EntityEnum.EVENTTYPE.getValue()) {
			return new TypeEditor();
		} else if (classIndex == EntityEnum.INSTITTYPE.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.TAG.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.DISCIPLINES.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.INSTITSTATUS.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.LOCALISATIONTYPE.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.TAXCHRONO.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.TAXGEO.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.TAXTHEME.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.DEPT.getValue()) {
			return new TagLikeEditor();
		} else if (classIndex == EntityEnum.COUNTRY.getValue()) {
			return new TagLikeEditor();
		} else {
			String msg = String.format("EntityEditorFactory.getEntityEditor -- entity '%s' not found!", classIndex);
			System.out.println(msg);
			return null;
		}
				
	}
}
