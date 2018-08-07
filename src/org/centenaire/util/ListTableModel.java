package org.centenaire.util;

import java.util.LinkedList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.centenaire.entity.Entity;
import org.centenaire.util.observer.Observer;

/**
 * A superclass for the table models of the project.
 * 
 * <p>
 * Includes methods for:
 * <ul>
 * <li> adding and deleting data (in the table)</li>
 * <li> converting String into Date and Float</li>
 * <li> updating data</li>
 * </ul>
 * 
 */
public class ListTableModel extends AbstractTableModel implements Observer{

	protected LinkedList<Entity> data;

	protected Class[] listClass;
	protected String[] title;
	protected GeneralController gc = GeneralController.getInstance();

	public ListTableModel(Class[] listClass, String[]  title, LinkedList<Entity> data){
		super();
		this.data=data;
		this.listClass=listClass;
		this.title=title;
	}

	// =====================================
	// Getters and setters
	public String getColumnName(int col) {
		return this.title[col];
	}

	public int getColumnCount(){
		return this.title.length;
	}

	public int getRowCount(){
		return this.data.size();
	}

	public Class getColumnClass(int col){
		return listClass[col];
	}

	/**
	 * Default implementation, to be changed depending on the specific table.
	 */
	public boolean isCellEditable(int row, int col){
		return false;
	}

	public Object getValueAt(int row, int column){
		return data.get(row).getEntry(column);
	}

	public void setValueAt(Object value, int row, int col){
		System.out.println("ListTableModel.setValueAt - col="+col+", value="+value.toString());
		data.get(row).setEntry(col, value);
	}

	public LinkedList<Entity> getData(){
		return this.data;
	}

	/**
	 * Update of the table model (and notify listeners). 
	 * 
	 * @param data
	 * 			the new data to include in the table.
	 */
	public void setData(LinkedList<Entity> data) {
		this.data = data;
		this.fireTableDataChanged();
	}
	
	
	/**
	 * Data update, provided by Observer pattern.
	 * 
	 * <p> It corresponds to data flowing from gc to the model.</p>
	 * 
	 * @param currentData
	 * 				the current content of the list.
	 */
	public void updateObserver(LinkedList<Entity> currentData){
		this.setData(currentData);
	}

	public void saveTable(){
		gc.saveTable(data);
	}
	
	/**
	 * Remove an element from the list.
	 * 
	 * <p>This method *does not* delete the element
	 * from the database!</p>
	 * 
	 * <p>This method triggers an update of the 
	 * content of the table.</p>
	 * 
	 * @param row
	 * 		the index of the row to remove.
	 */
	public void removeRow(int row){
		data.remove(row);
		this.fireTableDataChanged();
	}

	public void editRow(int row) {
		// TODO Auto-generated method stub
	}
	
	/**
	 * Return the selected 'row'
	 */
	public Entity getRow(int row) {
		Entity entity = data.get(row);
		return entity;
	}
	
}
