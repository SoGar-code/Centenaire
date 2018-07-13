package gestionBilicence.edition;

import gestionBilicence.general.Entity;

public class Semester extends Entity {
	protected String name;

	public Semester(String name) {
		super();
		this.name = name;
	}

	public Semester(int index, String name) {
		super(index);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public Object getEntry(int i) {
		switch(i){
			case 0:
				return name;
			default:
				return "-";
		}
	}

	@Override
	public void setEntry(int i, Object obj) {
		switch(i){
			case 0:
				this.name = (String) obj;
				break;
			default:
				System.out.println("Semester.setEntry - got int i="+i);
		}

	}

	public static Semester defaultElement() {
		return new Semester("default semester");
	}

	public String toString(){
		return name;
	}
}
