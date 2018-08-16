/**
 * 
 */
package org.centenaire.entity.relationeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.centenaire.dao.RelationDao;
import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Institution;
import org.centenaire.entity.LocalType;
import org.centenaire.entity.Tag;
import org.centenaire.entity.TaxChrono;
import org.centenaire.entity.TaxGeo;
import org.centenaire.entity.TaxTheme;
import org.centenaire.util.dragndrop.DropTable;
import org.centenaire.util.dragndrop.TargetHandlerDouble;
import org.centenaire.util.editorsRenderers.Delete;

/**
 * 
 *
 */
public class EventRelationEditor extends RelationEditor<Event> {
	private List<DropTable> tables;

	public EventRelationEditor(Event entity) {
		super(EntityEnum.EVENTS.getValue(), entity);
		
		this.setSize(800, 600);
		
		tables = new LinkedList<DropTable>();
		
		JPanel main = this.getMain();
		main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
		
		// Organizer table
		RelationDao<Event, Individual> invOrgDao = (RelationDao<Event, Individual>) gc.getInvertedRelationDao(EntityEnum.ORG.getValue());
		DropTable<Event, Individual> tableOrg = new DropTable(
				EntityEnum.INDIV.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom", "Retirer"},
				invOrgDao
				);
		tables.add(tableOrg);
		
		JPanel orgPan = new JPanel(new BorderLayout());
		JLabel orgLab = new JLabel("Organisateur(s)", SwingConstants.CENTER);
		orgPan.add(orgLab, BorderLayout.NORTH);
		orgPan.add(tableOrg, BorderLayout.CENTER);

		
		// Expert table
		RelationDao<Event, Individual> invExpertDao = (RelationDao<Event, Individual>) gc.getInvertedRelationDao(EntityEnum.EXPEVENT.getValue());
		DropTable<Event, Individual> tableExpert = new DropTable(
				EntityEnum.INDIV.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom", "Retirer"},
				invExpertDao
				);
		tables.add(tableExpert);
		
		JPanel expertPan = new JPanel(new BorderLayout());
		JLabel expertLab = new JLabel("Expert(s)", SwingConstants.CENTER);
		expertPan.add(expertLab, BorderLayout.NORTH);
		expertPan.add(tableExpert, BorderLayout.CENTER);
		
		// Auxiliary panel: organizers and experts
		JPanel orgExpPan = new JPanel(new GridLayout(1, 2));
		orgExpPan.add(orgPan);
		orgExpPan.add(expertPan);
		main.add(orgExpPan);
		
		
		// Table of institutional support
		DropTable<Event, DoubleEntity<Institution, LocalType>> tableLocal = new DropTable<Event, DoubleEntity<Institution, LocalType>>(
						EntityEnum.EVENTS.getValue(),
						EntityEnum.INSTIT.getValue(),
						EntityEnum.LOCALISATION.getValue(),
						new Class[] {String.class, LocalType.class, Delete.class},
						new String[] {"Institution", "Type de soutien", "Retirer"}
						);
		// Replace the default TargetHandler:
		tableLocal.getTable().setTransferHandler(
				new TargetHandlerDouble<Institution, LocalType>(EntityEnum.INSTIT.getValue()));
		tables.add(tableLocal);		
		
		JPanel localPan = new JPanel(new BorderLayout());
		JLabel localLab = new JLabel("Financement et soutien institutionnel", SwingConstants.CENTER);
		localPan.add(localLab, BorderLayout.NORTH);
		localPan.add(tableLocal, BorderLayout.CENTER);
		
		main.add(localPan);
		
		
		// Tag table
		DropTable<Event, Tag> tableEventTag = new DropTable<Event, Tag>(
				EntityEnum.EVENTS.getValue(), 
				EntityEnum.TAG.getValue(), 
				EntityEnum.EVENTTAG.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Mot-clef", "Retirer"}
				);
		tables.add(tableEventTag);
		
		// TaxChrono table
		DropTable<Event, TaxChrono> tableTaxChrono = new DropTable<Event, TaxChrono>(
				EntityEnum.EVENTS.getValue(), 
				EntityEnum.TAXCHRONO.getValue(), 
				EntityEnum.EVENTSTAXCHRONO.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie chronologique", "Retirer"}
				);
		tables.add(tableTaxChrono);
		
		// TaxGeo table
		DropTable<Event, TaxGeo> tableTaxGeo = new DropTable<Event, TaxGeo>(
				EntityEnum.EVENTS.getValue(), 
				EntityEnum.TAXGEO.getValue(), 
				EntityEnum.EVENTSTAXGEO.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie géographique", "Retirer"}
				);
		tables.add(tableTaxGeo);
		
		// TaxTheme table
		DropTable<Event, TaxTheme> tableTaxTheme = new DropTable<Event, TaxTheme>(
				EntityEnum.EVENTS.getValue(), 
				EntityEnum.TAXTHEME.getValue(), 
				EntityEnum.EVENTSTAXTHEME.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie thématique", "Retirer"}
				);
		tables.add(tableTaxTheme);
		
		// Auxiliary panel: tags
		JPanel tagsPan = new JPanel(new GridLayout(2, 2));
		tagsPan.add(tableEventTag);
		tagsPan.add(tableTaxChrono);
		tagsPan.add(tableTaxGeo);
		tagsPan.add(tableTaxTheme);
		main.add(tagsPan);
		
		this.setRelations();
	}

	@Override
	public boolean saveRelations() {
		Event currentEvent = this.getObject();
		
		for (DropTable table:tables) {
			table.saveContent(currentEvent);
		}
		
		return true;
	}

	@Override
	public boolean setRelations() {

		Event currentEvent = this.getObject();
		
		for (DropTable table:tables) {
			table.updateEntity(currentEvent);
		}
		
		return true;
	}
}
