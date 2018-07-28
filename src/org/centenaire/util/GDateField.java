/**
 * 
 */
package org.centenaire.util;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

/**
 * A class to get a JFormattedTextField accepting only Date values.
 *
 */
public class GDateField extends JFormattedTextField {

	/**
	 * Default constructor for this class
	 * 
	 * 
	 */
	public GDateField() {
		super(new SimpleDateFormat("yyyy-MM-dd"));
	}
	
	/**
	 * Set an Date value in the field.
	 * 
	 * @param date
	 * 			the date value to set
	 */
	public void setDate(Date date) {
		String text = date.toString();
		this.setText(text);
	}
	
	/**
	 * Recover a Date from text.
	 * 
	 * @return Date in the component.
	 */
	public Date getDate(){
		String value = this.getText();
		Date date = new Date(0);
		try{
			date = Date.valueOf((String)value);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "GDateField.getDateValue -- ERROR!"+e.getMessage(),"Input String not in format yyyy-MM-dd?",JOptionPane.ERROR_MESSAGE);
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"GDateField.getDateValue -- ERROR!",JOptionPane.ERROR_MESSAGE);
		}
		return date;
	}
}
