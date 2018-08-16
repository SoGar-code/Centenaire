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
	// Entity properly speaking
	ENTITY(0, "Entité"),
	INDIV(1, "Personne"),
	ITEM(2, "Production"),
	EVENTS(3, "Evénement"),
	INSTIT(4, "Institution"),
	
	// TagLike classes
	ITEMTYPE(5, "Type de production"),
	EVENTTYPE(6, "Type d'événement"),
	INSTITTYPE(7, "Type d'institution"),
	TAG(8, "Mot-clef"),
	DISCIPLINES(9, "Discipline"),
	INSTITSTATUS(10, "Statut institutionnel"),
	LOCALISATIONTYPE(11, "Type de soutien"),
	TAXCHRONO(23, "Taxinomie chronologique"),
	TAXGEO(24, "Taxinomie géographique"),
	TAXTHEME(25, "Taxinomie thématique"),
	DEPT(26, "Département"),
	COUNTRY(27, "Pays"),
	
	// Relation classes (with label)
	INDIVINSTIT(12, "Institut de rattachement"),
	LOCALISATION(13, "Soutien institutionnel"),
	
	// Relation classes (no labels)
	INDIVDISCIPL(14, "Discipline personnelle"),
	INDIVTAG(15, "Mot-clef personnel"),
	ITEMTAG(16, "Mot-clef production"),
	EVENTTAG(17, "Mot-clef événement"),
	AUTHOR(18, "Auteur"),
	DIRECTION(19, "Direction"),
	ORG(20, "Organisation"),
	PARTICIPANT(21, "Participant"),
	AFFILIATION(22, "Affiliation"),
	EXPITEM(28, "Expertise - production"),
	EXPEVENT(29, "Expertise - événement"),
	EXPINSTIT(30, "Expertise - institution"),
	ITEMTAXCHRONO(31, "Taxinomie chrono - production"),
	ITEMTAXGEO(32, "Taxinomie géo - production"),
	ITEMTAXTHEME(33, "Taxinomie thématique - production"),
	EVENTSTAXCHRONO(34, "Taxinomie chrono - production"),
	EVENTSTAXGEO(35, "Taxinomie géo - production"),
	EVENTSTAXTHEME(36, "Taxinomie thématique - production");
		
	// NB: max = 36 (see EVENTSTAXTHEME)
	
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
