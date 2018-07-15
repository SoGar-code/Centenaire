package org.centenaire.general;

import java.util.LinkedList;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.centenaire.edition.entities.Exams;
import org.centenaire.edition.entities.Individuals;
import org.centenaire.edition.entities.taglike.TagLike;
import org.centenaire.general.editorsRenderers.ButtonDeleteEditor;
import org.centenaire.general.editorsRenderers.ButtonEditEditor;
import org.centenaire.general.editorsRenderers.ButtonRenderer;
import org.centenaire.general.editorsRenderers.Delete;
import org.centenaire.general.editorsRenderers.Edit;
import org.centenaire.general.editorsRenderers.FloatEditor;
import org.centenaire.general.editorsRenderers.FloatRenderer;

/**
 * A table adapted to our needs!
 * 
 * <p> Added functionalities include:
 * <ul>
 * <li> scrolling </li>
 * <li> a copy of the general controller </li>
 * <li> a cast on the TableModel we use, </li>
 * <li> suitable editors and renderers.</li>
 * </ul>
 * <p>
 *  
 * NB: the delete function is implemented in ButtonDeleteEditor.
 */
public class GTable extends JScrollPane{
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
	    LinkedList<TagLike> listSemester = gc.getSemesterDao().getData();
	    JComboBox<TagLike> comboSemester = new JComboBox<TagLike>(listSemester.toArray(new TagLike[listSemester.size()]));
	    table.setDefaultEditor(TagLike.class, new DefaultCellEditor(comboSemester));

	    // Student CellEditor
	    LinkedList<Individuals> listStudent = gc.getStudentDao().getData();
	    JComboBox<Individuals> comboStudent = new JComboBox<Individuals>(listStudent.toArray(new Individuals[listStudent.size()]));
	    table.setDefaultEditor(Individuals.class, new DefaultCellEditor(comboStudent));
	    
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
	    LinkedList<Individuals> listStudent = gc.getStudentDao().getData();
	    JComboBox<Individuals> comboStudent = new JComboBox<Individuals>(listStudent.toArray(new Individuals[listStudent.size()]));
	    table.setDefaultEditor(Individuals.class, new DefaultCellEditor(comboStudent));
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
	    LinkedList<TagLike> listSemester = gc.getSemesterDao().getData();
	    JComboBox<TagLike> comboSemester = new JComboBox<TagLike>(listSemester.toArray(new TagLike[listSemester.size()]));
	    this.table.setDefaultEditor(TagLike.class, new DefaultCellEditor(comboSemester));
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
