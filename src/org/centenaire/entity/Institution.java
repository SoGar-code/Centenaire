/**
 * 
 */
package org.centenaire.entity;

/**
 * Implementation of 'Institution' Entity class.
 *
 */
public class Institution extends Entity {
	private String name;
	private String place;
	private InstitutionType institType;
	
	public Institution(int index, String name, String place, InstitutionType institType) {
		super(index);
		this.name = name;
		this.place = place;
		this.institType = institType;
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

	public InstitutionType getInstitType() {
		return institType;
	}

	public void setInstitType(InstitutionType institType) {
		this.institType = institType;
	}

	/* (non-Javadoc)
	 * @see org.centenaire.entity.Entity#getEntry(int)
	 */
	@Override
	public Object getEntry(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.centenaire.entity.Entity#setEntry(int, java.lang.Object)
	 */
	@Override
	public void setEntry(int i, Object obj) {
		// TODO Auto-generated method stub

	}

}
