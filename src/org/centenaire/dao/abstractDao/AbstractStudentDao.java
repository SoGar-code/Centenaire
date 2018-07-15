package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.edition.entities.Individuals;
import org.centenaire.editor.ExtraInfoStudent;

public abstract class AbstractStudentDao extends Dao<Individuals> {
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract Individuals anyElement();
	
	public abstract ExtraInfoStudent getInfo(Individuals stud);
	
	public abstract void updateInfo(Individuals stud, ExtraInfoStudent info);
	
}
