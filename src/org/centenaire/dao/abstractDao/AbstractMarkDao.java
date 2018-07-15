package org.centenaire.dao.abstractDao;

import java.util.LinkedList;
import java.util.List;

import org.centenaire.dao.Dao;
import org.centenaire.general.entities.Mark;
import org.centenaire.general.entities.individual.Individual;
import org.centenaire.general.entities.taglike.TagLike;
import org.centenaire.statistics.Average;

public abstract class AbstractMarkDao extends Dao<Mark> {
	
	// Returns an element of type Semester
	// either an already existing one or
	// we create and initialize a new one in the database
	public abstract Mark anyElement();
	
	public abstract LinkedList<Mark> getDataOnStudent(Individual stud);
	
	public abstract LinkedList<Average> getAverage(List<TagLike> listCurrentSemester);
	
}
