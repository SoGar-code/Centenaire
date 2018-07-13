package gestionBilicence.edition;

import gestionBilicence.general.Entity;

public class Student extends Entity {
	protected String first_name;
	protected String last_name;
	
	public Student(String first_name, String last_name) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public Student(int index, String first_name, String last_name) {
		super(index);
		this.first_name = first_name;
		this.last_name = last_name;
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

	@Override
	public Object getEntry(int i) {
		switch (i){
			case 0:
				return first_name;
			case 1:
				return last_name;
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
		}
	}
	
	public static Student defaultElement(){
		return new Student("Jean","Défaut");
	}

	public String toString(){
		return last_name+" "+first_name;
	}
}
