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
import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.Entity;

/**
 * Relation DAO for a PostgreSQL database.
 * 
 * <p>A key property of this implementation is that the instances in 
 * 'DoubleEntity<U, V>' share the same 'index' as their first components (here, the object 'U').</p>
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
public class PostgreSQLLabelRelationDao<T extends Entity, U extends Entity, V extends Entity> 
							extends PostgreSQLRelationDao<T, DoubleEntity<U, V>> {
	private String variableVName;
	private int classIndexV;
	/**
	 * Constructor of Dao for relations.
	 * 
	 * <p>This is a generic class used to deal with several types
	 * of TagLike elements, hence the extra arguments.</p>
	 * 
	 * @param conn
	 * 				the connection to use as support.
	 * @param databaseName
	 * 				name of the database to use (generic class).
	 * @param variableTName
	 * 				name used for the index of objects of type T in the database.
	 * @param variableUName
	 * 				name used for the index of objects of type U in the database.
	 * @param classIndexU
	 * 				classIndex of the 'Entity' objects of type U.
	 * @param classIndexV
	 * 				classIndex of the 'Entity' objects of type V.
	 * @param classIndex
	 * 				classIndex of the relation under consideration.
	 * 
	 */
	public PostgreSQLLabelRelationDao(
			Connection conn, 
			String databaseName, 
			String variableTName,
			String variableUName,
			String variableVName,
			int classIndexU,
			int classIndexV,
			int classIndex){
		super(
				conn, 
				databaseName, 
				variableTName,
				variableUName,
				classIndexU,
				classIndex
				);
		this.variableVName = variableVName;
		this.classIndexV = classIndexV;
	}

	/**
	 * Create a relation between *objT* and *doubleObj*.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
	 * when a new element is successfully created, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 */
	@Override
	public boolean create(T objT, DoubleEntity<U, V> doubleObj) {
		try{
			String query=String.format(
					"INSERT INTO %s(%s, %s, %s) VALUES(?, ?, ?)", 
					this.databaseName,
					this.variableTName,
					this.variableUName,
					this.variableVName
					);
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setInt(1, objT.getIndex());
			state.setInt(2, doubleObj.getIndex());
			state.setInt(3, doubleObj.getObjU().getIndex());
			int nb_rows = state.executeUpdate();
			
			System.out.println("PostgreSQLLabelRelationDao.create -- created "+nb_rows+" line.");
			
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(this.classIndex);
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLLabelRelationDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
		
	}

	/**
	 * 
	 */
	@Override
	public List<DoubleEntity<U, V>> findAll(T objT) {
		List<DoubleEntity<U, V>> data = new LinkedList<DoubleEntity<U, V>>();
		try{
			String query=String.format(
					"SELECT %s, %s FROM %s WHERE %s = ? ORDER BY %s", 
					this.variableUName,
					this.variableVName,
					this.databaseName,
					this.variableTName,
					this.variableUName
					);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, objT.getIndex());
			
			ResultSet res = state.executeQuery();
			
			// Recover suitable Dao
			Dao<U> daoU = (Dao<U>) RelationDao.gc.getDao(classIndexU);
			Dao<V> daoV = (Dao<V>) RelationDao.gc.getDao(classIndexV);
			
			while(res.next()){
				// Get the associated U object
				U objU = daoU.find(res.getInt(this.variableUName));

				// Get the associated V object
				V objV = daoV.find(res.getInt(this.variableVName));
				
				// Create DoubleEntity<U, V>
				DoubleEntity<U, V> doubleObj = new DoubleEntity<U, V>(objU, objV);
				
				// Add it to the list
				data.add(doubleObj);
			}
			System.out.println("PostgreSQLLabelRelationDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLLabelRelationDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
}
