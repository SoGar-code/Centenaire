/**
 * Package providing support for Drag'n Drop.
 * 
 * <p>This package could also be used to support Copy/Cut and Paste.</p>
 */
package org.centenaire.util.transferHandler;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.TransferHandler;

import org.centenaire.entity.Entity;
import org.centenaire.main.questionnaire.DropListTableModel;
import org.centenaire.util.GeneralController;

/**
 * Custom TransferHandler class for GTable with a ListTableModel.
 * 
 * <p>This TransferHandler should be used by the "target" objects,
 * thus it provides essentially the import functions.
 * </p>
 * 
 * @param <T> entity class associated to the TransferHandler.
 * 
 * @see org.centenaire.util.GTable
 * @see org.centenaire.util.ListTableModel
 *
 */
public class TargetHandler<T> extends TransferHandler {
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
	public TargetHandler(int classIndex) {
		this.classIndex = classIndex;
		
		// Make sure that all share actually the same DataFlavor
		GeneralController gc = GeneralController.getInstance();
		linkedListFlavor = gc.getLinkedListFlavor();
	}
	
	/**
	 * Import only suitable data.
	 * 
	 * <p>This method is called when the user hovers
	 * above the attached component.</p>
	 * 
	 * <p>In this method, the "TransferSupport" corresponds to the 
	 * data from the source component!</p>
	 * 
	 */
	public boolean canImport(TransferHandler.TransferSupport info) {
		// Check that received data have an authorised type.
		if (!info.isDataFlavorSupported(linkedListFlavor)) {
			return false;
		} else {
			// If data flavor is supported, check matching classIndex.
			LinkedList data;
			try {
				// Recover data from the transferable object
				data = (LinkedList) info.getTransferable().getTransferData(linkedListFlavor);
				
				// See if we can cast the provided data into the requested format
				Entity obj = (Entity) data.getFirst();
				
				int objClassIndex = obj.getClassIndex();
				
				boolean test = (this.classIndex == objClassIndex);
				
				return test;

			} catch (ClassCastException e) {
				System.out.print("Could not perform cast for provided data!");
				return false;
					
			} catch (UnsupportedFlavorException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
	}
	
	/**
	 * Method defining the content to be transferred.
	 * 
	 * <p>In our case, no transfer should be allowed from this component, so return empty transferable.</p>
	 * 
	 * <p>This class only applies to GTable and associated ListTableModel!</p>
	 * 
	 */
	protected Transferable createTransferable(JComponent c) {
		return null;
	}
	
	/**
	 * Action called on a successful drop, to initiate the transfer to the target component. 
	 */
	public boolean importData(TransferHandler.TransferSupport support) {
		String msg = "Calling importData!";
		System.out.println(msg);
				
		boolean test = support.isDataFlavorSupported(linkedListFlavor);
		
		if (!test) {
			return false;
		}
		
		String msg1 = "Component recovered!";
		System.out.println(msg1);
		
		// Fetch transferable and its data
		Transferable t = support.getTransferable();
		try {
			LinkedList<Entity> data = (LinkedList<Entity>) t.getTransferData(linkedListFlavor);
			
			// Using TransferSupport, recover the target component
			// NB: the support component is actually the JTable (inside GTable...)
			JTable table = (JTable) support.getComponent();
			
			// Perform the import properly speaking
			((DropListTableModel) table.getModel()).addAll(data);
			
		} catch (UnsupportedFlavorException e) {
			System.out.println("In TargetHandler.importData - unsupported flavor type!");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("In TargetHandler.importData - IO exception!");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
}
