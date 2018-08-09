/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.FlowLayout;
import java.awt.Font;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.centenaire.entity.Individual;
import org.centenaire.util.GeneralController;
import org.centenaire.util.pubsub.Subscriber;

/**
 * The JFrame corresponding to the questionnaire part of the system.
 *
 */
public class Questionnaire extends JFrame implements Subscriber{
	private LinkedList<QuestionTemplate> questions = new LinkedList<QuestionTemplate>();
	
	public Questionnaire() {
		super();
		// size: (width, height)
		this.setSize(900, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		// Respondent panel
		JPanel respondent = new RespondentPanel();
		content.add(respondent);
		
		// Title I separator
		// ===================
		JPanel titleIPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleILab = new JLabel("I. Activités de recherche");
		titleILab.setFont(new Font("Serif", Font.BOLD, 18));
		titleIPan.add(titleILab);
		content.add(titleIPan);
		
		// Question I 1
		// ==============
		String question1String = "Quels sont les thèmes de vos "
				+ "recherches actuelles relatives à la "
				+ "première guerre mondiale, quel est "
				+ "le champ chronologique et géographique "
				+ "de ces recherches ?";
		QuestionTemplate q_I_1 = new QuestionFreeText("1", question1String) {
			public void saveQuestion() {
				String q1 = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQ1(indiv, q1);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String q1 = gc.getIndividualDao().getQ1(indiv);
				this.setContent(q1);
			}
		};
		content.add(q_I_1);
		
		// add to the list of questions
		questions.add(q_I_1);
		
		// Question I 2
		// ==============
		String question2String = "Vos recherches vous ont-elles "
				+ "conduites à travailler sur des sources récemment "
				+ "déposées ou rendues publiques ? Si oui, lesquelles ?";
		QuestionTemplate q_I_2 = new QuestionFreeText("2", question2String) {
			public void saveQuestion() {
				String q2 = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQ2(indiv, q2);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String q2 = gc.getIndividualDao().getQ2(indiv);
				this.setContent(q2);
			}
		};
		content.add(q_I_2);
		
		// add to the list of questions
		questions.add(q_I_2);
		
		// Question 3 (initial implementation)
		// ====================================
		String question3String = "Publications du répondant";
		QuestionTemplate questionItem = new QuestionItem("3", question3String);
		content.add(questionItem);
		
		// add to the list of questions
		questions.add(questionItem);
		
		
		JScrollPane wrapper = new JScrollPane(content);
		wrapper.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Subscribe to channel 0 (change of currentIndividual)
		GeneralController gc = GeneralController.getInstance();
		gc.getChannel(0).addSubscriber(this);
	
		this.setContentPane(wrapper);
		this.setVisible(true);
	}

	/**
	 * Update questionnaire panel, implementing Subscriber part of Pub-sub pattern
	 * 
	 * <p>At the moment, this method is intended to define what to do when
	 * the currentIndividual changes (watch this on channel 0!).</p>
	 * 
	 * @see org.centenaire.util.pubsub
	 */
	@Override
	public void updateSubscriber(int indexClass) {
		
		// When channel 0 is called (change of currentIndividual)
		// set the questions with what is already known
		if (indexClass == 0) {
			for (QuestionTemplate question: questions) {
				question.setQuestion();
			}
		}
	}
	
}
