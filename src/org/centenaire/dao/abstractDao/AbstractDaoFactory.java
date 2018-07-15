package org.centenaire.dao.abstractDao;

import javax.swing.JOptionPane;

import org.centenaire.dao.ConnectionDialog;
import org.centenaire.dao.Dao;
import org.centenaire.dao.postgreSqlDao.PostgreSQLFactory;
import org.centenaire.general.entities.Exams;
import org.centenaire.general.entities.Mark;
import org.centenaire.general.entities.individual.Individual;
import org.centenaire.general.entities.taglike.TagLike;

/**
 * Factory class for Dao classes
 *
 * <p>
 * Includes getDao(int i).
 * <p>
 *
 */
public abstract class AbstractDaoFactory {

	public abstract AbstractIndividualDao getStudentDao();
	
	public abstract AbstractExamsDao getExamsDao();
	
	public abstract Dao<TagLike> getTagDao();
	
	public abstract AbstractMarkDao getMarkDao();
	
	/**
	 * To get a Dao class indexed by an integer
	 * 
	 * @param i
	 * 			index of the class under consideration.
	 * 
	 * @return suitable requested DAO element.
	 * 
	 * @see org.centenaire.general.Entity#getClassIndex()
	 */
	public Dao getDao(int i){
		switch (i){
			case 0:
				String msg = "AbstractDaoFactory.getDao -- classIndex 0 is for "
						+ "the abstract Entity class! So no DAO...";
				System.out.println(msg);
				return null;
			case 1:
				return getStudentDao();
			case 2:
				return getExamsDao();
			case 3:
				return getMarkDao();
			case 4:
				return getTagDao();
			default:
				System.out.println("AbstractDaoFactory.getDao -- type not found!");
				return null;
		}
	}
	
	public static AbstractDaoFactory getFactory(){
		ConnectionDialog dialogConn = new ConnectionDialog();
		String[] infoConn = dialogConn.showConnectionDialog();
		
		// Compare with available options in DialogConnection
		// Deduce what kind of Database is used
		if (new String("testdb").equals(infoConn[3])){
			return new PostgreSQLFactory(infoConn);
		}
		else if (new String("livedb").equals(infoConn[3])){
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
