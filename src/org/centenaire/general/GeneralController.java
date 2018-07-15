package org.centenaire.general;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractDaoFactory;
import org.centenaire.dao.abstractDao.AbstractExamsDao;
import org.centenaire.dao.abstractDao.AbstractMarkDao;
import org.centenaire.dao.abstractDao.AbstractStudentDao;
import org.centenaire.edition.entities.individual.Individual;
import org.centenaire.edition.entities.taglike.TagLike;
import org.centenaire.general.observer.Observable;
import org.centenaire.general.observer.Observer;

/**
 * Class used to store data regarding the current state of the interface.
 * 
 * <p>
 * For instance, it stores the variable "currentEntity".
 * It doubles as a singleton class giving access to the different Dao classes.
 * 
 */
public class GeneralController implements Observable, ChangeListener{

	
	private static GeneralController gc = new GeneralController();
	
	/**
	 * Implicitly, df encodes which type of Database we are using in this instance.
	 */
	private static AbstractDaoFactory df;
	
	/**
	 * The *currentEntity* variables encodes the kind of entity under consideration at the moment.
	 * 
	 */
	private int currentEntity=0;
	
	private ArrayList<Observer> listObserver = new ArrayList<Observer>();
	
	private GeneralController(){
		df = AbstractDaoFactory.getFactory();
	}
	
	
	public static GeneralController getInstance(){
		return gc;
	}

	public LinkedList<Entity> getCurrentData() {
		return this.getDao(currentEntity).getData();
	}
	
	public int getCurrentEntity() {
		return this.currentEntity;
	}
	
	public void removeRow(int position, LinkedList<Entity> currentData){
		boolean test = this.getDao(currentEntity).delete(currentData.get(position));
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
		boolean test = this.getDao(currentEntity).create(obj);
		if (test){
			currentData.add(obj);
			// To notify the table of the change of data
			this.updateObservable(currentData);
		} else {
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "DaoTableModel.addRow failed","ERROR",JOptionPane.ERROR_MESSAGE);
		}
	}

	// Create a new element
	public void addRow(LinkedList<Entity> currentData){
		Entity obj = (Entity)this.getDao(currentEntity).newElement();
		if (obj == null){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "DaoTableModel.addRow failed","ERROR",JOptionPane.ERROR_MESSAGE);
		} else {
			currentData.add(obj);
			// To notify the table of the change of data
			this.updateObservable(currentData);
		} 
	}

	// Action corresponding to the listener of the "Save/update" button
	public void saveTable(LinkedList<Entity> currentData){
		// Saves modified data
		int i = 0;
		for (Entity obj : currentData){
			if (gc.getDao(currentEntity).update(obj)){
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

	public AbstractStudentDao getStudentDao(){
		return df.getStudentDao();
	}

	public Dao<TagLike> getSemesterDao(){
		return df.getSemesterDao();
	}
	
	public AbstractExamsDao getExamsDao(){
		return df.getExamsDao();
	}	
	
	public AbstractMarkDao getMarkDao(){
		return df.getMarkDao();
	}
	
	public Dao<Entity> getDao(int i){
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

	// NB: listeners for WestList (in different tabs of "Statistics")
	// are in StudentAction and SemesterAction (see above)
}
