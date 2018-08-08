package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Individual;

public abstract class AbstractIndividualDao extends Dao<Individual> {
	
	// Returns an element of type Individual
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract Individual anyElement();
	
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
}