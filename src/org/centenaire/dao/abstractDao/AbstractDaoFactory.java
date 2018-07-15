package org.centenaire.dao.abstractDao;

import javax.swing.JOptionPane;

import org.centenaire.dao.ConnectionDialog;
import org.centenaire.dao.Dao;
import org.centenaire.dao.postgreSqlDao.PostgreSQLFactory;
import org.centenaire.edition.entities.Exams;
import org.centenaire.edition.entities.Mark;
import org.centenaire.edition.entities.individual.Individual;
import org.centenaire.edition.entities.taglike.TagLike;

/**
 * Factory class for Dao classes
 *
 * <p>
 * Includes getDao(int i).
 * <p>
 *
 */
public abstract class AbstractDaoFactory {

	public abstract AbstractStudentDao getStudentDao();
	
	public abstract AbstractExamsDao getExamsDao();
	
	public abstract Dao<TagLike> getSemesterDao();
	
	public abstract AbstractMarkDao getMarkDao();
	
	// to get Dao class indexed by an integer
	public Dao getDao(int i){
		switch (i){
			case 0:
				return getStudentDao();
			case 1:
				return getSemesterDao();
			case 2:
				return getExamsDao();
			case 3:
				return getMarkDao();
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
