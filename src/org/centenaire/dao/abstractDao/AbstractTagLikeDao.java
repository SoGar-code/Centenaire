package org.centenaire.dao.abstractDao;

import org.centenaire.dao.Dao;
import org.centenaire.entity.TagLike;

public abstract class AbstractTagLikeDao extends Dao<TagLike> {
	
	// 
	// 
	/**
	 * Returns an element of type TagLike
	 * 
	 * <p>This element is either an already existing one or
	 * we create and initialize a new one in the database.</p>
	 */
	public abstract TagLike anyElement();
}
