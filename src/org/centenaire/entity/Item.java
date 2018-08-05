/**
 * 
 */
package org.centenaire.entity;

import java.sql.Date;

/**
 * Implementation of 'Item' Entity class.
 *
 */
public class Item extends Entity {
	private String title;
	private ItemType itemType;
	private Date startDate;
	private Date endDate;

	public Item(int index, String title, ItemType itemType, Date startDate, Date endDate) {
		super(index);
		this.classIndex = EntityEnum.ITEM.getValue();
		
		this.title = title;
		this.itemType = itemType;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Item(String title, ItemType itemType, Date startDate, Date endDate) {
		super();
		this.classIndex = EntityEnum.ITEM.getValue();
		
		this.title = title;
		this.itemType = itemType;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
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
	
	public String toString() {
		return this.title;
	}

	/* (non-Javadoc)
	 * @see org.centenaire.entity.Entity#getEntry(int)
	 */
	@Override
	public Object getEntry(int i) {
		switch (i){
		case 0:
			return title;
		case 1:
			return startDate;
		default:
			return "-";
		}
	}

	/* (non-Javadoc)
	 * @see org.centenaire.entity.Entity#setEntry(int, java.lang.Object)
	 */
	@Override
	public void setEntry(int i, Object obj) {
		// TODO Auto-generated method stub

	}

}
