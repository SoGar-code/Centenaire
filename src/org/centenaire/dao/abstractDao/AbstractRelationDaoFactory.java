package org.centenaire.dao.abstractDao;

import org.centenaire.dao.RelationDao;
import org.centenaire.entity.Discipline;
import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Individual;
import org.centenaire.entity.InstitStatus;
import org.centenaire.entity.Institution;
import org.centenaire.entity.Item;
import org.centenaire.entity.LocalType;
import org.centenaire.entity.Tag;
import org.centenaire.entity.TaxChrono;
import org.centenaire.entity.TaxGeo;
import org.centenaire.entity.TaxTheme;

/**
 * Factory class for RelationDao classes
 *
 * <p>
 * This class includes the factory ("dispatcher") of RelationDao classes.
 * Concretely, it provides:
 * <ul>	<li>a list of the Entity classes linked by the relations,</li>
 * 		<li>the method "getRelationDao(int i)".</li>
 * </ul></p>
 * 
 * <p>In this "getRelationDao" method, the integer used to call the Dao class 
 * is defined by the EntityEnum Enumeration.</p>
 *
 * @see org.centenaire.entity.EntityEnum
 */
public abstract class AbstractRelationDaoFactory {
	
	public abstract RelationDao<Individual, Discipline> getIndivDiscpl();

	public abstract RelationDao<Individual, Tag> getIndivTag();
	
	public abstract RelationDao<Item, Tag> getItemTag();
	
	public abstract RelationDao<Event, Tag> getEventTag();
	
	public abstract RelationDao<Individual, Item> getSciAuthor();
	public abstract RelationDao<Item, Individual> getItemSciAuthor();
	
	public abstract RelationDao<Individual, Item> getOutreachAuthor();
	public abstract RelationDao<Item, Individual> getItemOutreachAuthor();
	
	public abstract RelationDao<Individual, Item> getDigAuthor();
	public abstract RelationDao<Item, Individual> getItemDigAuthor();
	
	public abstract RelationDao<Individual, Item> getDirection();
	public abstract RelationDao<Item, Individual> getItemDirection();
	
	public abstract RelationDao<Individual, Event> getOrg();
	public abstract RelationDao<Event, Individual> getEventOrg();
	
	public abstract RelationDao<Individual, Event> getSciParticipant();
	
	public abstract RelationDao<Individual, Event> getOutreachParticipantG();

	public abstract RelationDao<Individual, Event> getOutreachParticipantConf();
	
	public abstract RelationDao<Item, Institution> getAffiliation();
	
	public abstract RelationDao<Individual, DoubleEntity<Institution, InstitStatus>> getInstitStatus();
	
	public abstract RelationDao<Event, DoubleEntity<Institution, LocalType>> getFinancialSupport();
	
	public abstract RelationDao<Individual, Item> getExpItem();
	public abstract RelationDao<Item, Individual> getItemExp();
	
	public abstract RelationDao<Individual, Event> getExpEvent();
	public abstract RelationDao<Event, Individual> getEventExp();
	
	public abstract RelationDao<Individual, Institution> getExpInstit();

	public abstract RelationDao<Item, TaxChrono> getItemTaxChrono();
	
	public abstract RelationDao<Item, TaxGeo> getItemTaxGeo();
	
	public abstract RelationDao<Item, TaxTheme> getItemTaxTheme();
	
	public abstract RelationDao<Event, TaxChrono> getEventTaxChrono();
	
	public abstract RelationDao<Event, TaxGeo> getEventTaxGeo();
	
	public abstract RelationDao<Event, TaxTheme> getEventTaxTheme();
	
