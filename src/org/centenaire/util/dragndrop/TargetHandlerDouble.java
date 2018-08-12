/**
 * 
 */
package org.centenaire.util.dragndrop;

import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.TransferHandler;

import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.Entity;

/**
 * Class to treat the case of labeled relations.
 * 
 * <p>This class relies on the use of 'DoubleEntity', 
 * which inherits from 'Entity' without being a full
 * 'Entity' class (no DAO, for instance).</p>
 * 
 * @param <T> main entity class associated to the transfer handler.
 * @param <U> label associated to the main class.
 *
 * @see org.centenaire.entity.DoubleEntity
 */
public class TargetHandlerDouble<T, U> extends TargetHandler<T> {

	public TargetHandlerDouble(int classIndex) {
		super(classIndex);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Action called on a successful drop, to initiate the transfer to the target component. 
	 * 
	 * <p>Extra behaviour compared to regular 'importData' method: create a DoubleEntity.</p>
	 * 
	 * @see TargetHandler#canImport(TransferSupport)
	 */
	@Override
	public boolean importData(TransferHandler.TransferSupport support) {			
		boolean test = support.isDataFlavorSupported(linkedListFlavor);
		
		if (!test) {
			return false;
		}
		
		// Fetch transferable and its data
		Transferable t = support.getTransferable();
		try {
			// Recover data provided by the drop
			LinkedList<Entity> rawData = (LinkedList<Entity>) t.getTransferData(linkedListFlavor);
			
			// Convert it into a 'DoubleEntity' list
			LinkedList<Entity> data = new LinkedList<Entity>();
			
			for (Entity row: rawData) {
				// As initial objU, use null (otherwise, they are all TagLike, so TagLike.defaultElement would do)
				DoubleEntity<Entity, U> auxDouble = new DoubleEntity<Entity, U>(row, null);
				data.add(auxDouble);
			}
			
			// Using TransferSupport, recover the target component
			// NB: the support component is actually the JTable (inside GTable...)
			JTable table = (JTable) support.getComponent();
			
			// Perform the import properly speaking
			((DropListTableModel) table.getModel()).addAll(data);
			
		} catch (UnsupportedFlavorException e) {
			System.out.println("In TargetHandlerDouble.importData - unsupported flavor type!");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("In TargetHandlerDouble.importData - IO exception!");
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

}
