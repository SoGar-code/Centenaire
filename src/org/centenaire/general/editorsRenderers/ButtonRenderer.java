package org.centenaire.general.editorsRenderers;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ButtonRenderer extends JButton implements TableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row, int col) {
		//Write in the button with the value of the cell
		setText((value != null) ? value.toString() : "");
		return this;
	}
}
