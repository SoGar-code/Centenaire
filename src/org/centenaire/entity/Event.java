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
	/**
	 * Numbering of the different (concrete) Entity classes.
	 * 
	 */
	protected static int classIndex = EntityEnum.EVENTS.getValue();
	
	private String name;
	private String place;
	private Date startDate;
	private Date endDate;
	private EventType eventType;
	
	/**
	 * Default constructor for the 'Event' Entity class.
	 * 
	 * @param index
	 * 			index of this Event as an Entity class
	 * @param name
	 * 			Name of the Event
	 * @param place
	 * 			Place where the Event takes place
	 * @param startDate
	 * 			Start date for the Event (when applicable)
	 * @param endDate
	 * 			End date for the Event (when applicable)
	 * @param eventType
	 * 			Type of Event
	 */
	public Event(int index, String name, String place, Date startDate, Date endDate, EventType eventType) {
		super(index);
		this.name = name;
		this.place = place;
		this.startDate = startDate;
		this.endDate = endDate;
		this.eventType = eventType;
	}
	
	/**
	 * Constructor for the 'Event' Entity class (no index).
	 * 
	 * @param name
	 * 			Name of the Event
	 * @param place
	 * 			Place where the Event takes place
	 * @param startDate
	 * 			Start date for the Event (when applicable)
	 * @param endDate
	 * 			End date for the Event (when applicable)
	 * @param eventType
	 * 			Type of Event
	 */
	public Event(String name, String place, Date startDate, Date endDate, EventType eventType) {
		super();
		this.name = name;
		this.place = place;
		this.startDate = startDate;
		this.endDate = endDate;
		this.eventType = eventType;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setEntry(int i, Object obj) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString() {
		return this.name;
	}
}
