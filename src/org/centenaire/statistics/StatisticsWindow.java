package org.centenaire.statistics;

import java.awt.BorderLayout;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;

import org.centenaire.edition.entities.Exams;
import org.centenaire.edition.entities.Individuals;
import org.centenaire.edition.entities.taglike.TagLike;
import org.centenaire.general.Entity;
import org.centenaire.general.GeneralController;
import org.centenaire.general.GeneralWindow;
import org.centenaire.general.ListTableModel;

public class StatisticsWindow extends GeneralWindow {
	/*
	 * Window to display statistics
	 */
	private GeneralController gc = GeneralController.getInstance();
	private DefaultListModel<Individuals> listStudent;
	private DefaultListModel<TagLike> listSemester1;
	private DefaultListModel<TagLike> listSemester2;
	private ListTableModel[] tableModelVect = new ListTableModel[3];
	
	private JTabbedPane tabbedPane;

	public StatisticsWindow(){
		super();
		System.out.println("StatisticsWindow initialized");
		
		// Initialize listSemesterVect


		    
		// ====================================
		// tabEvolution: all marks per student
        tableModelVect[0] = new EvolutionModel(
        		new Class[] {Exams.class,float.class},
        		new String[] {"Exam","Mark"},
        		new LinkedList<Entity>()
        		);
        listStudent = new DefaultListModel<Individuals>();
        updateListStudent();
		StatisticsPanel tabEvolution = new StatisticsPanel(listStudent, tableModelVect[0]);
		// add listener on currentStudent (NB: listener in ListTableModel!)
		tabEvolution.getWestList().addListSelectionListener(tableModelVect[0].getStudentAction());
		System.out.println("StatisticsWindow - tabEvolution done");
		
		// ====================================
		// tabAverage: average per student (on selected semesters)
        tableModelVect[1] = new ListTableModel(
        		new Class[] {Individuals.class,float.class},
        		new String[] {"Student","Average"},
        		new LinkedList<Entity>()
        		);
		listSemester1 = new DefaultListModel<TagLike>();
		StatisticsPanel tabAverage = new StatisticsPanel(listSemester1, tableModelVect[1]);
		tabAverage.getWestList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		WeightPanel weightPan = new WeightPanel();
		tabAverage.getWestPan().add(weightPan);
		// add listener on currentSemester
		tabAverage.getWestList().addListSelectionListener(tableModelVect[1].getSemesterAction());
		tabAverage.getWestList().addListSelectionListener(weightPan);

		// ====================================
		// tabRate: success rate (on selected semesters?)
        tableModelVect[2] = new ListTableModel(
        		new Class[] {TagLike.class,float.class},
        		new String[] {"Semester","Average"},
        		new LinkedList<Entity>()
        		);
		listSemester2 = new DefaultListModel<TagLike>();
		StatisticsPanel tabRate = new StatisticsPanel(listSemester2, tableModelVect[2]);
		tabRate.getWestList().setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// add listener on currentSemester
		tabRate.getWestList().addListSelectionListener(tableModelVect[2].getSemesterAction());
		
		updateListSemester();

		// final assembly of tabbedPane
		tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Evolution",tabEvolution);
		tabbedPane.addTab("Average",tabAverage);
		tabbedPane.addTab("Success rate",tabRate);
		
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
	}
	
	public void updateWindow(){
		updateListSemester();
		updateListStudent();
	}
	
	public void updateListSemester(){
		listSemester1.removeAllElements();
		listSemester2.removeAllElements();
		
        LinkedList<TagLike> dataSemester = gc.getSemesterDao().getData();
        for(TagLike semester:dataSemester){
        	listSemester1.addElement(semester);
        	listSemester2.addElement(semester);
        }
	}
	
	public void updateListStudent(){
		listStudent.removeAllElements();
        LinkedList<Individuals> dataStudent = gc.getStudentDao().getData();
        for(Individuals stud:dataStudent){
        	listStudent.addElement(stud);
        }
	}
}
