package gestionBilicence.dao.abstractDao;

import javax.swing.JOptionPane;

import gestionBilicence.dao.Dao;
import gestionBilicence.dao.MySQLFactory;
import gestionBilicence.dao.postgreSqlDao.PostgreSQLFactory;
import gestionBilicence.edition.Exams;
import gestionBilicence.edition.Mark;
import gestionBilicence.edition.Semester;
import gestionBilicence.edition.Student;
import gestionBilicence.general.ConnectionDialog;

public abstract class AbstractDaoFactory {
	/*
	 * Factory class for Dao classes
	 * Includes getDao(int i)
	 */

	public abstract AbstractStudentDao getStudentDao();
	
	public abstract AbstractExamsDao getExamsDao();
	
	public abstract Dao<Semester> getSemesterDao();
	
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
