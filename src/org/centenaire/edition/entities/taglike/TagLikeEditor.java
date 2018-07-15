package org.centenaire.edition.entities.taglike;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.centenaire.general.EntityEditor;

/**
 * Class providing the editor form for TagLike. 
 *
 * @see TagLike
 */
public class TagLikeEditor extends EntityEditor<TagLike> {
	JTextField name;
	
	public TagLikeEditor(TagLike tl) {
		super(tl);
		
		JLabel description = new JLabel("Name: ");
		
		// Active text area...
		name = new JTextField(20);
		name.setText(tl.getName());
		
		this.add(description);
		this.add(name);
	}

	/** 
	 * Recover the 'TagLike' entity defined by the current instance.
	 * 
	 * @see org.centenaire.general.EntityEditor#getObject()
	 */
	@Override
	public TagLike getObject() {
		String name = this.name.getText();

		TagLike tl = new TagLike(name);
		return tl;
	}

}
