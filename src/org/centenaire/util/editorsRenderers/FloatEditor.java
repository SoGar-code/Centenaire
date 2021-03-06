package org.centenaire.util.editorsRenderers;

import java.awt.Component;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.DefaultCellEditor;
import javax.swing.JFormattedTextField;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.text.NumberFormatter;

/**
 * Editor for floats (in a table cell).
 * 
 * <p>
 * a number of decimals is specified in the constructor.
 */
public class FloatEditor extends DefaultCellEditor {
	protected int nbDecimals;
	
	public FloatEditor(int nbDecimals){
		super(new JFormattedTextField());
		this.nbDecimals = nbDecimals;
	}
	
	 public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column){
	        JFormattedTextField editor = (JFormattedTextField) super.getTableCellEditorComponent(table, value, isSelected, row, column);

	        if (value instanceof Number){

	            NumberFormat numberFormatB = NumberFormat.getInstance(Locale.UK);
	            numberFormatB.setMaximumFractionDigits(nbDecimals);
	            numberFormatB.setMinimumFractionDigits(nbDecimals);
	            numberFormatB.setMinimumIntegerDigits(1);

	            editor.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(
	                            new NumberFormatter(numberFormatB)));

	            editor.setHorizontalAlignment(SwingConstants.LEFT);
	            editor.setValue(value);
	        }
	        return editor;
	}
}
