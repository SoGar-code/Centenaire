package org.centenaire.dao.abstractDao;

import java.util.List;
import java.util.logging.Logger;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Item;
import org.centenaire.entity.typelike.CatEnum;

public abstract class AbstractItemDao extends Dao<Item> {
	protected final static Logger LOGGER = Logger.getLogger(AbstractItemDao.class.getName());
	
	/**
	 * Recover all Items in DB with category in a prescribed list.
	 * 
	 * @param categories
	 * 				list of categories whose elements we should recover.
	 */
	abstract public List<Item> findAll(List<CatEnum> categories);
}