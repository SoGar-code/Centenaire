/**
 * 
 */
package org.centenaire.main.editwindow.tab;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.centenaire.entity.Individual;
import org.centenaire.general.EntityDialog;
import org.centenaire.general.GTable;
import org.centenaire.general.GeneralController;
import org.centenaire.general.ListTableModel;
import org.centenaire.general.UpdateEntityPanel;
import org.centenaire.general.editorsRenderers.Delete;

/**
 * Class generating the tab related to the <it>Individual</it> Entity
 * 
 * <p>In the current design, it contains a tabbed pane itself!
 *
 */
public class IndividualTab extends JPanel {
	
	public IndividualTab() {
		super();
		
		// Get GeneralController
		GeneralController gc = GeneralController.getInstance();

		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvel individu");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("IndividualTab.newEntity activated!");
				
				Individual tl = Individual.defaultElement();

				EntityDialog<Individual> ed = new EntityDialog<Individual>(1);
				
				// Try to get a value from the dialog...
				try {
					Individual finalElt = ed.showEntityDialog();
					
					String aux = String.format("==> contenu finalElt: %s", finalElt.toString());
					System.out.println(aux);
					
					System.out.println(String.format("==> année de naissance : %d", finalElt.getBirth_year()));
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
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom"},
				gc.getCurrentData() // at that point, we should have currentEntity=0
				);
		// include listTableModel as observer of gc (changes in the data).
		gc.addObserver(entityListTableModel);
		GTable entityList = new GTable(entityListTableModel);
		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<Individual> uep = new UpdateEntityPanel<Individual>(1);
		
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
