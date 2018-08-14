/**
 * 
 */
package org.centenaire.util;

import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.JFormattedTextField;
import javax.swing.text.NumberFormatter;

/**
 * A class to get a JFormattedTextField accepting only float values.
 *
 */
public class GFloatField extends JFormattedTextField {

	/**
	 * Default constructor for this class
	 * 
	 * <p>Default behaviour is to accept all positive Integer values.</p>
	 * 
	 */
	public GFloatField() {
		super();
	    NumberFormat format = NumberFormat.getIntegerInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Float.class);
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
	public GFloatField(int minimum) {
		super();
	    NumberFormat format = NumberFormat.getIntegerInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Float.class);
	    formatter.setAllowsInvalid(false);
	    // If you want the value to be committed on each keystroke instead of focus lost
	    formatter.setCommitsOnValidEdit(true);
	    
	    this.setFormatter(formatter);
	}
	
	/**
	 * Set a float value in the field.
	 * 
	 * @param x
	 * 			the float value to set
	 */
	public void setFloatValue(float x) {
		String text = String.valueOf(x);
		this.setText(text);
	}
	
	/**
	 * Recover an integer value from text
	 * 
	 * @return integer value in the component.
	 */
	public float getFloatValue(){
		String content = this.getText();
		NumberFormat format = NumberFormat.getInstance();
		float value;
		try {
			value = format.parse(content).floatValue();
		} catch (ParseException e){
			System.out.println("Exception in getFloatValue: could not parse number, using 0 instead...");
			value = 0;
		}
		return value;
	}
}
