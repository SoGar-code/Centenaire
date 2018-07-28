package org.centenaire.entityeditor;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.EventType;
import org.centenaire.util.Converters;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GDateField;
import org.centenaire.util.GeneralController;

/**
 * Class providing the editor form for 'Event' Entity class. 
 *
 * @see Event
 */
public class EventEditor extends EntityEditor<Event> {
	JTextField nameField;
	JTextField placeField;
	GDateField startDateField;
	GDateField endDateField;
	EntityCombo<EventType> eventTypeCombo;

	public EventEditor() {
		super();
		
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		
		// Name field
		// =================
		JPanel namePan = new JPanel();
		JLabel nameLabel = new JLabel("Nom : ");
		
		// Active text area...
		nameField = new JTextField(20);
		nameField.setText("-");
		
		namePan.add(nameLabel);
		namePan.add(nameField);
		
		// Place field
		// =================
		JPanel placePan = new JPanel();
		JLabel placeLabel = new JLabel("Lieu : ");
		
		// Active text area...
		placeField = new JTextField(20);
		placeField.setText("-");
		
		placePan.add(placeLabel);
		placePan.add(placeField);
		
		// Start date field
		// ================
		JPanel startDatePan = new JPanel();
		JLabel startDateLabel = new JLabel("Date de début (AAAA-MM-JJ) : ");
		startDateField = new GDateField();
		startDateField.setDate(today);
		
		startDatePan.add(startDateLabel);
		startDatePan.add(startDateField);
		
		// End date field
		// ================
		JPanel endDatePan = new JPanel();
		JLabel endDateLabel = new JLabel("Date de fin (AAAA-MM-JJ) : ");
		endDateField = new GDateField();
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
		this.add(namePan);
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
		String name = this.nameField.getText();
		String place = this.placeField.getText();
		Date startDate = this.startDateField.getDate();
		Date endDate = this.endDateField.getDate();
		EventType eventType = this.eventTypeCombo.getSelectedEntity();

		Event event = new Event(name, 
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
		nameField.setText(obj.getName());
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
		nameField.setText("-");
		placeField.setText("-");
		startDateField.setDate(today);
		endDateField.setDate(today);
		eventTypeCombo.setSelectedItem(-1);
	}
}
