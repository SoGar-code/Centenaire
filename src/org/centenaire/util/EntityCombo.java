/**
 * 
 */
package org.centenaire.util;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.centenaire.dao.Dao;
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
public class EntityCombo<T> extends JComboBox<T> implements Subscriber{
	private final int classIndex;
	private Dao<T> dao;
	private ActionListener comboListener;

	/**
	 * Constructor for the EntityCombo class.
	 * 
	 * <p>The class comes without subscription to the Pub-Sub pattern!</p>
	 * 
	 * @param classIndex
	 * 				classIndex of the Entity under consideration.
	 */
	public EntityCombo(int classIndex){
		super();
		this.classIndex = classIndex;
		
		// recover the GeneralController
		GeneralController gc = GeneralController.getInstance();
		
		// recover the suitable Dao
		dao = (Dao<T>) gc.getDao(classIndex);
		
		// list of Entity elements		
		LinkedList<T> listEntity = (LinkedList<T>) dao.findAll();
		
		// Create combo to select Entity
		T[] entityVect = (T[]) listEntity.toArray();
		this.setModel(new DefaultComboBoxModel<T>(entityVect));
		this.setPreferredSize(new Dimension(200,30));
	}
	
	/**
	 * Method to get the current selected entity
	 * 
	 * @return the 'Entity' object currently selected (with its proper class).
	 */
	public T getSelectedEntity() {
		T currentEntity = (T) this.getSelectedItem();
		return currentEntity;
	}
	
	/**
	 * Default implementation of the Subscriber interface for this class.
	 * 
	 * <p>In details, this fetches the new list of Entity elements
	 * and removes any selection in the comboBox.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 */
	@Override
	public void updateSubscriber() {		
		// list of Entity elements		
		LinkedList<T> listEntity = (LinkedList<T>) dao.findAll();
		
		// Create combo to select Entity
		T[] entityVect = (T[]) listEntity.toArray();
		this.removeAllItems();
		this.setModel(new DefaultComboBoxModel<T>(entityVect));
		
		// Cancel selection
		this.setSelectedIndex(-1);
	}
}
