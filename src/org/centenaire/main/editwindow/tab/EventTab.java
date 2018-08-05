/**
 * 
 */
package org.centenaire.main.editwindow.tab;

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
import org.centenaire.entity.Event;
import org.centenaire.entity.Individual;
import org.centenaire.util.EntityDialog;
import org.centenaire.util.GTable;
import org.centenaire.util.GeneralController;
import org.centenaire.util.ListTableModel;
import org.centenaire.util.UpdateEntityPanel;
import org.centenaire.util.editorsRenderers.Delete;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Class generating the tab related to the 'Event' Entity.
 * 
 * <p>In the current design, it contains a tabbed panel itself!</p>
 *
 */
public class EventTab extends JPanel implements Subscriber{
	Dao<Entity> dao;
	ListTableModel entityListTableModel;
	
	public EventTab() {
		super();

		GeneralController gc = GeneralController.getInstance();
		dao = (Dao<Entity>) gc.getDao(EntityEnum.EVENTS.getValue());
		
		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvel �v�nement");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("EventTab.newEntity activated!");

				EntityDialog<Event> ed = new EntityDialog<Event>(EntityEnum.EVENTS.getValue());
				
				// Open the dialog (where new event is created)...
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
		
		// Creation of Event list (already initialized)
		//===================================================
		
		// starting from standard ListTableModel.
		entityListTableModel = new ListTableModel(
				new Class[] {String.class},
				new String[] {"Nom"},
				dao.findAll()
				);
		GTable entityList = new GTable(entityListTableModel);
		// Enable drag
		entityList.getTable().setDragEnabled(true);
		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<Event> uep = new UpdateEntityPanel<Event>(EntityEnum.EVENTS.getValue());
		// uep subscribes to suitable channel 
		gc.getChannel(EntityEnum.EVENTS.getValue()).addSubscriber(uep);
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Liste", entityList);
		tabbedPane.addTab("Modifier", uep);

		
		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
		// Let component subscribe to suitable channel
		gc.getChannel(EntityEnum.EVENTS.getValue()).addSubscriber(this);
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