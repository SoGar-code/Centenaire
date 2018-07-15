package org.centenaire.edition.entities;

import org.centenaire.dao.Dao;
import org.centenaire.edition.entities.taglike.TagLike;
import org.centenaire.general.Entity;
import org.centenaire.general.GeneralController;

public class Exams extends Entity {
	protected String name;
	protected TagLike semester;
	protected int coefficient;
	
	
	public Exams(String name, TagLike semester, int coefficient) {
		super();
		this.name = name;
		this.semester = semester;
		this.coefficient = coefficient;
	}
	
	public Exams(int index, String name, TagLike semester, int coefficient) {
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

	public TagLike getSemester() {
		return semester;
	}

	public void setSemester(TagLike semester) {
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
			this.semester=(TagLike)obj;
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
