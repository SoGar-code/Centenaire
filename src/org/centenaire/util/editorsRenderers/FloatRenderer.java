package org.centenaire.util.editorsRenderers;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Renderer for floats (in a table cell).
 * 
 * <p>
 * a number of decimals is specified in the constructor.
 */
public class FloatRenderer extends JLabel implements TableCellRenderer {
	protected int nbDecimals;
	
	public FloatRenderer(int nbDecimals){
		super();
		this.nbDecimals = nbDecimals;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean isFocus, int row,
			int column) {
		String text;
		try{
			float updatedVal=(float)table.getModel().getValueAt(row, column);
			
            NumberFormat numberFormatB = NumberFormat.getInstance(Locale.UK);
            numberFormatB.setMaximumFractionDigits(nbDecimals);
            numberFormatB.setMinimumFractionDigits(nbDecimals);
            numberFormatB.setMinimumIntegerDigits(1);
			
			text = numberFormatB.format(updatedVal);
		} catch (Exception e){
			text = "-";
		}
		this.setText(text);
		return this;
	}
}
