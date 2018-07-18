package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.main.editwindow.ExtraInfoStudent;

/**
 * DAO for a PostgreSQL database, relative to 'Individual' Entity.
 * 
 * <p>This implementation also includes a notification system 
 * for the Publisher
 * 
 * @see org.centenaire.entity.Entity
 */
public class PostgreSQLIndividualDao extends AbstractIndividualDao {
	
	public PostgreSQLIndividualDao(Connection conn){
		super();
		this.conn = conn;
	}

	/**
	 * Method to create a new Individual.
	 * 
	 * <p>In this method, the individual (originally created with index = 0) 
	 * is updated directly with a new index.</p>
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.INDIV.getValue()
	 * when a new element is successfully created, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.general.pubsub.Subscriber
	 * @see org.centenaire.general.pubsub.Dispatcher
	 */
	@Override
	public boolean create(Individual obj) {
		try{
			String query="INSERT INTO students(stud_firstname, stud_lastname) VALUES(?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getFirst_name());
			state.setString(2, obj.getLast_name());
			int nb_rows = state.executeUpdate();
			
			// Update of the index (should be 0 up to this point)
			ResultSet genKey = state.getGeneratedKeys();
			if (genKey.next()){
				obj.setIndex(genKey.getInt(1));
			};
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.INDIV.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to update an Individual object.
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.INDIV.getValue()
	 * when an element is successfully updated, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.general.pubsub.Subscriber
	 * @see org.centenaire.general.pubsub.Dispatcher
	 */
	@Override
	public boolean update(Individual obj) {
		try{
			String query="UPDATE students SET stud_firstname = ?, stud_lastname = ? WHERE id_stud = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getFirst_name());
			state.setString(2, obj.getLast_name());
			state.setInt(3, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.INDIV.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Method to delete an Individual object.
	 * 
	 * <p>This method notifies the dispatcher on channel EntityEnum.INDIV.getValue()
	 * when an element is successfully deleted, thus implementing the Publisher
	 * interface of the Publisher-Subscriber pattern.</p>
	 * 
	 * @see org.centenaire.general.pubsub.Subscriber
	 * @see org.centenaire.general.pubsub.Dispatcher
	 */
	@Override
	public boolean delete(Individual obj) {
		try{
			String query="DELETE FROM students WHERE id_stud = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Deleted "+nb_rows+" lines");
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.INDIV.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Individual find(int index) {
		try{
			String query="SELECT id_stud, stud_firstname, stud_lastname FROM students WHERE id_stud = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			Individual stud = new Individual(res.getInt("id_stud"),res.getString("stud_firstname"),res.getString("stud_lastname"));
			res.close();
			state.close();
			return stud;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	// Code to create a new element.
	// NB: create updates the index
	public Individual newElement(){
		Individual stud = Individual.defaultElement();
		this.create(stud);
		return stud;
	}

	
	public LinkedList<Individual> findAll() {
		LinkedList<Individual> data = new LinkedList<Individual>();
		try{
			String query="SELECT id_stud, stud_firstname, stud_lastname FROM students ORDER BY stud_lastname";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				Individual individual = new Individual(
						res.getInt("id_stud"),
						res.getString("stud_firstname"),
						res.getString("stud_lastname")
						);
				data.add(individual);
			}
			System.out.println("PostgreSQLStudentDao.getData(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.getData -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Individual anyElement(){
		try{
			String query="SELECT id_stud, stud_firstname, stud_lastname FROM students ORDER BY id_stud LIMIT 1";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			Individual stud;
			if (res.first()){
				stud = this.find(res.getInt("id_stud"));
			} else {
				stud = this.newElement();
			}
			res.close();
			state.close();
			return stud;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.anyElement -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<Individual> getData(boolean inverseSort) {
		LinkedList<Individual> data = new LinkedList<Individual>();
		try{
			String query="SELECT id_stud, stud_firstname, stud_lastname FROM students ORDER BY stud_lastname";
					if (inverseSort)
						query=query+" DESC";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				Individual stud = new Individual(
						res.getInt("id_stud"),
						res.getString("stud_firstname"),
						res.getString("stud_lastname")
						);
				data.add(stud);
			}
			System.out.println("PostgreSQLStudentDao.getData(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.getData -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
