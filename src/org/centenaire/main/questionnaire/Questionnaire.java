/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
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
	/**
	 * List of the questions in this questionnaire
	 */
	private LinkedList<QuestionTemplate> questions = new LinkedList<QuestionTemplate>();
	private RespondentPanel respondent;
	private Font titleFont = new Font("Serif", Font.BOLD, 18);
	
	public Questionnaire() {
		super();
		// size: (width, height)
		this.setSize(980, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		// Respondent panel
		respondent = new RespondentPanel();
		content.add(respondent);
		
		// Title I separator
		// ===================
		JPanel titleIPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleILab = new JLabel("I. Activités de recherche");
		titleILab.setFont(titleFont);
		titleIPan.add(titleILab);
		content.add(titleIPan);
		
		// Question I.1
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
		
		// Question I.2
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
		
		// Question I.3 (initial implementation)
		// ====================================
		String question3String = "Publications du répondant";
		QuestionTemplate questionItem = new QuestionItem("3", question3String);
		content.add(questionItem);
		
		// add to the list of questions
		questions.add(questionItem);
		
		// Question I.4.a. (initial implementation)
		// ====================================
		String question4aString = "Colloques organisés par le répondant";
		QuestionTemplate questionOrgConf = new QuestionOrgConf("4.a", question4aString);
		content.add(questionOrgConf);
		
		// add to the list of questions
		questions.add(questionOrgConf);
		
		// Questions I.4.b/c (initial implementation)
		// ====================================
		String question4bString = "Colloques auxquels le répondant à participé";
		QuestionTemplate questionParticipationConf = new QuestionParticipationConf("4.b/c", question4bString);
		content.add(questionParticipationConf);
		
		// add to the list of questions
		questions.add(questionParticipationConf);
		
		// Question I.5 
		// ====================================
		String question5String = "Activités numériques du répondant (productions)";
		QuestionTemplate questionItemBis = new QuestionItem("5", question5String);
		content.add(questionItemBis);
		
		// add to the list of questions
		questions.add(questionItemBis);
		
		// Question I.6.a/b/c/d (initial implementation)
		// ====================================
		String question6String = "Thèses dirigées par le répondant";
		QuestionTemplate questionTheses = new QuestionTheses("6.a/b/c/d", question6String);
		content.add(questionTheses);
		
		// add to the list of questions
		questions.add(questionTheses);
		
		// Question I.6.e (initial implementation)
		// ======================================
		String question6eString = "Animation de la recherche débutante "
				+ "sur la Première Guerre mondiale. Intitulé du séminaire "
				+ "de master si vous en dirigez un. Effectif moyen "
				+ "de la participation étudiante à ce séminaire. "
				+ "Nombre de mémoires de master « Première Guerre mondiale » "
				+ "(en 2012, 2013, 2014, 2015, 2016, 2017)";
		QuestionTemplate q_6_e = new QuestionFreeText("6.e", question6eString) {
			public void saveQuestion() {
				String q6e = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQ3(indiv, q6e);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String q6e = gc.getIndividualDao().getQ3(indiv);
				this.setContent(q6e);
			}
		};
		content.add(q_6_e);
		
		// add to the list of questions
		questions.add(q_6_e);
		
		// Question I.7
		// =============
		
		String question7 = "Institutions partenaires ayant concouru "
				+ "financièrement aux différentes opérations de recherche "
				+ "(notamment colloques) que vous avez organisées";
		QuestionTemplate q_7 = new QuestionFreeText("7", question7);
		((QuestionFreeText) q_7).setContent("-- ici, implémentation de la question 7 "
				+ "(pas reliée pour le moment !) --");
		content.add(q_7);
		
		// add to the list of questions
		questions.add(q_7);
		
		// Title II separator
		// ===================
		JPanel titleIIPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleIILab = new JLabel("II. Activités de vulgarisation");
		titleIILab.setFont(titleFont);
		titleIIPan.add(titleIILab);
		content.add(titleIIPan);
		
		// Question II.1 (initial implementation)
		// ====================================
		String questionII_1 = "Activités de vulgarisation du répondant";
		QuestionTemplate questionVulg = new QuestionItem("1.a/b/c/d/e/f", questionII_1);
		content.add(questionVulg);
		
		content.add(questionVulg);
		
		// add to the list of questions
		questions.add(questionVulg);
		
		// Title III separator
		// ===================
		JPanel titleIIIPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleIIILab = new JLabel("III. Questions qualitatives sur vos "
				+ "expériences personnelles pendant le centenaire");
		titleIIILab.setFont(titleFont);
		titleIIIPan.add(titleIIILab);
		content.add(titleIIIPan);
		
		// Question III.1
		// ======================================
		String questionIII_1 = "Dans vos échanges avec le public et les "
				+ "différents acteurs non-scientifiques, quelles ont été "
				+ "les questions, préoccupations etc. les plus récurrentes ?";
		QuestionTemplate q_III_1 = new QuestionFreeText("1", questionIII_1) {
			public void saveQuestion() {
				String qIII1String = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQuestionConcern(indiv, qIII1String);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String qIII1String = gc.getIndividualDao().getQuestionConcern(indiv);
				this.setContent(qIII1String);
			}
		};
		content.add(q_III_1);
		
		// add to the list of questions
		questions.add(q_III_1);
		
		// Question III.2
		// ======================================
		String questionIII_2 = "Quel regard portez-vous sur votre engagement "
				+ "dans les différents comités scientifiques dont "
				+ "vous avez fait partie ? Vos conseils ont-ils été écoutés "
				+ "et pris en compte ? Exemples concrets ?";
		QuestionTemplate q_III_2 = new QuestionFreeText("2", questionIII_2) {
			public void saveQuestion() {
				String content = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQuestionComittee(indiv, content);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String content = gc.getIndividualDao().getQuestionComittee(indiv);
				this.setContent(content);
			}
		};
		content.add(q_III_2);
		
		// add to the list of questions
		questions.add(q_III_2);
		
		// Question III.3
		// ======================================
		String questionIII_3 = "D’un point de vue du spécialiste/de la spécialiste "
				+ "de la Première Guerre mondiale que vous êtes, "
				+ "qu’est-ce que le Centenaire a apporté "
				+ "à notre compréhension de la guerre ?";
		QuestionTemplate q_III_3 = new QuestionFreeText("3", questionIII_3) {
			public void saveQuestion() {
				String content = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQuestionContribution(indiv, content);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String content = gc.getIndividualDao().getQuestionContribution(indiv);
				this.setContent(content);
			}
		};
		content.add(q_III_3);
		
		// add to the list of questions
		questions.add(q_III_3);
		
		// Question III.4
		// ======================================
		String questionIII_4 = "Selon vous, comment va "
				+ "se développer la recherche sur "
				+ "la Première Guerre mondiale dans les dix ans à venir ?";
		QuestionTemplate q_III_4 = new QuestionFreeText("4", questionIII_4) {
			public void saveQuestion() {
				String content = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQuestionDev(indiv, content);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String content = gc.getIndividualDao().getQuestionDev(indiv);
				this.setContent(content);
			}
		};
		content.add(q_III_4);
		
		// add to the list of questions
		questions.add(q_III_4);
		
		// Overall save button
		JButton saveButton = new JButton("Tout sauvegarder");
		saveButton.setBackground(Color.GREEN);
		
		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Save the content of all questions
				for (QuestionTemplate question: questions) {
					question.saveQuestion();
				}
				
				// Save the content of 'RespondentPanel'
				respondent.saveQuestion();
			}
		});
		
		JPanel saveButtonPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		saveButtonPan.add(saveButton);
		
		// Fit everything in a single panel
		JPanel assembly = new JPanel(new BorderLayout());
		assembly.add(content, BorderLayout.CENTER);
		assembly.add(saveButtonPan, BorderLayout.SOUTH);
		
		
		JScrollPane wrapper = new JScrollPane(assembly);
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
