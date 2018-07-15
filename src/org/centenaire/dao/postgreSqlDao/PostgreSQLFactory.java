package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.postgresql.util.PSQLException;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractDaoFactory;
import org.centenaire.dao.abstractDao.AbstractExamsDao;
import org.centenaire.dao.abstractDao.AbstractMarkDao;
import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.general.entities.taglike.TagLike;

/**
 * Create a connection to a PostgreSQL database
 */
public class PostgreSQLFactory extends AbstractDaoFactory {
	private static Connection conn;

	public PostgreSQLFactory(String[] infoConn) {
		try {
	        Class.forName("org.postgresql.Driver");
	        String url = "jdbc:postgresql://"+infoConn[2]+"/"+infoConn[3];
	        String user = infoConn[0];
	        String passwd = infoConn[1];
	        conn = DriverManager.getConnection(url, user, passwd);
	        // commits automatiques ou pas
	        conn.setAutoCommit(true);
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null,"PostgreSQLFactory -- Connection up and running!","PostgreSQLFactory", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException e) {
			JOptionPane jop = new JOptionPane();
	        jop.showMessageDialog(null,e.getMessage(),"PostgreSQLFactory -- ClassNotFoundException", JOptionPane.INFORMATION_MESSAGE);
		} catch (PSQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null , "Wrong password?", "PostGreSQLFactory -- PSQLException", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
	        jop.showMessageDialog(null,e.getMessage(),"PostgreSQLFactory -- SQLException", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public AbstractIndividualDao getStudentDao() {
		return new PostgreSQLIndividualDao(conn);
	}

	@Override
	public AbstractExamsDao getExamsDao() {
		return new PostgreSQLExamDao(conn);
	}
	
	@Override
	public Dao<TagLike> getSemesterDao() {
		return new PostgreSQLSemesterDao(conn);
	}
	
	@Override
	public AbstractMarkDao getMarkDao() {
		return new PostgreSQLMarkDao(conn);
	}
}
