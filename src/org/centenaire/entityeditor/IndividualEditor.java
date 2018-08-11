package org.centenaire.entityeditor;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.Institution;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GIntegerField;
import org.centenaire.util.GeneralController;

/**
 * Class providing the editor form for 'Individual' Entity class. 
 *
 * @see Individual
 */
public class IndividualEditor extends EntityEditor<Individual> {
	JTextField firstNameField;
	JTextField lastNameField;
	GIntegerField birthYearField;
	EntityCombo<Institution> labCombo;
	
	public IndividualEditor() {
		super();
		
		// First name field
		// =================
		JPanel firstNamePan = new JPanel();
		JLabel firstNameLabel = new JLabel("Prénom : ");
		
		// Active text area...
		firstNameField = new JTextField(20);
		firstNameField.setText("-");
		
		firstNamePan.add(firstNameLabel);
		firstNamePan.add(firstNameField);
		
		// Last name field
		// =================
		JPanel lastNamePan = new JPanel();
		JLabel lastNameLabel = new JLabel("Nom : ");
		
		// Active text area...
		lastNameField = new JTextField(20);
		lastNameField.setText("-");
		
		lastNamePan.add(lastNameLabel);
		lastNamePan.add(lastNameField);
		
		// Birthyear field
		// ================
		JPanel birthDayPan = new JPanel();
		JLabel birthYearLabel = new JLabel("Année de naissance : ");
		birthYearField = new GIntegerField(1800);
		birthYearField.setColumns(8);
		birthYearField.setIntegerValue(1901);
		
		birthDayPan.add(birthYearLabel);
		birthDayPan.add(birthYearField);
		
		// Lab Combo
		// =======================
		JPanel labPan = new JPanel();
		JLabel labLab = new JLabel("Laboratoire (ppal) : ");
		labCombo = new EntityCombo<Institution>(EntityEnum.INSTIT.getValue());
		
		// subscribe eventTypeCombo to suitable channel
		GeneralController gc = GeneralController.getInstance();
		gc.getChannel(EntityEnum.INSTIT.getValue()).addSubscriber(labCombo);
		
		labPan.add(labLab);
		labPan.add(labCombo);
		
		// Final assembly
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(firstNamePan);
		this.add(lastNamePan);
		this.add(birthDayPan);
		this.add(labPan);

	}

	/** 
	 * Recover the 'Individual' entity defined by the current instance.
	 * 
	 * @see org.centenaire.entityeditor.EntityEditor#getObject()
	 */
	@Override
	public Individual getObject() {
		int index = this.getIndexField();
		String firstName = this.firstNameField.getText();
		String lastName = this.lastNameField.getText();
		int birthYear = this.birthYearField.getIntegerValue();
		Institution lab = this.labCombo.getSelectedEntity();

		Individual individual = new Individual(firstName, lastName, birthYear, lab);
		individual.setIndex(index);
		return individual;
	}

	/**
	 * Update the fields in the editor to represent another 'Individual' instance.
	 */
	@Override
	public void setObject(Individual obj) {
		this.setIndexField(obj.getIndex());
		firstNameField.setText(obj.getFirst_name());
		lastNameField.setText(obj.getLast_name());
		birthYearField.setIntegerValue(obj.getBirth_year());
		labCombo.setSelectedEntity(obj.getLab());
	}
	
	/**
	 * Method to reset the EntityEditor to a neutral value.
	 */
	public void reset() {
		this.setIndexField(0);
		firstNameField.setText("-");
		lastNameField.setText("-");
		birthYearField.setIntegerValue(1901);
		labCombo.setSelectedIndex(-1);
	}
	
	/**
	 * Choose if the EntityEditor can be edited (default=true).
	 */
	public void setEnabled(boolean isEditable) {
		firstNameField.setEnabled(isEditable);
		lastNameField.setEnabled(isEditable);
		birthYearField.setEnabled(isEditable);
		labCombo.setEnabled(isEditable);
	}

}
