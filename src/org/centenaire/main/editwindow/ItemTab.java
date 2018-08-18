/**
 * 
 */
package org.centenaire.main.editwindow;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import org.centenaire.dao.abstractDao.AbstractItemDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Item;
import org.centenaire.entity.typelike.CatEnum;
import org.centenaire.entity.util.EntityDialog;
import org.centenaire.entity.util.GTable;
import org.centenaire.entity.util.ListTableModel;
import org.centenaire.entity.util.UpdateEntityPanel;
import org.centenaire.util.GeneralController;
import org.centenaire.util.dragndrop.SourceHandler;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Class generating the tab related to the 'Event' Entity.
 * 
 * <p>In the current design, it contains a tabbed panel itself!</p>
 *
 */
public class ItemTab extends JPanel implements Subscriber{
	protected final static Logger LOGGER = Logger.getLogger(ItemTab.class.getName());
	
	AbstractItemDao dao;
	List<ListTableModel> tableModels;
	
	public ItemTab() {
		super();
		
		LOGGER.setLevel(Level.ALL);

		GeneralController gc = GeneralController.getInstance();
		dao = (AbstractItemDao) gc.getDao(EntityEnum.ITEM.getValue());
		
		
		// Creation of listPan
		//=====================
		
		CardLayout cl = new CardLayout();
		JPanel listPan = new JPanel(cl);
		
		tableModels = new LinkedList<ListTableModel>();
		
		// Populate parts of the cardLayout
		for (CatEnum category: CatEnum.values()) {
			
			// Create list of categories for the given cardLayout
			List<CatEnum> currentCat = new LinkedList<CatEnum>();
			currentCat.add(category);
			
			LinkedList<Item> rawCurrentData = dao.findAll(currentCat);
			List<Entity> currentData = this.convertListType(rawCurrentData);
			
			ListTableModel catTableModel = new ListTableModel(
					new Class[] {String.class, String.class, Date.class},
					new String[] {"Nom", "Type", "Date de début"},
					currentData
					);
			tableModels.add(catTableModel);
			GTable entityList = new GTable(catTableModel);
			
			// Enable drag and define TransferHandler
			JTable table = entityList.getTable();
			table.setDragEnabled(true);
			table.setTransferHandler(new SourceHandler<Item>(EntityEnum.ITEM.getValue()));
			
			// Add GTable to the CardLayout
			listPan.add(entityList, category.toString());
		}
		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<Event> uep = new UpdateEntityPanel<Event>(EntityEnum.ITEM.getValue());
		// uep subscribes to suitable channel 
		gc.getChannel(EntityEnum.ITEM.getValue()).addSubscriber(uep);
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPan = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPan.addTab("Liste", listPan);
		tabbedPan.addTab("Modifier", uep);
		

		// Selection combo
		// =================
		JComboBox<CatEnum> catCombo = new JComboBox<CatEnum>(CatEnum.values());		
		
		// associated action
		catCombo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try {		
					// recover the currently selected object
					CatEnum category = (CatEnum) catCombo.getSelectedItem();
					
					// Display the suitable page of modifyPan
					cl.show(listPan, category.toString());
					
				} catch (ClassCastException except) {
					String msg = "UpdateEntityPanel -- error when casting entity,\n"
							+ "not updating the panel!";
					LOGGER.warning(msg);
				}
			}
		});
		// Include it in topPan
		JPanel topPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		topPan.add(catCombo);
		
		// *New entity* button
		// ====================
		JButton newEntity = new JButton("Nouvelle production");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				LOGGER.finest("ItemTab.newEntity activated!");

				EntityDialog<Event> ed = new EntityDialog<Event>(EntityEnum.ITEM.getValue());
				
				// Open the dialog (where new event is created)...
				try {
					ed.showEntityDialog();
				} catch (NullPointerException e) {
					// edition was cancelled before completion...
					String msg = "Edition of the element cancelled.";
					LOGGER.fine(msg);
				}
				
			}
		});
		// Include it in a bottomPan
		JPanel bottomPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		bottomPan.add(newEntity);

		
		// Final assembly
		this.setLayout(new BorderLayout());
		this.add(topPan, BorderLayout.NORTH);
		this.add(tabbedPan, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
		// Let component subscribe to suitable channel
		gc.getChannel(EntityEnum.ITEM.getValue()).addSubscriber(this);
	}

	/**
	 * Method called when a new piece of news is published on registered channel.
	 * 
	 * <p>In this specific implementation using different categories, we cannot rely
	 * on the default implementation of data update (based on 'Entity' lists). 
	 * For this reason, we update the content of tableListModels 'manually'.</p>
	 * 
	 * @see org.centenaire.general.Subscriber
	 */
	public void updateSubscriber(int channelIndex) {
		// Need only to update ListTableModel (since UpdateEntityPanel is treated separately)
		
		// Update content of the different parts of the cardLayout
		int cardIndex = 0;
		for (CatEnum category: CatEnum.values()) {
			
			// Create list of categories for the given card
			List<CatEnum> currentCat = new LinkedList<CatEnum>();
			currentCat.add(category);
			
			// Recover associated data
			LinkedList<Item> rawCurrentData = dao.findAll(currentCat);
			List<Entity> currentData = this.convertListType(rawCurrentData);
			
			// Get current tableModel
			ListTableModel tableModel = tableModels.get(cardIndex);
			tableModel.setData(currentData);
			
			cardIndex++;
		}
	}
	
	public List<Entity> convertListType(List<Item> rawList){
		List<Entity> currentData = rawList.stream()
				.map(element->(Entity) element)
                .collect(Collectors.toList());
		
		return currentData;
	}

}
