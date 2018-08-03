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
import org.centenaire.entity.Item;
import org.centenaire.entity.ItemType;
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
public class PostgreSQLItemDao extends Dao<Item> {
	
	public PostgreSQLItemDao(Connection conn){
		super();
		this.conn = conn;
	}

	/**
	 * Method to create a new Item.
	 * 
	 * <p>In this method, the event (originally created with index = 0) 
	 * is updated directly with a new index.</p>
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.ITEM.getValue()
	 * when a new element is successfully created, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean create(Item obj) {
		try{
			String query="INSERT INTO items(title, start_date, end_date, type) VALUES(?,?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getTitle());
			state.setDate(2, obj.getStartDate());
			state.setDate(3, obj.getEndDate());
			state.setInt(4, obj.getItemType().getIndex());
			
			// Run the query
			state.executeUpdate();
			
			// Update of the index (should be 0 up to this point)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIndex(genKey.getInt(1));
			};
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.ITEM.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLItemDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to update an Item object.
	 * 
	 * <p>This method notifies the dispatcher on the suitable channel
	 * when an element is successfully updated, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean update(Item obj) {
		try{
			String query="UPDATE items SET title = ?, start_date = ?, end_date = ?, type = ? WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getTitle());
			state.setDate(2, obj.getStartDate());
			state.setDate(3, obj.getEndDate());
			state.setInt(4, obj.getItemType().getIndex());
			state.setInt(5, obj.getIndex());

			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.ITEM.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLItemDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to delete an Item object.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
	 * when an element is successfully deleted, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean delete(Item obj) {
		try{
			String query="DELETE FROM items WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Deleted "+nb_rows+" lines");
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.ITEM.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLItemDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Item find(int index) {
		try{
			String query="SELECT id, title, start_date, end_date, type FROM items WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			
			// All you need to recover the EventType
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoItemType = (Dao<Entity>) gc.getDao(EntityEnum.ITEMTYPE.getValue());
			ItemType itemType = (ItemType) daoItemType.find(res.getInt("type"));
			
			// Create a suitable Item
			Item item = new Item(res.getInt("id"),
									 res.getString("title"),
									 itemType,
									 res.getDate("start_date"),
									 res.getDate("end_date")
									 );
			res.close();
			state.close();
			return item;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLItemDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	public LinkedList<Item> findAll() {
		LinkedList<Item> data = new LinkedList<Item>();
		try{
			String query="SELECT id, title, start_date, end_date, type FROM items ORDER BY start_date";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			
			// All you need to recover the EventType
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoItemType = (Dao<Entity>) gc.getDao(EntityEnum.ITEMTYPE.getValue());
			
			while(res.next()){
				ItemType itemType = (ItemType) daoItemType.find(res.getInt("type"));
				
				// Create a suitable Item
				Item item = new Item(res.getInt("id"),
										 res.getString("title"),
										 itemType,
										 res.getDate("start_date"),
										 res.getDate("end_date")
										 );
				data.add(item);
			}
			System.out.println("PostgreSQLItemDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLItemDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
