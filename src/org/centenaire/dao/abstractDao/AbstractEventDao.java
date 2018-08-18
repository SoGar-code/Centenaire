package org.centenaire.dao.abstractDao;

import java.util.List;
import java.util.logging.Logger;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Event;
import org.centenaire.entity.typelike.CatEnum;

public abstract class AbstractEventDao extends Dao<Event> {
	protected final static Logger LOGGER = Logger.getLogger(AbstractEventDao.class.getName());
	
	/**
	 * Recover all Items in DB with category in a prescribed list.
	 * 
	 * @param categories
	 * 				list of categories whose elements we should recover.
	 */
	abstract public List<Event> findAll(List<CatEnum> categories);
}