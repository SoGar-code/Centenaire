package org.centenaire.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.centenaire.editor.entities.IndividualTab;
import org.centenaire.editor.entities.TagLikeTab;
import org.centenaire.general.GTable;
import org.centenaire.general.GeneralWindow;

/**
 * Window to edit elements in the database
 * 
 * <p>
 * contains "New line" and "Save/update" buttons,
 * together with their listeners.
 * 
 * NB: GeneralWindow provides a copy of gc (GeneralController)
 * 
 */
public class EditionWindow extends GeneralWindow {
	
	private JPanel pan;
	private JButton saveButton;
	private JButton newLineButton;
	//private JButton editButton;
	private JTabbedPane tabbedPane;

	public EditionWindow(){
		super();
		
		// Initialize basic content
		tabbedPane = new JTabbedPane();
		
		// Final assembly into a tabbed panel.
		IndividualTab indivTab = new IndividualTab();
		tabbedPane.addTab("Personnes",indivTab);
		
		// Final assembly into a tabbed panel.
		TagLikeTab tagLikeTab = new TagLikeTab(4);
		tabbedPane.addTab("Tags",tagLikeTab);
		
		String[] listTabs = {"Institutions","Evénements"};
		String name;
		for (int i = 0; i < 2; i++){
			name = listTabs[i];
			JTabbedPane aux = this.createTabbedView(name);
			tabbedPane.addTab(name,aux);
		}
		
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
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
	}
	
	public JTabbedPane createTabbedView(String name) {
		JTabbedPane tabbedView = new JTabbedPane(JTabbedPane.LEFT);
		
		// Creates 3 tabs with suitable characteristics
		// ============================================
		
		// Tab with list
		String content = String.format("Fenêtre 'liste' de %s", name);
		JLabel listTab = new JLabel(content);
		tabbedView.add("Liste", listTab);
		
		// Tab with edit
		content = String.format("Fenêtre 'modifier' de %s", name);
		JLabel editTab = new JLabel(content);
		tabbedView.add("Modifier", editTab);
		
		// Tab with create
		content = String.format("Fenêtre 'nouveau' de %s", name);
		JLabel createTab = new JLabel(content);
		tabbedView.add("Nouveau", createTab);
		
		return tabbedView;
	}
}
