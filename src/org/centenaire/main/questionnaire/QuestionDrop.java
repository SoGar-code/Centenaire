package org.centenaire.main.questionnaire;

import java.sql.Date;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Item;
import org.centenaire.util.dragndrop.DropListTableModel;
import org.centenaire.util.dragndrop.DropTable;
import org.centenaire.util.editorsRenderers.Delete;

public class QuestionDrop extends QuestionTemplate {
	private DropTable dropTable;
	
	/**
	 * Constructor requesting a 'dropTable'.
	 * 
	 * @param numbering
	 *				string providing the numbering of the question
	 * @param questionString
	 * 				string providing the content of the question itself.
	 */
	public QuestionDrop(String numbering, String questionString, DropTable dropTable){
		super(numbering);

		this.setQuestionLab(questionString);
		
		JPanel main = this.getMain();

		// Table of items
		this.dropTable = dropTable;
		
		main.add(this.dropTable);
		
	}

	@Override
	public void saveQuestion() {
		Individual currentIndividual = gc.getCurrentIndividual();
		this.dropTable.saveContent(currentIndividual);
	}

	@Override
	public void setQuestion() {
		Individual currentIndividual = gc.getCurrentIndividual();
		this.dropTable.updateEntity(currentIndividual);
	}

	@Override
	public void resetQuestion() {
		LinkedList<Entity> listItem = new LinkedList<Entity>();
		((DropListTableModel) this.dropTable.getModel()).setData(listItem);

	}

}
