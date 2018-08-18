/**
 * 
 */
package org.centenaire.util.dragndrop;

import java.util.LinkedList;
import java.util.List;

import org.centenaire.dao.RelationDao;
import org.centenaire.entity.Entity;
import org.centenaire.entity.taglike.InstitStatus;
import org.centenaire.entity.taglike.LocalType;
import org.centenaire.entity.util.ListTableModel;
import org.centenaire.util.Converters;
import org.centenaire.util.editorsRenderers.Delete;

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
	
	/**
	 * Constructor for 'DropListTableModel', including the class index of the item displayed.
	 * 
	 * @param listClass
	 * 			list of classes used for choosing editors and column width.
	 * @param title
	 * 			list of titles of the different columns in the table.
	 * @param classIndexU
	 * 			classIndex of the type of Entity used as rows.
	 */
	public DropListTableModel(
			Class[] listClass, 
			String[] title,
			int classIndexU,
			int classIndexRelation) {
		super(listClass, title, new LinkedList<Entity>());
		
		relationDao = (RelationDao<T, U>) gc.getRelationDao(classIndexRelation);

	}
	
	/**
	 * Constructor for 'DropListTableModel', including the class index of the item displayed.
	 * 
	 * @param listClass
	 * 			list of classes used for choosing editors and column width.
	 * @param title
	 * 			list of titles of the different columns in the table.
	 * @param relationDao
	 * 			relationDao to use to populate, update and save the table.
	 */
	public DropListTableModel(
			Class[] listClass, 
			String[] title,
			RelationDao<T, U> relationDao) {
		super(listClass, title, new LinkedList<Entity>());
		
		this.relationDao = relationDao;
	}
	
	/**
	 * Define when a cell is editable.
	 * 
	 * <p>This is defined using a list of 'editable classes'.</p>
	 */
	@Override
	public boolean isCellEditable(int row, int col){
		List<Class> editableClass = new LinkedList<Class>();
		editableClass.add(Delete.class);
		editableClass.add(InstitStatus.class);
		editableClass.add(LocalType.class);
		
		return (editableClass.contains(listClass[col]));
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
		// Recover current data
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
	 * 
	 * <p>This is done by:
	 * <ul>
	 * 		<li>deleting all former relations for 'currentEntity', </li>
	 * 		<li>creating all relations as available in the table at that point.</p>
	 * </ul>
	 * To evaluate the currently existing relations, the attribute 'relationDao' is used.
	 * </p>
	 * 
	 * <p>In order to obtain a suitably ordered list, we rely on the database and reload the list.</p>
	 */
	public void saveContent(T currentEntity) {
		// Recover the current content of the model
		List<Entity> entityList = this.data;
		
		// Delete all previous relations associated to this individual
		relationDao.deleteAll(currentEntity);
		
		// Create the required relations
		for (Entity entity: entityList) {
			relationDao.create(currentEntity, (U) entity);
		}
		
		// Recover the list of relations (after re-ordering by the SQL database)
		List<U> rawData = relationDao.findAll(currentEntity);
		
		// Set the ordered list as new content.
		this.setData(Converters.convertListType(rawData));
	}
	
	public RelationDao<T, U> getRelationDao() {
		return relationDao;
	}

	public void setRelationDao(RelationDao<T, U> relationDao) {
		this.relationDao = relationDao;
	}
}
