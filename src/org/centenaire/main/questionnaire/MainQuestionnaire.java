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
import java.sql.Date;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Institution;
import org.centenaire.entity.Item;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GeneralController;
import org.centenaire.util.dragndrop.DropTable;
import org.centenaire.util.editorsRenderers.Delete;
import org.centenaire.util.pubsub.Subscriber;

/**
 * The JFrame corresponding to the questionnaire part of the system.
 *
 */
public class MainQuestionnaire extends JFrame implements Subscriber{
	private EntityCombo<Individual> entityCombo;
	private ActionListener comboListener;
	
	/**
	 * Panel with information about the respondent
	 */
	private RespondentPanel respondent;
	
	/**
	 * List of the questions in this questionnaire
	 */
	private LinkedList<QuestionTemplate> questions = new LinkedList<QuestionTemplate>();
	
	private GeneralController gc = GeneralController.getInstance();
	
	private Font titleFont = new Font("Serif", Font.BOLD, 18);
	
	public MainQuestionnaire() {
		super();
		// size: (width, height)
		this.setSize(960, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel content = new JPanel();
		content.setLayout(new BoxLayout(content, BoxLayout.PAGE_AXIS));
		
		// Combo panel
		// ============
		JPanel comboPan = new JPanel();
		JLabel respondentLab = new JLabel("Personne : ");
		
		// EntityCombo. NB: needs to subscribe to the entity channel!
		entityCombo = new EntityCombo<Individual>(EntityEnum.INDIV.getValue());
		
		// no selected element
		entityCombo.setSelectedIndex(-1);
		comboPan.add(respondentLab);
		comboPan.add(entityCombo);
		
		// Create listener for entityCombo
		comboListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {		
					// recover the currently selected object
					Individual currentIndividual = (Individual) entityCombo.getSelectedItem();
					
					// Modify currentIndividual in gc
					gc.setCurrentIndividual(currentIndividual);
					
				} catch (ClassCastException except) {
					String msg = "MainQuestionnaire comboListener -- error when casting entity,\n"
							+ "not updating the panel!";
					System.out.println(msg);
				}
			}
		};
		
		entityCombo.addActionListener(comboListener);
		
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
		String question3String = "Publications de la personne : ";
		QuestionTemplate questionItem = new QuestionDrop("3", question3String);
		content.add(questionItem);
		
		// add to the list of questions
		questions.add(questionItem);
		
		// Question I.4.a. (initial implementation)
		// ====================================
		String question4aString = "Colloques organisés par la personne : ";
		QuestionTemplate questionOrgConf = new QuestionOrgConf("4.a", question4aString);
		content.add(questionOrgConf);
		
		// add to the list of questions
		questions.add(questionOrgConf);
		
		// Questions I.4.b/c (initial implementation)
		// ====================================
		String question4bString = "Colloques auxquels la personne a participé : ";
		QuestionTemplate questionParticipationConf = new QuestionParticipationConf("4.b/c", question4bString);
		content.add(questionParticipationConf);
		
		// add to the list of questions
		questions.add(questionParticipationConf);
		
		// Question I.5 
		// ====================================
		String question5String = "Activités numériques de la personne (productions) : ";
		QuestionTemplate questionItemBis = new QuestionDrop("5", question5String);
		content.add(questionItemBis);
		
		// add to the list of questions
		questions.add(questionItemBis);
		
		// Question I.6.a/b/c/d (initial implementation)
		// ====================================
		String question6String = "Thèses dirigées par la personne : ";
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
				+ "(en 2012, 2013, 2014, 2015, 2016, 2017).";
		QuestionTemplate q_6_e = new QuestionMasterSeminar("6.e", question6eString);
		content.add(q_6_e);
		
		// add to the list of questions
		questions.add(q_6_e);
		
		// Question I.7
		// =============
		
