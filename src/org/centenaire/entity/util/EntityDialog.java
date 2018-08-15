/**
 * 
 */
package org.centenaire.entity.util;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.centenaire.dao.Dao;
import org.centenaire.entity.editor.EntityEditor;
import org.centenaire.entity.editor.EntityEditorFactory;
import org.centenaire.util.GeneralController;

/**
 * Dialog box to create a new Entity
 * 
 * 
 * @param <T> Entity class associated to the editor.
 */
public class EntityDialog<T> extends JDialog {
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
	 * @param classIndex
	 * 			classIndex of the Entity class we are considering.
	 */
	public EntityDialog(int classIndex) {
		super();
		this.setTitle("Création d'un nouvel objet");
		this.setModal(true);
		this.setSize(400, 400);
		this.setLocationRelativeTo(null);
		
		EntityEditor<T> entityEditor = EntityEditorFactory.getEntityEditor(classIndex);
		
		// Control panel, with buttons
		JPanel controlPan = new JPanel();
		
	    JButton cancelBouton = new JButton("Cancel");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  // ends dialog by making the box invisible
	    	  setVisible(false);
	      }      
	    });
	    
	    JButton okBouton = new JButton("Ok");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  // recover elements:
	    	  currentObject = entityEditor.getObject();
	    	  
	    	  GeneralController gc = GeneralController.getInstance();
	    	  Dao<T> dao = (Dao<T>) gc.getDao(classIndex);
	    	  
	    	  // object is created in database,
	    	  // no (instance) index required!
	    	  dao.create(currentObject);
	    	  
	    	  // Need to notify gc!
	    		    	
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
