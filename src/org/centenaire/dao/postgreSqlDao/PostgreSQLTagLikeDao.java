package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.abstractDao.AbstractTagLikeDao;
import org.centenaire.entity.taglike.TagLike;
import org.centenaire.entity.taglike.TagLikeFactory;

/**
 * DAO for a PostgreSQL database, relative to "TagLike" Entity.
 * 
 * <p>This implementation also includes a notification system. 
 * This component performs as Publisher.</p>
 * 
 * @param <T> Entity class associated to the editor.
 * 
 * @see org.centenaire.entity.Entity
 * @see org.centenaire.util.pubsub.Publisher
 */
public class PostgreSQLTagLikeDao<T extends TagLike> extends AbstractTagLikeDao<T> {
	private String databaseName;
	private int classIndex;
	
	/**
	 * Constructor of Dao for TagLike elements.
	 * 
	 * <p>This is a generic class used to deal with several types
	 * of TagLike elements, hence the extra arguments.</p>
	 * 
	 * @param conn
	 * 				the connection to use as support.
	 * @param databaseName
	 * 				name of the database to use (generic class).
	 * @param classIndex
	 * 				classIndex of the 'TagLike' Entity under consideration.
	 */
	public PostgreSQLTagLikeDao(Connection conn, String databaseName, int classIndex){
		super();
		this.conn = conn;
		this.databaseName = databaseName;
		this.classIndex = classIndex;
	}

	/**
	 * Method to create a new TagLike object.
	 * 
	 * <p>In this method, the tag (originally created with index = 0) 
	 * is updated directly with a new index.</p>
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.TAG.getValue()
	 * when a new element is successfully created, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean create(T obj) {
		try{
			String query=String.format("INSERT INTO %s(name) VALUES(?)", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
			int nb_rows = state.executeUpdate();
			
			// Update of the index (should be 0 up to this point)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIndex(genKey.getInt(1));
			};
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagLikeDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to update a TagLike object.
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.TAG.getValue()
	 * when an element is successfully updated, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean update(T obj) {
		try{
			String query=String.format("UPDATE %s SET name = ? WHERE id = ?", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getName());
			state.setInt(2, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to delete an Tag object.
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.TAG.getValue()
	 * when an element is successfully deleted, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean delete(T obj) {
		try{
			String query=String.format("DELETE FROM %s WHERE id = ?", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("PostgreSQLTagDao.delete: deleted "+nb_rows+" lines");
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagLikeDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public T find(int index) {
		try{
			String query=String.format("SELECT id, name FROM %s WHERE id = ?", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			
			ResultSet res = state.executeQuery();
			
			res.first();
			T tl = (T) TagLikeFactory.newElement(
										res.getInt("id"), 
										res.getString("name"), 
										this.classIndex
										);
			res.close();
			state.close();
			return tl;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagLikeDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	// Code to create a new element.
	// NB: create updates the index
	public T newElement(){
		T obj = this.newElement();
		this.create(obj);
		return obj;
	}
	
	// Returns an element of type TagLike
	// either an already existing one or
	// we create and initialize a new one in the database
	public T anyElement(){
		try{
			String query=String.format("SELECT id, name FROM %s ORDER BY id LIMIT 1", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			T tl;
			if (res.first()){
				tl = this.find(res.getInt("id"));
			} else {
				tl = this.newElement();
			}
			res.close();
			state.close();
			return tl;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagLikeDao.anyElement -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public LinkedList<T> findAll() {
		LinkedList<T> data = new LinkedList<T>();
		try{
			String query=String.format("SELECT id, name FROM %s ORDER BY id", this.databaseName);
			
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				T tl = (T) TagLikeFactory.newElement(
						res.getInt("id"),
						res.getString("name"),
						this.classIndex
						);
				data.add(tl);
			}
			System.out.println("PostgreSQLTagDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
}
