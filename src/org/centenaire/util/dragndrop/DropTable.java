/**
 * 
 */
package org.centenaire.util.dragndrop;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.DefaultCellEditor;
import javax.swing.DropMode;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;

import org.centenaire.dao.RelationDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.taglike.InstitStatus;
import org.centenaire.entity.taglike.LocalType;
import org.centenaire.entity.util.EntityCombo;
import org.centenaire.entity.util.GTable;
import org.centenaire.util.editorsRenderers.ButtonRenderer;
import org.centenaire.util.editorsRenderers.Delete;

/**
 * Component used for dropping item and updating relations.
 * 
 * <p>It incorporates the functions related to dropping items, 
 * as well as method to save, update and reset its content.</p>
 *
 */
public class DropTable<T extends Entity, U extends Entity> extends GTable {
	private Map<Class, Integer> colMap;
	
	/**
	 * 
	 * @param classIndexT
	 * 					class of the entity used as reference for selecting rows (as in 'findAll(Entity)').
	 * @param classIndexU
	 * 					class index of the class being displayed
	 * @param classIndexRelation
	 * @param listClass
	 * @param title
	 * @param deleteColumn
	 * 				defines which column contains the 'delete' button (it should be possible to edit it!).
	 * 
	 * @see org.centenaire.dao.RelationDao#findAll(Entity)
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
		
		// set editors and renderers
		this.setEditorsAndRenderers();
	    
	    // Set column width
	    // =================
	    
	    // Define colMap
	    colMap = new HashMap<Class, Integer>();
	    colMap.put(Delete.class, 75);
	    
	    // set column width accordingly
	    this.setColumnsWidth(listClass);
	}
	
	/**
	 * 
	 * @param classIndexT
	 * 					class of the entity used as reference for selecting rows (as in 'findAll(Entity)').
	 * @param classIndexU
	 * 					class index of the class being displayed
	 * @param classIndexRelation
	 * @param listClass
	 * @param title
	 * @param deleteColumn
	 * 				defines which column contains the 'delete' button (it should be possible to edit it!).
	 * 
	 * @see org.centenaire.dao.RelationDao#findAll(Entity)
	 */
	public DropTable(
			int classIndexU, 
			Class[] listClass, 
			String[] title,
			RelationDao<T,U> relationDao
			) {
		super(new DropListTableModel<T, U>(listClass, title, relationDao));
		
		// Support for Drop
		this.getTable().setDragEnabled(true);
		this.getTable().setDropMode(DropMode.INSERT);
		this.getTable().setTransferHandler(new TargetHandler<U>(classIndexU));
		
		// set editors and renderers
		this.setEditorsAndRenderers();
	    
	    // Set column width
	    // =================
	    
	    // Define colMap
	    colMap = new HashMap<Class, Integer>();
	    colMap.put(Delete.class, 75);
	    
	    // set column width accordingly
	    this.setColumnsWidth(listClass);
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
	
	/**
	 * Set the widths of columns in the table.
	 * 
	 * <p>This method sets column width based on
	 * the types specified in the constructor.</p>
	 */
	public void setColumnsWidth(Class[] listClass) {
		
		
		TableColumnModel tCM = this.getTable().getColumnModel();
		
		int n = listClass.length;
		for (int index = 0; index < n; index++) {
			Class colClass = listClass[index];

			// If the key is among the specified classes,
			// set an upper bound on width
			if (colMap.containsKey(colClass)) {
				int colWidth = colMap.get(colClass);
				tCM.getColumn(index).setMaxWidth(colWidth);
			}
		}
	}

	public Map<Class, Integer> getColMap() {
		return colMap;
	}

	public void setColMap(Map<Class, Integer> colMap) {
		this.colMap = colMap;
	}
	
	/**
	 * Method to create suitable editors and renderers.
	 * 
	 */
	public void setEditorsAndRenderers() {
		JTable table = this.getTable();
		
		// InstitStatus
		// ==============
		
		// Create EntityCombo and subscribe to suitable channel
		EntityCombo<InstitStatus> institStatusEditor = new EntityCombo<InstitStatus>(EntityEnum.INSTITSTATUS.getValue());
		gc.getChannel(EntityEnum.INSTITSTATUS.getValue()).addSubscriber(institStatusEditor);
		
		// Set Editor for InstitStatus
	    table.setDefaultEditor(InstitStatus.class, new DefaultCellEditor(institStatusEditor));
	    table.setDefaultRenderer(InstitStatus.class, new ButtonRenderer());
	    
	    
		// LocalType
	    // ===========
	    
		// Create EntityCombo and subscribe to suitable channel
		EntityCombo<LocalType> localTypeEditor = new EntityCombo<LocalType>(EntityEnum.LOCALISATIONTYPE.getValue());
		gc.getChannel(EntityEnum.LOCALISATIONTYPE.getValue()).addSubscriber(localTypeEditor);
		
		// Set Editor for LocalType
	    table.setDefaultEditor(LocalType.class, new DefaultCellEditor(localTypeEditor));
	    table.setDefaultRenderer(LocalType.class, new ButtonRenderer());
	}
	
}
