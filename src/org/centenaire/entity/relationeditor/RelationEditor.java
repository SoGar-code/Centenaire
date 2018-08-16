package org.centenaire.entity.relationeditor;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import org.centenaire.entity.Entity;
import org.centenaire.util.GeneralController;

/**
 * Abstract class to represent the "relation editors" of entities.
 * 
 * <p>No link to the database, this is simply a graphical component, 
 * with minimal non-graphical methods.</p>
 *
 * @param <T> entity class associated to the editor.
 */
public abstract class RelationEditor<T extends Entity> extends JDialog {
	/**
	 * Entity object whose relations we are editing.
	 */
	private final T entity;
	
	/**
	 * Index of the Entity object currently displayed.
	 * 
	 * <p>Initialized at 0.</p>
	 */
	private int indexField;
	
	/**
	 * Main panel of the dialog.
	 */
	private JPanel main;
	
	protected GeneralController gc = GeneralController.getInstance();
	
	private final static Logger LOGGER = Logger.getLogger(RelationEditor.class.getName());
	
	/**
	 * Constructor for EntityEditor: should be possible to generate it from a T object.
	 * 
	 * <p>Initialize "indexField" with the value 0. Do not forget to update in implementations!</p>
	 * 
	 * @param obj entity instance of the entity class associated to the editor.
	 */
	public RelationEditor(int indexField, T entity){
		super();
		this.indexField = indexField;
		this.entity = entity;
		
		LOGGER.setLevel(Level.ALL);
		
		String msg = String.format("Relations de \"%s\"", entity.toString());
		this.setTitle(msg);
		
		// Modality modified by 'ModalExclusionType' in Centenaire frame
		this.setModalityType(ModalityType.APPLICATION_MODAL);
		this.setLocationRelativeTo(null);
		
		
		main = new JPanel();
		
		// Control panel, with buttons
		// ============================
		JPanel controlPan = new JPanel();
		
	    JButton cancelBouton = new JButton("Annuler");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	  String msg = String.format("RelationEditor of '%s' was cancelled", entity.toString());
	    	  LOGGER.fine(msg);
	    	  
	    	  // ends dialog by making the box invisible
	    	  setVisible(false);
	      }      
	    });
	    
	    JButton okBouton = new JButton("Ok");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  String msg = String.format("RelationEditor of '%s' completed", entity.toString());
	    	  LOGGER.finest(msg);
	    	  
	    	  // Save the current state of relations
	    	  saveRelations();
	    		    	
	    	  // ends dialog by making the box invisible
	    	  setVisible(false);
	      }
	    });
	    
	    controlPan.add(cancelBouton);
	    controlPan.add(okBouton);
	    
	    Container content = this.getContentPane();
	    content.setLayout(new BorderLayout());
	    content.add(main, BorderLayout.CENTER);
	    content.add(controlPan, BorderLayout.SOUTH);
	}
	
	/**
	 * Recover the T object associated to the current instance.
	 * 
	 * <p>This is especially useful when using the panel to edit an element.</p>
	 * 
	 * @return the T object associated to the instance.
	 */
	public T getObject() {
		return this.entity;
	};

	public int getIndexField() {
		return indexField;
	}
	
	/**
	 * Set the relations included in the editor.
	 * 
	 * <p>This method should actually be called only once,
	 * when the editor is created, in order to populate the tables.</p>
	 */
	public abstract boolean setRelations();
	
	/**
	 * Save the relations included in the editor.
	 * 
	 * <p>As part of the 'Pub-Sub' pattern, no notifications are required 
	 * inside this method, since all these notifications are performed inside
	 * the RelationDao pattern.</p>
	 */
	public abstract boolean saveRelations();
	
	/**
	 * Method to display the current RelationEditor.
	 */
	public void showEntityDialog(){
		this.setVisible(true);
	}

	/**
	 * Get the main panel of the RelationEditor.
	 * 
	 * @return main panel of the RelationEditor.
	 */
	public JPanel getMain() {
		return main;
	}

	/**
	 * Set the main panel of the RelationEditor.
	 * 
	 */
	public void setMain(JPanel main) {
		this.main = main;
	}

}
