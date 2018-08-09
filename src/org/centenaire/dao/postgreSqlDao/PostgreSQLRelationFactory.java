/**
 * 
 */
package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;

import org.centenaire.dao.RelationDao;
import org.centenaire.dao.abstractDao.AbstractRelationDaoFactory;
import org.centenaire.dao.postgreSqlDao.PostgreSQLRelationDao;
import org.centenaire.entity.Discipline;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Item;
import org.centenaire.entity.Tag;

/**
 * Concrete implementation of 'AbstractRelationDaoFactory'.
 * 
 * <p>This classe does not deal with creating the connection,
 * this is done my the 'main' class PostgreSQLFactory. This
 * class intervenes once the connection has been setup.</p>
 *
 */
public class PostgreSQLRelationFactory extends AbstractRelationDaoFactory {
	private static Connection conn;	
	
	public PostgreSQLRelationFactory() {
		// Recover connection from 'main class'.
		this.conn = PostgreSQLFactory.getConnection();
	}

	/* (non-Javadoc)
	 * @see org.centenaire.dao.abstractDao.AbstractRelationDaoFactory#getIndivDiscpl()
	 */
	@Override
	public RelationDao<Individual, Discipline> getIndivDiscpl() {
		return new PostgreSQLRelationDao<Individual, Discipline>(
				conn, 
				"individual_discipline_relations", 
				"indiv_id",
				"disc_id",
				EntityEnum.DISCIPLINES.getValue(),
				EntityEnum.INDIVDISCIPL.getValue());
	}

	/* (non-Javadoc)
	 * @see org.centenaire.dao.abstractDao.AbstractRelationDaoFactory#getIndivTag()
	 */
	@Override
	public RelationDao<Individual, Tag> getIndivTag() {
		return new PostgreSQLRelationDao<Individual, Tag>(
				conn, 
				"individual_tag_relations", 
				"indiv_id",
				"tag_id",
				EntityEnum.TAG.getValue(),
				EntityEnum.INDIVTAG.getValue());
	}

	/* (non-Javadoc)
	 * @see org.centenaire.dao.abstractDao.AbstractRelationDaoFactory#getItemTag()
	 */
	@Override
	public RelationDao<Item, Tag> getItemTag() {
		return new PostgreSQLRelationDao<Item, Tag>(
				conn, 
				"items_tag_relations", 
				"item_id",
				"tag_id",
				EntityEnum.TAG.getValue(),
				EntityEnum.ITEMTAG.getValue());
	}
	
	@Override
	public RelationDao<Individual, Item> getAuthor() {
		return new PostgreSQLRelationDao<Individual, Item>(
				conn, 
				"author", 
				"indiv_id",
				"item_id",
				EntityEnum.ITEM.getValue(),
				EntityEnum.ITEMTAG.getValue());
	}

}
