/**
 * 
 */
package org.centenaire.entity.relationeditor;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;

/**
 * 
 *
 */
public class EventRelationEditor extends RelationEditor<Event> {

	public EventRelationEditor(Event entity) {
		super(EntityEnum.EVENTS.getValue(), entity);
	}

	@Override
	public Event getObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean saveRelations() {
		// TODO Auto-generated method stub
		return false;
	}
}
