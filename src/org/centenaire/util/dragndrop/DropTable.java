/**
 * 
 */
package org.centenaire.util.dragndrop;

import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.DropMode;
import javax.swing.JTable;

import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.InstitStatus;
import org.centenaire.entity.LocalType;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GTable;
import org.centenaire.util.editorsRenderers.ButtonRenderer;

/**
 * Component used for dropping item and updating relations.
 * 
 * <p>It incorporates the functions related to dropping items, 
 * as well as method to save, update and reset its content.</p>
 *
 */
public class DropTable<T extends Entity, U extends Entity> extends GTable {
	
	/**
	 * 
	 * @param classIndexT
	 * @param classIndexU
	 * 					class index of the class being displayed
	 * @param classIndexRelation
	 * @param listClass
	 * @param title
	 * @param deleteColumn
	 * 				defines which column contains the 'delete' button (it should be possible to edit it!).
	 */
	public DropTable(
			int classIndexT, 
			int classIndexU, 
			int classIndexRelation, 
			Class[] listClass, 
			String[] title
			) {
		super(new DropListTableModel<T, U>(listClass, title, classIndexU, classIndexRelation));
		
		// Support for Drop
		this.getTable().setDragEnabled(true);
		this.getTable().setDropMode(DropMode.INSERT);
		this.getTable().setTransferHandler(new TargetHandler<U>(classIndexU));
		
		// Include editors 
		// ================
		JTable table = this.getTable();
		
		// == InstitStatus ==
		// Create EntityCombo and subscribe to suitable channel
		EntityCombo<InstitStatus> institStatusEditor = new EntityCombo<InstitStatus>(EntityEnum.INSTITSTATUS.getValue());
		gc.getChannel(EntityEnum.INSTITSTATUS.getValue()).addSubscriber(institStatusEditor);
		
		// Set Editor for InstitStatus
	    table.setDefaultEditor(InstitStatus.class, new DefaultCellEditor(institStatusEditor));
	    table.setDefaultRenderer(InstitStatus.class, new ButtonRenderer());
	    
		// == LocalType ==
		// Create EntityCombo and subscribe to suitable channel
		EntityCombo<LocalType> localTypeEditor = new EntityCombo<LocalType>(EntityEnum.LOCALISATIONTYPE.getValue());
		gc.getChannel(EntityEnum.LOCALISATIONTYPE.getValue()).addSubscriber(localTypeEditor);
		
		// Set Editor for LocalType
	    table.setDefaultEditor(LocalType.class, new DefaultCellEditor(localTypeEditor));
	    table.setDefaultRenderer(LocalType.class, new ButtonRenderer());
	}
	
	/**
	 * Save the content of the table.
	 */
	public void saveContent(T currentEntity) {
		DropListTableModel<T, U> model = (DropListTableModel<T, U>) this.getModel();
		model.saveContent(currentEntity);
	}

	/**
	 * Using currentEntity, update the content of the table.
	 * 
	 */
	public void updateEntity(T currentEntity) {
		DropListTableModel<T, U> tableModel = (DropListTableModel<T, U>) this.getModel();
		
		tableModel.updateEntity(currentEntity);
	}
	
	/**
	 * Reset the display of the table
	 */
	public void reset() {
		LinkedList<Entity> listEntity = new LinkedList<Entity>();
		this.getModel().setData(listEntity);
	}
	
}
