/**
 * 
 */
package org.centenaire.main.questionnaire;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Class to implement a question
 *
 */
public class Question_I_1 extends QuestionTemplate {

	public Question_I_1(){
		super();

		String questionString = "Quels sont les th�mes de vos "
				+ "recherches actuelles relatives � la "
				+ "premi�re guerre mondiale, quel est "
				+ "le champ chronologique et g�ographique "
				+ "de ces recherches ?";

		this.setQuestionLab(questionString);
		
		
	}

	@Override
	public void saveQuestion() {
		// TODO Auto-generated method stub
		
	}
}
