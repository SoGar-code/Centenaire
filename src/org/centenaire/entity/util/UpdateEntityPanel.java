/**
 * 
 */
package org.centenaire.entity.util;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.editor.EntityEditor;
import org.centenaire.entity.editor.EntityEditorFactory;
import org.centenaire.entity.relationeditor.RelationEditor;
import org.centenaire.entity.relationeditor.RelationEditorFactory;
import org.centenaire.util.GeneralController;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Generic Entity update panel.
 * 
 * <p>This panel includes:
 * <ul>
 * 		<li>an 'EntityCombo', to select the entity to edit</li>
 * 		<li>an 'EntityEditor', to edit that entity after selection</li>
 * 		<li>a save button to forward the changes to the database</li>
 * </ul></p>
 * 
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
	private JButton relationEditorButton;
	
	private ActionListener comboListener;
	private Set<Integer> editableRelation = new HashSet<Integer>();
	
	/**
	 * Generate an update panel for the Entity class T.
	 * 
	 * @param classIndex label of the Entity class T.
	 */
	public UpdateEntityPanel(int classIndex) {
		super();
		this.classIndex = classIndex;
		
		// define editableRelation
		editableRelation.add(EntityEnum.EVENTS.getValue());
		editableRelation.add(EntityEnum.ITEM.getValue());
		
		// recover the GeneralController
		GeneralController gc = GeneralController.getInstance();
		
		// recover the suitable Dao
		dao = (Dao<T>) gc.getDao(classIndex);
		
		// Create new EntityCombo
		entityCombo = new EntityCombo<T>(classIndex);
		
		// Start with no element selected
		entityCombo.setSelectedIndex(-1);
		
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
					
					// enable the relation edition button
					relationEditorButton.setEnabled(true);
					
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
				T obj = updatePanel.getObject();
				
				dao.update(obj);
			}
		});
		
		
		// Relation editor button and its action
		relationEditorButton = new JButton("Déf. relations");
		relationEditorButton.setEnabled(false);
		relationEditorButton.addActionListener(new ActionListener() {

			// Call suitable relationEditor
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// Recover selected entity
				Entity currentEntity = (Entity) entityCombo.getSelectedEntity();
				
				if (currentEntity != null) {
					RelationEditor relEditor = RelationEditorFactory.getRelationEditor(classIndex, currentEntity);
					relEditor.showEntityDialog();
				}
			}
		});
		
		// Create bottom panel with right and left sides.
		JPanel bottomPan = new JPanel(new GridLayout(1,2));
		
		// In bottomLeftPan, include button only in suitable cases.
		JPanel bottomLeftPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		if (editableRelation.contains(classIndex)) {
			bottomLeftPan.add(relationEditorButton);
		}
		
		JPanel bottomRightPan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomRightPan.add(svgButton);
		


		
		bottomPan.add(bottomLeftPan);
		bottomPan.add(bottomRightPan);
		
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
	 * <p>Since in the 'save' button, the current object is saved, 
	 * the method below is in particular called when 'save' is called.</p>
	 * 
	 * @see org.centenaire.general.Subscriber
	 */
	public void updateSubscriber(int channelIndex) {
		// Check if the provided channelIndex matches the current classIndex
		if (channelIndex == this.classIndex) {
			// Need to update svgButton, entityCombo and updatePanel.
			
			// Disable svgButton (entityCombo is reset upon being notified...).
			svgButton.setEnabled(false);
			
			// remove action listener
			entityCombo.removeActionListener(comboListener);

			// update the combo using the predefined method
			entityCombo.updateSubscriber(channelIndex);
			
			// also  reset the combo
			entityCombo.reset();
			
			// put the action listener back again
			entityCombo.addActionListener(comboListener);
			
			// reset updatePanel
			updatePanel.reset();
		}

	}
}
