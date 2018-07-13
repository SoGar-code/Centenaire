package org.centenaire.general.editorsRenderers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import org.centenaire.general.GeneralController;
import org.centenaire.general.ListTableModel;

public class ButtonEditEditor extends DefaultCellEditor {
	/*
	 * An editor for Delete.class columns.
	 * Comes with a listener to delete the associated row
	 * 
	 * The listener depends on ListTableModel
	 */
	    
	 protected JButton button;
	 private ButtonListener bListener = new ButtonListener();
	 private JTable table;
	  
	 public ButtonEditEditor(JCheckBox checkBox) {
	    //By default, this type of object uses a JCheckBox
	    super(checkBox);
	    
	    // Recreate the button
	    button = new JButton();
	    button.setOpaque(true);
	    
	    //Add a listener
	    button.addActionListener(bListener);
	 }
	
	 public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
		 bListener.setRow(row);
		 bListener.setColumn(column);
		 bListener.setTable(table);
		 /*
	    //On passe aussi en paramètre le tableau pour des actions potentielles
	    bListener.setTable(table);
		  */
		 button.setText( (value ==null) ? "" : value.toString() );
		 return button;
	 }
	  
	 public class ButtonListener implements ActionListener{
		private int row, column;
		private JTable table;
		private JButton button;
		
		public void setColumn(int col){this.column=col;}
		public void setRow(int row){this.row=row;}
		public void setTable(JTable table){this.table=table;}
		
		
		public JButton getButton(){return this.button;}
		
		public void actionPerformed(ActionEvent event){
			((ListTableModel)table.getModel()).editRow(row);
		}
	 }
}
