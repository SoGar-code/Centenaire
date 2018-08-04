/**
 * 
 */
package org.centenaire.util;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
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
	 * Method to set the current selected entity.
	 * 
	 * <p>This is a sort of reimplementation of the default method
	 * "setSelectedItem" of JComboBox. Here, we compare entities using
	 * their index. Just like in the original method, if no element is 
	 * found, the combo is left without element selected.</p>
	 * 
	 */
	public void setSelectedEntity(T entity) {
		boolean foundEntity = false;
		DefaultComboBoxModel<T> model = (DefaultComboBoxModel<T>) this.getModel();
		
		int entityIndex = ((Entity) entity).getIndex();
		int listSize = model.getSize();
		int listIndex = 0;
		
		do {
			// Consider an object in the list
			T currentEntity = model.getElementAt(listIndex);
			
			int currentIndex = ((Entity) currentEntity).getIndex();
			
			// If the current element and the requested element have the same (entity) index...
			if (entityIndex == currentIndex) {
				// then we have found what we were looking for!
				this.setSelectedIndex(listIndex);
				foundEntity = true;
				System.out.println("EntityCombo.setSelectedEntity -- found something!");
				break;
			}
			listIndex++;
		} while (!foundEntity && (listIndex < listSize));

		// In case nothing was found...
		if (!foundEntity) {
			this.setSelectedIndex(-1);
		}
			
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
