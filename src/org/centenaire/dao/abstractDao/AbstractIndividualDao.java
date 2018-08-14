package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Individual;

public abstract class AbstractIndividualDao extends Dao<Individual> {
	
	/**
	 * Set the content of the Q1 variable
	 * 
	 * @param indiv
	 * @param text
	 * @return
	 */
	public abstract boolean setQ1(Individual indiv, String content);

	/**
	 * Set the content of the Q2 variable
	 * 
	 * @param indiv
	 * @param text
	 * @return
	 */
	public abstract boolean setQ2(Individual indiv, String content);
	
	/**
	 * Set the content of the Q3 variable
	 * 
	 * @param indiv
	 * @param text
	 * @return
	 */
	public abstract boolean setQ3(Individual indiv, String content);
	
	public abstract boolean setNbStudents(Individual indiv, int yearIndex, int content);
	
	public abstract boolean setQuestionConcern(Individual indiv, String qIII1String);
	
	public abstract boolean setQuestionComittee(Individual indiv, String content);
	
	public abstract boolean setQuestionContribution(Individual indiv, String content);
	
	public abstract boolean setQuestionDev(Individual indiv, String content);
	
	public abstract boolean setPhdOnGreatWar(Individual indiv, boolean content);

	public abstract boolean setHabilitationOnGreatWar(Individual indiv, boolean content);

	public abstract boolean setPhdDefenseYear(Individual indiv, int content);

	public abstract boolean setQuestionInstitNonSci(Individual indiv, String qII4String);
	
	public abstract boolean setQuestionSocMedExpectation(Individual indiv, String socMedExpectation);

	public abstract boolean setQuestionTwitterEvolution(Individual indiv, String twitterEvol);
	
	public abstract boolean setTwitterAccount(Individual indiv, boolean twitterAccount);
	
	public abstract boolean setFacebookAccount(Individual indiv, boolean facebookAccount);
	
	public boolean setSocMedAccount(Individual indiv, int accountIndex, boolean content) {
		switch (accountIndex) {
			case 0:
				return setTwitterAccount(indiv, content);
			case 1:
				return setFacebookAccount(indiv, content);		
			default:
				String msg = String.format("In AbstractIndividualDao.setSocMedAccount, "
						+ "unknown accountIndex '%s'", accountIndex);
				System.out.println(msg);
				return false;
		}
	}
	
	public abstract boolean setTwitterAccountYear(Individual indiv, int twitterAccountYear);
	
	public abstract boolean setFacebookAccountYear(Individual indiv, int facebookAccountYear);
	
	public boolean setSocMedAccountYear(Individual indiv, int accountIndex, int content) {
		switch (accountIndex) {
			case 0:
				return setTwitterAccountYear(indiv, content);
			case 1:
				return setFacebookAccountYear(indiv, content);		
			default:
				String msg = String.format("In AbstractIndividualDao.setSocMedAccountYear, "
						+ "unknown accountIndex '%s'", accountIndex);
				System.out.println(msg);
				return false;
		}
	}
	
	public abstract boolean setTweetsPerWeek(Individual indiv, float tweetsPerWeek);
	
	public abstract boolean setSuccessfulTweet(Individual indiv, String successfulTweet);
	
	/**
	 * Get the content of the Q1 variable
	 * 
	 * @param indiv
	 * @return
	 */
	public abstract String getQ1(Individual indiv);

	/**
	 * Get the content of the Q2 variable
	 * 
	 * @param indiv
	 * @return
	 */
	public abstract String getQ2(Individual indiv);

	/**
	 * Get the content of the Q3 variable.
	 * 
	 * @param indiv
	 * @return
	 */
	public abstract String getQ3(Individual indiv);
	
	public abstract int getNbStudents(Individual indiv, int yearIndex);

	public abstract String getQuestionConcern(Individual indiv);

	public abstract String getQuestionComittee(Individual indiv);

	public abstract String getQuestionContribution(Individual indiv);

	public abstract String getQuestionDev(Individual indiv);

	public abstract int getPhdDefenseYear(Individual indiv);

	public abstract boolean getPhdOnGreatWar(Individual indiv);

	public abstract boolean getHabilitationOnGreatWar(Individual indiv);

	public abstract String getQuestionInstitNonSci(Individual indiv);
	
	public abstract String getQuestionSocMedExpectation(Individual indiv);

	public abstract String getQuestionTwitterEvolution(Individual indiv);
	
	public abstract boolean getTwitterAccount(Individual indiv);
	
	public abstract boolean getFacebookAccount(Individual indiv);
	
	public boolean getSocMedAccount(Individual indiv, int accountIndex) {
		switch (accountIndex) {
			case 0:
				return getTwitterAccount(indiv);
			case 1:
				return getFacebookAccount(indiv);		
			default:
				String msg = String.format("In AbstractIndividualDao.getSocMedAccountYear, "
						+ "unknown accountIndex '%s'", accountIndex);
				System.out.println(msg);
				return false;
		}
	}
	
	public abstract int getTwitterAccountYear(Individual indiv);
	
	public abstract int getFacebookAccountYear(Individual indiv);
	
	public int getSocMedAccountYear(Individual indiv, int accountIndex) {
		switch (accountIndex) {
			case 0:
				return getTwitterAccountYear(indiv);
			case 1:
				return getFacebookAccountYear(indiv);		
			default:
				String msg = String.format("In AbstractIndividualDao.getSocMedAccountYear, "
						+ "unknown accountIndex '%s'", accountIndex);
				System.out.println(msg);
				return -1;
		}
	}
	
	public abstract float getTweetsPerWeek(Individual indiv);
	
	public abstract String getSuccessfulTweet(Individual indiv);

}