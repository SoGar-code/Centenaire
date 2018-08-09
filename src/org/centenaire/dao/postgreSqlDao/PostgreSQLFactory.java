package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractDaoFactory;
import org.centenaire.dao.abstractDao.AbstractIndividualDao;
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
import org.centenaire.entity.TaxChrono;
import org.centenaire.entity.TaxGeo;
import org.centenaire.entity.TaxTheme;
import org.postgresql.util.PSQLException;

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
	
	protected static Connection getConnection() {
		return conn;
	}

	@Override
	public AbstractIndividualDao getIndividualDao() {
		return new PostgreSQLIndividualDao(conn);
	}

	@Override
	public Dao<Item> getItemDao() {
		return new PostgreSQLItemDao(conn);
	}

	@Override
	public Dao<Event> getEventDao() {
		return new PostgreSQLEventDao(conn);
	}

	@Override
	public Dao<Institution> getInstitutionDao() {
		return new PostgreSQLInstitutionDao(conn);
	}

	@Override
	public Dao<ItemType> getItemTypeDao() {
		return new PostgreSQLTagLikeDao(conn, "item_type_relations", EntityEnum.ITEMTYPE.getValue());
	}

	@Override
	public Dao<EventType> getEventTypeDao() {
		return new PostgreSQLTagLikeDao(conn, "event_type_relations", EntityEnum.EVENTTYPE.getValue());
	}

	@Override
	public Dao<InstitutionType> getInstitTypeDao() {
		return new PostgreSQLTagLikeDao(conn, "institution_type_relations", EntityEnum.INSTITTYPE.getValue());
	}
	
	@Override
	public Dao<Tag> getTagDao() {
		return new PostgreSQLTagLikeDao(conn, "Tags", EntityEnum.TAG.getValue());
	}

	@Override
	public Dao<Discipline> getDisciplineDao() {
		return new PostgreSQLTagLikeDao(conn, "disciplines", EntityEnum.DISCIPLINES.getValue());
	}

	@Override
	public Dao<InstitStatus> getInstitStatusDao() {
		return new PostgreSQLTagLikeDao(conn, "institutional_status", EntityEnum.INSTITSTATUS.getValue());
	}

	@Override
	public Dao<LocalType> getLocalTypeDao() {
		return new PostgreSQLTagLikeDao(conn, "localisation_type_relations", EntityEnum.LOCALISATIONTYPE.getValue());
	}
	
	@Override
	public Dao<TaxChrono> getTaxChronoDao() {
		return new PostgreSQLTagLikeDao(conn, "tax_chrono", EntityEnum.TAXCHRONO.getValue());
	}
	
	@Override
	public Dao<TaxGeo> getTaxGeoDao() {
		return new PostgreSQLTagLikeDao(conn, "tax_geo", EntityEnum.TAXGEO.getValue());
	}
	
	@Override
	public Dao<TaxTheme> getTaxThemeDao() {
		return new PostgreSQLTagLikeDao(conn, "tax_theme", EntityEnum.TAXTHEME.getValue());
	}
}
