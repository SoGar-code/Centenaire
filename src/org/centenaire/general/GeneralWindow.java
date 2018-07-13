package org.centenaire.general;

import javax.swing.JPanel;

public class GeneralWindow extends JPanel {
	/**
	 * GeneralWindow provides a superclass for the different windows of the project.
	 * Part of the observer pattern included in MVC architecture.
	 * 
	 * Method:
	 * _ updateWindow()
	 */
	protected GeneralController gc = GeneralController.getInstance();

	// update the window when required
	public void updateWindow(){
		
	}
}
