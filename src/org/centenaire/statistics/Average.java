package org.centenaire.statistics;

import org.centenaire.edition.entities.individual.Individual;
import org.centenaire.general.Entity;

/**
 * Average class, an Entity class used for display only.
 * 
 * <p>so no setters.
 */

public class Average extends Entity {
	private Individual stud;
	private float average;

	public Average(Individual stud, float average) {
		super();
		this.stud = stud;
		this.average = average;
	}

	public Average(int index, Individual stud, float average) {
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
