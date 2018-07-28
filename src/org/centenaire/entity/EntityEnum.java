/**
 * 
 */
package org.centenaire.entity;

/**
 * An enumeration to keep track of the different 'Entity' classes in the system.
 * 
 * <p>With this enumeration, it is not necessary to remember the <it>classIndex</it>
 * of each 'Entity' class.</p>
 *
 */
public enum EntityEnum {
	ENTITY(0, "Entité"),
	INDIV(1, "Personne"),
	ITEM(2, "Production"),
	EVENTS(3, "Evénement"),
	INSTIT(4, "Institution"),
	ITEMTYPE(5, "Type de production"),
	EVENTTYPE(6, "Type d'événement"),
	INSTITTYPE(7, "Type d'institution"),
	TAG(8, "Mot-clef"),
	DISCIPLINES(9, "Discipline"),
	INSTITSTATUS(10, "Statut institutionnel"),
	LOCALISATIONTYPE(11, "Type de soutien");
	
	private final int classIndex;
	private final String name;
	
	EntityEnum(int classIndex, String name){
		this.classIndex = classIndex;
		this.name = name;
	}
	
	public int getValue() {
		return classIndex;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
}