		String question7String = "Institutions partenaires ayant concouru "
				+ "financièrement aux différentes opérations de recherche "
				+ "(notamment colloques) que vous avez organisées."
				+ "\n\nNB : 1. dans cette interface, seuls les événements "
				+ " mentionnés à la question I.4.a sont pris en compte (après sauvegarde)."
				+ "\n         2. chaque événement doit être renseigné et sauvegardé "
				+ "séparément.";
		QuestionTemplate question7 = new QuestionFinancialSupport("7", question7String);
		content.add(question7);
		
		// add to the list of questions
		// set it high in the list to avoid interactions with the update of the ORG relation.
		questions.addFirst(question7);
		
		// Title II separator
		// ===================
		JPanel titleIIPan = new JPanel(new FlowLayout(FlowLayout.LEFT));
		JLabel titleIILab = new JLabel("II. Activités de vulgarisation");
		titleIILab.setFont(titleFont);
		titleIIPan.add(titleIILab);
		content.add(titleIIPan);
		
		// Question II.1 (initial implementation)
		// ====================================
		String questionII_1 = "Activités de vulgarisation de la personne : ";
		QuestionTemplate questionVulg = new QuestionDrop("1.a/b/c/d/e/f", questionII_1);
		content.add(questionVulg);
		
		// add to the list of questions
		questions.add(questionVulg);
		
		// Question II.2
		// ===============
		String questionII_2_a = "Activités d'expertise scientifique de la personne - Productions";
		DropTable<Individual, Item> tableExpItem = new DropTable<Individual, Item>(
				EntityEnum.INDIV.getValue(), 
				EntityEnum.ITEM.getValue(), 
				EntityEnum.EXPITEM.getValue(), 
				new Class[] {String.class, String.class, Date.class, Delete.class},
				new String[] {"Titre", "Type", "Date de début", "Retirer"}
				);
		QuestionTemplate questionExpItem = new QuestionDrop("2.a", questionII_2_a, tableExpItem);
		content.add(questionExpItem);
		
		// add to the list of questions
		questions.add(questionExpItem);
		
		
		String questionII_2_b = "Activités d'expertise scientifique de la personne - Evénements";
		DropTable<Individual, Event> tableExpEvent = new DropTable<Individual, Event>(
				EntityEnum.INDIV.getValue(), 
				EntityEnum.EVENTS.getValue(), 
				EntityEnum.EXPEVENT.getValue(), 
				new Class[] {String.class, String.class, Date.class, String.class, Delete.class},
				new String[] {"Titre", "Type", "Date de début", "Pays", "Retirer"}
				);
		QuestionTemplate questionExpEvent = new QuestionDrop("2.b", questionII_2_b, tableExpEvent);
		content.add(questionExpEvent);
		
		// add to the list of questions
		questions.add(questionExpEvent);
		
		
		String questionII_2_c = "Activités d'expertise scientifique de la personne - Institutions";
		DropTable<Individual, Institution> tableExpInstit = new DropTable<Individual, Institution>(
				EntityEnum.INDIV.getValue(), 
				EntityEnum.INSTIT.getValue(), 
				EntityEnum.EXPINSTIT.getValue(), 
				new Class[] {String.class, String.class, Delete.class}, 
				new String[] {"Institution", "Type", "Retirer"}
				);
		QuestionTemplate questionExpInstit = new QuestionDrop("2.c", questionII_2_c, tableExpInstit);
		content.add(questionExpInstit);
		
		// add to the list of questions
		questions.add(questionExpInstit);
		
		// Question II.3
		// ==============
		String questionII_3_a = "Conférences, tables-rondes, débats grand-publics "
				+ "auxquelles vous avez été invité en France";
		DropTable<Individual, Event> tableGdPublicParticipation = new DropTable<Individual, Event>(
				EntityEnum.INDIV.getValue(), 
				EntityEnum.EVENTS.getValue(), 
				EntityEnum.PARTICIPANT.getValue(), 
				new Class[] {String.class, String.class, Date.class, String.class, Delete.class},
				new String[] {"Titre", "Type", "Date de début", "Pays", "Retirer"}
				);
		QuestionTemplate questionGdPublicParticipation = new QuestionDrop("3.a", questionII_3_a, tableGdPublicParticipation);
		content.add(questionGdPublicParticipation);
		
