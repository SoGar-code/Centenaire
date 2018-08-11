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
	private Departement dept;
	private Country country;
	private InstitutionType institType;
	
	public Institution(
			int index, 
			String name, 
			String place, 
			Departement dept,
			Country country,
			InstitutionType institType) {
		super(index);
		this.classIndex = EntityEnum.INSTIT.getValue();
		
		this.name = name;
		this.place = place;
		this.dept = dept;
		this.country = country;
		this.institType = institType;
	}
	
	public Institution(
			String name, 
			String place, 
			Departement dept,
			Country country,
			InstitutionType institType) {
		super();
		this.classIndex = EntityEnum.INSTIT.getValue();
		
		this.name = name;
		this.place = place;
		this.dept = dept;
		this.country = country;
		this.institType = institType;
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
	
	public String toString() {
		return name;
	}

	/* (non-Javadoc)
	 * @see org.centenaire.entity.Entity#getEntry(int)
	 */
	@Override
	public Object getEntry(int i) {
		switch (i){
		case 0:
			return name;
		case 1:
			return institType;
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
