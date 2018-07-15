package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.editor.ExtraInfoStudent;
import org.centenaire.general.entities.individual.Individual;

public abstract class AbstractIndividualDao extends Dao<Individual> {
	
	// Returns an element of type Individual
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract Individual anyElement();
	
	public abstract ExtraInfoStudent getInfo(Individual individual);
	
	public abstract void updateInfo(Individual individual, ExtraInfoStudent info);
	
}
