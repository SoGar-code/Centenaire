package gestionBilicence.edition;

import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import gestionBilicence.dao.Dao;
import gestionBilicence.general.Entity;
import gestionBilicence.general.GeneralController;

public class Mark extends Entity {
	protected float mark;
	protected Student student;
	protected Exams exam;
	
	
	public Mark(float mark, Student student, Exams exam) {
		super();
		this.mark = mark;
		this.student = student;
		this.exam = exam;
	}
	
	public Mark(int index,float mark, Student student, Exams exam) {
		super(index);
		this.mark = mark;
		this.student = student;
		this.exam = exam;
	}


	public float getMark() {
		return mark;
	}

	public void setMark(float mark) {
		this.mark = mark;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Exams getExam() {
		return exam;
	}

	public void setExam(Exams exam) {
		this.exam = exam;
	}

	@Override
	public Object getEntry(int i) {
		switch (i){
			case 0:
				return exam;
			case 1:
				return student;
			case 2:
				return mark;
			default:
				return "-";
		}
	}

	@Override
	public void setEntry(int i, Object obj) {
		switch (i){
		case 0:
			this.exam=(Exams)obj;
			break;
		case 1:
			this.student=(Student)obj;
			break;
		case 2:
			this.mark=convertStringToFloat((String)obj);
			break;
		}
	}
	
	public static Mark defaultElement(){
		return new Mark(
				0,
				GeneralController.getInstance().getStudentDao().anyElement(),
				GeneralController.getInstance().getExamsDao().anyElement()
				);
	}
	
	// Method to convert a String into a float 
	public float convertStringToFloat(Object value){
		NumberFormat format = NumberFormat.getInstance(Locale.UK);
		float val = 0.f;
		try{
			val = format.parse((String)value).floatValue();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ListTableModel.convertStringToFloat -- ERROR!",JOptionPane.ERROR_MESSAGE);
		}
		return val;
	}
}
