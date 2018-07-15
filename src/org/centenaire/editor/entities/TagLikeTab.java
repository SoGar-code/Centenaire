/**
 * 
 */
package org.centenaire.editor.entities;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.centenaire.general.EntityDialog;
import org.centenaire.general.GTable;
import org.centenaire.general.GeneralController;
import org.centenaire.general.ListTableModel;
import org.centenaire.general.UpdateEntityPanel;
import org.centenaire.general.editorsRenderers.Delete;
import org.centenaire.general.entities.individual.Individual;
import org.centenaire.general.entities.taglike.TagLike;

/**
 * Class generating the tabs related to <it>TagLike</it> Entity elements
 * 
 * <p>In the current design, it contains a tabbed pane itself!
 *
 */
public class TagLikeTab extends JPanel {
	
	/**
	 * The constructor takes a parameter, since several Entity classes are similar.
	 * 
	 * @param i classIndex of the TagLike under consideration
	 */
	public TagLikeTab(int i) {
		super();
		
		// Get GeneralController
		GeneralController gc = GeneralController.getInstance();

		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvelle étiquette");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("TagLikeTab.newEntity activated!");
				
				TagLike tl = TagLike.defaultElement();

				EntityDialog<TagLike> ed = new EntityDialog<TagLike>(tl);
				
				// Try to get a value from the dialog...
				try {
					TagLike finalElt = ed.showEntityDialog();
					
				} catch (NullPointerException e) {
					// edition was cancelled before completion...
					System.out.println("Edition of the element cancelled.");
				}
				
			}
		});
		// Include it in a bottomPan
		JPanel bottomPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottomPan.add(newEntity);
		
		// Creation of Individual list (already initialized)
		//===================================================
		
		// starting from standard ListTableModel.
		ListTableModel entityListTableModel = new ListTableModel(
				new Class[] {String.class},
				new String[] {"Etiquette"},
				gc.getDao(i).findAll() // at that point, we should have currentEntity=0
				);
		// include listTableModel as observer of gc (changes in the data).
		gc.addObserver(entityListTableModel);
		GTable entityList = new GTable(entityListTableModel);
		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<TagLike> uep = new UpdateEntityPanel<TagLike>(i);
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Liste", entityList);
		tabbedPane.addTab("Modifier", uep);

		
		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
	}

}
