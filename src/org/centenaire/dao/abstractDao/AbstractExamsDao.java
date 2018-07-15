package org.centenaire.dao.abstractDao;

import java.util.List;

import org.centenaire.dao.Dao;
import org.centenaire.general.entities.Exams;
import org.centenaire.general.entities.taglike.TagLike;

public abstract class AbstractExamsDao extends Dao<Exams> {

	public abstract float getTotalWeight(List<TagLike> listCurrentSemester);
	
}
