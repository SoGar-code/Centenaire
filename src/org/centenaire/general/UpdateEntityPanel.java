/**
 * 
 */
package org.centenaire.general;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entityeditor.EntityEditor;
import org.centenaire.entityeditor.EntityEditorFactory;

/**
 * Generic Entity update panel
 *
 * @param <T> the Entity class under consideration
 *
 */
public class UpdateEntityPanel<T> extends JPanel {
	private EntityEditor<T> updatePanel;
	private final int classIndex;
	private JButton svgButton;
	private Dao<T> dao;

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
		
		// list of Entity elements		
		LinkedList<T> listEntity = (LinkedList<T>) dao.findAll();
		
		// Create combo to select Entity
		T[] entityVect = (T[]) listEntity.toArray();
		JComboBox<T> entityCombo = new JComboBox<T>(entityVect);
		entityCombo.setPreferredSize(new Dimension(200,30));
		
		// Description label
		JLabel selectedLabel = new JLabel("Elément sélectionné : ");
		
		// Processing combo selection
		entityCombo.addActionListener(new ActionListener() {
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
		});
		
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
				
				String msg = String.format("Recovered object: %s", obj.toString());
				System.out.println(msg);
				
				String msg2 = String.format("Obj index: %d", ((Entity) obj).getIndex());
				System.out.println(msg2);
				
				dao.update(obj);
				
				// Notifier gc ?
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
