package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.Dao;
import org.centenaire.entity.typelike.CatEnum;
import org.centenaire.entity.typelike.TypeLike;

/**
 * DAO for a PostgreSQL database, relative to "TypeLike" Entity.
 * 
 * <p>This implementation also includes a notification system. 
 * This component performs as Publisher.</p>
 * 
 * @param <T> Entity class associated to the editor.
 * 
 * @see org.centenaire.entity.Entity
 * @see org.centenaire.util.pubsub.Publisher
 */
public class PostgreSQLTypeLikeDao<T extends TypeLike> extends Dao<T> {
	private String databaseName;
	private int classIndex;
	
	/**
	 * Constructor of Dao for TypeLike elements.
	 * 
	 * <p>This is a generic class used to deal with several types
	 * of TypeLike elements, hence the extra arguments.</p>
	 * 
	 * @param conn
	 * 				the connection to use as support.
	 * @param databaseName
	 * 				name of the database to use (generic class).
	 * @param classIndex
	 * 				classIndex of the 'TypeLike' Entity under consideration.
	 */
	public PostgreSQLTypeLikeDao(Connection conn, String databaseName, int classIndex){
		super();
		this.conn = conn;
		this.databaseName = databaseName;
		this.classIndex = classIndex;
	}

	/**
	 * Method to create a new TypeLike object.
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
			String query=String.format("INSERT INTO %s(name, category) VALUES(?, ?)", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
			state.setInt(2, obj.getCategory().getValue());
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
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTypeLikeDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to update a TypeLike object.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
	 * when an element is successfully updated, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean update(T obj) {
		try{
			String query=String.format("UPDATE %s SET name = ?, category = ? WHERE id = ?", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getName());
			state.setInt(2, obj.getCategory().getValue());
			state.setInt(3, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTypeLikeDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to delete a TypeLike object.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
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
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTypeLikeDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public T find(int index) {
		try{
			String query=String.format("SELECT id, name, category FROM %s WHERE id = ?", this.databaseName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			
			ResultSet res = state.executeQuery();
			
			res.first();
			
			// Recover the requested category
			int catIndex = res.getInt("category");
			CatEnum category = CatEnum.values()[catIndex];
			
			T tl = (T) TypeLike.newElement(
										res.getInt("id"), 
										res.getString("name"),
										category,
										this.classIndex
										);
			res.close();
			state.close();
			return tl;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTypeLikeDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}	
	
	public LinkedList<T> findAll() {
		LinkedList<T> data = new LinkedList<T>();
		try{
			String query=String.format("SELECT id, name, category FROM %s ORDER BY category, id", this.databaseName);
			
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				// Recover the requested category
				int catIndex = res.getInt("category");
				CatEnum category = CatEnum.values()[catIndex];
				
				T tl = (T) TypeLike.newElement(
						res.getInt("id"),
						res.getString("name"),
						category,
						this.classIndex
						);
				data.add(tl);
			}
			System.out.println("PostgreSQLTypeLikeDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTypeLikeDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
}
