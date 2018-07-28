/**
 * 
 */
package org.centenaire.util;

import java.sql.Date;

import javax.swing.JOptionPane;

/**
 * Provide converters from different format to another.
 *
 */
public class Converters {

	/**
	 * Auxiliary function: convert String to Date.
	 * 
	 * <p>With more details: requested format is 'yyyy-MM-dd', 
	 * the output Date is a 'java.sql.Date'.</p>
	 * 
	 * @param value
	 * 			should actually be a String, convertible to a Date.
	 * @return Date
	 * 			output of the converter.
	 */
	public static Date convertStringToDate(Object value){
		Date date = new Date(0);
		try{
			date = Date.valueOf((String)value);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Converters.convertStringToDate -- ERROR!"+e.getMessage(),"Input String not in format yyyy-MM-dd?",JOptionPane.ERROR_MESSAGE);
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"Converters.convertStringToDate -- ERROR!",JOptionPane.ERROR_MESSAGE);
		}
		return date; 
	}
}
