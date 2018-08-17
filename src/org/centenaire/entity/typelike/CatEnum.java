/**
 * 
 */
package org.centenaire.entity.typelike;

/**
 * An Enumeration recording the different 'categories' describing events and items.
 *
 */
public enum CatEnum {

	SCI(0, "Scientifique"),
	DIG(1, "Numérique"),
	OUTREACH(2, "Vulgarisation");
	
	private final int catIndex;
	private final String name;
	
	CatEnum(int catIndex, String name){
		this.catIndex = catIndex;
		this.name = name;
	}
	
	public int getValue() {
		return catIndex;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
