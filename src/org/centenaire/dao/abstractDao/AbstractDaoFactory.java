package org.centenaire.dao.abstractDao;

import javax.swing.JOptionPane;

import org.centenaire.dao.ConnectionDialog;
import org.centenaire.dao.Dao;
import org.centenaire.dao.postgreSqlDao.PostgreSQLFactory;
import org.centenaire.entity.Discipline;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.EventType;
import org.centenaire.entity.InstitStatus;
import org.centenaire.entity.Institution;
import org.centenaire.entity.InstitutionType;
import org.centenaire.entity.Item;
import org.centenaire.entity.ItemType;
import org.centenaire.entity.LocalType;
import org.centenaire.entity.Tag;

/**
 * Factory class for Dao classes
 *
 * <p>
 * This class includes the factory ("dispatcher") of Dao classes.
 * Concretely, it provides the method getDao(int i). </p>
 * 
 * <p>In this "getDao" method, the integer used to call the Dao class 
 * is defined by the EntityEnum Enumeration.</p>
 *
 * @see org.centenaire.entity.EntityEnum
 */
public abstract class AbstractDaoFactory {
	
	public abstract AbstractIndividualDao getIndividualDao();
	
	public abstract Dao<Item> getItemDao();
	
	public abstract Dao<Event> getEventDao();
	
	public abstract Dao<Institution> getInstitutionDao();
	
	public abstract Dao<ItemType> getItemTypeDao();
	
	public abstract Dao<EventType> getEventTypeDao();
	
	public abstract Dao<InstitutionType> getInstitTypeDao();
	
	public abstract Dao<Tag> getTagDao();
	
	public abstract Dao<Discipline> getDisciplineDao();
	
	public abstract Dao<InstitStatus> getInstitStatusDao();
	
	public abstract Dao<LocalType> getLocalTypeDao();
	
	/**
	 * To get a Dao class indexed by an integer
	 * 
	 * @param i
	 * 			index of the class under consideration.
	 * 
	 * @return suitable requested DAO element.
	 * 
	 * @see org.centenaire.entity.Entity#getClassIndex()
	 */
	public Dao getDao(int i){
		if (i == EntityEnum.ENTITY.getValue()) {
			String msg = "AbstractDaoFactory.getDao -- classIndex 0 is for "
					+ "the abstract Entity class! So no DAO...";
			System.out.println(msg);
			return null;
		} else if (i == EntityEnum.INDIV.getValue()) {
			return getIndividualDao();
		} else if (i == EntityEnum.ITEM.getValue()) {
			return getItemDao();
		} else if (i == EntityEnum.EVENTS.getValue()) {
			return getEventDao();
		} else if (i == EntityEnum.INSTIT.getValue()) {
			return getInstitutionDao();
		} else if (i == EntityEnum.ITEMTYPE.getValue()) {
			return getItemTypeDao();
		} else if (i == EntityEnum.EVENTTYPE.getValue()) {
			return getEventTypeDao();
		} else if (i == EntityEnum.INSTITTYPE.getValue()) {
			return getInstitTypeDao();
		} else if (i == EntityEnum.TAG.getValue()) {
			return getTagDao();
		} else if (i == EntityEnum.DISCIPLINES.getValue()) {
			return getDisciplineDao();
		} else if (i == EntityEnum.INSTITSTATUS.getValue()) {
			return getInstitStatusDao();
		} else if (i == EntityEnum.LOCALISATIONTYPE.getValue()) {
			return getLocalTypeDao();
		} else {
			System.out.println("AbstractDaoFactory.getDao -- type not found!");
			return null;
		}
	}
	
	public static AbstractDaoFactory getFactory(){
		ConnectionDialog dialogConn = new ConnectionDialog();
		String[] infoConn = dialogConn.showConnectionDialog();
		
		// Compare with available options in DialogConnection
		// Deduce what kind of Database is used
		if (new String("bdd_centenaire_test").equals(infoConn[3])){
			return new PostgreSQLFactory(infoConn);
		}
		else {
			System.out.println("AbstractDaoFactory.getFactory() -- unknown database");
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, null,"AbstractDaoFactory.getFactory() -- unknown database!",JOptionPane.ERROR_MESSAGE);
			return null;			
		}

	}
}
