package org.centenaire.general;

/**
 * Superclass of the POJO used for DAO
 * 
 * @author Olivier GABRIEL
 */
public abstract class Entity {
	protected int index;
	
	// Initialization of index, 
	// to be set by associated DAO class
	public Entity(){
		index = 0;
	}
	
	// With ,
	// used by getData
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
	
	// To get the different entries of the entity
	// Used in DaoTableModel
	public abstract Object getEntry(int i);
	
	public abstract void setEntry(int i, Object obj);
}
