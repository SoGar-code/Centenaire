package gestionBilicence.edition;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import gestionBilicence.general.Entity;
import gestionBilicence.general.GTable;
import gestionBilicence.general.GeneralWindow;
import gestionBilicence.general.ListTableModel;
import gestionBilicence.general.editorsRenderers.Delete;
import gestionBilicence.general.editorsRenderers.Edit;

/**
 * Window to edit elements in the database
 * contains "New line" and "Save/update" buttons,
 * together with their listeners.
 * 
 * NB: GeneralWindow provides a copy of gc (GeneralController)
 */

public class EditionWindow extends GeneralWindow {
	
	private JPanel pan;
	private JButton saveButton;
	private JButton newLineButton;
	//private JButton editButton;
	private JTabbedPane tabbedPane;

	public EditionWindow(){
		super();
		
		//======================
		// Creation of the different tabs
		//======================
		
		LinkedList<Entity>[] dataVect = (LinkedList<Entity>[]) new LinkedList<?>[4];
		ListTableModel[] listTableModelVect = new ListTableModel[4];
		GTable[] tabEntityVect = new GTable[4];
		
		// Creation of student tab (already initialized):
		listTableModelVect[0] = new ListTableModel(
				new Class[] {String.class, String.class, Edit.class, Delete.class},
				new String[] {"First name","Family Name","Edit","Delete"},
				gc.getCurrentData() // at that point, we should have currentEntity=0
				);
		// include listTableModel as observer of gc (changes in the data).
		gc.addObserver(listTableModelVect[0]);
		tabEntityVect[0] = new GTable(listTableModelVect[0]);
		
		// Creation of semester tab:
		listTableModelVect[1] = new ListTableModel(
				new Class[] {String.class, Delete.class},
				new String[] {"Name","Delete"},
				new LinkedList<Entity>()
				);
		gc.addObserver(listTableModelVect[1]);
		tabEntityVect[1] = new GTable(listTableModelVect[1]);
		
		// Creation of exams tab:
		listTableModelVect[2] = new ListTableModel(
				new Class[] {String.class, Semester.class, Integer.class,Delete.class},
				new String[] {"Name","Semester","Coefficient", "Delete"},
				new LinkedList<Entity>()
				);
		gc.addObserver(listTableModelVect[2]);
		tabEntityVect[2] = new GTable(listTableModelVect[2]);
		
		// Creation of marks tab:
		listTableModelVect[3] = new ListTableModel(
				new Class[] {Exams.class, Student.class, float.class, Delete.class},
				new String[] {"Exam","Student","Mark","Delete"},
				new LinkedList<Entity>()
				);
		gc.addObserver(listTableModelVect[3]);
		tabEntityVect[3] = new GTable(listTableModelVect[3]);
		
		// Final assembly into a tabbed panel.
		tabbedPane = new JTabbedPane();
		String[] listTabs = {"Students","Semesters","Exams","Marks"};
		for (int i = 0; i <= 3; i++){
			tabbedPane.addTab(listTabs[i],tabEntityVect[i]);
		}
		
		// Listeners of the tabbedPane
		tabbedPane.addChangeListener(gc);
		
		//======================
		// Buttons and listeners
		//======================
		newLineButton = new JButton("New line");
		//editButton = new JButton("Edit/More info");
		saveButton = new JButton("Save/update");
	    
		// Keeping the listeners as generic as possible
		// while putting the "data methods" in the TableModel
		
		// listener on the "New line" button
		class AddListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				((GTable) tabbedPane.getSelectedComponent()).getModel().addRow();
			}
		}
		
		/*
		// listener on the "Edit" button
		class EditListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				int currentEntity = tabbedPane.getSelectedIndex();
				// currentEntity==0 corresponds to Student
				if (currentEntity==0){
					EditStudentDialog studDialog = new EditStudentDialog(Student.defaultElement());
					studDialog.showEditStudentDialog();
				}
				//((GTable) tabbedPane.getSelectedComponent()).getModel().addRow();
			}
		}
		*/
		
		// listener on the "Save/update" button
		class SaveListener implements ActionListener{
			public void actionPerformed(ActionEvent event){
				((GTable) tabbedPane.getSelectedComponent()).getModel().saveTable();
				
				// if currentEntity == 0 or 2, notify the Marks tab
				Set<Integer> indices = new HashSet<Integer>(Arrays.asList(0,2));
				int currentEntity = tabbedPane.getSelectedIndex();
				if (indices.contains(currentEntity)){
					((GTable) tabbedPane.getComponent(3)).updateCombo(currentEntity);
				}
				// currentEntity == 1 i.e. Semesters
				if (currentEntity==1){
					// update comboSemester for Exams and Marks tab
					((GTable) tabbedPane.getComponent(1)).updateComboSemester();
					((GTable) tabbedPane.getComponent(3)).updateComboSemester();
				}
			}
		}
	    
	    newLineButton.addActionListener(new AddListener());
	    //editButton.addActionListener(new EditListener());
	    saveButton.addActionListener(new SaveListener());
		
		
		//======================
		// Final assembly
		//======================
		
		// Assembling pan (with buttons)
	    GridLayout gl = new GridLayout(1,2);
	    gl.setHgap(5);
	    pan = new JPanel(gl);
	    pan.add(newLineButton);
	    //pan.add(editButton);
	    pan.add(saveButton);
		
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
	    this.add(pan, BorderLayout.SOUTH);
	}
}
