package gestionBilicence.general;

public abstract class Entity {
	protected int index;

	/*
	 * Superclass for the POJO used for DAO
	 * 
	 */
	
	// Initialization of index, 
	// to be set by associated DAO class
	public Entity(){
		index = 0;
	}
	
	// With initialization of index,
	// used by getData
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
