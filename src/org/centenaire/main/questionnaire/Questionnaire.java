/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * The JFrame corresponding to the questionnaire part of the system.
 * 
 * @author OG
 *
 */
public class Questionnaire extends JFrame {
	
	public Questionnaire() {
		super();
		// size: (width, height)
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		// Respondent panel
		JPanel respondent = new RespondentPanel();
		content.add(respondent);
		
		JPanel question;
		// Questions 1 to 25: generate and assemble!
		for (int i = 0; i <= 25; i++){
			String body = String.format("Contenu de la question %d. Ce contenu peut-être "
					+ "très très très très très très très long...", i);
			question = this.createQuestion(i, body);
			content.add(question);
		}
		
		JScrollPane wrapper = new JScrollPane(content);
		wrapper.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	
		this.setContentPane(wrapper);
		this.setVisible(true);
	}
	
	public JPanel createQuestion(int number, String questionText) {
		// Numbering
		String label = String.format("Question %d.", number);
		JLabel numbering = new JLabel(label);
		
		// Body of the question
		JLabel body = new JLabel(questionText);
		
		JPanel question = new JPanel();
		BoxLayout boxLayout = new BoxLayout(question, BoxLayout.PAGE_AXIS);
		
		question.setLayout(boxLayout);
		
		question.add(numbering);
		question.add(body);
		question.add(Box.createRigidArea(new Dimension(0,10)));
		
		return question;
	}
	
}
