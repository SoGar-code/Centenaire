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
import org.centenaire.entity.Institution;
import org.centenaire.entity.InstitutionType;
import org.centenaire.util.GeneralController;

/**
 * DAO for a PostgreSQL database, relative to 'Institution' Entity.
 * 
 * <p>This implementation also includes a notification system. 
 * This component performs as Publisher.</p>
 * 
 * @see org.centenaire.entity.Entity
 * @see org.centenaire.util.pubsub.Publisher
 */
public class PostgreSQLInstitutionDao extends Dao<Institution> {
	
	public PostgreSQLInstitutionDao(Connection conn){
		super();
		this.conn = conn;
	}

	/**
	 * Method to create a new Institution.
	 * 
	 * <p>In this method, the event (originally created with index = 0) 
	 * is updated directly with a new index.</p>
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.INSTIT.getValue()
	 * when a new element is successfully created, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean create(Institution obj) {
		try{
			String query="INSERT INTO institutions(name, place, type) VALUES(?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
			state.setString(2, obj.getPlace());
			state.setInt(3, obj.getInstitType().getIndex());
			
			// Run the query
			state.executeUpdate();
			
			// Update of the index (should be 0 up to this point)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIndex(genKey.getInt(1));
			};
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.INSTIT.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLInstitutionDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to update an Institution object.
	 * 
	 * <p>This method notifies the dispatcher on the suitable channel
	 * when an element is successfully updated, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean update(Institution obj) {
		try{
			String query="UPDATE institutions SET name = ?, place = ?, type = ? WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getName());
			state.setString(2, obj.getPlace());
			state.setInt(3, obj.getInstitType().getIndex());
			state.setInt(4, obj.getIndex());

			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.INSTIT.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLInstitutionDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to delete an Institution object.
	 * 
	 * <p>This method notifies the dispatcher on a suitable channel
	 * when an element is successfully deleted, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean delete(Institution obj) {
		try{
			String query="DELETE FROM institutions WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Deleted "+nb_rows+" lines");
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.INSTIT.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLInstitutionDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Institution find(int index) {
		try{
			String query="SELECT id, name, place, type FROM institutions WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			
			// All you need to recover the EventType
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoInstitType = (Dao<Entity>) gc.getDao(EntityEnum.INSTITTYPE.getValue());
			InstitutionType institType = (InstitutionType) daoInstitType.find(res.getInt("type"));
			
			// Create a suitable object
			Institution instit = new Institution(res.getInt("id"),
							 					 res.getString("name"),
							 					 res.getString("place"),
							 					 institType
							 					 );
			res.close();
			state.close();
			return instit;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLInstitutionDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	public LinkedList<Institution> findAll() {
		LinkedList<Institution> data = new LinkedList<Institution>();
		try{
			String query="SELECT id, name, place, type FROM institutions ORDER BY name";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			
			// All you need to recover the EventType
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoInstitType = (Dao<Entity>) gc.getDao(EntityEnum.INSTITTYPE.getValue());
			
			while(res.next()){
				InstitutionType institType = (InstitutionType) daoInstitType.find(res.getInt("type"));
				
				// Create a suitable object
				Institution instit = new Institution(res.getInt("id"),
								 					 res.getString("name"),
								 					 res.getString("place"),
								 					 institType
								 					 );
				data.add(instit);
			}
			System.out.println("PostgreSQLInstitutionDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLInstitutionDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
