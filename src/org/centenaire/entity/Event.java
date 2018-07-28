/**
 * 
 */
package org.centenaire.entity;

import java.util.Date;

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
	private int eventType;
	
	/**
	 * Default constructor for the 'Event' Entity class.
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
	public Event(String name, String place, Date startDate, Date endDate, int eventType) {
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
	public String getplace() {
		return place;
	}
	public void setplace(String place) {
		this.place = place;
	}
	public Date getstartDate() {
		return startDate;
	}
	public void setstartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getendDate() {
		return endDate;
	}
	public void setendDate(Date endDate) {
		this.endDate = endDate;
	}
	public int geteventType() {
		return eventType;
	}
	public void seteventType(int eventType) {
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
	
	
}
