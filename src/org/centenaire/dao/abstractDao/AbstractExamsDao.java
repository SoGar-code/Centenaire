package gestionBilicence.dao.abstractDao;

import java.util.List;

import gestionBilicence.dao.Dao;
import gestionBilicence.edition.Exams;
import gestionBilicence.edition.Semester;

public abstract class AbstractExamsDao extends Dao<Exams> {

	public abstract float getTotalWeight(List<Semester> listCurrentSemester);
	
}
