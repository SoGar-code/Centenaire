/**
 * 
 */
package org.centenaire.general;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Dialog box to create a new Entity
 * 
 * @param <T> Entity class associated to the editor.
 * @param <U> EntityEditor<T> class associated to the editor.
 */
public class EntityDialog<T, U> extends JDialog {
	T currentObject;

	/**
	 * Constructor of the dialog.
	 * 
	 * <p>It takes an object as input because otherwise 
	 * we would not know what is the Entity class to consider.
	 * 
	 * <p>However, in practice, this element should be  
	 * the "default element" provided by the Entity class.
	 * In order to avoid the problem with initialization, 
	 * it should be created without *id* (so no gap in index
	 * if the object creation is finally cancelled).
	 * 
	 * @param entity
	 * 			Entity object whose values are going to be
	 * 			used by the EntityEditor.
	 */
	public EntityDialog(T entity, U entityEditor) {
		super();
		this.setTitle("Création d'un nouvel objet");
		this.setModal(true);
		this.setSize(400,250);
		this.setLocationRelativeTo(null);
		
		// Control panel, with buttons
		JPanel controlPan = new JPanel();
		
	    JButton cancelBouton = new JButton("Cancel");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	// ends the dialog and stops the program:
	    	System.exit(ABORT);
	      }      
	    });
	    
	    JButton okBouton = new JButton("Ok");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  // recover elements:
	    	  currentObject = (T) (((EntityEditor<T>) entityEditor).getObject());
	    		    	
	    	  // ends dialog by making the box invisible
	    	  setVisible(false);
	      }
	    });
	    
	    controlPan.add(cancelBouton);
	    controlPan.add(okBouton);
	    
	    this.getContentPane().add((JPanel) entityEditor, BorderLayout.CENTER);
	    this.getContentPane().add(controlPan, BorderLayout.SOUTH);
	}
	
	public T showEntityDialog(){
		this.setVisible(true);

		return currentObject;
	}

}
