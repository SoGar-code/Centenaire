package org.centenaire.dao.abstractDao;

import java.util.LinkedList;
import java.util.List;

import org.centenaire.dao.Dao;
import org.centenaire.edition.entities.Mark;
import org.centenaire.edition.entities.TagLike;
import org.centenaire.edition.entities.Individuals;
import org.centenaire.statistics.Average;

public abstract class AbstractMarkDao extends Dao<Mark> {
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract Mark anyElement();
	
	public abstract LinkedList<Mark> getDataOnStudent(Individuals stud);
	
	public abstract LinkedList<Average> getAverage(List<TagLike> listCurrentSemester);
	
}
