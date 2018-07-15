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
import org.centenaire.general.entities.individual.Individual;

/**
 * Generic Entity update panel
 *
 * @param <T> the Entity class under consideration
 *
 */
public class UpdateEntityPanel<T> extends JPanel {
	JPanel updatePanel;
	private final int classIndex;

	/**
	 * Generate an update panel for the (Entity) type T.
	 * 
	 * @param i classIndex of the (Entity) type T.
	 */
	public UpdateEntityPanel(int i) {
		super();
		this.classIndex = i;
		
		// recover the GeneralController
		GeneralController gc = GeneralController.getInstance();
		
		// recover the suitable Dao
		Dao<?> dao = gc.getDao(i);
		
		// list of Entity elements
		LinkedList<?> aux = dao.findAll();
		
		LinkedList<T> listEntity = (LinkedList<T>) aux;
		
		// Create combo to select Entity
		T[] entityVect = (T[]) listEntity.toArray();
		JComboBox<T> entityCombo = new JComboBox<T>(entityVect);
		entityCombo.setPreferredSize(new Dimension(200,30));
		
		JLabel selectedLabel = new JLabel("El�ment s�lectionn� : ");
		
		// Processing combo selection
		entityCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {
				T entity = (T) entityCombo.getSelectedItem();
				setUpdatePanel(entity);
				
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
		
		updatePanel = new JPanel();
		
		// Save button and its action
		JButton svgButton = new JButton("Sauvegarder");
			// il suffit de mettre � jour l'�l�ment courant, s'il y en a...
			// ... mais il faut notifier gc...
		
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
	 * A method to update the 'updatePanel'.
	 * 
	 * <p>This method is called by <it>gc</it> whenever the current entity
	 * changes.
	 */
	public void setUpdatePanel(T entity) {
		// peut-�tre que �a pourrait �tre une pure histoire interne au composant ?
		// => lorsqu'il y a une mise � jour, il faut notifier gc... (peut-�tre automatiquement ?) 
		System.out.println("Calling UpdatePanel...");
		
		updatePanel.removeAll();
		updatePanel.add(((WithEditor<T>) entity).editionForm());
		updatePanel.validate();
	}
	
}
