package org.centenaire.main.questionnaire;

import java.sql.Date;
import java.util.LinkedList;

import javax.swing.JPanel;

import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Individual;
import org.centenaire.util.dragndrop.DropListTableModel;
import org.centenaire.util.dragndrop.DropTable;
import org.centenaire.util.editorsRenderers.Delete;

public class QuestionOrgConf extends QuestionTemplate {
	private DropTable<Individual, Event> dropTableEvent;
	
	public QuestionOrgConf(String numbering, String questionString){
		super(numbering);

		this.setQuestionLab(questionString);
		
		JPanel main = this.getMain();

		// Table of items
		dropTableEvent = new DropTable<Individual, Event>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.EVENTS.getValue(),
				EntityEnum.ORG.getValue(),
				new Class[] {String.class, String.class, Date.class, String.class, Delete.class},
				new String[] {"Titre", "Type", "Date de début", "Lieu", "Retirer"},
				4
				);
		
		main.add(dropTableEvent);
		
	}

	@Override
	public void saveQuestion() {
		Individual currentIndividual = gc.getCurrentIndividual();
		this.dropTableEvent.saveContent(currentIndividual);
	}

	@Override
	public void setQuestion() {
		Individual currentIndividual = gc.getCurrentIndividual();
		this.dropTableEvent.updateEntity(currentIndividual);
	}

	@Override
	public void resetQuestion() {
		LinkedList<Entity> listItem = new LinkedList<Entity>();
		((DropListTableModel<Individual, Event>) this.dropTableEvent.getModel()).setData(listItem);

	}

}
