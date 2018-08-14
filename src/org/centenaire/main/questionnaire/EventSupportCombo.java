/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.Dimension;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;

import org.centenaire.dao.RelationDao;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Individual;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GeneralController;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Generic combo for entities
 * 
 * <p>Provided with an implementation of the Subscriber interface
 * for the Publisher-Subscriber pattern, but no registration!</p>
 * 
 * @see org.centenaire.util.pubsub.Subscriber
 *
 */
public class EventSupportCombo extends EntityCombo<Event> implements Subscriber{

	/**
	 * Constructor for the EventSupportCombo class.
	 * 
	 * <p>The class comes with subscriptions to the Pub-Sub pattern 
	 * on the following channels:
	 * <ul>
	 * 		<li>change of currentIndividual</li>
	 * 		<li>change on the ORG relation</li>
	 * 		<li>change on the 'Event' entity</li>
	 * </ul>
	 * It follows that the combo is updated whenever 
	 * an overall save is called (since even in the absence
	 * of change, all relations are fully updated every time!).
	 * </p>
	 * 
	 * @param classIndex
	 * 				classIndex of the Entity under consideration.
	 */
	public EventSupportCombo(){
		super(EntityEnum.EVENTS.getValue());
		
		// Update subscriber (i.e. get current list of Events)
		updateSubscriber(0);
		
		GeneralController gc = GeneralController.getInstance();
		
		// Subscribe to update of currentIndividual
		gc.getChannel(0).addSubscriber(this);
		
		// Subscribe to update of 'Event'
		gc.getChannel(EntityEnum.EVENTS.getValue()).addSubscriber(this);
		
		// Subscribe to update of 'Organizer'
		gc.getChannel(EntityEnum.ORG.getValue()).addSubscriber(this);
		
		this.setPreferredSize(new Dimension(200,30));
	}
	
	/**
	 * Default implementation of the Subscriber interface for this class.
	 * 
	 * <p>In details, this fetches the new list of Entity elements
	 * and try to keep the same selection in the comboBox.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 */
	@Override
	public void updateSubscriber(int channelIndex) {
		
		// Only update when suitable channels called
		if ((channelIndex == EntityEnum.ORG.getValue()) 
				|| (channelIndex == 0) 
				|| (channelIndex == EntityEnum.EVENTS.getValue())) {
			
			// Recover the current selected Entity
			Event currentEntity = this.getSelectedEntity();
			
			// recover the GeneralController
			GeneralController gc = GeneralController.getInstance();
			
			Individual currentIndividual = gc.getCurrentIndividual();
			
			if (currentIndividual != null) {
			
				RelationDao<Individual, Event> orgDao = (RelationDao<Individual, Event>) gc.getRelationDao(EntityEnum.ORG.getValue());
			
				// list of Entity elements		
				LinkedList<Event> listEntity = (LinkedList<Event>) orgDao.findAll(currentIndividual);
				
				// Create combo to select Entity
				Vector<Event> entityVect = new Vector<Event>(listEntity);
				
				this.removeAllItems();
				this.setModel(new DefaultComboBoxModel<Event>(entityVect));
				
				// try to set the combo back to the same selected Entity
				if (currentEntity != null) {
					this.setSelectedEntity(currentEntity);
				} else {
					// Cancel selection
					this.setSelectedIndex(-1);
				}
			}
		}
	}
}
