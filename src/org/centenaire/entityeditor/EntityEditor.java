package org.centenaire.entityeditor;

import javax.swing.JPanel;

import org.centenaire.util.pubsub.Subscriber;

/**
 * Abstract class to represent the "editor forms" of entities.
 * 
 * <p>No link to the database, this is simply a graphical component, 
 * with minimal non-graphical methods.</p>
 *
 * @param <T> entity class associated to the editor.
 */
public abstract class EntityEditor<T> extends JPanel {
	/**
	 * Index of the Entity object currently displayed.
	 * 
	 * <p>Initialized at 0.
	 */
	private int indexField;
	
	/**
	 * Constructor for EntityEditor: should be possible to generate it from a T object.
	 * 
	 * <p>Initialize "indexField" with the value 0. Do not forget to update in implementations!
	 * 
	 * @param obj entity instance of the entity class associated to the editor.
	 */
	public EntityEditor(){
		super();
		setIndexField(0);
	}
	
	/**
	 * Recover the T object associated to the current instance.
	 * 
	 * <p>This is especially useful when using the panel to edit an element.
	 * 
	 * @return the T object associated to the instance.
	 */
	public abstract T getObject();
	
	/**
	 * Change the fields of the editor to represent another T object.
	 */
	public abstract void setObject(T obj);

	public int getIndexField() {
		return indexField;
	}

	public void setIndexField(int indexField) {
		this.indexField = indexField;
	}
	
	/**
	 * Method to reset the EntityEditor to a neutral value.
	 */
	public abstract void reset();

}
