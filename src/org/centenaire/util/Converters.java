/**
 * 
 */
package org.centenaire.util;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import org.centenaire.entity.Entity;
import org.centenaire.entity.Item;

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
	
	/**
	 * Method to cast a list of a specific 'Entity' type into a list of generic 'Entity' instances.
	 * 
	 * @param rawList
	 * 			 the original list with a type extending 'Entity'.
	 * @return currentData
	 * 			 the list after conversion to List<Entity>.
	 */
	public static List<Entity> convertListType(List<? extends Entity> rawList){
		List<Entity> currentData = rawList.stream()
				.map(element->(Entity) element)
                .collect(Collectors.toList());
		
		return currentData;
	}
}
