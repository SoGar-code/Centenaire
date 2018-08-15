package org.centenaire.entity.editor;

import java.sql.Date;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.centenaire.entity.Country;
import org.centenaire.entity.Departement;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.EventType;
import org.centenaire.entity.util.EntityCombo;
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
	EntityCombo<Departement> deptCombo;
	EntityCombo<Country> countryCombo;
	GDateField startDateField;
	GDateField endDateField;
	EntityCombo<EventType> eventTypeCombo;

	public EventEditor() {
		super();
		
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		
		GeneralController gc = GeneralController.getInstance();
		
		// Full name field
		// =================
		JPanel fullNamePan = new JPanel();
		JLabel fullNameLabel = new JLabel("Nom complet : ");
		
		// Active text area...
		fullNameField = new JTextArea(3, 30);
		fullNameField.setLineWrap(true);
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
		shortNamePan.add(shortNameField);
		
		// Place field
		// =================
		JPanel placePan = new JPanel();
		JLabel placeLabel = new JLabel("Lieu : ");
		
		// Active text area...
		placeField = new JTextField(30);
		placeField.setText("-");
		
		placePan.add(placeLabel);
		placePan.add(placeField);
		
		// Departement Combo
		// =======================
		JPanel deptPan = new JPanel();
		JLabel deptLab = new JLabel("Département : ");
		deptCombo = new EntityCombo<Departement>(EntityEnum.DEPT.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		gc.getChannel(EntityEnum.DEPT.getValue()).addSubscriber(deptCombo);
		
		deptPan.add(deptLab);
		deptPan.add(deptCombo);
		
		// Country Combo
		// =======================
		JPanel countryPan = new JPanel();
		JLabel countryLab = new JLabel("Pays : ");
		countryCombo = new EntityCombo<Country>(EntityEnum.COUNTRY.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		gc.getChannel(EntityEnum.COUNTRY.getValue()).addSubscriber(countryCombo);
		
		countryPan.add(countryLab);
		countryPan.add(countryCombo);
		
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
		gc.getChannel(EntityEnum.EVENTTYPE.getValue()).addSubscriber(eventTypeCombo);
		
		eventTypePan.add(eventTypeLabel);
		eventTypePan.add(eventTypeCombo);
		
		// Final assembly
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(fullNamePan);
		this.add(shortNamePan);
		this.add(placePan);
		this.add(deptPan);
		this.add(countryPan);
		this.add(startDatePan);
		this.add(endDatePan);
		this.add(eventTypePan);
	}

	/** 
	 * Recover the 'Event' entity defined by the current instance.
	 * 
	 * @see org.centenaire.entity.editor.EntityEditor#getObject()
	 */
	@Override
	public Event getObject() {
		int index = this.getIndexField();
		String fullName = this.fullNameField.getText();
		String shortName = this.shortNameField.getText();
		String place = this.placeField.getText();
		Departement dept = this.deptCombo.getSelectedEntity();
		Country country = this.countryCombo.getSelectedEntity();
		Date startDate = this.startDateField.getDate();
		Date endDate = this.endDateField.getDate();
		EventType eventType = this.eventTypeCombo.getSelectedEntity();

		Event event = new Event(fullName,
								shortName,
								place, 
								dept,
								country,
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
		deptCombo.setSelectedEntity(obj.getDept());
		countryCombo.setSelectedEntity(obj.getCountry());
		startDateField.setDate(obj.getStartDate());
		endDateField.setDate(obj.getEndDate());
		eventTypeCombo.setSelectedEntity(obj.getEventType());
	}
	
	/**
	 * Method to reset the EntityEditor to a neutral value.
	 */
	public void reset() {
		
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		
		this.setIndexField(0);
		fullNameField.setText("-");
		shortNameField.setText("-");
		placeField.setText("-");
		deptCombo.setSelectedItem(-1);
		countryCombo.setSelectedItem(-1);
		startDateField.setDate(today);
		endDateField.setDate(today);
		eventTypeCombo.setSelectedItem(-1);
	}
}
