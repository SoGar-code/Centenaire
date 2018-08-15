package org.centenaire.entity.relationeditor;

import java.util.logging.Logger;

import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Item;
import org.centenaire.main.questionnaire.QuestionFinancialSupport;

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
public class RelationEditorFactory {
	protected final static Logger LOGGER = Logger.getLogger(RelationEditorFactory.class.getName());
	
	/**
	 * Given classIndex, return the suitable RelationEditor
	 * 
	 * @param classIndex
	 * 				parameter used to label Entity classes.
	 * @return the class of the associated EntityEditor.
	 */
	public static RelationEditor getRelationEditor(int classIndex, Entity entity) {
		if (classIndex == EntityEnum.ENTITY.getValue()) {
			String msg = "EntityEditorFactory.getEntityEditor -- classIndex 0 is for "
					+ "the abstract Entity class! So no defaultElement...";
			LOGGER.info(msg);
			return null;
		} else if (classIndex == EntityEnum.ITEM.getValue()) {
			return new ItemRelationEditor((Item) entity);
		} else if (classIndex == EntityEnum.EVENTS.getValue()) {
			return new EventRelationEditor((Event) entity);
		} else {
			String msg = String.format("EntityEditorFactory.getEntityEditor -- classIndex '%s' not found!", classIndex);
			LOGGER.warning(msg);
			return null;
		}
				
	}
}
