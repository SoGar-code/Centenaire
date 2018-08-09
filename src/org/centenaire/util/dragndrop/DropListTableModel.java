/**
 * 
 */
package org.centenaire.util.dragndrop;

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
 * <p>This class incorporates a 'TransferHandler' as defined for 
 * 'target' objects.</p>
 * 
 * <p>This model does NOT rely on the notification system 
 * (at least as far as the <em>relations</em> are concerned). 
 * Instead, to update relations, it depends on "save" buttons.</p>
 *
 */
public class DropListTableModel<T extends Entity, U extends Entity> extends ListTableModel {
	private RelationDao<T, U> relationDao;
	private int deleteColumn;
	
	/**
	 * Constructor for 'DropListTableModel', including the class index of the item displayed.
	 * 
	 * @param listClass
	 * @param title
	 * @param classIndexU
	 */
	public DropListTableModel(
			Class[] listClass, 
			String[] title,
			int classIndexU,
			int classIndexRelation, 
			int deleteColumn) {
		super(listClass, title, new LinkedList<Entity>());
		
		relationDao = (RelationDao<T, U>) gc.getRelationDao(classIndexRelation);
		this.deleteColumn = deleteColumn;

	}
	
	/**
	 * Define when a cell is editable.
	 */
	@Override
	public boolean isCellEditable(int row, int col){
		return (col == this.deleteColumn);
	};
	
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
	 * Using currentEntity, recover the suitable list of elements.
	 * 
	 */
	public void updateEntity(T currentEntity){
		// Initialise list of Entity
		LinkedList<Entity> listEntity = new LinkedList<Entity>();
		
		// if an individual is currently selected...
		if (currentEntity != null){
			// Get list of relevant tags
			List<U> listU = relationDao.findAll(currentEntity);
			
			// Add these to "listEntity"
			for (U row:listU) {
				listEntity.add((Entity) row);
			}
		}
		
		this.setData(listEntity);
	}
	
	/**
	 * Save the content of the Table Model.
	 */
	public void saveContent(T currentEntity) {
		// Recover the current content of the model
		List<Entity> entityList = this.getData();
		
		// Delete all previous relations associated to this individual
		relationDao.deleteAll(currentEntity);
		
		// Create the required relations
		for (Entity entity: entityList) {
			relationDao.create(currentEntity, (U) entity);
		}
	}
}
