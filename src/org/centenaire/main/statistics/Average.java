package org.centenaire.main.statistics;

import org.centenaire.entity.Entity;
import org.centenaire.entity.Individual;

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
