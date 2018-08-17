/**
 * 
 */
package org.centenaire.entity;

/**
 * Class providing a factory for the different TagLike classes.
 *
 */
public class TagLikeFactory {

	public static TagLike newElement(int index, String name, int classIndex) {
		if (classIndex == EntityEnum.INSTITTYPE.getValue()) {
			InstitutionType tl = new InstitutionType(index, name);
			return tl;
		} else if (classIndex == EntityEnum.TAG.getValue()) {
			Tag tag = new Tag(index, name);
			return tag;
		} else if (classIndex == EntityEnum.DISCIPLINES.getValue()) {
			Discipline tl = new Discipline(index, name);
			return tl;
		} else if (classIndex == EntityEnum.INSTITSTATUS.getValue()) {
			InstitStatus tl = new InstitStatus(index, name);
			return tl;
		} else if (classIndex == EntityEnum.LOCALISATIONTYPE.getValue()) {
			LocalType tl = new LocalType(index, name);
			return tl;
		} else if (classIndex == EntityEnum.TAXCHRONO.getValue()) {
			TaxChrono tl = new TaxChrono(index, name);
			return tl;
		} else if (classIndex == EntityEnum.TAXGEO.getValue()) {
			TaxGeo tl = new TaxGeo(index, name);
			return tl;
		} else if (classIndex == EntityEnum.TAXTHEME.getValue()) {
			TaxTheme tl = new TaxTheme(index, name);
			return tl;
		} else if (classIndex == EntityEnum.DEPT.getValue()) {
			Departement tl = new Departement(index, name);
			return tl;
		} else if (classIndex == EntityEnum.COUNTRY.getValue()) {
			Country tl = new Country(index, name);
			return tl;
		} else {
			String msg = String.format("TagLikeFactory.newElement -- TagLike classIndex '%s' not found!", classIndex);
			System.out.println(msg);
			return null;
		}
	}
	


}
