/**
 * 
 */
package org.centenaire.entity.relationeditor;

import java.awt.BorderLayout;
import java.awt.GridLayout;

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
	private DropTable<Item, Individual> tableAuthor;
	private DropTable<Item, Individual> tableDirection;
	private DropTable<Item, Individual> tableExpert;
	private DropTable<Item, Institution> tableAffiliation;
	private DropTable<Item, Tag> tableItemTag;
	private DropTable<Item, TaxChrono> tableTaxChrono;
	private DropTable<Item, TaxGeo> tableTaxGeo;
	private DropTable<Item, TaxTheme> tableTaxTheme;
	

	public ItemRelationEditor(Item entity) {
		super(EntityEnum.ITEM.getValue(), entity);
		this.setSize(800, 600);
		
		JPanel main = this.getMain();
		main.setLayout(new GridLayout(4,2));
		
		// Author table
		JPanel authorPan = new JPanel(new BorderLayout());
		JLabel authorLab = new JLabel("Auteur(s)", SwingConstants.CENTER);
		RelationDao<Item, Individual> invAuthorDao = (RelationDao<Item, Individual>) gc.getInvertedRelationDao(EntityEnum.AUTHOR.getValue());
		tableAuthor = new DropTable(
				EntityEnum.INDIV.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom", "Retirer"},
				invAuthorDao
				);
		authorPan.add(authorLab, BorderLayout.NORTH);
		authorPan.add(tableAuthor, BorderLayout.CENTER);
		main.add(authorPan);
		
		// Direction table
		JPanel directionPan = new JPanel(new BorderLayout());
		JLabel directionLab = new JLabel("Directeur(s)", SwingConstants.CENTER);
		RelationDao<Item, Individual> invDirectionDao = (RelationDao<Item, Individual>) gc.getInvertedRelationDao(EntityEnum.DIRECTION.getValue());
		tableDirection = new DropTable(
				EntityEnum.INDIV.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom", "Retirer"},
				invDirectionDao
				);
		directionPan.add(directionLab, BorderLayout.NORTH);
		directionPan.add(tableDirection, BorderLayout.CENTER);
		main.add(directionPan);
		
		// Expert table
		JPanel expertPan = new JPanel(new BorderLayout());
		JLabel expertLab = new JLabel("Expert(s)", SwingConstants.CENTER);
		RelationDao<Item, Individual> invExpertDao = (RelationDao<Item, Individual>) gc.getInvertedRelationDao(EntityEnum.EXPITEM.getValue());
		tableExpert = new DropTable(
				EntityEnum.INDIV.getValue(),
				new Class[] {String.class, String.class, Delete.class},
				new String[] {"Prénom", "Nom", "Retirer"},
				invExpertDao
				);
		expertPan.add(expertLab, BorderLayout.NORTH);
		expertPan.add(tableExpert, BorderLayout.CENTER);
		main.add(expertPan);
		
		// Affiliation table
		JPanel affiliationPan = new JPanel(new BorderLayout());
		JLabel affiliationLab = new JLabel("Affiliation(s)", SwingConstants.CENTER);
		RelationDao<Item, Institution> affiliationDao = (RelationDao<Item, Institution>) gc.getInvertedRelationDao(EntityEnum.AFFILIATION.getValue());
		tableAffiliation = new DropTable(
				EntityEnum.INSTIT.getValue(),
				new Class[] {String.class, Delete.class},
				new String[] {"Institution", "Retirer"},
				invExpertDao
				);
		affiliationPan.add(affiliationLab, BorderLayout.NORTH);
		affiliationPan.add(tableAffiliation, BorderLayout.CENTER);
		main.add(affiliationPan);
		
		// Tag table
		tableItemTag = new DropTable<Item, Tag>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAG.getValue(), 
				EntityEnum.ITEMTAG.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Mot-clef", "Retirer"}
				);
		main.add(tableItemTag);
		
		// TaxChrono table
		tableTaxChrono = new DropTable<Item, TaxChrono>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAXCHRONO.getValue(), 
				EntityEnum.ITEMTAXCHRONO.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie chronologique", "Retirer"}
				);
		main.add(tableTaxChrono);
		
		// TaxGeo table
		tableTaxGeo = new DropTable<Item, TaxGeo>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAXGEO.getValue(), 
				EntityEnum.ITEMTAXGEO.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie géographique", "Retirer"}
				);
		main.add(tableTaxGeo);
		
		// TaxTheme table
		tableTaxTheme = new DropTable<Item, TaxTheme>(
				EntityEnum.ITEM.getValue(), 
				EntityEnum.TAXTHEME.getValue(), 
				EntityEnum.ITEMTAXTHEME.getValue(), 
				new Class[] {String.class, Delete.class}, 
				new String[] {"Taxinomie thématique", "Retirer"}
				);
		main.add(tableTaxTheme);
	}

	@Override
	public void reset() {
		
	}

	@Override
	public boolean saveRelations() {
		// TODO Auto-generated method stub
		return false;
	}

}
