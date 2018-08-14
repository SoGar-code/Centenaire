package org.centenaire.util;

import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.centenaire.entity.Individual;
import org.centenaire.entity.Tag;
import org.centenaire.entity.TagLike;
import org.centenaire.util.editorsRenderers.ButtonDeleteEditor;
import org.centenaire.util.editorsRenderers.ButtonEditEditor;
import org.centenaire.util.editorsRenderers.ButtonRenderer;
import org.centenaire.util.editorsRenderers.Delete;
import org.centenaire.util.editorsRenderers.Edit;
import org.centenaire.util.editorsRenderers.FloatEditor;
import org.centenaire.util.editorsRenderers.FloatRenderer;

/**
 * A table adapted to our needs!
 * 
 * <p> Added functionalities include:
 * <ul>
 * <li> scrolling </li>
 * <li> a copy of the general controller </li>
 * <li> a cast on the TableModel we use, </li>
 * <li> suitable editors and renderers.</li>
 * </ul>
 * <p>
 *  
 * NB: the delete function is implemented in ButtonDeleteEditor.
 */
public class GTable extends JScrollPane{
	protected GeneralController gc = GeneralController.getInstance();
	private JTable table;

	public GTable(ListTableModel model) {
		super();
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//		table = new JTable(){
//            public void changeSelection(final int row, final int column, boolean toggle, boolean extend)
//            {
//                super.changeSelection(row, column, toggle, extend);
//                table.editCellAt(row, column);
//                table.transferFocus();
//            }
//		};
		table = new JTable();
		table.setModel(model);
		
		// Avoid Dropping issues with empty tables (cf. Java tutorial "Empty Table Drop")
		table.setFillsViewportHeight(true);
		
		table.setRowHeight(30);
	    //==================================
	    // Custom editors and renderers
	    //==================================
		
		// delete CellEditor
	    table.setDefaultEditor(Delete.class, new ButtonDeleteEditor(new JCheckBox()));
	    table.setDefaultRenderer(Delete.class, new ButtonRenderer());
	    
	    // edit CellEditor
	    table.setDefaultEditor(Edit.class, new ButtonEditEditor(new JCheckBox()));
	    table.setDefaultRenderer(Edit.class, new ButtonRenderer());

	    // float CellEditor and renderers
	    table.setDefaultEditor(float.class, new FloatEditor(2)); // for two decimals
	    table.setDefaultRenderer(float.class, new FloatRenderer(2)); // for two decimals

	    this.setViewportView(table);
	}
	
	public ListTableModel getModel(){
		// since the only constructor involves a ListTableModel, 
		// the cast below should be safe...
		return (ListTableModel) this.table.getModel();
	}
	
	/**
	 * Extract the attribute *Table* of this component.
	 * 
	 * @return table, the JTable in this component.
	 */
	public JTable getTable() {
		return this.table;
	}
	
}
