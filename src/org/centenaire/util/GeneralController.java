package org.centenaire.util;

import java.awt.datatransfer.DataFlavor;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractDaoFactory;
import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.Tag;
import org.centenaire.util.observer.Observable;
import org.centenaire.util.observer.Observer;
import org.centenaire.util.pubsub.Channel;
import org.centenaire.util.pubsub.Dispatcher;

/**
 * Class used to store data regarding the current state of the interface.
 * 
 * <p>
 * For instance, it stores the variable "currentEntity".
 * It doubles as a singleton class giving access to the different Dao classes.
 * </p>
 * 
 * <p>It also acts as 'Dispatcher' in the Publisher-Subscriber pattern. More details 
 * about this pattern can be found in the interface implementing it here.</p>
 * 
 * <p>In our specific implementation, we apply the following guidelines:
 * <ul>
 * <li>publishers are created in the DAOs, notifying upon creation, update or deletion of elements</li>
 * <li>subscribers are all suitable graphical elements.</li>
 * </ul>
 * Since our implementation of the pattern enables only one reaction of the subscriber, whatever the channel
 * which is updated, we should not put the 'update functions' in components dealing with multiple types of 
 * Entity classes.</p>
 * 
 * @see org.centenaire.util.pubsub
 * @see org.centenaire.util.pubsub.Dispatcher
 * 
 */
public class GeneralController implements Observable, ChangeListener, Dispatcher{
	/**
	 * Controls the number of channels in the Publisher-Subscriber pattern.
	 * 
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	private int nbChannels = 12;
	
	/**
	 * List of channels to use for the Publisher-Subscriber pattern.
	 * 
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	private ArrayList<Channel> listChannels;
	
	/**
	 * Implicitly, df encodes which type of Database we are using in this instance.
	 */
	private static AbstractDaoFactory df;
	
	/**
	 * The *currentEntity* variables encodes the kind of entity under consideration at the moment.
	 * 
	 */
	private int currentEntity=1;
	
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	
	private DataFlavor linkedListFlavor = new DataFlavor(LinkedList.class, "LinkedList");
	
	/**
	 * The instance of GeneralController itself!
	 * 
	 * <p>This part implements the Singleton pattern for GeneralController.</p>
	 * 
	 */
	private static GeneralController gc = new GeneralController();
	
	/**
	 * Private constructor of the Singleton class GeneralController.
	 */
	private GeneralController(){
		// Generate a suitable data factory
		df = AbstractDaoFactory.getFactory();
		
		// Initialize 'listChannels'
		listChannels = new ArrayList<Channel>();
		for (int i=0; i<nbChannels; i++) {
			Channel channel = new Channel();
			listChannels.add(channel);
		}
	}
	
	
	public static GeneralController getInstance(){
		return gc;
	}

	public LinkedList<Entity> getCurrentData() {
		return (LinkedList<Entity>) this.getDao(currentEntity).findAll();
	}
	
	public int getCurrentEntity() {
		return this.currentEntity;
	}
	
	public void deleteRow(int position, LinkedList<Entity> currentData){
		boolean test = ((Dao<Entity>) this.getDao(currentEntity)).delete(currentData.get(position));
		if (test){
			currentData.remove(position);
			// To notify the table of the change of data
			this.updateObservable(currentData);
		} else {
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "GeneralController.removeRow","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}

	// Take a non-initialized element
	public void addRow(Entity obj,LinkedList<Entity> currentData){
		boolean test = ((Dao<Entity>) this.getDao(currentEntity)).create(obj);
		if (test){
			currentData.add(obj);
			// To notify the table of the change of data
			this.updateObservable(currentData);
		} else {
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "GeneralController.addRow failed","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}

	// Action corresponding to the listener of the "Save/update" button
	public void saveTable(LinkedList<Entity> currentData){
		// Saves modified data
		int i = 0;
		for (Entity obj : currentData){
			if (((Dao<Entity>) gc.getDao(currentEntity)).update(obj)){
				i++;						
			} else {
				System.out.println("GeneralController.saveTable: skipped one line.");
			};
		};
		System.out.println("GeneralController.saveTable: saved "+i+" lines.");
		this.updateObservable(currentData);
	}
	
	//===================================
	// management of Dao
	//===================================

	public AbstractIndividualDao getIndividualDao(){
		return df.getIndividualDao();
	}

	public Dao<Tag> getTagDao(){
		return df.getTagDao();
	}
	
	public Dao<?> getDao(int i){
		return df.getDao(i);
	}
	
	//=================================
	// Listeners and observable
	//=================================

	// listener of JTabbedPane
	// (recovers currentEntity)
	@Override
	public void stateChanged(ChangeEvent e) {
		// NB: the source is the JTabbedPane in EditionWindow
		JTabbedPane tabbedPane = (JTabbedPane)e.getSource();
		
		// change the currentEntity
		this.currentEntity=tabbedPane.getSelectedIndex();
		System.out.println("GC.stateChanged - current Entity = "+currentEntity);
		
		// update the data associated to the source according to currentEntity
		GTable gTable = (GTable)tabbedPane.getSelectedComponent();
		ListTableModel model = gTable.getModel();
		model.setData(this.getCurrentData());
		model.fireTableDataChanged();
	}
	
	// methods related to the Observer pattern
	@Override
	public void addObserver(Observer obs) {
		this.listObserver.add(obs);
	}

	@Override
	public void updateObservable(LinkedList<Entity> currentData) {
		for (Observer obs: listObserver){
			obs.updateObserver(currentData);
		}
	}

	/**
	 * Implementation of Publisher-Subscriber pattern.
	 * 
	 * <p>In our specific case, publishers are going to be the 
	 * methods 'create' and 'update' in the Dao pattern.
	 * 
	 * @see org.centenaire.util.pubsub.Dispatcher
	 * @see org.centenaire.dao.Dao#create(Object)
	 * @see org.centenaire.dao.Dao#update(Object)
	 */
	@Override
	public Channel getChannel(int channelIndex) {	
		Channel channel = listChannels.get(channelIndex);
		return channel;
	}

	// NB: listeners for WestList (in different tabs of "Statistics")
	// are in StudentAction and SemesterAction (see above)
	
	// DataFlavor
	// ===========
	public DataFlavor getLinkedListFlavor() {
		return this.linkedListFlavor;
	}
}