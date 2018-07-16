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
import org.centenaire.entity.TagLike;
import org.centenaire.dao.abstractDao.AbstractIndividualDao;

/**
 * Create a connection to a PostgreSQL database
 */
public class PostgreSQLFactory extends AbstractDaoFactory {
	private static Connection conn;

	public PostgreSQLFactory(String[] infoConn) {
		JOptionPane jop = new JOptionPane();
		try {
	        Class.forName("org.postgresql.Driver");
	        String url = "jdbc:postgresql://"+infoConn[2]+"/"+infoConn[3];
	        String user = infoConn[0];
	        String passwd = infoConn[1];
	        conn = DriverManager.getConnection(url, user, passwd);
	        // commits automatiques ou pas
	        conn.setAutoCommit(true);
			//jop.showMessageDialog(null,"PostgreSQLFactory -- Connection up and running!","PostgreSQLFactory", JOptionPane.INFORMATION_MESSAGE);
		} catch (ClassNotFoundException e) {
	        jop.showMessageDialog(null,e.getMessage(),"PostgreSQLFactory -- ClassNotFoundException", JOptionPane.INFORMATION_MESSAGE);
		} catch (PSQLException e){
			jop.showMessageDialog(null , "Wrong password?", "PostGreSQLFactory -- PSQLException", JOptionPane.ERROR_MESSAGE );
			e.printStackTrace();
		} catch (SQLException e){
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
	public Dao<TagLike> getTagDao() {
		return new PostgreSQLTagDao(conn);
	}
	
	@Override
	public AbstractMarkDao getMarkDao() {
		return new PostgreSQLMarkDao(conn);
	}
}
