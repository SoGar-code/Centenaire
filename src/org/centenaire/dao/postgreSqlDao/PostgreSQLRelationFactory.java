/**
 * 
 */
package org.centenaire.dao.postgreSqlDao;

import java.sql.Connection;

import org.centenaire.dao.RelationDao;
import org.centenaire.dao.abstractDao.AbstractRelationDaoFactory;
import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Institution;
import org.centenaire.entity.Item;
import org.centenaire.entity.taglike.Discipline;
import org.centenaire.entity.taglike.InstitStatus;
import org.centenaire.entity.taglike.LocalType;
import org.centenaire.entity.taglike.Tag;
import org.centenaire.entity.taglike.TaxChrono;
import org.centenaire.entity.taglike.TaxGeo;
import org.centenaire.entity.taglike.TaxTheme;

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
	public RelationDao<Event, Tag> getEventTag() {
		return new PostgreSQLRelationDao<Event, Tag>(
				conn, 
				"event_tag_relations", 
				"event_id",
				"tag_id",
				EntityEnum.TAG.getValue(),
				EntityEnum.EVENTTAG.getValue());
	}
	
	@Override
	public RelationDao<Individual, Item> getSciAuthor() {
		return new PostgreSQLRelationDao<Individual, Item>(
				conn, 
				"sci_author", 
				"indiv_id",
				"item_id",
				EntityEnum.ITEM.getValue(),
				EntityEnum.SCIAUTHOR.getValue());
	}
	
	@Override
	public RelationDao<Item, Individual> getItemSciAuthor() {
		return new PostgreSQLRelationDao<Item, Individual>(
				conn, 
				"sci_author", 
				"item_id",
				"indiv_id",
				EntityEnum.INDIV.getValue(),
				EntityEnum.SCIAUTHOR.getValue());
	}
	
	@Override
	public RelationDao<Individual, Item> getOutreachAuthor() {
		return new PostgreSQLRelationDao<Individual, Item>(
				conn, 
				"outreach_author", 
				"indiv_id",
				"item_id",
				EntityEnum.ITEM.getValue(),
				EntityEnum.OUTREACHAUTHOR.getValue());
	}

	@Override
	public RelationDao<Item, Individual> getItemOutreachAuthor() {
		return new PostgreSQLRelationDao<Item, Individual>(
				conn, 
				"outreach_author", 
				"item_id",
				"indiv_id",
				EntityEnum.INDIV.getValue(),
				EntityEnum.OUTREACHAUTHOR.getValue());
	}

	@Override
	public RelationDao<Individual, Item> getDigAuthor() {
		return new PostgreSQLRelationDao<Individual, Item>(
				conn, 
				"dig_author", 
				"indiv_id",
				"item_id",
				EntityEnum.ITEM.getValue(),
				EntityEnum.DIGAUTHOR.getValue());
	}

	@Override
	public RelationDao<Item, Individual> getItemDigAuthor() {
		return new PostgreSQLRelationDao<Item, Individual>(
				conn, 
				"dig_author", 
				"item_id",
				"indiv_id",
				EntityEnum.INDIV.getValue(),
				EntityEnum.DIGAUTHOR.getValue());
	}

	@Override
	public RelationDao<Individual, Item> getDirection() {
		return new PostgreSQLRelationDao<Individual, Item>(
				conn, 
				"direction", 
				"indiv_id",
				"item_id",
				EntityEnum.ITEM.getValue(),
				EntityEnum.DIRECTION.getValue());
	}
	
	@Override
	public RelationDao<Item, Individual> getItemDirection() {
		return new PostgreSQLRelationDao<Item, Individual>(
				conn, 
				"direction", 
				"item_id",
				"indiv_id",
				EntityEnum.INDIV.getValue(),
				EntityEnum.DIRECTION.getValue());
	}

	@Override
	public RelationDao<Individual, Event> getOrg() {
		return new PostgreSQLRelationDao<Individual, Event>(
				conn, 
				"organizer", 
				"indiv_id",
				"event_id",
				EntityEnum.EVENTS.getValue(),
				EntityEnum.ORG.getValue());
	}
	
	@Override
	public RelationDao<Event, Individual> getEventOrg() {
		return new PostgreSQLRelationDao<Event, Individual>(
				conn, 
				"organizer", 
				"event_id",
				"indiv_id",
				EntityEnum.INDIV.getValue(),
				EntityEnum.ORG.getValue());
	}

	@Override
	public RelationDao<Individual, Event> getSciParticipant() {
		return new PostgreSQLRelationDao<Individual, Event>(
				conn, 
				"sci_participant", 
				"indiv_id",
				"event_id",
				EntityEnum.EVENTS.getValue(),
				EntityEnum.SCIPARTICIPANT.getValue());
	}
	
	@Override
	public RelationDao<Individual, Event> getOutreachParticipantG() {
		return new PostgreSQLRelationDao<Individual, Event>(
				conn, 
				"outreach_participant_g", 
				"indiv_id",
				"event_id",
				EntityEnum.EVENTS.getValue(),
				EntityEnum.OUTREACHPARTICIPANTG.getValue());
	}

	@Override
	public RelationDao<Individual, Event> getOutreachParticipantConf() {
		return new PostgreSQLRelationDao<Individual, Event>(
				conn, 
				"outreach_participant_conf", 
				"indiv_id",
				"event_id",
				EntityEnum.EVENTS.getValue(),
				EntityEnum.OUTREACHPARTICIPANTCONF.getValue());
	}

	@Override
	public RelationDao<Item, Institution> getAffiliation() {
		return new PostgreSQLRelationDao<Item, Institution>(
				conn, 
				"affiliation", 
				"item_id",
				"instit_id",
				EntityEnum.INSTIT.getValue(),
				EntityEnum.AFFILIATION.getValue());
	}

	@Override
	public RelationDao<Individual, DoubleEntity<Institution, InstitStatus>> getInstitStatus() {
		return new PostgreSQLLabelRelationDao<Individual, Institution, InstitStatus>(
				conn, 
				"individual_institution_relations", 
				"indiv_id",
				"instit_id",
				"instit_status",
				EntityEnum.INSTIT.getValue(),
				EntityEnum.INSTITSTATUS.getValue(),
				EntityEnum.INDIVINSTIT.getValue());
	}

	@Override
	public RelationDao<Individual, Item> getExpItem() {
		return new PostgreSQLRelationDao<Individual, Item>(
				conn, 
				"expert_item", 
				"indiv_id",
				"item_id",
				EntityEnum.ITEM.getValue(),
				EntityEnum.EXPITEM.getValue());
	}
	
	@Override
	public RelationDao<Item, Individual> getItemExp() {
		return new PostgreSQLRelationDao<Item, Individual>(
				conn, 
				"expert_item", 
				"item_id",
				"indiv_id",
				EntityEnum.INDIV.getValue(),
				EntityEnum.EXPITEM.getValue());
	}

	@Override
	public RelationDao<Individual, Event> getExpEvent() {
		return new PostgreSQLRelationDao<Individual, Event>(
				conn, 
				"expert_event", 
				"indiv_id",
				"event_id",
				EntityEnum.EVENTS.getValue(),
				EntityEnum.EXPEVENT.getValue());
	}
	
	@Override
	public RelationDao<Event, Individual> getEventExp() {
		return new PostgreSQLRelationDao<Event, Individual>(
				conn, 
				"expert_event", 
				"event_id",
				"indiv_id",
				EntityEnum.INDIV.getValue(),
				EntityEnum.EXPEVENT.getValue());
	}

	@Override
	public RelationDao<Individual, Institution> getExpInstit() {
		return new PostgreSQLRelationDao<Individual, Institution>(
				conn, 
				"expert_institutions", 
				"indiv_id",
				"instit_id",
				EntityEnum.INSTIT.getValue(),
				EntityEnum.EXPINSTIT.getValue());
	}

	@Override
	public RelationDao<Event, DoubleEntity<Institution, LocalType>> getFinancialSupport() {
		return new PostgreSQLLabelRelationDao<Event, Institution, LocalType>(
				conn, 
				"Localisations", 
				"event_id",
				"instit_id",
				"loc_type",
				EntityEnum.INSTIT.getValue(),
				EntityEnum.LOCALISATIONTYPE.getValue(),
				EntityEnum.LOCALISATION.getValue());
	}

	@Override
	public RelationDao<Item, TaxChrono> getItemTaxChrono() {
		return new PostgreSQLRelationDao<Item, TaxChrono>(
				conn, 
				"item_tax_chrono_relations", 
				"item_id",
				"tax_chrono_id",
				EntityEnum.TAXCHRONO.getValue(),
				EntityEnum.ITEMTAXCHRONO.getValue());
	}

	@Override
	public RelationDao<Item, TaxGeo> getItemTaxGeo() {
		return new PostgreSQLRelationDao<Item, TaxGeo>(
				conn, 
				"item_tax_geo_relations", 
				"item_id",
				"tax_geo_id",
				EntityEnum.TAXGEO.getValue(),
				EntityEnum.ITEMTAXGEO.getValue());
	}

	@Override
	public RelationDao<Item, TaxTheme> getItemTaxTheme() {
		return new PostgreSQLRelationDao<Item, TaxTheme>(
				conn, 
				"item_tax_theme_relations", 
				"item_id",
				"tax_theme_id",
				EntityEnum.TAXTHEME.getValue(),
				EntityEnum.ITEMTAXTHEME.getValue());
	}

	@Override
	public RelationDao<Event, TaxChrono> getEventTaxChrono() {
		return new PostgreSQLRelationDao<Event, TaxChrono>(
				conn, 
				"event_tax_chrono_relations", 
				"event_id",
				"tax_chrono_id",
				EntityEnum.TAXCHRONO.getValue(),
				EntityEnum.ITEMTAXCHRONO.getValue());
	}

	@Override
	public RelationDao<Event, TaxGeo> getEventTaxGeo() {
		return new PostgreSQLRelationDao<Event, TaxGeo>(
				conn, 
				"event_tax_geo_relations", 
				"event_id",
				"tax_geo_id",
				EntityEnum.TAXGEO.getValue(),
				EntityEnum.ITEMTAXGEO.getValue());
	}

	@Override
	public RelationDao<Event, TaxTheme> getEventTaxTheme() {
		return new PostgreSQLRelationDao<Event, TaxTheme>(
				conn, 
				"event_tax_theme_relations", 
				"event_id",
				"tax_theme_id",
				EntityEnum.TAXTHEME.getValue(),
				EntityEnum.ITEMTAXTHEME.getValue());
	}

}
