package org.centenaire.entityeditor;

import java.sql.Date;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Institution;
import org.centenaire.entity.InstitutionType;
import org.centenaire.entity.Item;
import org.centenaire.entity.ItemType;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GDateField;
import org.centenaire.util.GeneralController;

/**
 * Class providing the editor form for 'Institution' Entity class. 
 *
 * @see Event
 */
public class InstitutionEditor extends EntityEditor<Institution> {
	JTextField nameField;
	JTextField placeField;
	EntityCombo<InstitutionType> institutionTypeCombo;

	public InstitutionEditor() {
		super();
		
		// Name field
		// =================
		JPanel namePan = new JPanel();
		JLabel nameLabel = new JLabel("Nom : ");
		
		// Active text area...
		nameField = new JTextField(20);
		nameField.setText("-");
		
		namePan.add(nameLabel);
		namePan.add(nameField);
		
		// Name field
		// =================
		JPanel placePan = new JPanel();
		JLabel placeLabel = new JLabel("Lieu : ");
		
		// Active text area...
		placeField = new JTextField(20);
		placeField.setText("-");
		
		placePan.add(placeLabel);
		placePan.add(placeField);
		
		// Institution Type Combo
		// =======================
		JPanel institutionTypePan = new JPanel();
		JLabel institutionTypeLabel = new JLabel("Type d'institution : ");
		institutionTypeCombo = new EntityCombo<InstitutionType>(EntityEnum.INSTITTYPE.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		GeneralController gc = GeneralController.getInstance();
		gc.getChannel(EntityEnum.INSTITTYPE.getValue()).addSubscriber(institutionTypeCombo);
		
		institutionTypePan.add(institutionTypeLabel);
		institutionTypePan.add(institutionTypeCombo);
		
		// Final assembly
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(namePan);
		this.add(placePan);
		this.add(institutionTypePan);
	}

	/** 
	 * Recover the 'Institution' entity defined by the current instance.
	 * 
	 * @see org.centenaire.entityeditor.EntityEditor#getObject()
	 */
	@Override
	public Institution getObject() {
		int index = this.getIndexField();
		String name = this.nameField.getText();
		String place = this.placeField.getText();
		InstitutionType institutionType = this.institutionTypeCombo.getSelectedEntity();

		Institution item = new Institution(name, 
										   place, 
										   institutionType);
		item.setIndex(index);
		return item;
	}

	/**
	 * Update the fields in the editor to represent another 'Institution' instance.
	 */
	@Override
	public void setObject(Institution obj) {
		this.setIndexField(obj.getIndex());
		nameField.setText(obj.getName());
		placeField.setText(obj.getPlace());
		institutionTypeCombo.setSelectedEntity(obj.getInstitType());
	}
	
	/**
	 * Method to reset the InstitutionEditor to a neutral value.
	 */
	public void reset() {
		
		this.setIndexField(0);
		nameField.setText("-");
		placeField.setText("-");
		institutionTypeCombo.setSelectedItem(-1);
	}
}
