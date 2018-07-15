package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.abstractDao.AbstractTagDao;
import org.centenaire.general.entities.individual.Individual;
import org.centenaire.general.entities.taglike.TagLike;

public class PostgreSQLTagDao extends AbstractTagDao {
	
	public PostgreSQLTagDao(Connection conn){
		super();
		this.conn = conn;
	}

	@Override
	public boolean create(TagLike obj) {
		try{
			String query="INSERT INTO semesters(semester_name) VALUES(?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
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
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(TagLike obj) {
		try{
			String query="UPDATE semesters SET semester_name = ? WHERE id_semester = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getName());
			state.setInt(2, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
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

	@Override
	public boolean delete(TagLike obj) {
		try{
			String query="DELETE FROM semesters WHERE id_semester = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("PostgreSQLTagDao.delete: deleted "+nb_rows+" lines");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public TagLike find(int index) {
		try{
			String query="SELECT id_semester, semester_name FROM semesters WHERE id_semester = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			TagLike semester = new TagLike(res.getInt("id_semester"),res.getString("semester_name"));
			res.close();
			state.close();
			return semester;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	// Code to create a new element.
	// NB: create updates the index
	public TagLike newElement(){
		TagLike semester = TagLike.defaultElement();
		this.create(semester);
		return semester;
	}
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public TagLike anyElement(){
		try{
			String query="SELECT id_semester, semester_name FROM semesters ORDER BY id_semester LIMIT 1";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			TagLike semester;
			if (res.first()){
				semester = this.find(res.getInt("id_semester"));
			} else {
				semester = this.newElement();
			}
			res.close();
			state.close();
			return semester;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagDao.anyElement -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	
	public LinkedList<TagLike> findAll() {
		LinkedList<TagLike> data = new LinkedList<TagLike>();
		try{
			String query="SELECT id_semester, semester_name FROM semesters ORDER BY id_semester";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				TagLike semester = new TagLike(
						res.getInt("id_semester"),
						res.getString("semester_name")
						);
				data.add(semester);
			}
			System.out.println("PostgreSQLTagDao.getData(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLTagDao.getData -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	};
}
