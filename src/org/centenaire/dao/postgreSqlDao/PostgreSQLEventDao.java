package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.EventType;
import org.centenaire.entity.Individual;
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
public class PostgreSQLEventDao extends Dao<Event> {
	
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
			String query="INSERT INTO events(full_name, short_name, place, start_date, end_date, type) VALUES(?,?,?,?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getFullName());
			state.setString(2, obj.getShortName());
			state.setString(3, obj.getPlace());
			state.setDate(4, obj.getStartDate());
			state.setDate(5, obj.getEndDate());
			state.setInt(6, obj.getEventType().getIndex());
			
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
			String query="UPDATE events SET full_name = ?, short_name = ?, place = ?, start_date = ?, end_date = ?, type = ? WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getFullName());
			state.setString(2, obj.getShortName());
			state.setString(3, obj.getPlace());
			state.setDate(4, obj.getStartDate());
			state.setDate(5, obj.getEndDate());
			state.setInt(6, obj.getEventType().getIndex());

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
			String query="SELECT id, full_name, short_name, place, start_date, end_date, type FROM events WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			
			// All you need to recover the EventType
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoEventType = (Dao<Entity>) gc.getDao(EntityEnum.EVENTTYPE.getValue());
			EventType eventType = (EventType) daoEventType.find(res.getInt("type"));
			
			// Create a suitable Event
			Event event = new Event(res.getInt("id"),
									 res.getString("full_name"),
									 res.getString("short_name"),
									 res.getString("place"),
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
			String query="SELECT id, full_name, short_name, place, start_date, end_date, type FROM events ORDER BY start_date";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			
			// All you need to recover the EventType
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoEventType = (Dao<Entity>) gc.getDao(EntityEnum.EVENTTYPE.getValue());
			
			while(res.next()){
				EventType eventType = (EventType) daoEventType.find(res.getInt("type"));
				
				// Create a suitable Event
				Event event = new Event(res.getInt("id"),
										 res.getString("full_name"),
										 res.getString("short_name"),
										 res.getString("place"),
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
}
