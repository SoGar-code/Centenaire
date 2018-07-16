package org.centenaire.entityeditor;

import javax.swing.JPanel;

/**
 * Abstract class to represent the "editor forms" of entities.
 *
 * @param <T> entity class associated to the editor.
 */
public abstract class EntityEditor<T> extends JPanel {
	
	/**
	 * Constructor for EntityEditor: should be possible to generate it from a T object.
	 * 
	 * @param obj entity instance of the entity class associated to the editor.
	 */
	public EntityEditor(){
		super();
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
}
