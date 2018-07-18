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

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.general.EntityDialog;
import org.centenaire.general.GTable;
import org.centenaire.general.GeneralController;
import org.centenaire.general.ListTableModel;
import org.centenaire.general.UpdateEntityPanel;
import org.centenaire.general.editorsRenderers.Delete;
import org.centenaire.general.pubsub.Subscriber;

/**
 * Class generating the tab related to the 'Individual' Entity.
 * 
 * <p>In the current design, it contains a tabbed panel itself!</p>
 *
 */
public class IndividualTab extends JPanel implements Subscriber{
	GeneralController gc = GeneralController.getInstance();
	EntityDialog<Individual> ed;
	ListTableModel entityListTableModel;
	UpdateEntityPanel<Individual> uep;
	
	public IndividualTab() {
		super();

		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvel individu");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("IndividualTab.newEntity activated!");
				
				Individual tl = Individual.defaultElement();

				ed = new EntityDialog<Individual>(1);
				
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
		entityListTableModel = new ListTableModel(
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom"},
				gc.getCurrentData()
				);
		GTable entityList = new GTable(entityListTableModel);
		
		// Creation of 'modifier' pane
		//===================================================
		uep = new UpdateEntityPanel<Individual>(EntityEnum.INDIV.getValue());
		// register uep as subscriber for suitable channel 
		gc.getChannel(EntityEnum.INDIV.getValue()).addSubscriber(uep);
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Liste", entityList);
		tabbedPane.addTab("Modifier", uep);

		
		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
	}

	/**
	 * Method called when a new piece of news is published on registered channel.
	 * 
	 * <p>The method updates the values of the current element, if any.</p>
	 * 
	 * @see org.centenaire.general.Subscriber
	 */
	public void updateSubscriber() {
		// Need to update combo, ListTableModel and UpdateEntityPanel
		
		
		
		// include listTableModel as observer of gc (changes in the data).
		gc.addObserver(entityListTableModel);
		
		// Check the index of the current object. If == 0, do nothing...
//		if (this.getIndexField() != 0) {
//			gc.getDao(i)
//		}
				
	}

}
