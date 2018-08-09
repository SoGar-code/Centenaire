/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.centenaire.dao.Dao;
import org.centenaire.entity.Discipline;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.InstitStatus;
import org.centenaire.entity.Institution;
import org.centenaire.entity.Tag;
import org.centenaire.entityeditor.IndividualEditor;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GeneralController;
import org.centenaire.util.editorsRenderers.Delete;
import org.centenaire.util.pubsub.Subscriber;

/**
 * Panel describing the current respondent.
 * 
 * <p>To be included in the 'Questionnaire' section.</p>
 *
 */
public class RespondentPanel extends JPanel implements Subscriber{
	GeneralController gc = GeneralController.getInstance();
	private EntityCombo<Individual> entityCombo;
	private EntityCombo<Institution> institCombo;
	private EntityCombo<InstitStatus> statusCombo;
	private EntityCombo<Institution> laboCombo;
	private ActionListener comboListener;
	private IndividualEditor indivEditor;
	private DropTable<Individual, Tag> dropTableTag;
	private DropTable<Individual, Discipline> dropTableDiscipline;
	private JButton svgButton;
	private JCheckBox lockBox;
	private Dao daoIndiv;
	
	RespondentPanel(){
		super();
		
		// Generic data objects
		daoIndiv = gc.getIndividualDao();
		
		// Top panel
		// ===============
		
		// Respondent label
		JLabel respondentLab = new JLabel("Répondant : ");
		
		// EntityCombo. NB: needs to subscribe to the entity channel!
		entityCombo = new EntityCombo<Individual>(EntityEnum.INDIV.getValue());
		
		// no selected element
		entityCombo.setSelectedIndex(-1);
		
		// create action listener
		comboListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {		
					// recover the currently selected object
					Individual entity = (Individual) entityCombo.getSelectedItem();

					// update the panel accordingly
					indivEditor.setObject(entity);
				
					// enable the save button
					svgButton.setEnabled(true);
					
					// update dropTableTag
					dropTableTag.updateEntity(entity);
					
					// update dropTableDiscipline
					dropTableDiscipline.updateEntity(entity);
					
					// Modify currentIndividual in gc.
					gc.setCurrentIndividual(entity);
					
				} catch (ClassCastException except) {
					String msg = "UpdateEntityPanel -- error when casting entity,\n"
							+ "not updating the panel!";
					System.out.println(msg);
				}
			}
		};
		
		// Link entityCombo and listener
		entityCombo.addActionListener(comboListener);
		
		// New checkBox
		lockBox = new JCheckBox("Verrouiller");
		
		// Add action listener
		lockBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				entityCombo.setEnabled(!lockBox.isSelected());
				
				if (!lockBox.isSelected()) {
					// unplug listener
					entityCombo.removeActionListener(comboListener);
					
					// update content (as if 'individual' had changed)
					entityCombo.updateSubscriber(EntityEnum.INDIV.getValue());
					
					// replug listener
					entityCombo.addActionListener(comboListener);
				}
			}
		});
		
		// Creating and populating top panel
		JPanel topPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		topPan.add(respondentLab);
		topPan.add(entityCombo);
		topPan.add(lockBox);
		
		// Center panel
		// ======================
		
		// left: editor for the individual
		indivEditor = new IndividualEditor();
		// Choose size (width, height)
		indivEditor.setSize(100, 200);
		
		// Institution panel
		JPanel institPan = new JPanel(new GridLayout(4, 2));
		
		JLabel institLab = new JLabel("Institution : ");
		institCombo = new EntityCombo<Institution>(EntityEnum.INSTIT.getValue());
		
		JLabel statusLab = new JLabel("Statut : ");
		statusCombo = new EntityCombo<InstitStatus>(EntityEnum.INSTITSTATUS.getValue());
		
		JLabel laboLab = new JLabel("Labo de recherche : ");
		laboCombo = new EntityCombo<Institution>(EntityEnum.INSTIT.getValue());
		
		institPan.add(institLab);
		institPan.add(institCombo);
		institPan.add(statusLab);
		institPan.add(statusCombo);
		institPan.add(laboLab);
		institPan.add(laboCombo);
		
		// Auxiliary 'individual' panel
		JPanel auxIndivPan = new JPanel(new FlowLayout());
		auxIndivPan.add(indivEditor);
		auxIndivPan.add(institPan);
		
		// Table of discipline
		dropTableDiscipline = new DropTable<Individual, Discipline>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.DISCIPLINES.getValue(),
				EntityEnum.INDIVDISCIPL.getValue(),
				new Class[] {String.class, Delete.class},
				new String[] {"Discipline", "Retirer"}
				);
		
		// Table of tags
		dropTableTag = new DropTable<Individual, Tag>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.TAG.getValue(),
				EntityEnum.INDIVTAG.getValue(),
				new Class[] {String.class, Delete.class},
				new String[] {"Taxinomie", "Retirer"}
				);
		
		JPanel dropTablePan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		dropTablePan.add(dropTableDiscipline);
		dropTablePan.add(new JLabel(" "));
		dropTablePan.add(dropTableTag);
		
		JPanel centerPan = new JPanel();
		centerPan.setLayout(new BoxLayout(centerPan, BoxLayout.PAGE_AXIS));
		centerPan.add(auxIndivPan);
		centerPan.add(dropTablePan);
		
		// Bottom panel
		// ==============
		// Save button and its action
		svgButton = new JButton("Sauvegarder");
		svgButton.setEnabled(false);
		
		svgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Calling save button");
				
				Individual obj = indivEditor.getObject();
			
				dropTableTag.saveContent(obj);
				
				dropTableDiscipline.saveContent(obj);
				
				// NB: updating individual triggers a 'subscriber update'
				// including (possibly) a reset of the droptables,
				// so it has to be done last!
				daoIndiv.update(obj);
			}
		});
		
		JPanel bottomPan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPan.add(svgButton);
		
		// Final assembly
		// ===============
		this.setLayout(new BorderLayout());
		this.add(topPan, BorderLayout.NORTH);
		this.add(centerPan, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
		// register for Individual channel
		gc.getChannel(EntityEnum.INDIV.getValue()).addSubscriber(this);
	}
	
	/**
	 * Method implementing the Subscriber interface in the current class
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 */
	@Override
	public void updateSubscriber(int channelIndex) {
		// unplug listener
		entityCombo.removeActionListener(comboListener);
		
		// when 'lock' is not selected, update entityCombo
		if (!lockBox.isSelected()) {

			// Update entityCombo
			entityCombo.updateSubscriber(channelIndex);
			
			// Reset indivEditor
			indivEditor.reset();
			
			// Reset dropTableTag
			dropTableTag.reset();

			// Reset dropTableDiscipline
			dropTableDiscipline.reset();
			
		} else {
			
			// When 'lock' is selected, update indivEditor with current selected individual
			Individual indiv = (Individual) entityCombo.getSelectedItem();
			
			// Find latest 'version' of this individual
			Individual newIndiv = (Individual) daoIndiv.find(indiv.getIndex());
			
			indivEditor.setObject(newIndiv);
			
			// Update entityCombo
			entityCombo.setEnabled(true);
			entityCombo.setSelectedItem(newIndiv);
			entityCombo.setEnabled(false);
		}
		
		// replug listener
		entityCombo.addActionListener(comboListener);
		
		// Should do something about tagListTableModel ...
	}
	
	/**
	 * Set the values of 'institPan' according to 'currentIndiv'
	 * 
	 * @param currentIndiv
	 */
	public void updateInstitPanel(Individual currentIndiv) {
		// Recover suitable discipline
//		RelationDao<Individual, Discipline> indivDiscipl = (RelationDao<Individual, Discipline>) gc.getRelationDao(EntityEnum.INDIVDISCIPL.getValue());
//		List<Discipline> disciplList = indivDiscipl.findAll(currentIndiv);
//		disciplineCombo.setSelectedEntity(disciplList.get(0));
//		
//		private EntityCombo<Institution> institCombo;
//		private EntityCombo<InstitStatus> statusCombo;
//		private EntityCombo<Institution> laboCombo;
	}
	
	/**
	 * Save the values of 'institPan'
	 * 
	 */
	public void saveInstitPan(Individual currentIndiv) {
//		Discipline discipl = disciplineCombo.getSelectedEntity();
		
	}
}
