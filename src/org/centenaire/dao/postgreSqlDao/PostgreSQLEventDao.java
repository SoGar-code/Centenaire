package org.centenaire.dao.postgreSqlDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractEventDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.taglike.Country;
import org.centenaire.entity.taglike.Departement;
import org.centenaire.entity.typelike.CatEnum;
import org.centenaire.entity.typelike.EventType;
import org.centenaire.util.GeneralController;

/**
 * DAO for a PostgreSQL database, relative to 'Individual' Entity.
 * 
 * <p>This implementation also includes a notification system. 
 * This component performs as Publisher.</p>
 * 
 * @see org.centenaire.entity.Entity
 * @see org.centenaire.util.pubsub.Publisher
 */
public class PostgreSQLEventDao extends AbstractEventDao {
	
	public PostgreSQLEventDao(Connection conn){
		super();
		this.conn = conn;
	}

	/**
	 * Method to create a new Event.
	 * 
	 * <p>In this method, the event (originally created with index = 0) 
	 * is updated directly with a new index.</p>
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.EVENTS.getValue()
	 * when a new element is successfully created, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean create(Event obj) {
		try{
			String query="INSERT INTO "
					+ "events(full_name, short_name, place, id_dept, id_country, start_date, end_date, type)"
					+ " VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getFullName());
			state.setString(2, obj.getShortName());
			state.setString(3, obj.getPlace());
			state.setInt(4, obj.getDept().getIndex());
			state.setInt(5, obj.getCountry().getIndex());
			state.setDate(6, obj.getStartDate());
			state.setDate(7, obj.getEndDate());
			state.setInt(8, obj.getEventType().getIndex());
			
			// Run the query
			state.executeUpdate();
			
			// Update of the index (should be 0 up to this point)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIndex(genKey.getInt(1));
			};
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.EVENTS.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLEventDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to update an Event object.
	 * 
	 * <p>This method notifies the dispatcher on the suitable channel
	 * when an element is successfully updated, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean update(Event obj) {
		try{
			String query="UPDATE events SET full_name = ?, short_name = ?, place = ?,"
					+ "id_dept = ?, id_country = ?, start_date = ?, end_date = ?, type = ? WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getFullName());
			state.setString(2, obj.getShortName());
			state.setString(3, obj.getPlace());
			state.setInt(4, obj.getDept().getIndex());
			state.setInt(5, obj.getCountry().getIndex());
			state.setDate(6, obj.getStartDate());
			state.setDate(7, obj.getEndDate());
			state.setInt(8, obj.getEventType().getIndex());
			state.setInt(9, obj.getIndex());
			
			int nb_rows = state.executeUpdate();
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.EVENTS.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLEventDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to delete an Event object.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
	 * when an element is successfully deleted, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean delete(Event obj) {
		try{
			String query="DELETE FROM events WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Deleted "+nb_rows+" lines");
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.EVENTS.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLEventDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Event find(int index) {
		try{
			String query="SELECT id, full_name, short_name, place, id_dept, id_country, start_date, end_date, type"
					+ " FROM events WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			
			GeneralController gc = GeneralController.getInstance();
			
			// All you need to recover the Departement
			Dao<Entity> daoDept = (Dao<Entity>) gc.getDao(EntityEnum.DEPT.getValue());
			Departement dept = (Departement) daoDept.find(res.getInt("id_dept"));	
			
			// All you need to recover the Country
			Dao<Entity> daoCountry = (Dao<Entity>) gc.getDao(EntityEnum.COUNTRY.getValue());
			Country country = (Country) daoCountry.find(res.getInt("id_country"));	
			
			// All you need to recover the EventType
			Dao<Entity> daoEventType = (Dao<Entity>) gc.getDao(EntityEnum.EVENTTYPE.getValue());
			EventType eventType = (EventType) daoEventType.find(res.getInt("type"));
			
			// Create a suitable Event
			Event event = new Event(res.getInt("id"),
									 res.getString("full_name"),
									 res.getString("short_name"),
									 res.getString("place"),
									 dept,
									 country,
									 res.getDate("start_date"),
									 res.getDate("end_date"),
									 eventType
									 );
			res.close();
			state.close();
			return event;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLEventDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	public LinkedList<Event> findAll() {
		LinkedList<Event> data = new LinkedList<Event>();
		try{
			String query="SELECT id, full_name, short_name, place, id_dept, id_country, start_date, end_date, type"
					+ " FROM events ORDER BY start_date";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			
			GeneralController gc = GeneralController.getInstance();
			
			// All you need to recover the EventType
			Dao<Entity> daoDept = (Dao<Entity>) gc.getDao(EntityEnum.DEPT.getValue());
			Dao<Entity> daoCountry = (Dao<Entity>) gc.getDao(EntityEnum.COUNTRY.getValue());
			Dao<Entity> daoEventType = (Dao<Entity>) gc.getDao(EntityEnum.EVENTTYPE.getValue());
			
			while(res.next()){
				Departement dept = (Departement) daoDept.find(res.getInt("id_dept"));
				Country country = (Country) daoCountry.find(res.getInt("id_country"));
				EventType eventType = (EventType) daoEventType.find(res.getInt("type"));
				
				// Create a suitable Event
				Event event = new Event(res.getInt("id"),
										 res.getString("full_name"),
										 res.getString("short_name"),
										 res.getString("place"),
										 dept,
										 country,
										 res.getDate("start_date"),
										 res.getDate("end_date"),
										 eventType
										 );
				data.add(event);
			}
			System.out.println("PostgreSQLEventDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLEventDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Event> findAll(List<CatEnum> categories) {
		List<Event> data = new LinkedList<Event>();
		try{
			// Create array encoding the categories
			int nb = categories.size();
			Object[] listInt = new Object[nb];
			int i = 0;
			for (CatEnum category:categories){
				listInt[i] = category.getValue();
				i++;
			}
			Array catArray = conn.createArrayOf("INTEGER",listInt);
		
			// Generate query
			String query="SELECT events.id, full_name, short_name, place, id_dept, id_country, start_date, end_date, type, category"
					+ " FROM events, event_type_relations WHERE events.type = event_type_relations.id AND category = ANY(?)"
					+ " ORDER BY start_date";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setArray(1, catArray);
			ResultSet res = state.executeQuery();
			
			GeneralController gc = GeneralController.getInstance();
			
			// All you need to recover the EventType
			Dao<Entity> daoDept = (Dao<Entity>) gc.getDao(EntityEnum.DEPT.getValue());
			Dao<Entity> daoCountry = (Dao<Entity>) gc.getDao(EntityEnum.COUNTRY.getValue());
			Dao<Entity> daoEventType = (Dao<Entity>) gc.getDao(EntityEnum.EVENTTYPE.getValue());
			
			while(res.next()){
				Departement dept = (Departement) daoDept.find(res.getInt("id_dept"));
				Country country = (Country) daoCountry.find(res.getInt("id_country"));
				EventType eventType = (EventType) daoEventType.find(res.getInt("type"));
				
				// Create a suitable Event
				Event event = new Event(res.getInt("id"),
										 res.getString("full_name"),
										 res.getString("short_name"),
										 res.getString("place"),
										 dept,
										 country,
										 res.getDate("start_date"),
										 res.getDate("end_date"),
										 eventType
										 );
				data.add(event);
			}
			System.out.println("PostgreSQLEventDao.findAll(categories): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLEventDao.findAll(categories) -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
