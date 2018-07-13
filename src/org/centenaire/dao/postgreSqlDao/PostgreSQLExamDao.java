package org.centenaire.dao.postgreSqlDao;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import org.centenaire.dao.abstractDao.AbstractExamsDao;
import org.centenaire.edition.entities.Exams;
import org.centenaire.edition.entities.TagLike;
import org.centenaire.general.GeneralController;

public class PostgreSQLExamDao extends AbstractExamsDao {
	
	public PostgreSQLExamDao(Connection conn){
		super();
		this.conn = conn;
	}

	@Override
	public boolean create(Exams obj) {
		try{
			String query="INSERT INTO exams(name, id_semester, coefficient) VALUES(?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getName());
			state.setInt(2, obj.getSemester().getIndex());
			state.setInt(3, obj.getCoefficient());
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
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Exams obj) {
		try{
			String query="UPDATE exams SET name = ?, id_semester = ?, coefficient = ? WHERE id_exam = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getName());
			state.setInt(2, obj.getSemester().getIndex());
			state.setInt(3, obj.getCoefficient());
			state.setInt(4, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Exams obj) {
		try{
			String query="DELETE FROM exams WHERE id_exam = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("PostgreSQLExamsDao.delete: deleted "+nb_rows+" lines");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Exams find(int index) {
		try{
			String query="SELECT id_exam, name, id_semester, coefficient FROM exams WHERE id_exam = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			TagLike semester = GeneralController.getInstance().getSemesterDao().find(res.getInt("id_semester"));
			Exams exam = new Exams(
					res.getInt("id_exam"),
					res.getString("name"),
					semester,
					res.getInt("coefficient")
					);
			res.close();
			state.close();
			return exam;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	// Code to create a new element.
	// NB: create updates the index
	public Exams newElement(){
		Exams exam = Exams.defaultElement();
		this.create(exam);
		return exam;
	}
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public Exams anyElement(){
		try{
			String query="SELECT id_exam, name, id_semester, coefficient FROM exams ORDER BY id_exam LIMIT 1";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			Exams exam;
			if (res.first()){
				exam = this.find(res.getInt("id_exam"));
			} else {
				exam = this.newElement();
			}
			res.close();
			state.close();
			return exam;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.anyElement -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public LinkedList<Exams> getData() {
		LinkedList<Exams> data = new LinkedList<Exams>();
		try{
			String query="SELECT id_exam, name, id_semester, coefficient FROM exams ORDER BY id_exam";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				TagLike semester = GeneralController.getInstance().getSemesterDao().find(res.getInt("id_semester"));
				Exams exam = new Exams(
						res.getInt("id_exam"),
						res.getString("name"),
						semester,
						res.getInt("coefficient")
						);
				data.add(exam);
			}
			System.out.println("PostgreSQLExamsDao.getData(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.getData -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public float getTotalWeight(List<TagLike> listCurrentSemester) {
		float totalWeight;
		try{
			String query="SELECT SUM(coefficient) AS totalweight FROM exams "
					+ " WHERE id_semester = ANY(?) ";
			PreparedStatement state = conn.prepareStatement(query);
			int nb = listCurrentSemester.size();
			Object[] listInt = new Object[nb];
			int i = 0;
			for (TagLike semester:listCurrentSemester){
				listInt[i] = semester.getIndex();
				i++;
			}
			Array array = conn.createArrayOf("INTEGER",listInt);
			state.setArray(1, array);
			ResultSet res = state.executeQuery();
			res.next();
			totalWeight = res.getFloat("totalweight");
			System.out.println("PostgreSQLExamsDao.getTotalWeight(): "+totalWeight);
			res.close();
			state.close();
			return totalWeight;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.getTotalWeight -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return 0;
		} catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	};

}
