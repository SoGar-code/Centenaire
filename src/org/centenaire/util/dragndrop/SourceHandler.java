/**
 * Package providing support for Drag'n Drop.
 * 
 * <p>This package could also be used to support Copy/Cut and Paste.</p>
 */
package org.centenaire.util.dragndrop;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import org.centenaire.util.GeneralController;
import org.centenaire.util.ListTableModel;

/**
 * Custom TransferHandler class for GTable with a ListTableModel.
 * 
 * <p>This TransferHandler should be used by the "source" objects,
 * thus it provides essentially the export functions.
 * </p>
 * 
 * @param <T> entity class associated to the TransferHandler.
 * 
 * @see org.centenaire.util.GTable
 * @see org.centenaire.util.ListTableModel
 *
 */
public class SourceHandler<T> extends TransferHandler {
	/**
	 * Index of the different (concrete) Entity classes.
	 * 
	 * <p>This index should be coherent with the parameter T
	 * used when creating the class.</p>
	 * 
	 */
	private int classIndex;
	private DataFlavor linkedListFlavor;
	
	/**
	 * Constructor taking only the class index as input.
	 * 
	 * @param classIndex
	 */
	public SourceHandler(int classIndex) {
		this.classIndex = classIndex;
		
		// Make sure that all share actually the same DataFlavor
		GeneralController gc = GeneralController.getInstance();
		linkedListFlavor = gc.getLinkedListFlavor();
	}
	
	/**
	 * No support for imports.
	 */
	public boolean canImport(TransferHandler.TransferSupport info) {
		return false;
	}
	
	/**
	 * Method defining the content to be transferred
	 * 
	 * <p>This class only applies to GTable and associated ListTableModel!</p>
	 * 
	 */
	protected Transferable createTransferable(JComponent c) {
		return new EntityTransferable<T>(exportList(c), classIndex);
	}
	
    /**
     * Bundle up the selected items in the list
     * as a single string, for export.
     * 
     * @param c
     * 			the 'source' component.
     * @return data to export, as a String.
     */
    protected LinkedList<T> exportList(JComponent c) {
    	// NB: the source component is a JTable, even within a GTable!
		JTable table = (JTable) c;
		int[] selection = table.getSelectedRows();
		ListTableModel model = (ListTableModel) table.getModel();
		
		// Create empty LinkedList
		LinkedList<T> data = new LinkedList<T>();
		
		// Fill this list with suitable rows
		for (int i = 0; i < selection.length; i++) {
			T obj = (T) model.getRow(selection[i]);
			data.add(obj);
		}
		
		return data;
    }
	
    /**
     * Configuration of the source 
     * 
     * @param c
     * 			source component.
     * 
     * return
     * 		integer representing the actions supported by the source component.
     */
    public int getSourceActions(JComponent c) {
        return TransferHandler.COPY;
    }
}