	/**
	 * To get a Relation Dao class indexed by an integer
	 * 
	 * @param i
	 * 			index of the class under consideration.
	 * 
	 * @return suitable requested RelationDAO element.
	 * 
	 * @see org.centenaire.entity.Entity#getClassIndex()
	 */
	public RelationDao getRelationDao(int i){
		if (i == EntityEnum.ENTITY.getValue()) {
			String msg = "AbstractRelationDaoFactory.getRelationDao -- classIndex 0 is for "
					+ "the abstract Entity class! So no DAO...";
			System.out.println(msg);
			return null;
		} else if (i == EntityEnum.INDIVDISCIPL.getValue()) {
			return getIndivDiscpl();
		} else if (i == EntityEnum.INDIVTAG.getValue()) {
			return getIndivTag();
		} else if (i == EntityEnum.ITEMTAG.getValue()) {
			return getItemTag();
		} else if (i == EntityEnum.EVENTTAG.getValue()) {
			return getEventTag();
		} else if (i == EntityEnum.SCIAUTHOR.getValue()) {
			return getSciAuthor();
		} else if (i == EntityEnum.OUTREACHAUTHOR.getValue()) {
			return getOutreachAuthor();
		} else if (i == EntityEnum.DIGAUTHOR.getValue()) {
			return getDigAuthor();
		} else if (i == EntityEnum.DIRECTION.getValue()) {
			return getDirection();
		} else if (i == EntityEnum.ORG.getValue()) {
			return getOrg();
		} else if (i == EntityEnum.SCIPARTICIPANT.getValue()) {
			return getSciParticipant();
		} else if (i == EntityEnum.OUTREACHPARTICIPANTG.getValue()) {
			return getOutreachParticipantG();
		} else if (i == EntityEnum.OUTREACHPARTICIPANTCONF.getValue()) {
			return getOutreachParticipantConf();
		} else if (i == EntityEnum.AFFILIATION.getValue()) {
			return getAffiliation();
		} else if (i == EntityEnum.INDIVINSTIT.getValue()) {
			return getInstitStatus();
		} else if (i == EntityEnum.LOCALISATION.getValue()) {
			return getFinancialSupport();
		} else if (i == EntityEnum.EXPITEM.getValue()) {
			return getExpItem();
		} else if (i == EntityEnum.EXPEVENT.getValue()) {
			return getExpEvent();
		} else if (i == EntityEnum.EXPINSTIT.getValue()) {
			return getExpInstit();
		} else if (i == EntityEnum.ITEMTAXCHRONO.getValue()) {
			return getItemTaxChrono();
		} else if (i == EntityEnum.ITEMTAXGEO.getValue()) {
			return getItemTaxGeo();
		} else if (i == EntityEnum.ITEMTAXTHEME.getValue()) {
			return getItemTaxTheme();
		} else if (i == EntityEnum.EVENTSTAXCHRONO.getValue()) {
			return getEventTaxChrono();
		} else if (i == EntityEnum.EVENTSTAXGEO.getValue()) {
			return getEventTaxGeo();
		} else if (i == EntityEnum.EVENTSTAXTHEME.getValue()) {
			return getEventTaxTheme();
		} else {
			String msg = String.format(
					"AbstractRelationDaoFactory.getRelationDao -- type '%s' not found!",
					i);
			System.out.println(msg);
			return null;
		}
	}
	
	/**
	 * To get an inverted Relation Dao class indexed by an integer.
	 * 
	 * <p>The 'inverted' relation Dao are not as common as 'regular' relation Dao.</p>
	 * 
	 * @param i
	 * 			index of the (inverted) class under consideration.
	 * 
	 * @return suitable requested (inverted) relation DAO element.
	 * 
	 * @see org.centenaire.entity.Entity#getClassIndex()
	 */
	public RelationDao getInvertedRelationDao(int i){
		if (i == EntityEnum.ENTITY.getValue()) {
			String msg = "AbstractRelationDaoFactory.getRelationDao -- classIndex 0 is for "
					+ "the abstract Entity class! So no DAO...";
			System.out.println(msg);
			return null;
		} else if (i == EntityEnum.SCIAUTHOR.getValue()) {
			return getItemSciAuthor();
		} else if (i == EntityEnum.OUTREACHAUTHOR.getValue()) {
			return getItemOutreachAuthor();
		} else if (i == EntityEnum.DIGAUTHOR.getValue()) {
			return getItemDigAuthor();
		} else if (i == EntityEnum.DIRECTION.getValue()) {
			return getItemDirection();
		} else if (i == EntityEnum.ORG.getValue()) {
			return getEventOrg();
		} else if (i == EntityEnum.EXPITEM.getValue()) {
			return getItemExp();
		} else if (i == EntityEnum.EXPEVENT.getValue()) {
			return getEventExp();
		} else {
			String msg = String.format(
					"AbstractRelationDaoFactory.getInvertedRelationDao -- type '%s' not found!",
					i);
			System.out.println(msg);
			return null;
		}
	}
	
}
