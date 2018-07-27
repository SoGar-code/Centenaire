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
import org.centenaire.entity.Individual;
import org.centenaire.entity.TagLike;
import org.centenaire.general.EntityDialog;
import org.centenaire.general.GTable;
import org.centenaire.general.GeneralController;
import org.centenaire.general.ListTableModel;
import org.centenaire.general.UpdateEntityPanel;
import org.centenaire.general.editorsRenderers.Delete;
import org.centenaire.general.pubsub.Subscriber;

/**
 * Class generating the tabs related to 'TagLike' Entity elements.
 * 
 * <p>In the current design, it contains a tabbed pane itself!</p>
 *
 */
public class TagLikeTab extends JPanel implements Subscriber{
	ListTableModel entityListTableModel;
	Dao<Entity> dao;
	private int classIndex;
	
	/**
	 * The constructor takes a parameter, since several Entity classes are similar.
	 * 
	 * @param classIndex classIndex of the TagLike under consideration
	 */
	public TagLikeTab(int classIndex) {
		super();
		this.classIndex = classIndex;
		
		// Get GeneralController and dao
		GeneralController gc = GeneralController.getInstance();
		dao = gc.getDao(classIndex);

		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvelle étiquette");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("TagLikeTab.newEntity activated!");
				
				TagLike tl = TagLike.defaultElement();

				EntityDialog<TagLike> ed = new EntityDialog<TagLike>(classIndex);
				
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
		
		// Creation of Tag list (already initialized)
		//===================================================
		
		// starting from standard ListTableModel.
		entityListTableModel = new ListTableModel(
				new Class[] {String.class},
				new String[] {"Mot-clef"},
				dao.findAll()
				);
		GTable entityList = new GTable(entityListTableModel);
		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<TagLike> uep = new UpdateEntityPanel<TagLike>(classIndex);
		// uep subscribes to suitable channel 
		gc.getChannel(classIndex).addSubscriber(uep);
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Liste", entityList);
		tabbedPane.addTab("Modifier", uep);

		
		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
		// Let component subscribe to suitable channel
		gc.getChannel(classIndex).addSubscriber(this);
	}

	/**
	 * Method called when a new piece of news is published on registered channel.
	 * 
	 * @see org.centenaire.general.Subscriber
	 */
	@Override
	public void updateSubscriber() {
		// Need to update ListTableModel (since UpdateEntityPanel is treated separately)
		LinkedList<Entity> data = dao.findAll();
		entityListTableModel.setData(data);
	}

}
