package org.centenaire.main.questionnaire;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.centenaire.entity.Individual;
import org.centenaire.util.GIntegerField;
import org.centenaire.util.GeneralController;

public class QuestionMasterSeminar extends QuestionTemplate {
	private JTextArea questionContent;
	private List<GIntegerField> nbStudentsList;
	private int nbYear = 6;
	
	public GeneralController gc = GeneralController.getInstance();
	
	public QuestionMasterSeminar(String numbering, String questionString){
		super(numbering);
		
		

		this.setQuestionLab(questionString);
		
		JPanel main = this.getMain();
		main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
		
		// nbStudents panel
		GridLayout gl = new GridLayout(2,nbYear/2);
		gl.setHgap(5);
		gl.setVgap(10);
		JPanel nbStudentsPan = new JPanel(gl);
		String[] years = {"Effectifs 2012 : ", "Effectifs 2013 : ", "Effectifs 2014 : ", 
				"Effectifs 2015 : ", "Effectifs 2016 : ", "Effectifs 2017 : "};
		nbStudentsList = new LinkedList<GIntegerField>();
		
		for (String year: years) {
			// Create components
			JPanel yearPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel yearLab = new JLabel(year);
			GIntegerField nbStudentField = new GIntegerField();
			nbStudentField.setColumns(5);

			// Include them in panel
			yearPan.add(yearLab);
			yearPan.add(nbStudentField);
			
			nbStudentsPan.add(yearPan);
			
			// Save active component in global variable
			nbStudentsList.add(nbStudentField);
		}

		// Set up free text area
		questionContent = new JTextArea();
		questionContent.setLineWrap(true);
		questionContent.setColumns(70);
		questionContent.setRows(5);
		
		// Final assembly
		main.add(nbStudentsPan);
		main.add(questionContent);
		
	}

	public String getStringContent() {
		return questionContent.getText();
	}
	
	public void setStringContent(String content) {
		questionContent.setText(content);
	}
	
	/**
	 * Set number in prescribed student number field
	 * 
	 * @param yearIndex
	 * 				which year 
	 * @return
	 */
	public int getIntContent(int yearIndex) {
		return nbStudentsList.get(yearIndex).getIntegerValue();
	}
	
	public void setIntContent(int yearIndex, int content) {
		nbStudentsList.get(yearIndex).setIntegerValue(content);
	}

	@Override
	public void saveQuestion() {
		String q3 = this.getStringContent();
		Individual indiv = gc.getCurrentIndividual();
		gc.getIndividualDao().setQ3(indiv, q3);
		
		for (int yearIndex=0; yearIndex<nbYear; yearIndex++) {
			int aux = nbStudentsList.get(yearIndex).getIntegerValue();
			gc.getIndividualDao().setNbStudents(indiv, yearIndex, aux);
		}
	}

	@Override
	public void setQuestion() {
		Individual indiv = gc.getCurrentIndividual();
		String q3 = gc.getIndividualDao().getQ3(indiv);
		this.setStringContent(q3);
		
		for (int yearIndex=0; yearIndex<nbYear; yearIndex++) {
			 int aux = gc.getIndividualDao().getNbStudents(indiv, yearIndex);
			 nbStudentsList.get(yearIndex).setIntegerValue(aux);
		}
	}
	
	@Override
	public void resetQuestion() {
		setStringContent("");
		
		for (int yearIndex=0; yearIndex<nbYear; yearIndex++) {
			 nbStudentsList.get(yearIndex).setIntegerValue(0);
		}
	}
}
