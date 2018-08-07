/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.util.LinkedList;

import javax.swing.DropMode;

import org.centenaire.entity.Entity;
import org.centenaire.util.GTable;
import org.centenaire.util.transferHandler.TargetHandler;

/**
 * Component used for dropping item and updating relations.
 *
 */
public class DropTable<T extends Entity, U extends Entity> extends GTable {

	public DropTable(int classIndexT, int classIndexU, Class[] listClass, String[] title) {
		super(new DropListTableModel<T, U>(listClass, title, classIndexU));
		
		// Support for Drop
		this.getTable().setDragEnabled(true);
		this.getTable().setDropMode(DropMode.INSERT);
		this.getTable().setTransferHandler(new TargetHandler<U>(classIndexU));
	}
	
	/**
	 * Save the content of the table.
	 */
	public void saveContent(T currentEntity) {
		((DropListTableModel) this.getModel()).saveContent(currentEntity);
	}

	/**
	 * Using currentEntity, update the content of the table.
	 * 
	 */
	public void updateEntity(T currentEntity) {
		((DropListTableModel) this.getModel()).updateEntity(currentEntity);
	}
	
	/**
	 * Reset the display of the table
	 */
	public void reset() {
		LinkedList<Entity> listEntity = new LinkedList<Entity>();
		this.getModel().setData(listEntity);
	}
}
