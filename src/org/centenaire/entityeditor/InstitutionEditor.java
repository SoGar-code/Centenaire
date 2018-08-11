package org.centenaire.entityeditor;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.entity.Country;
import org.centenaire.entity.Departement;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Event;
import org.centenaire.entity.Institution;
import org.centenaire.entity.InstitutionType;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GeneralController;

/**
 * Class providing the editor form for 'Institution' Entity class. 
 *
 * @see Event
 */
public class InstitutionEditor extends EntityEditor<Institution> {
	JTextField nameField;
	JTextField placeField;
	EntityCombo<Departement> deptCombo;
	EntityCombo<Country> countryCombo;
	EntityCombo<InstitutionType> institutionTypeCombo;

	public InstitutionEditor() {
		super();
		
		GeneralController gc = GeneralController.getInstance();
		
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
		
		// Departement Combo
		// =======================
		JPanel deptPan = new JPanel();
		JLabel deptLab = new JLabel("Département : ");
		deptCombo = new EntityCombo<Departement>(EntityEnum.DEPT.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		gc.getChannel(EntityEnum.DEPT.getValue()).addSubscriber(deptCombo);
		
		deptPan.add(deptLab);
		deptPan.add(deptCombo);
		
		// Country Combo
		// =======================
		JPanel countryPan = new JPanel();
		JLabel countryLab = new JLabel("Pays : ");
		countryCombo = new EntityCombo<Country>(EntityEnum.COUNTRY.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		gc.getChannel(EntityEnum.COUNTRY.getValue()).addSubscriber(countryCombo);
		
		countryPan.add(countryLab);
		countryPan.add(countryCombo);
		
		// Institution Type Combo
		// =======================
		JPanel institutionTypePan = new JPanel();
		JLabel institutionTypeLabel = new JLabel("Type d'institution : ");
		institutionTypeCombo = new EntityCombo<InstitutionType>(EntityEnum.INSTITTYPE.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		gc.getChannel(EntityEnum.INSTITTYPE.getValue()).addSubscriber(institutionTypeCombo);
		
		institutionTypePan.add(institutionTypeLabel);
		institutionTypePan.add(institutionTypeCombo);
		
		// Final assembly
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(namePan);
		this.add(placePan);
		this.add(deptPan);
		this.add(countryPan);
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
		Departement dept = this.deptCombo.getSelectedEntity();
		Country country = this.countryCombo.getSelectedEntity();
		InstitutionType institutionType = this.institutionTypeCombo.getSelectedEntity();

		Institution item = new Institution(name, 
										   place,
										   dept,
										   country,
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
		deptCombo.setSelectedEntity(obj.getDept());
		countryCombo.setSelectedEntity(obj.getCountry());
		institutionTypeCombo.setSelectedEntity(obj.getInstitType());
	}
	
	/**
	 * Method to reset the InstitutionEditor to a neutral value.
	 */
	public void reset() {
		
		this.setIndexField(0);
		nameField.setText("-");
		placeField.setText("-");
		deptCombo.setSelectedItem(-1);
		countryCombo.setSelectedItem(-1);
		institutionTypeCombo.setSelectedItem(-1);
	}
}
