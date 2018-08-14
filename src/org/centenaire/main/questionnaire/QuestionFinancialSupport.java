package org.centenaire.main.questionnaire;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JPanel;

import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Institution;
import org.centenaire.entity.LocalType;
import org.centenaire.util.dragndrop.DropTable;
import org.centenaire.util.dragndrop.TargetHandlerDouble;
import org.centenaire.util.editorsRenderers.Delete;

public class QuestionFinancialSupport extends QuestionTemplate {
	protected final static Logger LOGGER = Logger.getLogger(QuestionFinancialSupport.class.getName());
	
	private EventSupportCombo eventCombo;
	private DropTable<Event, DoubleEntity<Institution, LocalType>> dropTableEvent;
	private ActionListener comboListener;
	private Event currentEvent;
	
	public Event getCurrentEvent() {
		return currentEvent;
	}

	public void setCurrentEvent(Event currentEvent) {
		this.currentEvent = currentEvent;
	}

	public QuestionFinancialSupport(String numbering, String questionString){
		super(numbering);
		LOGGER.setLevel(Level.FINEST);

		this.setQuestionLab(questionString);
		
		JPanel main = this.getMain();
		main.setLayout(new BorderLayout());

		// comboPan for improved layout
		JPanel comboPan = new JPanel();
		eventCombo = new EventSupportCombo();
		eventCombo.setSelectedIndex(-1);
		
		comboListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {		
					// recover the currently selected object
					Event currentEvent = (Event) eventCombo.getSelectedItem();
					
					// save it in variable with 'QuestionFinancialSupport' scope
					setCurrentEvent(currentEvent);
					
					// call an update
					setQuestion();
					
				} catch (NullPointerException except) {
					String msg = "QuestionFinancialSupport comboListener -- null currentEvent,\n"
							+ "resetting the panel!";
					LOGGER.info(msg);
					
					resetQuestion();
				}
			}
		};
		
		eventCombo.addActionListener(comboListener);
		
		comboPan.add(eventCombo);
		
		// Table of items
		dropTableEvent = new DropTable<Event, DoubleEntity<Institution, LocalType>>(
				EntityEnum.EVENTS.getValue(),
				EntityEnum.INSTIT.getValue(),
				EntityEnum.LOCALISATION.getValue(),
				new Class[] {String.class, LocalType.class, Delete.class},
				new String[] {"Institution", "Type de soutien", "Retirer"}
				);
		// Replace the default TargetHandler:
		dropTableEvent.getTable().setTransferHandler(
				new TargetHandlerDouble<Institution, LocalType>(EntityEnum.INSTIT.getValue()));
		
		main.add(comboPan, BorderLayout.NORTH);
		main.add(dropTableEvent, BorderLayout.CENTER);
		
	}

	/**
	 * Save the content of this question.
	 * 
	 * <p>This method needs to be implemented by subclasses.
	 * It is the one called when pressing the save button.</p>
	 */
	@Override
	public void saveQuestion() {
		try {
			this.dropTableEvent.saveContent(currentEvent);
		} catch (NullPointerException e) {
			
			// if currentEvent is null, just print message
			String msg = "QuestionFinancialSupport.saveQuestion -- null currentEvent,\n"
					+ "resetting the panel, no save.";
			LOGGER.info(msg);
		}
	}

	/**
	 * Set the content of the question (when changing individual, for instance).
	 */
	@Override
	public void setQuestion() {
		try {
			this.dropTableEvent.updateEntity(currentEvent);
		} catch (NullPointerException e) {
			// if currentEvent is null, reset table
			this.dropTableEvent.reset();
		}
	}

	/**
	 * Reset the content of the question
	 */
	@Override
	public void resetQuestion() {
		this.eventCombo.setSelectedIndex(-1);
	}

}
