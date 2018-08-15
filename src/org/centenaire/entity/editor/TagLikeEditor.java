package org.centenaire.entity.editor;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.centenaire.entity.TagLike;

/**
 * Class providing the editor form for TagLike. 
 *
 * @see TagLike
 */
public class TagLikeEditor extends EntityEditor<TagLike> {
	JTextField nameField;
	
	public TagLikeEditor() {
		super();
		
		JLabel description = new JLabel("Name: ");
		
		// Active text area...
		nameField = new JTextField(20);
		nameField.setText("-");
		
		this.add(description);
		this.add(nameField);
	}

	/** 
	 * Recover the 'TagLike' entity defined by the current instance.
	 * 
	 * @see org.centenaire.entity.editor.EntityEditor#getObject()
	 */
	@Override
	public TagLike getObject() {
		int index = this.getIndexField();
		String name = this.nameField.getText();

		TagLike tl = new TagLike(name);
		tl.setIndex(index);
		return tl;
	}

	/**
	 * Update the fields in the editor to represent another 'TagLike' instance
	 */
	@Override
	public void setObject(TagLike obj) {
		this.setIndexField(obj.getIndex());
		nameField.setText(obj.getName());
	}

	/**
	 * Method to reset the EntityEditor to a neutral value.
	 */
	@Override
	public void reset() {
		// index = 0, this is not a real element
		this.setIndexField(0);
		this.nameField.setText("-");
	}

}
