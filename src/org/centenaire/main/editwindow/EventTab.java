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

import org.centenaire.dao.abstractDao.AbstractEventDao;
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
public class EventTab extends JPanel implements Subscriber{
	protected final static Logger LOGGER = Logger.getLogger(EventTab.class.getName());
	
	AbstractEventDao dao;
	List<ListTableModel> tableModels;
	
	/**
	 * List of the different list of Categories used in the tabs.
	 * 
	 * <p>More explicitly, each tab contains 'Event' elements corresponding to certain categories.
	 * the first item of 'catEnumBase' contains the categories corresponding to the first tab.
	 * The second contains the categories for the elements of the second tab, and so on...</p>
	 * 
	 * <p>See also ItemTab for a simpler use case.</p>
	 * 
	 * @see ItemTab
	 */
	List<List<CatEnum>> catEnumBase;
	
	public EventTab() {
		super();
		
		LOGGER.setLevel(Level.ALL);

		GeneralController gc = GeneralController.getInstance();
		dao = (AbstractEventDao) gc.getDao(EntityEnum.EVENTS.getValue());
		
		// Which category sets to use below
		// (first: scientific and digital, second: outreach)
		List<CatEnum> firstList = new LinkedList<CatEnum>();
		firstList.add(CatEnum.SCI);
		firstList.add(CatEnum.DIG);
		
		List<CatEnum> secondList = new LinkedList<CatEnum>();
		secondList.add(CatEnum.OUTREACH);
		
		catEnumBase = new LinkedList<List<CatEnum>>();
		catEnumBase.add(firstList);
		catEnumBase.add(secondList);
		
		// Names to use in the combo
		String[] comboNames = {"Scientifique (et al.)", "Vulgarisation"};
		
		// Creation of listPan
		//=====================
		
		CardLayout cl = new CardLayout();
		JPanel listPan = new JPanel(cl);
		
		tableModels = new LinkedList<ListTableModel>();
		
		// Populate parts of the cardLayout
		for (int cardIndex = 0; cardIndex < 2; cardIndex++) {
			
			// Get list of categories for the given cardLayout
			List<CatEnum> currentCat = catEnumBase.get(cardIndex);
			
			List<Event> rawCurrentData = dao.findAll(currentCat);
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
			table.setTransferHandler(new SourceHandler<Event>(EntityEnum.EVENTS.getValue()));
			
			// Add GTable to the CardLayout
			listPan.add(entityList, comboNames[cardIndex]);
		}
		
		// Creation of 'modifier' pane
		//===================================================
		UpdateEntityPanel<Event> uep = new UpdateEntityPanel<Event>(EntityEnum.EVENTS.getValue());
		// uep subscribes to suitable channel 
		gc.getChannel(EntityEnum.EVENTS.getValue()).addSubscriber(uep);
		
		// Include different elements in JTabbedPane
		JTabbedPane tabbedPan = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPan.addTab("Liste", listPan);
		tabbedPan.addTab("Modifier", uep);
		

		// Selection combo
		// =================
		JComboBox<String> catCombo = new JComboBox<String>(comboNames);		
		
		// associated action
		catCombo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				try {		
					// recover the currently selected index
					int cardIndex = catCombo.getSelectedIndex();
					
					// Display the suitable page of modifyPan
					cl.show(listPan, comboNames[cardIndex]);
					
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
				LOGGER.finest("EventTab.newEntity activated!");

				EntityDialog<Event> ed = new EntityDialog<Event>(EntityEnum.EVENTS.getValue());
				
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
		gc.getChannel(EntityEnum.EVENTS.getValue()).addSubscriber(this);
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
		for (int cardIndex = 0; cardIndex < 2; cardIndex++) {
			
			// Create list of categories for the given card
			List<CatEnum> currentCat = catEnumBase.get(cardIndex);
			
			// Recover associated data
			List<Event> rawCurrentData = dao.findAll(currentCat);
			List<Entity> currentData = this.convertListType(rawCurrentData);
			
			// Get current tableModel
			ListTableModel tableModel = tableModels.get(cardIndex);
			tableModel.setData(currentData);
		}
	}
	
	public List<Entity> convertListType(List<Event> rawList){
		List<Entity> currentData = rawList.stream()
				.map(element->(Entity) element)
                .collect(Collectors.toList());
		
		return currentData;
	}

}