		// add to the list of questions
		questions.add(questionGdPublicParticipation);
		
		// Question II.4
		// ==============
		String questionII_4 = "Avec quelles organisations, associations, "
				+ "institutions non-scientifiques avez-vous été amené "
				+ "à travailler le plus souvent et le plus étroitement ?"
				+ "\n\nNB : dans le cadre de cette interface, ces institutions "
				+ "doivent être renseignées dans le tableau des relations de la personne. "
				+ "Ici ne devraient apparaître (en texte libre) que des précisions à ce sujet.";
		QuestionTemplate questionInstitNonSci = new QuestionFreeText("4", questionII_4) {
			public void saveQuestion() {
				String qII4String = this.getContent();
				Individual indiv = gc.getCurrentIndividual();
				gc.getIndividualDao().setQuestionInstitNonSci(indiv, qII4String);
			}
			
			public void setQuestion() {
				Individual indiv = gc.getCurrentIndividual();
				String qII4String = gc.getIndividualDao().getQuestionInstitNonSci(indiv);
				this.setContent(qII4String);
			}
		};
		content.add(questionInstitNonSci);
		
		// Question II.5
		// ===============
		String questionII_5 = "Nouveaux médias";
		QuestionTemplate questionSocialMedia = new QuestionSocialMedia("5", questionII_5);
		content.add(questionSocialMedia);
		
		// add to the list of questions
		questions.add(questionSocialMedia);
		
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
				// Save the content of 'RespondentPanel'
				respondent.savePanel();
				
				// Save the content of all questions
				for (QuestionTemplate question: questions) {
					question.saveQuestion();
				}
			}
		});
		
		JPanel saveButtonPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		saveButtonPan.add(saveButton);		
		
		JScrollPane wrapper = new JScrollPane(content);
		wrapper.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		// Speed up scrolling
		wrapper.getVerticalScrollBar().setUnitIncrement(16);
		
		// Fit everything in a single panel
		JPanel assembly = new JPanel(new BorderLayout());
		assembly.add(comboPan, BorderLayout.NORTH);
		assembly.add(wrapper, BorderLayout.CENTER);
		assembly.add(saveButtonPan, BorderLayout.SOUTH);
		
		// Subscribe to channel 0 (change of currentIndividual)
		gc.getChannel(0).addSubscriber(this);
		
		// Subscribe to channel 1 (change in 'Individual' table of the database)
		gc.getChannel(EntityEnum.INDIV.getValue());
	
		this.setContentPane(assembly);
		this.setVisible(true);
	}

	/**
	 * Update questionnaire panel, implementing Subscriber part of Pub-sub pattern
	 * 
	 * <p>This method defines what to do when
	 * the currentIndividual changes (watch this on channel 0!).</p>
	 * 
	 * <p>It also provides the update when changes happen on the 'Individual' table.</p>
	 * 
	 * <p>In any case, the different questions and their components should have their 
	 * own updates when changes happen on other tables.</p>
	 * 
	 * @see org.centenaire.util.pubsub
	 */
	@Override
	public void updateSubscriber(int indexClass) {
		
		// When channel 0 is called (change of currentIndividual)
		// set the questions with what is already known
		if (indexClass == 0) {
			respondent.setPanel();
			
			for (QuestionTemplate question: questions) {
				question.setQuestion();
			}
		}
		
		// When the 'Individual' channel is called
		if (indexClass == EntityEnum.INDIV.getValue()) {
			// Save current individual
			Individual oldIndividual = entityCombo.getSelectedEntity();
			
			// remove action listener
			entityCombo.removeActionListener(comboListener);

			// update the combo using the predefined method
			entityCombo.updateSubscriber(indexClass);
			
			// put the action listener back again
			entityCombo.addActionListener(comboListener);
			
			// Get new selected individual 
			Individual newIndividual = entityCombo.getSelectedEntity();
			
			// If they are different, change currentIndividual (triggers update!)
			if (oldIndividual.getIndex() != newIndividual.getIndex()) {
				gc.setCurrentIndividual(newIndividual);
			}
		}
	}
	
}
