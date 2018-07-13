package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.edition.ExtraInfoStudent;
import org.centenaire.edition.Student;

public abstract class AbstractStudentDao extends Dao<Student> {
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract Student anyElement();
	
	public abstract ExtraInfoStudent getInfo(Student stud);
	
	public abstract void updateInfo(Student stud, ExtraInfoStudent info);
	
}
