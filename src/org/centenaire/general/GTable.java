package gestionBilicence.general;

import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestionBilicence.edition.Exams;
import gestionBilicence.edition.Semester;
import gestionBilicence.edition.Student;
import gestionBilicence.general.editorsRenderers.ButtonDeleteEditor;
import gestionBilicence.general.editorsRenderers.ButtonEditEditor;
import gestionBilicence.general.editorsRenderers.ButtonRenderer;
import gestionBilicence.general.editorsRenderers.Delete;
import gestionBilicence.general.editorsRenderers.Edit;
import gestionBilicence.general.editorsRenderers.FloatEditor;
import gestionBilicence.general.editorsRenderers.FloatRenderer;

public class GTable extends JScrollPane{
	/*
	 * A table adapted to our needs, with:
	 * _ scrolling
	 * _ a copy of the general controller
	 * _ a cast on the TableModel we use.
	 * _ suitable editors and renderers.
	 *  
	 * NB: the delete function is implemented in ButtonDeleteEditor
	 */
	private GeneralController gc = GeneralController.getInstance();
	private JTable table;

	public GTable(ListTableModel model) {
		super();
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		table = new JTable(){
            public void changeSelection(final int row, final int column, boolean toggle, boolean extend)
            {
                super.changeSelection(row, column, toggle, extend);
                table.editCellAt(row, column);
                table.transferFocus();
            }
		};
		table.setModel(model);
		
		table.setRowHeight(30);
	    //==================================
	    // Custom editors and renderers
	    //==================================
		
		// delete CellEditor
	    table.setDefaultEditor(Delete.class, new ButtonDeleteEditor(new JCheckBox()));
	    table.setDefaultRenderer(Delete.class, new ButtonRenderer());
	    
	    // edit CellEditor
	    table.setDefaultEditor(Edit.class, new ButtonEditEditor(new JCheckBox()));
	    table.setDefaultRenderer(Edit.class, new ButtonRenderer());
	    
	    // Semester CellEditor
	    LinkedList<Semester> listSemester = gc.getSemesterDao().getData();
	    JComboBox<Semester> comboSemester = new JComboBox<Semester>(listSemester.toArray(new Semester[listSemester.size()]));
	    table.setDefaultEditor(Semester.class, new DefaultCellEditor(comboSemester));

	    // Student CellEditor
	    LinkedList<Student> listStudent = gc.getStudentDao().getData();
	    JComboBox<Student> comboStudent = new JComboBox<Student>(listStudent.toArray(new Student[listStudent.size()]));
	    table.setDefaultEditor(Student.class, new DefaultCellEditor(comboStudent));
	    
	    // Exams CellEditor
	    LinkedList<Exams> listExams = gc.getExamsDao().getData();
	    JComboBox<Exams> comboExams = new JComboBox<Exams>(listExams.toArray(new Exams[listExams.size()]));
	    table.setDefaultEditor(Exams.class, new DefaultCellEditor(comboExams));
	    
	    // float CellEditor and renderers
	    table.setDefaultEditor(float.class, new FloatEditor(2)); // for two decimals
	    table.setDefaultRenderer(float.class, new FloatRenderer(2)); // for two decimals

	    this.setViewportView(table);
	}
	
	public ListTableModel getModel(){
		// since the only constructor involves a ListTableModel, 
		// the cast below should be safe...
		return ((ListTableModel)this.table.getModel());
	}
	
	public void updateComboStudent(){
		// Method called by GeneralPanel when currentEntity==0 (Students)
		// when "Save/update" button is pushed.
		// Only for the Mark tab
	    LinkedList<Student> listStudent = gc.getStudentDao().getData();
	    JComboBox<Student> comboStudent = new JComboBox<Student>(listStudent.toArray(new Student[listStudent.size()]));
	    table.setDefaultEditor(Student.class, new DefaultCellEditor(comboStudent));
	}
	
	public void updateComboExams(){
		// Method called by GeneralPanel when currentEntity==1 (Exams)
		// when "Save/update" button is pushed.
		// Only for the Mark tab
	    LinkedList<Exams> listExams = gc.getExamsDao().getData();
	    JComboBox<Exams> comboExams = new JComboBox<Exams>(listExams.toArray(new Exams[listExams.size()]));
	    table.setDefaultEditor(Exams.class, new DefaultCellEditor(comboExams));
	}
	
	public void updateComboSemester(){
		// Method called by GeneralPanel when currentEntity==2 (Semester)
		// when "Save/update" button is pushed.
		// Exams tab and Mark tab
	    LinkedList<Semester> listSemester = gc.getSemesterDao().getData();
	    JComboBox<Semester> comboSemester = new JComboBox<Semester>(listSemester.toArray(new Semester[listSemester.size()]));
	    this.table.setDefaultEditor(Semester.class, new DefaultCellEditor(comboSemester));
	}
	
	public void updateCombo(int index){
		switch(index){
			case 0:
				updateComboStudent();
				break;
			case 1:
				updateComboExams();
				break;
			case 2:
				updateComboSemester();
				break;
		}
	}
	
}
