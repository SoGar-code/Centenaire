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

public class QuestionTheses extends QuestionTemplate {
	private DropTable<Individual, Item> dropTableItems;
	
	public QuestionTheses(String numbering, String questionString){
		super(numbering);

		this.setQuestionLab(questionString);
		
		JPanel main = this.getMain();

		// Table of items
		dropTableItems = new DropTable<Individual, Item>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.ITEM.getValue(),
				EntityEnum.DIRECTION.getValue(),
				new Class[] {String.class, String.class, Date.class, Delete.class},
				new String[] {"Titre", "Type", "Date de début", "Retirer"},
				3
				);
		
		main.add(dropTableItems);
		
	}

	@Override
	public void saveQuestion() {
		Individual currentIndividual = gc.getCurrentIndividual();
		this.dropTableItems.saveContent(currentIndividual);
	}

	@Override
	public void setQuestion() {
		Individual currentIndividual = gc.getCurrentIndividual();
		this.dropTableItems.updateEntity(currentIndividual);
	}

	@Override
	public void resetQuestion() {
		LinkedList<Entity> listItem = new LinkedList<Entity>();
		((DropListTableModel) this.dropTableItems.getModel()).setData(listItem);

	}

}
