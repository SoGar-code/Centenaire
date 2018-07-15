package org.centenaire.general;

import javax.swing.JComponent;

/**
 * Superclass of the POJO used for DAO
 * 
 * <p>A concrete Entity class T should both
 * <ul>
 * <li> inherit from this abstract Entity class</li>
 * <li> implement the interface WithEditor<T> </li>
 * <li> have a <it>static</li> defaultElement method</li>
 * </ul>
 * in order to use the generic EntityDialog.
 * 
 * @see WithEditor
 * 
 */
public abstract class Entity{
	protected int index;
	
	// Initialization of index, 
	// to be set by associated DAO class
	public Entity(){
		index = 0;
	}
	
	/**
	 * Constructor with initialization of index, used by getData.
	 * 
	 * @param index
	 * 			the index of the entity we create.
	 * 
	 */
	public Entity(int index){
		this.index = index;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	
	public int getIndex(){
		return index;
	}
	
	/**
	 * Method to get the different entries of the entity.
	 * 
	 * @param i
	 * 			Index of the required entry.
	 * @return Entry of the object for the prescribed index.
	 * 
	 * @see ListTableModel#ListTableModel
	 */
	public abstract Object getEntry(int i);
	
	public abstract void setEntry(int i, Object obj);
	
	/**
	 * Provide the 'display form' of the Entity
	 * 
	 * <p>This is the form is the one used when a simple display of the entity is needed.</p>
	 * 
	 * @return Edition form of the Entity
	 */
	public abstract JComponent displayForm();
}
