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

import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.entity.Individual;
import org.centenaire.util.GFloatField;
import org.centenaire.util.GIntegerField;
import org.centenaire.util.GeneralController;

public class QuestionSocialMedia extends QuestionTemplate {
	private JTextArea socMedExpectationArea;
	private JTextArea twitterEvolutionArea;
	private List<JCheckBox> socialMediaAccount;
	private List<GIntegerField> socialMediaStartYear;
	private GFloatField twitterNbField;
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
		twitterNbField = new GFloatField();
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

	@Override
	public void saveQuestion() {
		Individual indiv = gc.getCurrentIndividual();
		AbstractIndividualDao indivDao = gc.getIndividualDao();
		
		String socMedExpectation = socMedExpectationArea.getText();
		indivDao.setQuestionSocMedExpectation(indiv, socMedExpectation);
		
		String twitterEvolution = twitterEvolutionArea.getText(); 
		indivDao.setQuestionTwitterEvolution(indiv, twitterEvolution);
		
		for (int accountIndex=0; accountIndex<nbAccounts; accountIndex++) {
			
			boolean socMedAccount = socialMediaAccount.get(accountIndex).isSelected();
			indivDao.setSocMedAccount(indiv, accountIndex, socMedAccount);
			
			int socMedAccountYear = socialMediaStartYear.get(accountIndex).getIntegerValue();
			indivDao.setSocMedAccountYear(indiv, accountIndex, socMedAccountYear);
		}
		
		int durationInt = comboDuration.getSelectedIndex();
		float tweetsPerWeek = 0;
		switch (durationInt) {
			// If 'month' is selected
			case 0:
				float rawTwitterNb = twitterNbField.getFloatValue();
				tweetsPerWeek = (float) (rawTwitterNb/4.5);
				break;
			// if 'week' is selected
			case 1:
				tweetsPerWeek = twitterNbField.getFloatValue();
				break;
			default:
				String msg = String.format("Unknown duration '%s' in combo!", durationInt);
				System.out.println(msg);
				tweetsPerWeek = -1;
		}
		indivDao.setTweetsPerWeek(indiv, tweetsPerWeek);
		
		String successfulTweet = successfulTweetArea.getText();
		indivDao.setSuccessfulTweet(indiv, successfulTweet);
	}

	@Override
	public void setQuestion() {
		Individual indiv = gc.getCurrentIndividual();
		AbstractIndividualDao indivDao = gc.getIndividualDao();
		
		String socMedExpectation = indivDao.getQuestionSocMedExpectation(indiv);
		socMedExpectationArea.setText(socMedExpectation);
		
		String twitterEvolution = indivDao.getQuestionTwitterEvolution(indiv);
		twitterEvolutionArea.setText(twitterEvolution);
		
		for (int accountIndex=0; accountIndex<nbAccounts; accountIndex++) {
			
			boolean socMedAccount = indivDao.getSocMedAccount(indiv, accountIndex);
			socialMediaAccount.get(accountIndex).setSelected(socMedAccount);
			
			int socMedAccountYear = indivDao.getSocMedAccountYear(indiv, accountIndex);
			socialMediaStartYear.get(accountIndex).setIntegerValue(socMedAccountYear);
		}
		
		float twitterNb = indivDao.getTweetsPerWeek(indiv);
		twitterNbField.setFloatValue(twitterNb);
		comboDuration.setSelectedIndex(1);
		
		String successfulTweet = indivDao.getSuccessfulTweet(indiv);
		successfulTweetArea.setText(successfulTweet);
	}
	
	@Override
	public void resetQuestion() {	
		socMedExpectationArea.setText("");
		twitterEvolutionArea.setText("");
		
		for (int accountIndex=0; accountIndex<nbAccounts; accountIndex++) {
			socialMediaAccount.get(accountIndex).setSelected(false);
			socialMediaStartYear.get(accountIndex).setIntegerValue(0);
		}
		
		twitterNbField.setFloatValue(0);
		comboDuration.setSelectedIndex(-1);
		
		successfulTweetArea.setText("");
	}
}
