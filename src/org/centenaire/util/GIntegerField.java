/**
 * 
 */
package org.centenaire.util;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
 * A class to get a JFormattedTextField accepting only integer values.
 *
 */
public class GIntegerField extends JFormattedTextField {
	int minimum;

	/**
	 * Default constructor for this class
	 * 
	 * <p>Default behaviour is to accept all positive Integer values.</p>
	 * 
	 */
	public GIntegerField() {
		super();
		int minimum = 0;
	    NumberFormat format = NumberFormat.getIntegerInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(minimum);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    
	    this.setFormatter(formatter);
	}
	
	/**
	 * Another constructor for the class, specifying a minimal value.
	 * 
	 * @param minimum 
	 * 				the minimum value accepted by the field.
	 */
	public GIntegerField(int minimum) {
		super();
	    NumberFormat format = NumberFormat.getIntegerInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(minimum);
	    formatter.setMaximum(Integer.MAX_VALUE);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    
	    this.setFormatter(formatter);
	}
	
	/**
	 * Set an integer value in the field.
	 * 
	 * @param j
	 * 			the integer value to set
	 */
	public void setIntegerValue(int j) {
		String text = String.valueOf(j);
		this.setText(text);
	}
	
	/**
	 * Recover an integer value from text
	 * 
	 * @return integer value in the component.
	 */
	public int getIntegerValue(){
		String content = this.getText();
		NumberFormat format = NumberFormat.getInstance();
		int value;
		try {
			value = format.parse(content).intValue();
		} catch (ParseException e){
			System.out.println("Exception in getIntegerValue: could not parse number, using minimum instead...");
			value = this.minimum;
		}
		return value;
	}
}
