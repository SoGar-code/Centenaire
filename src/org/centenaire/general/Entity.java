package org.centenaire.general;

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
 * <p>For more convenience, these concrete classes should 
 * also provide a tractable toString method, used as the 
 * standard "display form" of the entity.
 * 
 * <p>This abstract class also provides support for the <it>classIndex</it>
 * variable, used to get the correct DAO object.
 * 
 * 
 * @see WithEditor
 * @see org.centenaire.dao.abstractDao.AbstractDaoFactory#getDao(int)
 * 
 */
public abstract class Entity{
	/**
	 * Numbering of the different (concrete) Entity classes.
	 * 
	 * <p>Value 0 is for the abstract Entity class.
	 */
	protected static int classIndex = 0;
	
	
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
	 * To get the classIndex for this class.
	 * 
	 * No setters for this static variable!
	 * 
	 * @return classIndex, an integer.
	 * 
	 * @see Entity
	 */
	public static int getClassIndex() {
		return classIndex;
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
	
}
