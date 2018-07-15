package org.centenaire.edition.entities;

import org.centenaire.general.Entity;

/**
 * POJO for the 'Individuals' table in the database.
 */
public class Individuals extends Entity {
	protected String first_name;
	protected String last_name;
	protected int birth_year;

	public Individuals(String first_name, String last_name) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public Individuals(int index, String first_name, String last_name) {
		super(index);
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public Individuals(String first_name, String last_name, int birth_year) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.birth_year = birth_year;
	}
	
	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	
	public int getBirth_year() {
		return birth_year;
	}

	public void setBirth_year(int birth_year) {
		this.birth_year = birth_year;
	}

	@Override
	public Object getEntry(int i) {
		switch (i){
			case 0:
				return first_name;
			case 1:
				return last_name;
			case 2:
				return birth_year;
			default:
				return "-";
		}
	}

	@Override
	public void setEntry(int i, Object obj) {
		switch (i){
		case 0:
			this.first_name=(String)obj;
			break;
		case 1:
			this.last_name=(String)obj;
			break;
		case 2:
			this.birth_year=(int)obj;
			break;
		}
	}
	
	public static Individuals defaultElement(){
		return new Individuals("Jean","Défaut", 1901);
	}

	public String toString(){
		return last_name+" "+first_name;
	}
}
