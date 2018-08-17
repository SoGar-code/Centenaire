package org.centenaire.entity.editor;

import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.centenaire.entity.typelike.CatEnum;
import org.centenaire.entity.typelike.TypeLike;

/**
 * Class providing the editor form for 'Type' Entities. 
 *
 * @see TypeLike
 */
public class TypeEditor extends EntityEditor<TypeLike> {
	JTextField nameField;
	JComboBox<CatEnum> catCombo = new JComboBox<CatEnum>(CatEnum.values());
	
	public TypeEditor() {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel namePan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		
		JLabel description = new JLabel("Nom : ");
		// Active text area...
		nameField = new JTextField(20);
		nameField.setText("-");
		
		namePan.add(description);
		namePan.add(nameField);
		
		this.add(namePan);
		
		// Category line
		JPanel catPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel catLab = new JLabel("Catégorie : ");
		catPan.add(catLab);
		catPan.add(catCombo);
		
		this.add(catPan);
	}

	/** 
	 * Recover the 'Type' entity defined by the current instance.
	 * 
	 * @see org.centenaire.entity.editor.EntityEditor#getObject()
	 */
	@Override
	public TypeLike getObject() {
		int index = this.getIndexField();
		String name = this.nameField.getText();
		CatEnum category = (CatEnum) this.catCombo.getSelectedItem();

		TypeLike tl = new TypeLike(index, name, category);
		return tl;
	}

	/**
	 * Update the fields in the editor to represent another 'Type' instance
	 */
	@Override
	public void setObject(TypeLike obj) {
		this.setIndexField(obj.getIndex());
		nameField.setText(obj.getName());
		catCombo.setSelectedItem(obj.getCategory());
	}

	/**
	 * Method to reset the EntityEditor to a neutral value.
	 */
	@Override
	public void reset() {
		// index = 0, this is not a real element
		this.setIndexField(0);
		this.nameField.setText("-");
		this.catCombo.setSelectedIndex(-1);
	}

}
