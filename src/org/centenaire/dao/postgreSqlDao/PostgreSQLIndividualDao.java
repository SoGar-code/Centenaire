package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.editor.ExtraInfoStudent;
import org.centenaire.general.entities.Exams;
import org.centenaire.general.entities.individual.Individual;

public class PostgreSQLIndividualDao extends AbstractIndividualDao {
	
	public PostgreSQLIndividualDao(Connection conn){
		super();
		this.conn = conn;
	}

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

	@Override
	public boolean delete(Individual obj) {
		try{
			String query="DELETE FROM students WHERE id_stud = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Deleted "+nb_rows+" lines");
			state.close();
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

	@Override
	public ExtraInfoStudent getInfo(Individual stud) {
		ExtraInfoStudent info = new ExtraInfoStudent();

		try{
			// First part: apb
			String query0="SELECT id_stud_apb, stud_apb FROM stud_apb WHERE id_stud =?";
			PreparedStatement state0 = conn.prepareStatement(query0,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state0.setInt(1, stud.getIndex());
			ResultSet res0 = state0.executeQuery();
			if(res0.next()){
				info.setHasApbNum(true);
				info.setApbNum(res0.getString("stud_apb"));
			}
			// else info retains its original value (false, 0 for APB)
			res0.close();
			state0.close();

			// Second part: student number
			String query1="SELECT id_stud_num, stud_number FROM stud_num WHERE id_stud =?";
			PreparedStatement state1 = conn.prepareStatement(query1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state1.setInt(1, stud.getIndex());
			ResultSet res1 = state1.executeQuery();
			if(res1.next()){
				info.setHasApogeeNum(true);
				info.setApogeeNum(res1.getString("stud_number"));
			}
			// else info retains its original value (false, 0 for student number)
			res1.close();
			state1.close();
			
			// third part: e-mail address
			String query2="SELECT id_stud_email, email FROM stud_email WHERE id_stud =?";
			PreparedStatement state2 = conn.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state2.setInt(1, stud.getIndex());
			ResultSet res2 = state2.executeQuery();
			if(res2.next()){
				info.setHasEmail(true);
				info.setEmail(res2.getString("email"));
			}
			// else info retains its original value (false, 0 for student number)
			res2.close();
			state2.close();
			
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.getInfo -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
		return info;
	}

	// updates when possible, deletes when needed.
	@Override
	public void updateInfo(Individual stud, ExtraInfoStudent info) {
		try{
			// First part: apb
			// delete everything and create again when needed
			String query0="DELETE FROM stud_apb WHERE id_stud =?";
			PreparedStatement state0 = conn.prepareStatement(query0,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state0.setInt(1, stud.getIndex());
			state0.executeUpdate();
			state0.close();
			
			if (info.isHasApbNum()){
				String query00="INSERT INTO stud_apb(stud_apb, id_stud) VALUES(?,?)";
				PreparedStatement state00 = conn.prepareStatement(query00,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				state00.setString(1, info.getApbNum());
				state00.setInt(2, stud.getIndex());
				state00.executeUpdate();
				state00.close();
			}
			
			// Second part: student number (apogee)
			String query1="DELETE FROM stud_num WHERE id_stud =?";
			PreparedStatement state1 = conn.prepareStatement(query1,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state1.setInt(1, stud.getIndex());
			state1.executeUpdate();
			state1.close();
			if (info.isHasApogeeNum()){
				String query10="INSERT INTO stud_num(stud_number, id_stud) VALUES(?,?)";
				PreparedStatement state10 = conn.prepareStatement(query10,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				state10.setString(1, info.getApogeeNum());
				state10.setInt(2, stud.getIndex());
				state10.executeUpdate();
				state10.close();
			}
			
			// third part: e-mail address
			String query2="DELETE FROM stud_email WHERE id_stud =?";
			PreparedStatement state2 = conn.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state2.setInt(1, stud.getIndex());
			state2.executeUpdate();
			state2.close();
			if (info.isHasEmail()){
				String query20="INSERT INTO stud_email(email, id_stud) VALUES(?,?)";
				PreparedStatement state20 = conn.prepareStatement(query20,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				state20.setString(1, info.getEmail());
				state20.setInt(2, stud.getIndex());
				state20.executeUpdate();
				state20.close();
			}
			
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLStudentDao.updateInfo -- ERROR!",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}

}
