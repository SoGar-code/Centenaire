/**
 * 
 */
package org.centenaire.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entityeditor.EntityEditor;
import org.centenaire.entityeditor.EntityEditorFactory;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Generic Entity update panel.
 * 
 * <p>It includes an implementation of the 'Subscriber' interface 
 * of the Publisher-Subscriber pattern used to notify the component 
 * about updates in the database.</p>
 *
 * @param <T> the Entity class under consideration
 *
 * @see org.centenaire.util.pubsub.Subscriber
 */
public class UpdateEntityPanel<T> extends JPanel implements Subscriber{
	private final int classIndex;
	private Dao<T> dao;

	private EntityEditor<T> updatePanel;
	private EntityCombo<T> entityCombo;

	private JButton svgButton;
	
	private ActionListener comboListener;
	
	/**
	 * Generate an update panel for the Entity class T.
	 * 
	 * @param classIndex label of the Entity class T.
	 */
	public UpdateEntityPanel(int classIndex) {
		super();
		this.classIndex = classIndex;
		
		// recover the GeneralController
		GeneralController gc = GeneralController.getInstance();
		
		// recover the suitable Dao
		dao = (Dao<T>) gc.getDao(classIndex);
		
		// Create new EntityCombo
		entityCombo = new EntityCombo<T>(classIndex);
		
		// create action listener
		comboListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {		
					// recover the currently selected object
					T entity = (T) entityCombo.getSelectedItem();

					// update the panel accordingly
					updatePanel.setObject(entity);
				
					// enable the save button
					svgButton.setEnabled(true);
				} catch (ClassCastException except) {
					String msg = "UpdateEntityPanel -- error when casting entity,\n"
							+ "not updating the panel!";
					System.out.println(msg);
				}
			}
		};
		
		// Description label
		JLabel selectedLabel = new JLabel("Elément sélectionné : ");
		
		// Processing combo selection
		entityCombo.addActionListener(comboListener);
		
		// Create top panel
		JPanel topPan = new JPanel();
		topPan.setLayout(new FlowLayout(FlowLayout.CENTER));
		topPan.add(selectedLabel);
		topPan.add(entityCombo);
		
		// Create the update panel
		updatePanel = EntityEditorFactory.getEntityEditor(classIndex);
		
		// Save button and its action
		svgButton = new JButton("Sauvegarder");
		svgButton.setEnabled(false);
		
		svgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Calling save button");
				
				T obj = updatePanel.getObject();
				
				dao.update(obj);
			}
		});

		
		// Create bottom panel
		JPanel bottomPan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPan.add(svgButton);
		
		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(topPan, BorderLayout.NORTH);
		this.add(updatePanel);
		this.add(bottomPan, BorderLayout.SOUTH);
	}

	/**
	 * Method to implement the 'Subscriber' interface of the Publisher-Subscriber pattern.
	 * 
	 * <p>It updates the combo as well as the EntityEditor panel.</p>
	 * 
	 * @see org.centenaire.general.Subscriber
	 */
	@Override
	public void updateSubscriber() {
		// Need to update svgButton, entityCombo and updatePanel.
		
		// Disable svgButton (entityCombo is reset upon being notified...).
		svgButton.setEnabled(false);
		
		// remove action listener
		entityCombo.removeActionListener(comboListener);

		// update the combo using the predefined method
		entityCombo.updateSubscriber();
		
		// put the action listener back again
		entityCombo.addActionListener(comboListener);
		
		// reset updatePanel
		updatePanel.reset();
	}
	
//	/**
//	 * A method to update the 'updatePanel'.
//	 * 
//	 * <p>This method is called by <it>gc</it> whenever the current entity
//	 * changes.
//	 */
//	public void setUpdatePanel(T entity) {	
//
//		
//		updatePanel.removeAll();
//		updatePanel.add(((WithEditor<T>) entity).editionForm());
//		// needed for the change to take effect
//		updatePanel.validate();
//	}
	
}
