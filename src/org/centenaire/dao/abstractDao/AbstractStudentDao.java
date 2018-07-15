package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.edition.entities.individual.Individual;
import org.centenaire.editor.ExtraInfoStudent;

public abstract class AbstractStudentDao extends Dao<Individual> {
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract Individual anyElement();
	
	public abstract ExtraInfoStudent getInfo(Individual stud);
	
	public abstract void updateInfo(Individual stud, ExtraInfoStudent info);
	
}
