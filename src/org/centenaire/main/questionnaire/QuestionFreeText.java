/**
 * 
 */
package org.centenaire.main.questionnaire;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.centenaire.util.GeneralController;

/**
 * Class to implement a question
 *
 */
public class QuestionFreeText extends QuestionTemplate {
	private JTextArea questionContent;
	public GeneralController gc = GeneralController.getInstance();

	public QuestionFreeText(String numbering, String questionString){
		super(numbering);

		this.setQuestionLab(questionString);
		
		JPanel main = this.getMain();

		questionContent = new JTextArea();
		questionContent.setLineWrap(true);
		questionContent.setColumns(70);
		questionContent.setRows(5);
		
		main.add(questionContent);
		
	}
	
	public String getContent() {
		return questionContent.getText();
	}
	
	public void setContent(String content) {
		questionContent.setText(content);
	}

	@Override
	public void saveQuestion() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setQuestion() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void resetQuestion() {
		setContent("");
	}
}
