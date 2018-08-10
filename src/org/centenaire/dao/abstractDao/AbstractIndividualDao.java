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
	
	public abstract boolean setQuestionConcern(Individual indiv, String qIII1String);
	
	public abstract boolean setQuestionComittee(Individual indiv, String content);
	
	public abstract boolean setQuestionContribution(Individual indiv, String content);
	
	public abstract boolean setQuestionDev(Individual indiv, String content);

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

	public abstract String getQuestionConcern(Individual indiv);

	public abstract String getQuestionComittee(Individual indiv);

	public abstract String getQuestionContribution(Individual indiv);

	public abstract String getQuestionDev(Individual indiv);
}