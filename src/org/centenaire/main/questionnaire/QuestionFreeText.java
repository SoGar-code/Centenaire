/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.util.logging.Logger;

import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.entity.Individual;
import org.centenaire.util.GeneralController;

/**
 * Class to implement a question
 *
 */
public class QuestionFreeText extends QuestionTemplate {
	protected final static Logger LOGGER = Logger.getLogger(QuestionFreeText.class.getName());
	
	private JTextArea questionContent;
	public GeneralController gc = GeneralController.getInstance();
	private final int questionIndex;

	public QuestionFreeText(String numbering, String questionString, int questionIndex){
		super(numbering);
		this.questionIndex = questionIndex;

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

	public void saveQuestion() {
		String content = this.getContent();
		Individual indiv = gc.getCurrentIndividual();
		if (indiv != null) {
			gc.getIndividualDao().setFreeTextQuestion(indiv, questionIndex, content);
		} else {
			LOGGER.fine("Null currentIndividual, did not save anything!");
		}

	}

	public void setQuestion() {
		Individual indiv = gc.getCurrentIndividual();
		if (indiv != null) {
			String content = gc.getIndividualDao().getFreeTextQuestion(indiv, questionIndex);
			this.setContent(content);
		} else {
			LOGGER.fine("Null currentIndividual, resetting question.");
			this.resetQuestion();
		}
	}
	
	@Override
	public void resetQuestion() {
		setContent("");
	}
}
