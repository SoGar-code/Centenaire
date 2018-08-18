package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.centenaire.dao.Dao;
import org.centenaire.dao.RelationDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;

/**
 * Relation DAO for a PostgreSQL database.
 * 
 * <p>This implementation also includes a notification system. 
 * This component performs as Publisher.</p>
 * 
 * @param <T> First Entity class associated to the relation.
 * @param <U> Second Entity class associated to the relation.
 * 
 * @see org.centenaire.entity.Entity
 * @see org.centenaire.util.pubsub.Publisher
 */
public class PostgreSQLRelationDao<T extends Entity, U extends Entity> extends RelationDao<T, U> {
	protected String tableName;
	protected String variableTName;
	protected String variableUName;
	protected int classIndexU;
	protected int classIndex;
	
	/**
	 * Constructor of Dao for relations.
	 * 
	 * <p>This is a generic class used to deal with several types
	 * of TagLike elements, hence the extra arguments.</p>
	 * 
	 * @param conn
	 * 				the connection to use as support.
	 * @param databaseName
	 * 				name of the table to use (generic class).
	 * @param variableTName
	 * 				name used for the index of objects of type T in the database.
	 * @param variableUName
	 * 				name used for the index of objects of type U in the database.
	 * @param classIndexU
	 * 				classIndex of the 'Entity' objects of type U.
	 * @param classIndex
	 * 				classIndex of the relation under consideration.
	 * 
	 */
	public PostgreSQLRelationDao(
			Connection conn, 
			String databaseName, 
			String variableTName,
			String variableUName,
			int classIndexU,
			int classIndex){
		super();
		this.conn = conn;
		this.tableName = databaseName;
		this.variableTName = variableTName;
		this.variableUName = variableUName;	
		this.classIndexU = classIndexU;
		this.classIndex = classIndex;
		
		
	}

	/**
	 * Create a relation between *objT* and *objU*.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
	 * when a new element is successfully created, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 */
	@Override
	public boolean create(T objT, U objU) {
		try{
			String query=String.format(
					"INSERT INTO %s(%s, %s) VALUES(?, ?)", 
					this.tableName,
					this.variableTName,
					this.variableUName
					);
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setInt(1, objT.getIndex());
			state.setInt(2, objU.getIndex());
			int nb_rows = state.executeUpdate();
			
			System.out.println("PostgreSQLRelationDao.create -- created "+nb_rows+" line.");
			
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLRelationDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * Method to delete a relation.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
	 * when a relation is successfully deleted, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean delete(T objT, U objU) {
		try{
			String query=String.format(
					"DELETE FROM %s WHERE (%s, %s) = (?, ?)", 
					this.tableName,
					this.variableTName,
					this.variableUName
					);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, objT.getIndex());
			state.setInt(2, objU.getIndex());
			
			int nb_rows = state.executeUpdate();
			System.out.println("PostgreSQLRelationDao.delete: deleted "+nb_rows+" lines");
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLRelationDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<U> findAll(T objT) {
		List<U> data = new LinkedList<U>();
		try{
			String query=String.format(
					getFormatString(classIndexU), 
					this.variableUName,
					this.tableName,
					this.variableTName
					);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, objT.getIndex());
			
			ResultSet res = state.executeQuery();
			
			// Recover suitable Dao
			Dao<U> dao = (Dao<U>) RelationDao.gc.getDao(classIndexU);
			
			while(res.next()){
				// Get the associated U object
				U objU = dao.find(res.getInt(this.variableUName));
						
				// Add it to the list
				data.add(objU);
			}
			System.out.println("PostgreSQLRelationDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLRelationDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
	
	/**
	 * Delete all relations involving the object 'objT'.
	 * 
	 * @param objT
	 */
	@Override
	public boolean deleteAll(T objT) {
		try{
			String query=String.format(
					"DELETE FROM %s WHERE %s = ?", 
					this.tableName,
					this.variableTName
					);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, objT.getIndex());
			
			int nb_rows = state.executeUpdate();
			System.out.println("PostgreSQLRelationDao.deleteAll: deleted "+nb_rows+" lines");
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLRelationDao.deleteAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	};
	
	/**
	 * Provide the format string to use in the request 'FindAll'.
	 * 
	 * <p>Concretely, this format string can be used to specify 
	 * the requested ordering for the 'Entity' instances returned
	 * by the method 'findAll'. It depends on the type of objects
	 * that should be retrieved by 'findAll'.</p>
	 * 
	 * <p>Constraint on these format strings: they should be ready 
	 * for use in a 'String.format(...)' whose arguments are (in that
	 * order): </p>
	 * <ul>
	 * 		<li>variableUName,</li>
	 *		<li>tableName,</li>
	 *		<li>variableTName.</li>
	 * </ul>
	 * 
	 * @return formatQuery, suitable format string.
	 */
	public String getFormatString(int classIndexU) {
		if (classIndexU == EntityEnum.ITEM.getValue()) {
			String formatQuery = "SELECT %1$s, type, title FROM %2$s, items "
					+ "WHERE %3$s = ? AND %1$s = items.id ORDER BY type, title ";
			return formatQuery;
		} else {
			return "SELECT %1$s FROM %2$s WHERE %3$s = ? ORDER BY %1$s";
		}
	}
}
