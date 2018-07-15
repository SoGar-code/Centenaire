package org.centenaire.general;

import javax.swing.JPanel;

/**
 * GeneralWindow provides a superclass for the different windows of the project.
 * 
 * <p>
 * Part of the observer pattern included in MVC architecture.
 * 
 */
public class GeneralWindow extends JPanel {

	protected GeneralController gc = GeneralController.getInstance();

	/**
	 * Update the window when required.
	 * 
	 * <p>
	 * All elements in the window should be notified...
	 * 
	 */
	public void updateWindow(){
		
	}
}
