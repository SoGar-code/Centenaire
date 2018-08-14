/**
 * 
 */
package org.centenaire.entity;

import java.sql.Date;

/**
 * Implementation of the 'Event' Entity class.
 *
 */
public class Event extends Entity {
	private String full_name;
	private String short_name;
	private String place;
	private Departement dept;
	private Country country;
	private Date startDate;
	private Date endDate;
	private EventType eventType;
	
	/**
	 * Default constructor for the 'Event' Entity class.
	 * 
	 * @param index
	 * 			index of this Event as an Entity class
	 * @param full_name
	 * 			Full name of the Event
	 * @param short_name
	 * 			Short name of the Event
	 * @param place
	 * 			Place where the Event takes place
	 * @param startDate
	 * 			Start date for the Event (when applicable)
	 * @param endDate
	 * 			End date for the Event (when applicable)
	 * @param eventType
	 * 			Type of Event
	 */
	public Event(int index, 
					String full_name, 
					String short_name,
					String place,
					Departement dept,
					Country country,
					Date startDate, 
					Date endDate, 
					EventType eventType) {
		super(index);
		this.classIndex = EntityEnum.EVENTS.getValue();
		
		this.full_name = full_name;
		this.short_name = short_name;
		this.place = place;
		this.dept = dept;
		this.country = country;
		this.startDate = startDate;
		this.endDate = endDate;
		this.eventType = eventType;
	}
	
	/**
	 * Default constructor for the 'Event' Entity class.
	 * 
	 * @param full_name
	 * 			Full name of the Event
	 * @param short_name
	 * 			Short name of the Event
	 * @param place
	 * 			Place where the Event takes place
	 * @param startDate
	 * 			Start date for the Event (when applicable)
	 * @param endDate
	 * 			End date for the Event (when applicable)
	 * @param eventType
	 * 			Type of Event
	 */
	public Event(
					String full_name, 
					String short_name,
					String place, 
					Departement dept,
					Country country,
					Date startDate, 
					Date endDate, 
					EventType eventType) {
		super();
		this.classIndex = EntityEnum.EVENTS.getValue();
		
		this.full_name = full_name;
		this.short_name = short_name;
		this.place = place;
		this.dept = dept;
		this.country = country;
		this.startDate = startDate;
		this.endDate = endDate;
		this.eventType = eventType;
	}

	public Departement getDept() {
		return dept;
	}

	public void setDept(Departement dept) {
		this.dept = dept;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public String getFullName() {
		return full_name;
	}

	public void setFullName(String full_name) {
		this.full_name = full_name;
	}

	public String getShortName() {
		return short_name;
	}

	public void setShortName(String short_name) {
		this.short_name = short_name;
	}

	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public EventType getEventType() {
		return eventType;
	}
	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	
	
	
	@Override
	public Object getEntry(int i) {
		switch (i){
		case 0:
			return short_name;
		case 1:
			return eventType;
		case 2:
			return startDate;
		case 3:
			return country;
		default:
			return "-";
		}
	}
	@Override
	public void setEntry(int i, Object obj) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return this.short_name;
	}
}
