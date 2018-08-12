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
import org.centenaire.entity.Tag;

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
	
	public abstract RelationDao<Individual, Item> getAuthor();
	
	public abstract RelationDao<Individual, Item> getDirection();
	
	public abstract RelationDao<Individual, Event> getOrg();
	
	public abstract RelationDao<Individual, Event> getParticipant();
	
	public abstract RelationDao<Item, Institution> getAffiliation();
	
	public abstract RelationDao<Individual, DoubleEntity<Institution, InstitStatus>> getInstitStatus();
	
	/**
	 * To get a Dao class indexed by an integer
	 * 
	 * @param i
	 * 			index of the class under consideration.
	 * 
	 * @return suitable requested DAO element.
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
		} else if (i == EntityEnum.AUTHOR.getValue()) {
			return getAuthor();
		} else if (i == EntityEnum.DIRECTION.getValue()) {
			return getDirection();
		} else if (i == EntityEnum.ORG.getValue()) {
			return getOrg();
		} else if (i == EntityEnum.PARTICIPANT.getValue()) {
			return getParticipant();
		} else if (i == EntityEnum.AFFILIATION.getValue()) {
			return getAffiliation();
		} else if (i == EntityEnum.INSTITSTATUS.getValue()) {
			return getInstitStatus();
		} else {
			String msg = String.format(
					"AbstractRelationDaoFactory.getRelationDao -- type '%s' not found!",
					i);
			System.out.println(msg);
			return null;
		}
	}
	
}
