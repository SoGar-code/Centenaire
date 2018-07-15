package org.centenaire.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.centenaire.edition.entities.Individuals;
import org.centenaire.general.GeneralController;

/**
 * A class to update the additional elements linked to Students
 * 
 * @author OG
 * 
 */
public class EditStudentDialog extends JDialog {
	private ExtraInfoStudent infoOutput;
	private boolean updated = false;

	public EditStudentDialog(Individuals stud){
		super();
		this.setTitle("Edition dialog — student "+stud.toString());
		this.setModal(true);
		this.setSize(400,150);
		this.setLocationRelativeTo(null);
		
		JPanel selectionPan = new JPanel();
		JPanel controlPan = new JPanel();
		
		GeneralController gc = GeneralController.getInstance();
		
		ExtraInfoStudent info = gc.getStudentDao().getInfo(stud);
		
		// Definition of selection panel
		StringInfoPanel apogeePan = new StringInfoPanel("APOGEE number",
				info.isHasApogeeNum(),
				info.getApogeeNum()
				);	
		StringInfoPanel emailPan = new StringInfoPanel("E-mail",
				info.isHasEmail(),
				info.getEmail()
				);	
		StringInfoPanel apbPan = new StringInfoPanel("APB number",
				info.isHasApbNum(),
				info.getApbNum()
				);	
		
		// final assembly selectionPan
		selectionPan.setLayout(new BoxLayout(selectionPan,BoxLayout.PAGE_AXIS));
		selectionPan.add(apogeePan);
		selectionPan.add(emailPan);
		selectionPan.add(apbPan);
		
		// Definition of control panel
	    JButton cancelBouton = new JButton("Cancel");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	// ends the dialog
	    	setVisible(false);
	      }      
	    });
		
	    JButton okBouton = new JButton("Ok");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){  		  
	    	  // recover elements:
	    	  infoOutput = new ExtraInfoStudent(
	    			  apbPan.getBool(),
	    			  apbPan.getValue(),
	    			  apogeePan.getBool(),
	    			  apogeePan.getValue(),
	    			  emailPan.getBool(),
	    			  emailPan.getValue());
	    	  // ends dialog by making the box invisible
	    	  updated = true;
	    	  setVisible(false);
	      }
	    });
	    
	    controlPan.add(cancelBouton);
	    controlPan.add(okBouton);
	    
	    this.getContentPane().add(selectionPan, BorderLayout.CENTER);
	    this.getContentPane().add(controlPan, BorderLayout.SOUTH);
	}
	
	public boolean showEditStudentDialog(){
		this.setVisible(true);
		return updated;
	}
	
	public ExtraInfoStudent getInfoOutput(){
		return infoOutput;
	}
}
