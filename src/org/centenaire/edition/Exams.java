package gestionBilicence.edition;

import gestionBilicence.dao.Dao;
import gestionBilicence.general.Entity;
import gestionBilicence.general.GeneralController;

public class Exams extends Entity {
	protected String name;
	protected Semester semester;
	protected int coefficient;
	
	
	public Exams(String name, Semester semester, int coefficient) {
		super();
		this.name = name;
		this.semester = semester;
		this.coefficient = coefficient;
	}
	
	public Exams(int index, String name, Semester semester, int coefficient) {
		super(index);
		this.name = name;
		this.semester = semester;
		this.coefficient = coefficient;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Semester getSemester() {
		return semester;
	}

	public void setSemester(Semester semester) {
		this.semester = semester;
	}

	public int getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(int coefficient) {
		this.coefficient = coefficient;
	}

	@Override
	public Object getEntry(int i) {
		switch (i){
			case 0:
				return name;
			case 1:
				return semester;
			case 2:
				return coefficient;
			default:
				return "-";
		}
	}

	@Override
	public void setEntry(int i, Object obj) {
		switch (i){
		case 0:
			this.name=(String)obj;
			break;
		case 1:
			this.semester=(Semester)obj;
			break;
		case 2:
			this.coefficient=(int)obj;
			break;
		}
	}
	
	public static Exams defaultElement(){
		return new Exams("Examen par défaut",
				GeneralController.getInstance().getSemesterDao().anyElement(),
				1
				);
	}

	public String toString(){
		return this.name;
	}
	
}
