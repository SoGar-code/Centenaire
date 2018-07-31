package org.centenaire.entityeditor;

import java.sql.Date;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.EventType;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GDateField;
import org.centenaire.util.GeneralController;

/**
 * Class providing the editor form for 'Event' Entity class. 
 *
 * @see Event
 */
public class EventEditor extends EntityEditor<Event> {
	JTextArea fullNameField;
	JTextField shortNameField;
	JTextField placeField;
	GDateField startDateField;
	GDateField endDateField;
	EntityCombo<EventType> eventTypeCombo;

	public EventEditor() {
		super();
		
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		
		// Full name field
		// =================
		JPanel fullNamePan = new JPanel();
		JLabel fullNameLabel = new JLabel("Nom complet : ");
		
		// Active text area...
		fullNameField = new JTextArea(3, 30);
		fullNameField.setText("-");
		
		fullNamePan.add(fullNameLabel);
		fullNamePan.add(fullNameField);
		
		// Name field
		// =================
		JPanel shortNamePan = new JPanel();
		JLabel shortNameLabel = new JLabel("Abréviation : ");
		
		// Active text area...
		shortNameField = new JTextField(20);
		shortNameField.setText("-");
		
		shortNamePan.add(shortNameLabel);
		shortNamePan.add(fullNameField);
		
		// Place field
		// =================
		JPanel placePan = new JPanel();
		JLabel placeLabel = new JLabel("Lieu : ");
		
		// Active text area...
		placeField = new JTextField(30);
		placeField.setText("-");
		
		placePan.add(placeLabel);
		placePan.add(placeField);
		
		// Start date field
		// ================
		JPanel startDatePan = new JPanel();
		JLabel startDateLabel = new JLabel("Date de début (AAAA-MM-JJ) : ");
		startDateField = new GDateField();
		startDateField.setColumns(12);
		startDateField.setDate(today);
		
		startDatePan.add(startDateLabel);
		startDatePan.add(startDateField);
		
		// End date field
		// ================
		JPanel endDatePan = new JPanel();
		JLabel endDateLabel = new JLabel("Date de fin (AAAA-MM-JJ) : ");
		endDateField = new GDateField();
		endDateField.setColumns(12);
		endDateField.setDate(today);
		
		endDatePan.add(endDateLabel);
		endDatePan.add(endDateField);
		
		// Event Type Combo
		// =================		
		JPanel eventTypePan = new JPanel();
		JLabel eventTypeLabel = new JLabel("Type d'événement : ");
		eventTypeCombo = new EntityCombo<EventType>(EntityEnum.EVENTTYPE.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		GeneralController gc = GeneralController.getInstance();
		gc.getChannel(EntityEnum.EVENTTYPE.getValue()).addSubscriber(eventTypeCombo);
		
		eventTypePan.add(eventTypeLabel);
		eventTypePan.add(eventTypeCombo);
		
		// Final assembly
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(fullNamePan);
		this.add(shortNamePan);
		this.add(placePan);
		this.add(startDatePan);
		this.add(endDatePan);
		this.add(eventTypePan);
	}

	/** 
	 * Recover the 'Event' entity defined by the current instance.
	 * 
	 * @see org.centenaire.entityeditor.EntityEditor#getObject()
	 */
	@Override
	public Event getObject() {
		int index = this.getIndexField();
		String fullName = this.fullNameField.getText();
		String shortName = this.shortNameField.getText();
		String place = this.placeField.getText();
		Date startDate = this.startDateField.getDate();
		Date endDate = this.endDateField.getDate();
		EventType eventType = this.eventTypeCombo.getSelectedEntity();

		Event event = new Event(fullName,
								shortName,
								place, 
								startDate, 
								endDate, 
								eventType);
		event.setIndex(index);
		return event;
	}

	/**
	 * Update the fields in the editor to represent another 'Event' instance.
	 */
	@Override
	public void setObject(Event obj) {
		this.setIndexField(obj.getIndex());
		fullNameField.setText(obj.getFullName());
		shortNameField.setText(obj.getShortName());
		placeField.setText(obj.getPlace());
		startDateField.setDate(obj.getStartDate());
		endDateField.setDate(obj.getEndDate());
		eventTypeCombo.setSelectedItem(obj.getEventType());
	}
	
	/**
	 * Method to reset the EntityEditor to a neutral value.
	 */
	public void reset() {
		
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		
		this.setIndexField(0);
		fullNameField.setText("-");
		placeField.setText("-");
		startDateField.setDate(today);
		endDateField.setDate(today);
		eventTypeCombo.setSelectedItem(-1);
	}
}
