package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Institution;
import org.centenaire.util.GeneralController;

/**
 * DAO for a PostgreSQL database, relative to 'Individual' Entity.
 * 
 * <p>This implementation also includes a notification system. 
 * This component performs as Publisher.</p>
 * 
 * @see org.centenaire.entity.Entity
 * @see org.centenaire.util.pubsub.Publisher
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
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean create(Individual obj) {
		try{
			String query="INSERT INTO individuals(first_name, last_name, birth_year, id_lab) VALUES(?,?,?,?)";
			PreparedStatement state = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			state.setString(1, obj.getFirst_name());
			state.setString(2, obj.getLast_name());
			state.setInt(3, obj.getBirth_year());
			state.setInt(4, obj.getLab().getIndex());
			state.executeUpdate();
			
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
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.create -- ERROR!",JOptionPane.ERROR_MESSAGE);
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
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean update(Individual obj) {
		try{
			String query="UPDATE individuals SET first_name = ?, last_name = ?, "
					+ "birth_year = ?, id_lab = ? WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, obj.getFirst_name());
			state.setString(2, obj.getLast_name());
			state.setInt(3, obj.getBirth_year());
			state.setInt(4, obj.getLab().getIndex());
			state.setInt(5, obj.getIndex());
			int nb_rows = state.executeUpdate();
			state.close();
			
			// Notify the Dispatcher on a suitable channel.
			this.publish(EntityEnum.INDIV.getValue());
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.update -- ERROR!",JOptionPane.ERROR_MESSAGE);
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
	 * @see org.centenaire.util.pubsub.Subscriber
	 * @see org.centenaire.util.pubsub.Dispatcher
	 */
	@Override
	public boolean delete(Individual obj) {
		try{
			String query="DELETE FROM individuals WHERE id = ?";
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
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.delete -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}

	@Override
	public Individual find(int index) {
		try{
			String query="SELECT id, first_name, last_name, birth_year, id_lab FROM individuals WHERE id = ?";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, index);
			ResultSet res = state.executeQuery();
			res.first();
			
			// All you need to recover the Institution
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoInstit = (Dao<Entity>) gc.getDao(EntityEnum.INSTIT.getValue());
			Institution lab = (Institution) daoInstit.find(res.getInt("id_lab"));
			
			Individual individual = new Individual(
											res.getInt("id"),
											res.getString("first_name"),
											res.getString("last_name"),
											res.getInt("birth_year"),
											lab
											 );
			res.close();
			state.close();
			return individual;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.find -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}

	public LinkedList<Individual> findAll() {
		LinkedList<Individual> data = new LinkedList<Individual>();
		try{
			String query="SELECT id, first_name, last_name, birth_year, id_lab FROM individuals ORDER BY last_name";
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet res = state.executeQuery();
			
			// All you need to recover the Institution
			GeneralController gc = GeneralController.getInstance();
			Dao<Entity> daoInstit = (Dao<Entity>) gc.getDao(EntityEnum.INSTIT.getValue());
			
			while(res.next()){
				// Get the lab first
				Institution lab = (Institution) daoInstit.find(res.getInt("id_lab"));
				
				// Then create the individual
				Individual individual = new Individual(
						res.getInt("id"),
						res.getString("first_name"),
						res.getString("last_name"),
						res.getInt("birth_year"),
						lab
						);
				data.add(individual);
			}
			System.out.println("PostgreSQLIndividualDao.findAll(): found "+data.size()+" lines.");
			res.close();
			state.close();
			return data;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.findAll -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Generic method to set a string 'content' into a prescribed variable 'variableName'.
	 * 
	 * <p>This method does <em>not</em> trigger an update on the 'Individual' channel.</p>
	 * 
	 * @param indiv
	 * @param variableName
	 * @param content
	 * @return
	 */
	public boolean setStringContent(Individual indiv, String variableName, String content) {
		try{
			String query=String.format(
					"UPDATE individuals SET %s = ? WHERE id = ?",
					variableName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, content);
			state.setInt(2, indiv.getIndex());
			
			int nb_rows = state.executeUpdate();
			state.close();
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.setStringContent -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean setQ1(Individual indiv, String content) {
		return setStringContent(indiv, "question_one", content);
	}

	@Override
	public boolean setQ2(Individual indiv, String content) {
		return setStringContent(indiv, "question_two", content);
	}
	
	@Override
	public boolean setQ3(Individual indiv, String content) {
		return setStringContent(indiv, "question_three", content);
	}
	
	@Override
	public boolean setQuestionConcern(Individual indiv, String content) {
		return setStringContent(indiv, "question_concern", content);
	}

	@Override
	public boolean setQuestionComittee(Individual indiv, String content) {
		return setStringContent(indiv, "question_comittee", content);
	}

	@Override
	public boolean setQuestionContribution(Individual indiv, String content) {
		return setStringContent(indiv, "question_contribution", content);
	}

	@Override
	public boolean setQuestionDev(Individual indiv, String content) {
		return setStringContent(indiv, "question_dev", content);
	}
	
	/**
	 * Generic method to set an integer 'content' into a prescribed variable 'variableName'.
	 * 
	 * <p>This method does <em>not</em> trigger an update on the 'Individual' channel.</p>
	 * 
	 * @param indiv
	 * @param variableName
	 * @param content
	 * @return
	 */
	public boolean setIntContent(Individual indiv, String variableName, int content) {
		try{
			String query=String.format(
					"UPDATE individuals SET %s = ? WHERE id = ?",
					variableName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, content);
			state.setInt(2, indiv.getIndex());
			
			int nb_rows = state.executeUpdate();
			state.close();
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.setIntContent -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean setPhdDefenseYear(Individual indiv, int content) {
		return setIntContent(indiv, "phd_defense_year", content);
	}
	
	/**
	 * Generic method to set a boolean 'content' into a prescribed variable 'variableName'.
	 * 
	 * <p>This method does <em>not</em> trigger an update on the 'Individual' channel.</p>
	 * 
	 * @param indiv
	 * @param variableName
	 * @param content
	 * @return
	 */
	public boolean setBooleanContent(Individual indiv, String variableName, boolean content) {
		try{
			String query=String.format(
					"UPDATE individuals SET %s = ? WHERE id = ?",
					variableName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setBoolean(1, content);
			state.setInt(2, indiv.getIndex());
			
			int nb_rows = state.executeUpdate();
			state.close();
			
			return true;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.setBooleanContent -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public boolean setPhdOnGreatWar(Individual indiv, boolean content) {
		return setBooleanContent(indiv, "phd_on_great_war", content);
	}
	
	@Override
	public boolean setHabilitationOnGreatWar(Individual indiv, boolean content) {
		return setBooleanContent(indiv, "habilitation_on_great_war", content);
	}
	
	/**
	 * Used to set the number of students in question I.6.e.
	 * 
	 * @see org.centenaire.main.questionnaire.QuestionMasterSeminar
	 */
	@Override
	public boolean setNbStudents(Individual indiv, int yearIndex, int content) {
		if ((yearIndex <6) && (0<=yearIndex)) {
			String variableName = String.format("nb_stud_%s", yearIndex);
			setIntContent(indiv, variableName, content);
			return true;
		} else {
			String msg = String.format("PostgreSQLIndividualDao.setNbStudents -- Unknown yearIndex '%s'", yearIndex);
			System.out.println(msg);
			return false;
		}
	}
	
	@Override
	public boolean setQuestionInstitNonSci(Individual indiv, String qII4String) {
		return setStringContent(indiv, "question_instit_non_sci", qII4String);
	}
	
	/**
	 * Generic method to get a string 'content' from a prescribed variable 'variableName'.
	 * 
	 * @param indiv
	 * @param variableName
	 * @param content
	 * @return
	 */
	public String getStringContent(Individual indiv, String variableName) {
		try{
			String query=String.format(
					"SELECT %s FROM individuals WHERE id = ?",
					variableName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, indiv.getIndex());
			ResultSet res = state.executeQuery();
			res.first();
			
			String content = res.getString(variableName);
			
			res.close();
			state.close();
			return content;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.getStringContent -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}	
	}
	
	@Override
	public String getQ1(Individual indiv) {
		return getStringContent(indiv, "question_one");
	}

	@Override
	public String getQ2(Individual indiv) {
		return getStringContent(indiv, "question_two");
	}
	
	@Override
	public String getQ3(Individual indiv) {
		return getStringContent(indiv, "question_three");
	}

	@Override
	public String getQuestionConcern(Individual indiv) {
		return getStringContent(indiv, "question_concern");
	}

	@Override
	public String getQuestionComittee(Individual indiv) {
		return getStringContent(indiv, "question_comittee");
	}

	@Override
	public String getQuestionContribution(Individual indiv) {
		return getStringContent(indiv, "question_contribution");
	}

	@Override
	public String getQuestionDev(Individual indiv) {
		return getStringContent(indiv, "question_dev");
	}
	
	public int getIntContent(Individual indiv, String variableName) {
		try{
			String query=String.format(
					"SELECT %s FROM individuals WHERE id = ?",
					variableName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, indiv.getIndex());
			ResultSet res = state.executeQuery();
			res.first();
			
			int content = res.getInt(variableName);
			
			res.close();
			state.close();
			return content;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.getIntContent -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return -1;
		} catch (Exception e){
			e.printStackTrace();
			return -1;
		}	
	}

	@Override
	public int getPhdDefenseYear(Individual indiv) {
		return getIntContent(indiv, "phd_defense_year");
	}
	
	public boolean getBooleanContent(Individual indiv, String variableName) {
		try{
			String query=String.format(
					"SELECT %s FROM individuals WHERE id = ?",
					variableName);
			PreparedStatement state = conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, indiv.getIndex());
			ResultSet res = state.executeQuery();
			res.first();
			
			boolean content = res.getBoolean(variableName);
			
			res.close();
			state.close();
			return content;
		} catch (SQLException e){
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"PostgreSQLIndividualDao.getBooleanContent -- ERROR!",JOptionPane.ERROR_MESSAGE);
			return false;
		} catch (Exception e){
			e.printStackTrace();
			return false;
		}	
	}
	
	@Override
	public boolean getPhdOnGreatWar(Individual indiv) {
		return getBooleanContent(indiv, "phd_on_great_war");
	}

	@Override
	public boolean getHabilitationOnGreatWar(Individual indiv) {
		return getBooleanContent(indiv, "habilitation_on_great_war");
	}

	@Override
	public int getNbStudents(Individual indiv, int yearIndex) {
		if ((yearIndex <6) && (0<=yearIndex)) {
			String variableName = String.format("nb_stud_%s", yearIndex);
			return getIntContent(indiv, variableName);
		} else {
			String msg = String.format("PostgreSQLIndividualDao.getNbStudents -- Unknown yearIndex '%s'", yearIndex);
			System.out.println(msg);
			return -1;
		}

	}

	@Override
	public String getQuestionInstitNonSci(Individual indiv) {
		return getStringContent(indiv, "question_instit_non_sci");
	};
}
