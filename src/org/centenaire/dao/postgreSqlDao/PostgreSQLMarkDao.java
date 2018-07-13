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

import org.centenaire.dao.abstractDao.AbstractMarkDao;
import org.centenaire.edition.entities.Exams;
import org.centenaire.edition.entities.Mark;
import org.centenaire.edition.entities.TagLike;
import org.centenaire.edition.entities.Individuals;
import org.centenaire.general.GeneralController;
import org.centenaire.statistics.Average;

public class PostgreSQLMarkDao extends AbstractMarkDao {
	
	public PostgreSQLMarkDao(Connection conn){
		super();
		this.conn = conn;
	}

	@Override
	public boolean create(Mark obj) {
		try{
			String query="INSERT INTO marks(id_exam, id_stud, mark) VALUES(?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setInt(1, obj.getExam().getIndex());
			state.setInt(2, obj.getStudent().getIndex());
			state.setFloat(3, obj.getMark());
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
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLMarkDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Mark obj) {
		try{
			String query="UPDATE Marks SET id_exam = ?, id_stud = ?, mark = ? WHERE id_mark = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getExam().getIndex());
			state.setInt(2, obj.getStudent().getIndex());
			state.setFloat(3, obj.getMark());
			state.setInt(4, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLMarkDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Mark obj) {
		try{
			String query="DELETE FROM Marks WHERE id_mark = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, obj.getIndex());
			int nb_rows = state.executeUpdate();
			System.out.println("Deleted "+nb_rows+" lines");
			state.close();
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLMarkDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Mark find(int index) {
		try{
			String query="SELECT id_exam, id_stud, mark FROM marks WHERE id_mark = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			Exams exam = GeneralController.getInstance().getExamsDao().find(res.getInt("id_exam"));
			Individuals stud = GeneralController.getInstance().getStudentDao().find(res.getInt("id_stud"));
			Mark mark = new Mark(
					index,
					res.getFloat("mark"),
					stud,
					exam
					);
			res.close();
			state.close();
			return mark;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLMarkDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	// Code to create a new element.
	// NB: create updates the index
	public Mark newElement(){
		Mark mark = Mark.defaultElement();
		this.create(mark);
		return mark;
	}

	
	public LinkedList<Mark> getData() {
		LinkedList<Mark> data = new LinkedList<Mark>();
		try{
			String query="SELECT id_mark, id_exam, id_stud, mark FROM marks ORDER BY id_exam, id_stud";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			while(res.next()){
				Exams exam = GeneralController.getInstance().getExamsDao().find(res.getInt("id_exam"));
				Individuals stud = GeneralController.getInstance().getStudentDao().find(res.getInt("id_stud"));
				Mark mark = new Mark(
						res.getInt("id_mark"),
						res.getFloat("mark"),
						stud,
						exam
						);
				data.add(mark);
			}
			System.out.println("PostgreSQLMarkDao.getData(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLMarkDao.getData -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Mark anyElement(){
		try{
			String query="SELECT id_mark, id_exam, id_stud, mark FROM marks ORDER BY id_mark LIMIT 1";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			Mark mark;
			if (res.first()){
				mark = this.find(res.getInt("id_mark"));
			} else {
				mark = this.newElement();
			}
			res.close();
			state.close();
			return mark;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLMarkDao.anyElement -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedList<Mark> getDataOnStudent(Individuals stud) {
		LinkedList<Mark> data = new LinkedList<Mark>();
		try{
			String query="SELECT id_mark, id_exam, mark FROM marks WHERE id_stud = ? ORDER BY id_exam";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, stud.getIndex());
			ResultSet res = state.executeQuery();
			while(res.next()){
				Exams exam = GeneralController.getInstance().getExamsDao().find(res.getInt("id_exam"));
				Mark mark = new Mark(
						res.getInt("id_mark"),
						res.getFloat("mark"),
						stud,
						exam
						);
				data.add(mark);
			}
			System.out.println("PostgreSQLMarkDao.getDataOnStudent: found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLMarkDao.getDataOnStudent -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public LinkedList<Average> getAverage(List<TagLike> listCurrentSemester) {
		float totalWeight = GeneralController.getInstance().getExamsDao().getTotalWeight(listCurrentSemester);
		LinkedList<Average> data = new LinkedList<Average>();
		try{
			// a view containing all required information
			String query0 = "SELECT id_stud, exams.id_exam, coefficient, mark from marks, exams "
					+ " WHERE marks.id_exam = exams.id_exam AND "
					+ " id_semester = ANY(?) ";
			String query="SELECT id_stud, SUM(mark*coefficient)/? AS average FROM ( "+query0+" ) AS view "
					+ " GROUP BY id_stud ";
			PreparedStatement state = conn.prepareStatement(query);
			
			int nb = listCurrentSemester.size();
			Object[] listInt = new Object[nb];
			int i = 0;
			for (TagLike semester:listCurrentSemester){
				listInt[i] = semester.getIndex();
				i++;
			}
			Array array = conn.createArrayOf("INTEGER",listInt);
			
			// nesting of Strings => order perturbed 
			state.setFloat(1, totalWeight);
			state.setArray(2, array);
			ResultSet res = state.executeQuery();
			while(res.next()){
				Individuals stud = GeneralController.getInstance().getStudentDao().find(res.getInt("id_stud"));
				Average average = new Average(
						stud,
						res.getFloat("average")
						);
				data.add(average);
			}
			System.out.println("PostgreSQLMarkDao.getAverage: found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLExamsDao.getAverage -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

}
