package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.entity.taglike.TagLike;

public abstract class AbstractTagLikeDao<T> extends Dao<T> {
	
	// 
	// 
	/**
	 * Returns an element of type <T>
	 * 
	 * <p>This element is either an already existing one or
	 * we create and initialize a new one in the database.</p>
	 */
	public abstract T anyElement();
}
