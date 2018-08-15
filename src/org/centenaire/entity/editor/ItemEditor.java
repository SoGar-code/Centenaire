package org.centenaire.entity.editor;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Item;
import org.centenaire.entity.ItemType;
import org.centenaire.entity.util.EntityCombo;
import org.centenaire.util.GDateField;
import org.centenaire.util.GeneralController;

/**
 * Class providing the editor form for 'Event' Entity class. 
 *
 * @see Event
 */
public class ItemEditor extends EntityEditor<Item> {
	JTextField titleField;
	GDateField startDateField;
	GDateField endDateField;
	EntityCombo<ItemType> itemTypeCombo;

	public ItemEditor() {
		super();
		
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		LocalDate localDate = LocalDate.of(2099, 12, 31);
		Date noEnd = Date.valueOf(localDate);
		
		
		// Name field
		// =================
		JPanel titlePan = new JPanel();
		JLabel titleLabel = new JLabel("Titre : ");
		
		// Active text area...
		titleField = new JTextField(20);
		titleField.setText("-");
		
		titlePan.add(titleLabel);
		titlePan.add(titleField);
		
		// Start date field
		// ================
		JPanel startDatePan = new JPanel();
		JLabel startDateLabel = new JLabel("Date de début (AAAA-MM-JJ) : ");
		startDateField = new GDateField();
		startDateField.setColumns(12);
		startDateField.setDate(today);
		
		startDatePan.add(startDateLabel);
		startDatePan.add(startDateField);
		
		// End date field
		// ================
		JPanel endDatePan = new JPanel();
		JLabel endDateLabel = new JLabel("Date de fin (AAAA-MM-JJ) : ");
		endDateField = new GDateField();
		endDateField.setColumns(12);
		endDateField.setDate(noEnd);
		
		endDatePan.add(endDateLabel);
		endDatePan.add(endDateField);
		
		// Item Type Combo
		// =================		
		JPanel itemTypePan = new JPanel();
		JLabel itemTypeLabel = new JLabel("Type de production : ");
		itemTypeCombo = new EntityCombo<ItemType>(EntityEnum.ITEMTYPE.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		GeneralController gc = GeneralController.getInstance();
		gc.getChannel(EntityEnum.ITEMTYPE.getValue()).addSubscriber(itemTypeCombo);
		
		itemTypePan.add(itemTypeLabel);
		itemTypePan.add(itemTypeCombo);
		
		// Final assembly
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(titlePan);
		this.add(startDatePan);
		this.add(endDatePan);
		this.add(itemTypePan);
	}

	/** 
	 * Recover the 'Item' entity defined by the current instance.
	 * 
	 * @see org.centenaire.entity.editor.EntityEditor#getObject()
	 */
	@Override
	public Item getObject() {
		int index = this.getIndexField();
		String title = this.titleField.getText();
		Date startDate = this.startDateField.getDate();
		Date endDate = this.endDateField.getDate();
		ItemType itemType = this.itemTypeCombo.getSelectedEntity();

		Item item = new Item(title,
								itemType, 
								startDate,
								endDate
								);
		item.setIndex(index);
		return item;
	}

	/**
	 * Update the fields in the editor to represent another 'Item' instance.
	 */
	@Override
	public void setObject(Item obj) {
		this.setIndexField(obj.getIndex());
		titleField.setText(obj.getTitle());
		startDateField.setDate(obj.getStartDate());
		endDateField.setDate(obj.getEndDate());
		itemTypeCombo.setSelectedEntity(obj.getItemType());
	}
	
	/**
	 * Method to reset the ItemEditor to a neutral value.
	 */
	public void reset() {
		
		Date today = new Date(Calendar.getInstance().getTimeInMillis());
		LocalDate localDate = LocalDate.of(2099, 12, 31);
		Date noEnd = Date.valueOf(localDate);
		
		this.setIndexField(0);
		titleField.setText("-");
		startDateField.setDate(today);
		endDateField.setDate(noEnd);
		itemTypeCombo.setSelectedItem(-1);
	}
}
