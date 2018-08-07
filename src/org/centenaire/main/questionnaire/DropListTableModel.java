/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.util.LinkedList;
import java.util.List;

import org.centenaire.dao.RelationDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Tag;
import org.centenaire.util.ListTableModel;

/**
 * Table Model specific for coding the "relations" in the database.
 * 
 * <p>This model does NOT rely on the notification system 
 * (at least as far as the <em>relations</em> are concerned). 
 * Instead, to update relations, it depends on "save" buttons.</p>
 *
 */
public class DropListTableModel extends ListTableModel {
	private RelationDao<Individual, Tag> relationDao;
	
	public DropListTableModel(
			Class[] listClass, 
			String[] title, 
			LinkedList<Entity> data) {
		super(listClass, title, data);
		
		relationDao = (RelationDao<Individual, Tag>) gc.getRelationDao(EntityEnum.INDIVTAG.getValue());
	}
	
	/**
	 * Include additional rows in data.
	 * 
	 * <p>For the moment, the additional data is appended
	 * to the existing one, while avoiding duplication.</p> 
	 * 
	 * <p>More precise behavior, such as 
	 * inserting at a given position, would require an improved
	 * method here!</p>
	 * 
	 */
	public void addAll(List<Entity> moreData) {
		// Recover current data and its size
		int dataSize = this.data.size();
		LinkedList<Integer> indexList = new LinkedList<Integer>();
		
		// populate 'indexList'
		for (Entity row:this.data) {
			indexList.add(row.getIndex());
		}
		
		// Add new rows, as required
		for (Entity newRow:moreData) {
			// Check that the row does not already exist
			if (!indexList.contains(newRow.getIndex())) {
				this.data.add(newRow);
			}
		}
		this.fireTableDataChanged();
	}
	
	/**
	 * Using the currently selected individual, recover the suitable list of tags.
	 * 
	 * @return list of tag for the currently selected individual.
	 */
	public void updateEntity(Individual currentIndividual){
		// Initialise list of Entity
		LinkedList<Entity> listEntity = new LinkedList<Entity>();
		
		System.out.println("==> updateEntity called...");
		
		// if an individual is currently selected...
		if (currentIndividual != null){
			// Get list of relevant tags
			List<Tag> tagList = relationDao.findAll(currentIndividual);
			
			String msg = String.format("==> nb recovered items: %s", tagList.size());
			System.out.println(msg);
			
			// Add these to "listEntity"
			for (Tag tag:tagList) {
				listEntity.add((Entity) tag);
			}
		}
		
		this.setData(listEntity);
	}
	
	/**
	 * Save the content of the Table Model.
	 */
	public void saveContent(Individual currentIndividual) {
		// Delete all previous relations associated to this individual
		relationDao.deleteAll(currentIndividual);
		
		// Recover the current content of the model
		List<Entity> entityList = this.getData();
		
		// Create the required relations
		for (Entity entity: entityList) {
			relationDao.create(currentIndividual, (Tag) entity);
		}
	}
}
