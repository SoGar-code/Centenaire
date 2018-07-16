package org.centenaire.entityeditor;

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
	 * @see org.centenaire.entityeditor.EntityEditor#getObject()
	 */
	@Override
	public TagLike getObject() {
		String name = this.nameField.getText();

		TagLike tl = new TagLike(name);
		return tl;
	}

	/**
	 * Update the fields in the editor to represent another 'TagLike' instance
	 */
	@Override
	public void setObject(TagLike obj) {
		nameField.setText(obj.getName());
		
	}

}
