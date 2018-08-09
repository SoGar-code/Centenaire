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
import javax.swing.JTextArea;

import org.centenaire.util.GeneralController;

/**
 * A template for dealing with the different questions.
 *
 *<p>It contains a string for the question statement
 * and a "save" button. It requires a "save" method 
 * to be implemented in the inherited classes.</p>
 */
public abstract class QuestionTemplate extends JPanel {
	protected GeneralController gc = GeneralController.getInstance();
	
	private JTextArea questionJTA;
	/**
	 * Main panel, where content can be added.
	 */
	private JPanel main;
	
	public QuestionTemplate(String numbering) {
		super();
		
		JLabel questionLab = new JLabel(String.format("Question %s.", numbering));
		questionJTA = new JTextArea();
		questionJTA.setEditable(false);
		questionJTA.setLineWrap(true);
		questionJTA.setColumns(70);
		
		// Include these in top panel
		JPanel topPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		topPan.add(questionLab);
		topPan.add(questionJTA);
		
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
		this.add(topPan, BorderLayout.NORTH);
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
		this.questionJTA.setText(questionString);
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
	
	/**
	 * Set the content of the question (when changing individual, for instance).
	 */
	public abstract void setQuestion();
	
	/**
	 * Reset the content of the question
	 */
	public abstract void resetQuestion();
}
