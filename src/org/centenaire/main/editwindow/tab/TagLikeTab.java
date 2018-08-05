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
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Tag;
import org.centenaire.entity.TagLike;
import org.centenaire.util.EntityDialog;
import org.centenaire.util.GTable;
import org.centenaire.util.GeneralController;
import org.centenaire.util.ListTableModel;
import org.centenaire.util.UpdateEntityPanel;
import org.centenaire.util.pubsub.Subscriber;
import org.centenaire.util.transferHandler.SourceHandler;

/**
 * Class generating the tabs related to 'TagLike' Entity elements.
 * 
 * <p>In the current design, it contains a tabbed pane itself!</p>
 *
 */
public class TagLikeTab extends JPanel implements Subscriber{
	GeneralController gc = GeneralController.getInstance();
	ListTableModel entityListTableModel;
	Dao<Entity> dao;
	private int classIndex;
	
	/**
	 * The constructor takes a parameter, since several Entity classes are similar.
	 * 
	 */
	public TagLikeTab() {
		super();
		// Default TagLike entity
		this.classIndex = EntityEnum.TAG.getValue();
		
		// Get dao
		dao = (Dao<Entity>) gc.getDao(classIndex);
		
		// Selection combo
		// =================
		EntityEnum[] entityEnumList = {EntityEnum.ITEMTYPE, 
									EntityEnum.EVENTTYPE,
									EntityEnum.INSTITTYPE,
									EntityEnum.TAG,
									EntityEnum.DISCIPLINES,
									EntityEnum.INSTITSTATUS,
									EntityEnum.LOCALISATIONTYPE};
		JComboBox<EntityEnum> entityCombo = new JComboBox<EntityEnum>(entityEnumList);
		entityCombo.setSelectedItem(EntityEnum.TAG);
		
		// associated action
		entityCombo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("TagLikeTab.entityCombo activated!");

				try {		
					// recover the currently selected object
					EntityEnum entity = (EntityEnum) entityCombo.getSelectedItem();

					// update the classIndex
					classIndex = entity.getValue();
					
					// Update selected dao
					dao = (Dao<Entity>) gc.getDao(classIndex);
					
					// Update panel
					updateSubscriber(classIndex);
					
				} catch (ClassCastException except) {
					String msg = "UpdateEntityPanel -- error when casting entity,\n"
							+ "not updating the panel!";
					System.out.println(msg);
				}
			}
		});
		
		// Include it in a panel
		JPanel comboPan = new JPanel();
		comboPan.add(entityCombo);
		
		// Creation of Tag list (already initialized)
		//===================================================
		
		// starting from standard ListTableModel.
		entityListTableModel = new ListTableModel(
				new Class[] {String.class},
				new String[] {"Mot-clef"},
				dao.findAll()
				);
		GTable entityList = new GTable(entityListTableModel);
		// Enable drag and define TransferHandler
		JTable table = entityList.getTable();
		table.setDragEnabled(true);
		table.setTransferHandler(new SourceHandler<Tag>(EntityEnum.TAG.getValue()));

		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<TagLike> uep = new UpdateEntityPanel<TagLike>(classIndex);
		// uep subscribes to all suitable channels 
		for (EntityEnum entity:entityEnumList) {
			gc.getChannel(entity.getValue()).addSubscriber(uep);
		}
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Liste", entityList);
		tabbedPane.addTab("Modifier", uep);
		
		
		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvelle étiquette");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("TagLikeTab.newEntity activated!");

				EntityDialog<TagLike> ed = new EntityDialog<TagLike>(classIndex);
				
				// Try to get a value from the dialog...
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

		
		// Final assembly
		// ===============
		this.setLayout(new BorderLayout());
		this.add(comboPan, BorderLayout.NORTH);
		this.add(tabbedPane, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
		// Let component subscribe to all suitable channels
		for (EntityEnum entity:entityEnumList) {
			gc.getChannel(entity.getValue()).addSubscriber(this);
		}

	}

	/**
	 * Method called when a new piece of news is published on registered channel.
	 * 
	 * @see org.centenaire.general.Subscriber
	 */
	@Override
	public void updateSubscriber(int channelIndex) {
		// Update only if channelIndex matches the current classIndex
		if (channelIndex == classIndex) {
			// Need to update ListTableModel (since UpdateEntityPanel is treated separately)
			LinkedList<Entity> data = dao.findAll();
			entityListTableModel.setData(data);
		}
	}

}
