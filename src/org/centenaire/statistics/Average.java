package gestionBilicence.statistics;

import gestionBilicence.edition.Student;
import gestionBilicence.general.Entity;

/**
 * 
 * @author Trivy
 * Average class, an Entity class used for display only
 * so no setters.
 */

public class Average extends Entity {
	private Student stud;
	private float average;

	public Average(Student stud, float average) {
		super();
		this.stud = stud;
		this.average = average;
	}

	public Average(int index, Student stud, float average) {
		super(index);
		this.stud = stud;
		this.average = average;
	}

	@Override
	public Object getEntry(int i) {
		switch (i){
		case 0:
			return stud;
		case 1:
			return average;
		default:
			return "-";
		}
	}

	@Override
	public void setEntry(int i, Object obj) {
	}

}
