/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A template for dealing with the different questions.
 *
 *<p>It contains a string for the question statement
 * and a "save" button. It requires a "save" method 
 * to be implemented in the inherited classes.</p>
 */
public abstract class QuestionTemplate extends JPanel {
	private static int nbQuestion = 0;
	
	private JLabel questionLab;
	/**
	 * Main panel, where content can be added.
	 */
	private JPanel main;
	
	public QuestionTemplate() {
		super();
		questionLab = new JLabel();
		main = new JPanel();
		
		// Save button, its listener and its panel
		JPanel savePan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton saveButton = new JButton("Svg question");
		savePan.add(saveButton);
		
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				saveQuestion();
			}
		});
		
		this.setLayout(new BorderLayout());
		this.add(questionLab, BorderLayout.NORTH);
		this.add(main, BorderLayout.CENTER);
		this.add(savePan, BorderLayout.SOUTH);
	}
	
	/**
	 * Set the content of the 'question' field.
	 * 
	 * @param questionString
	 * 			string to be used in 'question' field.
	 */
	public void setQuestionLab(String questionString) {
		this.questionLab.setText(questionString);
	}
	
	/**
	 * Recover the content of the 'main' panel.
	 */
	public JPanel getMain() {
		return this.main;
	}
	
	/**
	 * Save the content of this question.
	 * 
	 * <p>This method needs to be implemented by subclasses.
	 * It is the one called when pressing the save button.</p>
	 */
	public abstract void saveQuestion();
}
