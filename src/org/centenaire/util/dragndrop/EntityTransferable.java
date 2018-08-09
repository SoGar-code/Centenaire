/**
 * 
 */
package org.centenaire.util.dragndrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.LinkedList;

import org.centenaire.util.GeneralController;

/**
 * Custom implementation of Transferable interface.
 * 
 * <p>This implementation is designed to perform well
 * with GTable and ListTableModel.</p>
 * 
 * @see org.centenaire.util.GTable
 * @see org.centenaire.util.ListTableModel
 */
public class EntityTransferable<T> implements Transferable {
	private LinkedList<T> data;
	private DataFlavor linkedListFlavor;
	private int classIndex;
	
	/**
	 * Constructor with initial data and specified 'classIndex'.
	 * 
	 * @param data
	 * @param classIndex
	 * 
	 * @see org.centenaire.entity.Entity
	 */
	public EntityTransferable(LinkedList<T> data, int classIndex) {
		this.classIndex = classIndex;
		this.data = data;
		
		// Make sure that all share actually the same DataFlavor
		GeneralController gc = GeneralController.getInstance();
		linkedListFlavor = gc.getLinkedListFlavor();
	}
	
	/**
	 * Constructor without initial data, but with a specified 'classIndex'.
	 * 
	 * @param data
	 * @param classIndex
	 * 
	 * @see org.centenaire.entity.Entity
	 */
	public EntityTransferable(int classIndex) {
		this.classIndex = classIndex;
		this.data = new LinkedList<T>();
	}
	
	/**
	 * Add another object to data
	 * 
	 */
	public void addEntity(T obj) {
		this.data.add(obj);
	}
	
	/**
	 * Return the class index associated to this Transferable.
	 * 
	 * @return classIndex for this Transferable.
	 */
	public int getClassIndex() {
		return this.classIndex;
	}

	/**
	 * Return the 'content' of this 'carrier object'
	 * 
	 * @see java.awt.datatransfer.Transferable#getTransferData(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public Object getTransferData(DataFlavor arg0) throws UnsupportedFlavorException, IOException {
		return data;
	}

	/**
	 * Define the DataFlavors of data that can be introduced.
	 * 
	 * <p>Only one DataFlavor is supported: that of LinkedList.</p>
	 * 
	 * @see java.awt.datatransfer.Transferable#getTransferDataFlavors()
	 */
	@Override
	public DataFlavor[] getTransferDataFlavors() {
		DataFlavor[] dataFlavorList = {linkedListFlavor};
		return dataFlavorList;
	}

	/**
	 * Check if the specified data flavor is supported.
	 * 
	 * <p>True only when the DataFlavor is the prescribed one!</p>
	 * 
	 * @see java.awt.datatransfer.Transferable#isDataFlavorSupported(java.awt.datatransfer.DataFlavor)
	 */
	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(linkedListFlavor);
	}
}
