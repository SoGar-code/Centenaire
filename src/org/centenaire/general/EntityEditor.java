package org.centenaire.general;

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
	public EntityEditor(T obj){
		super();
	}
	
	/**
	 * Recover the T object associated to the current instance.
	 * 
	 * @return the T object associated to the instance.
	 */
	public abstract T getObject();
}
