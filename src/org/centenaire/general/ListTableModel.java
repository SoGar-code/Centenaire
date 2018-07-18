package org.centenaire.general;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import org.centenaire.entity.Entity;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Mark;
import org.centenaire.entity.TagLike;
import org.centenaire.general.observer.Observer;
import org.centenaire.main.statistics.Average;
import org.centenaire.main.statistics.StatisticsWindow;

/**
 * A superclass for the table models of the project.
 * 
 * <p>
 * Includes methods for:
 * <ul>
 * <li> adding and deleting data (in the table)</li>
 * <li> converting String into Date and Float</li>
 * <li> updating data</li>
 * </ul>
 * 
 */
public class ListTableModel extends AbstractTableModel implements Observer{

	private LinkedList<Entity> data;

	protected Class[] listClass;
	protected String[] title;
	protected GeneralController gc = GeneralController.getInstance();
	
	// Listeners for Student and Semester (from "Statistics" part)
	private StudentAction studAction;
	private SemesterAction semesterAction;

	public ListTableModel(Class[] listClass, String[]  title, LinkedList<Entity> data){
		super();
		this.data=data;
		this.listClass=listClass;
		this.title=title;
		studAction = new StudentAction();
		semesterAction = new SemesterAction();
	}

	// =====================================
	// Getters and setters
	public String getColumnName(int col) {
		return this.title[col];
	}

	public int getColumnCount(){
		return this.title.length;
	}

	public int getRowCount(){
		return this.data.size();
	}

	public Class getColumnClass(int col){
		return listClass[col];
	}

	/**
	 * Default implementation, to be changed depending on the specific table.
	 */
	public boolean isCellEditable(int row, int col){
		return false;
	}

	public Object getValueAt(int row, int column){
		return data.get(row).getEntry(column);
	}

	public void setValueAt(Object value, int row, int col){
		System.out.println("ListTableModel.setValueAt - col="+col+", value="+value.toString());
		data.get(row).setEntry(col, value);
	}

	public LinkedList<Entity> getData(){
		return this.data;
	}

	/**
	 * Update of the table model (and notify listeners). 
	 * 
	 * @param data
	 * 			the new data to include in the table.
	 */
	public void setData(LinkedList<Entity> data) {
		this.data = data;
		this.fireTableDataChanged();
	}
	
	
	/**
	 * Data update, provided by Observer pattern.
	 * 
	 * <p> It corresponds to data flowing from gc to the model.
	 */
	public void updateObserver(LinkedList<Entity> currentData){
		this.setData(currentData);
	}

	public void addRow(){
		gc.addRow(data);
	}

	public void saveTable(){
		gc.saveTable(data);
	}
	
	public void removeRow(int row){
		gc.removeRow(row,data);
	}
	
	public void editRow(int row){

	}
	
	public StudentAction getStudentAction(){
		return this.studAction;
	}
	
	public SemesterAction getSemesterAction(){
		return this.semesterAction;
	}
	 
	/**
	 * A class to notify 'observers' about an update about the entity Student.
	 * 
	 * <p>
	 * Several such classes are needed, for dealing with different types of Entities.
	 * 
	 * <p>
	 * Here, when selection in westList changes
	 * StudentAction identifies currentStudent
	 * and update data accordingly.
	 *
	 * @see StatisticsWindow
	 */
	class StudentAction implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent event) {
			JList<Individual> westList = (JList<Individual>)event.getSource();
			// deals with the case of an empty selection
			try{
				Individual currentStudent = westList.getSelectedValue();
				System.out.println("ListTableModel.StudentAction - current Student = "+currentStudent.toString());
				LinkedList<Mark> listMark = gc.getMarkDao().getDataOnStudent(currentStudent);
				
				// Conversion problem from LinkedList<Mark> to LinkedList<Entity>,
				// bypassed in brute force!
				data = new LinkedList<Entity>();
				for (Entity mark:listMark){
					data.add(mark);
				}
				fireTableDataChanged();
			} catch (NullPointerException e){
				// no currentStudent detected?
				System.out.println("ListTableModel.StudentAction - no current Student!");
				data = new LinkedList<Entity>();
				fireTableDataChanged();
			}

		}
	}
	
	/**
	 * A class to notify 'observers' about an update about the entity Semester.
	 * 
	 * <p>
	 * Several such classes are needed, for dealing with different types of Entities.
	 *
	 * @see StatisticsWindow
	 */
	class SemesterAction implements ListSelectionListener{
		@Override
		public void valueChanged(ListSelectionEvent event) {
			JList<TagLike> westList = (JList<TagLike>)event.getSource();
			List<TagLike> listCurrentSemester = westList.getSelectedValuesList();
			
			System.out.println("GC.SemesterAction - current Semesters contains "+listCurrentSemester.size()+" elements.");
			
			LinkedList<Average> listAverage = gc.getMarkDao().getAverage(listCurrentSemester);
			// Conversion problem from LinkedList<Mark> to LinkedList<Entity>,
			// bypassed in brute force!
			data = new LinkedList<Entity>();
			for (Entity average:listAverage){
				data.add(average);
			}
			fireTableDataChanged();
		}
	}

	  /*
	  // fonction utilisée pour la date dans TransModel
	  public static Date convertStringToDate(Object value){
		  Date date = new Date(0);
		  try{
			  date = Date.valueOf((String)value);
		  } catch (IllegalArgumentException e){
			  e.printStackTrace();
			  JOptionPane jop = new JOptionPane();
			  jop.showMessageDialog(null, "Date pas au format yyyy-mm-dd ? "+e.getMessage(),"ERREUR ds ZModel.convertStringToDate",JOptionPane.ERROR_MESSAGE);
		  } catch (Exception e){
			  e.printStackTrace();
			  JOptionPane jop = new JOptionPane();
			  jop.showMessageDialog(null, e.getMessage(),"ERREUR ds ZModel.convertStringToDate",JOptionPane.ERROR_MESSAGE);
		  }
		  return date; 
	  }
	 */
}
