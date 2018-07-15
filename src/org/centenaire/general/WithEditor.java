/**
 * 
 */
package org.centenaire.general;

/**
 * Express abstractly that the class has editors.
 * 
 * <p>Entity classes T for our purposes should be both:
 * <ul>
 * <li>subclasses of Entity</li>
 * <li>implement WithEditor<T></li>
 * </ul>
 * 
 *  @param <T> associated entity class.
 *
 */
public interface WithEditor<T> {

	/**
	 * Provide the 'edition form' of the Entity
	 * 
	 * <p>This is the form used in the different edition panels.</p>
	 * 
	 * @return Editor of the Entity
	 */
	public abstract EntityEditor<T> editionForm();
}
