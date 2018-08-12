/**
 * 
 */
package org.centenaire.main.editwindow;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.util.EntityDialog;
import org.centenaire.util.GTable;
import org.centenaire.util.GeneralController;
import org.centenaire.util.ListTableModel;
import org.centenaire.util.UpdateEntityPanel;
import org.centenaire.util.editorsRenderers.Delete;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Class generating the tab related to the 'Individual' Entity.
 * 
 * <p>In the current design, it contains a tabbed panel itself!</p>
 *
 */
public class IndividualTab extends JPanel implements Subscriber{
	Dao<Entity> dao;
	ListTableModel entityListTableModel;
	
	public IndividualTab() {
		super();

		GeneralController gc = GeneralController.getInstance();
		dao = (Dao<Entity>) gc.getDao(EntityEnum.INDIV.getValue());
		
		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvel individu");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				EntityDialog<Individual> ed = new EntityDialog<Individual>(EntityEnum.INDIV.getValue());
				
				// Open the dialog (where new individual is created)...
				try {
					ed.showEntityDialog();
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
				new Class[] {String.class, String.class},
				new String[] {"Prénom", "Nom"},
				dao.findAll()
				);
		GTable entityList = new GTable(entityListTableModel);
		// Enable drag
		entityList.getTable().setDragEnabled(true);
		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<Individual> uep = new UpdateEntityPanel<Individual>(EntityEnum.INDIV.getValue());
		// uep subscribes to suitable channel 
		gc.getChannel(EntityEnum.INDIV.getValue()).addSubscriber(uep);
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Liste", entityList);
		tabbedPane.addTab("Modifier", uep);

		
		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
		// Let component subscribe to suitable channel
		gc.getChannel(EntityEnum.INDIV.getValue()).addSubscriber(this);
	}

	/**
	 * Method called when a new piece of news is published on registered channel.
	 * 
	 * @see org.centenaire.general.Subscriber
	 */
	public void updateSubscriber(int channelIndex) {
		// Need to update ListTableModel (since UpdateEntityPanel is treated separately)
		LinkedList<Entity> data = dao.findAll();
		entityListTableModel.setData(data);
	}

}
