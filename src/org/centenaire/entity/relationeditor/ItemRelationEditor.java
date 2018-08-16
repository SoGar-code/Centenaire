/**
 * 
 */
package org.centenaire.entity.relationeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.centenaire.dao.RelationDao;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Institution;
import org.centenaire.entity.Item;
import org.centenaire.entity.Tag;
import org.centenaire.entity.TaxChrono;
import org.centenaire.entity.TaxGeo;
import org.centenaire.entity.TaxTheme;
import org.centenaire.util.dragndrop.DropTable;
import org.centenaire.util.editorsRenderers.Delete;

/**
 * Dialog to edit relation for 'Item' objects.
 *
 */
public class ItemRelationEditor extends RelationEditor<Item> {
	private List<DropTable> tables;

	public ItemRelationEditor(Item entity) {
		super(EntityEnum.ITEM.getValue(), entity);
		this.setSize(800, 600);
		
		tables = new LinkedList<DropTable>();
		
		JPanel main = this.getMain();
		main.setLayout(new GridLayout(4,2));
		
		// Author table
		RelationDao<Item, Individual> invAuthorDao = (RelationDao<Item, Individual>) gc.getInvertedRelationDao(EntityEnum.AUTHOR.getValue());
		DropTable<Item, Individual> tableAuthor = new DropTable(
				EntityEnum.INDIV.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom", "Retirer"},
				invAuthorDao
				);
		tables.add(tableAuthor);
		
		JPanel authorPan = new JPanel(new BorderLayout());
		JLabel authorLab = new JLabel("Auteur(s)", SwingConstants.CENTER);
		authorPan.add(authorLab, BorderLayout.NORTH);
		authorPan.add(tableAuthor, BorderLayout.CENTER);
		main.add(authorPan);
		
		
		// Direction table
		RelationDao<Item, Individual> invDirectionDao = (RelationDao<Item, Individual>) gc.getInvertedRelationDao(EntityEnum.DIRECTION.getValue());
		DropTable<Item, Individual> tableDirection = new DropTable(
				EntityEnum.INDIV.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom", "Retirer"},
				invDirectionDao
				);
		tables.add(tableDirection);
		
		JPanel directionPan = new JPanel(new BorderLayout());
		JLabel directionLab = new JLabel("Directeur(s)", SwingConstants.CENTER);
		directionPan.add(directionLab, BorderLayout.NORTH);
		directionPan.add(tableDirection, BorderLayout.CENTER);
		main.add(directionPan);
		
		
		// Expert table
		RelationDao<Item, Individual> invExpertDao = (RelationDao<Item, Individual>) gc.getInvertedRelationDao(EntityEnum.EXPITEM.getValue());
		DropTable<Item, Individual> tableExpert = new DropTable(
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
		main.add(expertPan);
		
		
		// Affiliation table
		RelationDao<Item, Institution> affiliationDao = (RelationDao<Item, Institution>) gc.getRelationDao(EntityEnum.AFFILIATION.getValue());
		DropTable<Item, Institution> tableAffiliation = new DropTable(
				EntityEnum.INSTIT.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Institution", "Type", "Retirer"},
				affiliationDao
				);
		tables.add(tableAffiliation);
		
		JPanel affiliationPan = new JPanel(new BorderLayout());
		JLabel affiliationLab = new JLabel("Affiliation(s)", SwingConstants.CENTER);
		affiliationPan.add(affiliationLab, BorderLayout.NORTH);
		affiliationPan.add(tableAffiliation, BorderLayout.CENTER);
		main.add(affiliationPan);
		
		
		// Tag table
		DropTable<Item, Tag> tableItemTag = new DropTable<Item, Tag>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAG.getValue(), 
				EntityEnum.ITEMTAG.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Mot-clef", "Retirer"}
				);
		tables.add(tableItemTag);
		main.add(tableItemTag);
		
		// TaxChrono table
		DropTable<Item, TaxChrono> tableTaxChrono = new DropTable<Item, TaxChrono>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAXCHRONO.getValue(), 
				EntityEnum.ITEMTAXCHRONO.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie chronologique", "Retirer"}
				);
		tables.add(tableTaxChrono);
		main.add(tableTaxChrono);
		
		// TaxGeo table
		DropTable<Item, TaxGeo> tableTaxGeo = new DropTable<Item, TaxGeo>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAXGEO.getValue(), 
				EntityEnum.ITEMTAXGEO.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie géographique", "Retirer"}
				);
		tables.add(tableTaxGeo);
		main.add(tableTaxGeo);
		
		// TaxTheme table
		DropTable<Item, TaxTheme> tableTaxTheme = new DropTable<Item, TaxTheme>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAXTHEME.getValue(), 
				EntityEnum.ITEMTAXTHEME.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie thématique", "Retirer"}
				);
		tables.add(tableTaxTheme);
		main.add(tableTaxTheme);
		
		this.setRelations();
	}

	@Override
	public boolean saveRelations() {
		Item currentItem = this.getObject();
		
		for (DropTable table:tables) {
			table.saveContent(currentItem);
		}
		
		return true;
	}

	@Override
	public boolean setRelations() {

		Item currentItem = this.getObject();
		
		for (DropTable table:tables) {
			table.updateEntity(currentItem);
		}
		
		return true;
	}

}
