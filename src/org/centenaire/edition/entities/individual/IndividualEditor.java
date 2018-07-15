package org.centenaire.edition.entities.individual;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.edition.entities.taglike.TagLike;
import org.centenaire.general.EntityEditor;
import org.centenaire.general.editorsRenderers.GIntegerField;

/**
 * Class providing the editor form for TagLike. 
 *
 * @see TagLike
 */
public class IndividualEditor extends EntityEditor<Individual> {
	JTextField firstNameField;
	JTextField lastNameField;
	GIntegerField birthYearField;
	
	public IndividualEditor(Individual individual) {
		super(individual);
		
		// First name field
		// =================
		JPanel firstNamePan = new JPanel();
		JLabel firstNameLabel = new JLabel("First name: ");
		
		// Active text area...
		firstNameField = new JTextField(20);
		firstNameField.setText(individual.getFirst_name());
		
		firstNamePan.add(firstNameLabel);
		firstNamePan.add(firstNameField);
		
		// Last name field
		// =================
		JPanel lastNamePan = new JPanel();
		JLabel lastNameLabel = new JLabel("Last name: ");
		
		// Active text area...
		lastNameField = new JTextField(20);
		lastNameField.setText(individual.getLast_name());
		
		lastNamePan.add(lastNameLabel);
		lastNamePan.add(lastNameField);
		
		// Birthyear field
		// ================
		JPanel birthDayPan = new JPanel();
		JLabel birthYearLabel = new JLabel("Birth year: ");
		birthYearField = new GIntegerField(1900);
		birthYearField.setColumns(8);
		birthYearField.setIntegerValue(individual.getBirth_year());
		
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
	 * @see org.centenaire.general.EntityEditor#getObject()
	 */
	@Override
	public Individual getObject() {
		String firstName = this.firstNameField.getText();
		String lastName = this.lastNameField.getText();
		int birthYear = this.birthYearField.getIntegerValue();

		Individual individual = new Individual(firstName, lastName, birthYear);
		return individual;
	}

}
