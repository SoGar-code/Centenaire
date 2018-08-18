/**
 * 
 */
package org.centenaire.main.editwindow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.taglike.Tag;
import org.centenaire.entity.taglike.TagLike;
import org.centenaire.entity.typelike.TypeLike;
import org.centenaire.entity.util.EntityDialog;
import org.centenaire.entity.util.GTable;
import org.centenaire.entity.util.ListTableModel;
import org.centenaire.entity.util.UpdateEntityPanel;
import org.centenaire.util.GeneralController;
import org.centenaire.util.dragndrop.SourceHandler;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Class generating the tabs related to 'Type' Entity elements.
 * 
 * <p>In the current design, it contains a tabbed pane itself!</p>
 *
 */
public class TypeLikeTab extends JPanel implements Subscriber{
	protected final static Logger LOGGER = Logger.getLogger(TypeLikeTab.class.getName());
	
	GeneralController gc = GeneralController.getInstance();
	ListTableModel entityListTableModel;
	Dao<Entity> dao;
	private int classIndex;
	
	/**
	 * The constructor takes a parameter, since several Entity classes are similar.
	 * 
	 */
	public TypeLikeTab() {
		super();
		// Default TagLike entity
		this.classIndex = EntityEnum.ITEMTYPE.getValue();
		
		// Get dao
		dao = (Dao<Entity>) gc.getDao(classIndex);
		
		// Supported types of 'Entity':
		EntityEnum[] entityEnumList = {
				EntityEnum.ITEMTYPE, 
				EntityEnum.EVENTTYPE,
				};
		
		// Creation of 'modifier' pane (CardLayout)
		//===================================================
		CardLayout cl = new CardLayout();
		JPanel modifyPan = new JPanel(cl);
		
		UpdateEntityPanel<TypeLike> uep;

		for (EntityEnum entityEnum:entityEnumList) {
			// Create uep for each suitable classIndex			
			uep = new UpdateEntityPanel<TypeLike>(entityEnum.getValue());
			
			// Subscribe that uep to the suitable panel
			gc.getChannel(entityEnum.getValue()).addSubscriber(uep);
			
			// Include it in the CardLayout
			modifyPan.add(uep, entityEnum.toString());
		}
		
		// Selection combo
		// =================
		JComboBox<EntityEnum> entityCombo = new JComboBox<EntityEnum>(entityEnumList);		
		
		// associated action
		entityCombo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try {		
					// recover the currently selected object
					EntityEnum entityEnum = (EntityEnum) entityCombo.getSelectedItem();

					// update the classIndex
					classIndex = entityEnum.getValue();
					
					// Display the suitable page of modifyPan
					cl.show(modifyPan, entityEnum.toString());
					
					// Update selected dao
					dao = (Dao<Entity>) gc.getDao(classIndex);
					
					// Update panel (i.e. the 'Liste' Tab)
					updateSubscriber(classIndex);
					
				} catch (ClassCastException except) {
					String msg = "UpdateEntityPanel -- error when casting entity,\n"
							+ "not updating the panel!";
					LOGGER.warning(msg);
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
				new Class[] {String.class, String.class},
				new String[] {"Intitulé", "Catégorie"},
				dao.findAll()
				);
		GTable entityList = new GTable(entityListTableModel);
		
		// Enable drag and define TransferHandler
		JTable table = entityList.getTable();
		table.setDragEnabled(true);
		table.setTransferHandler(new SourceHandler<TypeLike>(EntityEnum.ITEMTYPE.getValue()));
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addTab("Liste", entityList);
		tabbedPane.addTab("Modifier", modifyPan);
		
		
		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouveau type");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){

				EntityDialog<TagLike> ed = new EntityDialog<TagLike>(classIndex);
				
				// Try to get a value from the dialog...
				try {
					ed.showEntityDialog();
					
				} catch (NullPointerException e) {
					// edition was cancelled before completion...
					LOGGER.finer("Edition of the element cancelled.");
				}
				
			}
		});
		
		// Include it in a bottomPan
		JPanel bottomPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottomPan.add(newEntity);

		// Setting all variable components to starting point
		entityCombo.setSelectedItem(EntityEnum.ITEMTYPE);
		cl.show(modifyPan, EntityEnum.ITEMTYPE.toString());
		
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
