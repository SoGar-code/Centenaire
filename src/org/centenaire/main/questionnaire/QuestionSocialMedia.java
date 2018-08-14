package org.centenaire.main.questionnaire;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.centenaire.entity.Individual;
import org.centenaire.util.GIntegerField;
import org.centenaire.util.GeneralController;

public class QuestionSocialMedia extends QuestionTemplate {
	private JTextArea socMedExpectationArea;
	private JTextArea twitterEvolutionArea;
	private List<JCheckBox> socialMediaAccount;
	private List<GIntegerField> socialMediaStartYear;
	private GIntegerField twitterNbField;
	private JComboBox<String> comboDuration;
	private JTextArea successfulTweetArea;
	
	private int nbAccounts = 2;
	
	public GeneralController gc = GeneralController.getInstance();
	
	public QuestionSocialMedia(String numbering, String questionString){
		super(numbering);

		this.setQuestionLab(questionString);
		
		JPanel main = this.getMain();
		
		// Question about accounts
		JLabel accountQuestion = new JLabel("a.	Disposez-vous d’un compte Twitter "
				+ "ou Facebook que vous utilisez à des fins professionnelles ? "
				+ "Si oui depuis quand ? ");

		
		// Social media account panel
		JPanel accountAuxPan = new JPanel();
		GridLayout accountGL = new GridLayout(1,2);
		accountGL.setHgap(10);
		accountAuxPan.setLayout(accountGL);
		
		String[] accounts = {"Compte Twitter : ", "Compte Facebook : "};
		socialMediaAccount = new LinkedList<JCheckBox>();
		socialMediaStartYear = new LinkedList<GIntegerField>();
		
		for (String account: accounts) {
			// Check box row
			JPanel checkPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
			JLabel checkLab = new JLabel(account);
			JCheckBox accountField = new JCheckBox();
			checkPan.add(checkLab);
			checkPan.add(accountField);
			
			// Year row
			JPanel yearPan = new JPanel();
			JLabel yearLab = new JLabel("depuis (année) : ");
			GIntegerField socialMediaStartYearField = new GIntegerField();
			socialMediaStartYearField.setColumns(4);
			yearPan.add(yearLab);
			yearPan.add(socialMediaStartYearField);
			
			// Create auxPan
			JPanel verticalIntegrationPan = new JPanel();
			verticalIntegrationPan.setLayout(new BoxLayout(verticalIntegrationPan, BoxLayout.Y_AXIS));
			verticalIntegrationPan.add(checkPan);
			verticalIntegrationPan.add(yearPan);
			
			// Add auxPan to the account panel
			accountAuxPan.add(verticalIntegrationPan);
			
			// Save active component in global variable
			socialMediaAccount.add(accountField);
			socialMediaStartYear.add(socialMediaStartYearField);
		}
		
		// Expectations regarding social media
		// ======================================
		JLabel expectationLab = new JLabel("Quelles ont-été vos attentes "
				+ "au moment de vous abonner ? Si non (pas de comptes), pourquoi pas ?");

		// Set up free text area
		socMedExpectationArea = new JTextArea();
		socMedExpectationArea.setLineWrap(true);
		socMedExpectationArea.setColumns(30);
		socMedExpectationArea.setRows(5);
		
		// Twitter evolution
		// ======================================
		JLabel twitterEvolutionLab = new JLabel("b. Sur Twitter, quelle a été l’évolution du "
				+ "nombre de followers depuis le début du Centenaire ?");

		// Set up free text area
		twitterEvolutionArea = new JTextArea();
		twitterEvolutionArea.setLineWrap(true);
		twitterEvolutionArea.setColumns(30);
		twitterEvolutionArea.setRows(5);
		
		// Nb tweets per week
		// ======================================
		JLabel twitterNbTweetsLab = new JLabel("c. En moyenne, combien de tweets rédigez-vous"
				+ "par mois/semaine ?");
		
		JPanel twitterNbPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		twitterNbField = new GIntegerField();
		twitterNbField.setColumns(5);
		JLabel sepLab = new JLabel(" par ");
		comboDuration = new JComboBox<String>(new String[] {"mois", "semaine"});
		twitterNbPan.add(twitterNbField);
		twitterNbPan.add(sepLab);
		twitterNbPan.add(comboDuration);

		// Most successful tweet
		// ======================================
		JLabel successfulTweet = new JLabel("d. Pendant le Centenaire, "
				+ "quel a été votre tweet ayant connu le plus de succès ?");

		// Set up free text area
		successfulTweetArea = new JTextArea();
		successfulTweetArea.setLineWrap(true);
		successfulTweetArea.setColumns(30);
		successfulTweetArea.setRows(5);
		
		// For layout reasons, include auxiliary panel
		JPanel auxPan = new JPanel();
		GridLayout gl = new GridLayout(10,1);
		auxPan.setLayout(gl);
		
		auxPan.add(accountQuestion);
		auxPan.add(accountAuxPan);
		auxPan.add(expectationLab);
		auxPan.add(socMedExpectationArea);
		auxPan.add(twitterEvolutionLab);
		auxPan.add(twitterEvolutionArea);
		auxPan.add(twitterNbTweetsLab);
		auxPan.add(twitterNbPan);
		auxPan.add(successfulTweet);
		auxPan.add(successfulTweetArea);
		
		// Final assembly
		main.add(auxPan);

		
	}

	public String getStringContent() {
		return socMedExpectationArea.getText();
	}
	
	public void setStringContent(String content) {
		socMedExpectationArea.setText(content);
	}
	
	/**
	 * Set number in prescribed student number field
	 * 
	 * @param accountIndex
	 * 				which year 
	 * @return
	 */
	public int getIntContent(int accountIndex) {
		return socialMediaStartYear.get(accountIndex).getIntegerValue();
	}
	
	public void setIntContent(int yearIndex, int content) {
		socialMediaStartYear.get(yearIndex).setIntegerValue(content);
	}

	@Override
	public void saveQuestion() {
		String q3 = this.getStringContent();
		Individual indiv = gc.getCurrentIndividual();
		gc.getIndividualDao().setQ3(indiv, q3);
		
		for (int accountIndex=0; accountIndex<nbAccounts; accountIndex++) {
			int aux = socialMediaStartYear.get(accountIndex).getIntegerValue();
			gc.getIndividualDao().setNbStudents(indiv, accountIndex, aux);
		}
	}

	@Override
	public void setQuestion() {
		Individual indiv = gc.getCurrentIndividual();
		String q3 = gc.getIndividualDao().getQ3(indiv);
		this.setStringContent(q3);
		
		for (int accountIndex=0; accountIndex<nbAccounts; accountIndex++) {
			 int aux = gc.getIndividualDao().getNbStudents(indiv, accountIndex);
			 socialMediaStartYear.get(accountIndex).setIntegerValue(aux);
		}
	}
	
	@Override
	public void resetQuestion() {
		setStringContent("");
		
		for (int accountIndex=0; accountIndex<nbAccounts; accountIndex++) {
			socialMediaStartYear.get(accountIndex).setIntegerValue(0);
		}
	}
}
