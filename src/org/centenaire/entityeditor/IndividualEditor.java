package org.centenaire.entityeditor;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.entity.Individual;
import org.centenaire.entity.TagLike;
import org.centenaire.general.editorsRenderers.GIntegerField;

/**
 * Class providing the editor form for 'Individual' Entity class. 
 *
 * @see Individual
 */
public class IndividualEditor extends EntityEditor<Individual> {
	JTextField firstNameField;
	JTextField lastNameField;
	GIntegerField birthYearField;
	
	public IndividualEditor() {
		super();
		
		// First name field
		// =================
		JPanel firstNamePan = new JPanel();
		JLabel firstNameLabel = new JLabel("First name: ");
		
		// Active text area...
		firstNameField = new JTextField(20);
		firstNameField.setText("-");
		
		firstNamePan.add(firstNameLabel);
		firstNamePan.add(firstNameField);
		
		// Last name field
		// =================
		JPanel lastNamePan = new JPanel();
		JLabel lastNameLabel = new JLabel("Last name: ");
		
		// Active text area...
		lastNameField = new JTextField(20);
		lastNameField.setText("-");
		
		lastNamePan.add(lastNameLabel);
		lastNamePan.add(lastNameField);
		
		// Birthyear field
		// ================
		JPanel birthDayPan = new JPanel();
		JLabel birthYearLabel = new JLabel("Birth year: ");
		birthYearField = new GIntegerField(1900);
		birthYearField.setColumns(8);
		birthYearField.setIntegerValue(1901);
		
		birthDayPan.add(birthYearLabel);
		birthDayPan.add(birthYearField);
		
		// Final assembly
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.add(firstNamePan);
		this.add(lastNamePan);
		this.add(birthDayPan);

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

		Individual individual = new Individual(firstName, lastName, birthYear);
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
	}
	
	/**
	 * Method to reset the EntityEditor to a neutral value.
	 */
	public void reset() {
		this.setIndexField(0);
		firstNameField.setText("-");
		lastNameField.setText("-");
		birthYearField.setIntegerValue(1901);
	}

}
