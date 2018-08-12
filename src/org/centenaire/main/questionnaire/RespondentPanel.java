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
import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.entity.Discipline;
import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.InstitStatus;
import org.centenaire.entity.Institution;
import org.centenaire.entity.Tag;
import org.centenaire.entityeditor.IndividualEditor;
import org.centenaire.util.EntityCombo;
import org.centenaire.util.GIntegerField;
import org.centenaire.util.GeneralController;
import org.centenaire.util.dragndrop.DropTable;
import org.centenaire.util.dragndrop.TargetHandlerDouble;
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
	private DropTable<Individual, DoubleEntity<Institution, InstitStatus>> dropTableInstitStatus;
	private ActionListener comboListener;
	private IndividualEditor indivEditor;
	private DropTable<Individual, Tag> dropTableTag;
	private DropTable<Individual, Discipline> dropTableDiscipline;
	private JButton svgButton;
	private JCheckBox lockBox;
	private Dao daoIndiv;
	private GIntegerField phdYearField;
	private JCheckBox phdOnGreatWarField;
	private JCheckBox habilitationOnGreatWarField;
	
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
		
		// create action listener (called when new individual is selected
		comboListener = new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try {		
					// recover the currently selected object
					Individual currentIndividual = (Individual) entityCombo.getSelectedItem();

					// update the panel accordingly
					indivEditor.setObject(currentIndividual);
				
					// enable the save button
					svgButton.setEnabled(true);
					
					// update dropTableInstitStatus
					//dropTableInstitStatus.updateEntity(currentIndividual);
					
					// update dropTableTag
					dropTableTag.updateEntity(currentIndividual);
					
					// update dropTableDiscipline
					dropTableDiscipline.updateEntity(currentIndividual);
					
					// Set Phd defense year
					int phdYear = ((AbstractIndividualDao) daoIndiv).getPhdDefenseYear(
							currentIndividual);
					phdYearField.setIntegerValue(phdYear);
					
					// Save state of CheckBox
					boolean phdOnGreatWar = ((AbstractIndividualDao) daoIndiv).getPhdOnGreatWar(
							currentIndividual);
					phdOnGreatWarField.setSelected(phdOnGreatWar);
					
					// Save state of CheckBox
					boolean habilitationOnGreatWar = ((AbstractIndividualDao) daoIndiv).getHabilitationOnGreatWar(
							currentIndividual);
					habilitationOnGreatWarField.setSelected(habilitationOnGreatWar);
					
					// Modify currentIndividual in gc.
					gc.setCurrentIndividual(currentIndividual);
					
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
		// disable the editor 
		indivEditor.setEnabled(false);
		
		// Institution panel
		JPanel institPan = new JPanel(new GridLayout(3, 2));
		
		JLabel phdYearLab = new JLabel("Date soutenance (si entre 2012 et 2017) : ");
		phdYearField = new GIntegerField();
		
		JLabel phdOnGreatWarLab = new JLabel("Thèse portant sur la Grande Guerrre : ");
		phdOnGreatWarField = new JCheckBox();

		JLabel habilitationOnGreatWarLab = new JLabel("HDR portant sur la Grande Guerrre : ");
		habilitationOnGreatWarField = new JCheckBox();
		
		institPan.add(phdYearLab);
		institPan.add(phdYearField);
		institPan.add(phdOnGreatWarLab);
		institPan.add(phdOnGreatWarField);
		institPan.add(habilitationOnGreatWarLab);
		institPan.add(habilitationOnGreatWarField);
		
		// Auxiliary 'individual' panel
		JPanel auxIndivPan = new JPanel(new FlowLayout());
		auxIndivPan.add(indivEditor);
		auxIndivPan.add(institPan);
		
		// Table of institutional affiliation
		// ===================================
		dropTableInstitStatus = new DropTable<Individual, DoubleEntity<Institution, InstitStatus>>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.INSTIT.getValue(),
				EntityEnum.INDIVINSTIT.getValue(),
				new Class[] {String.class, InstitStatus.class, Delete.class},
				new String[] {"Institution", "Statut", "Retirer"},
				2
				);
		// Replace the default TargetHandler:
		dropTableInstitStatus.getTable().setTransferHandler(
				new TargetHandlerDouble<Institution, InstitStatus>(EntityEnum.INSTIT.getValue()));
		
		
		// DropTable panel
		// ================
		
		// Table of discipline
		dropTableDiscipline = new DropTable<Individual, Discipline>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.DISCIPLINES.getValue(),
				EntityEnum.INDIVDISCIPL.getValue(),
				new Class[] {String.class, Delete.class},
				new String[] {"Discipline", "Retirer"},
				1
				);
		
		// Table of tags
		dropTableTag = new DropTable<Individual, Tag>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.TAG.getValue(),
				EntityEnum.INDIVTAG.getValue(),
				new Class[] {String.class, Delete.class},
				new String[] {"Mot-clef", "Retirer"},
				1
				);
		
		JPanel dropTablePan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		dropTablePan.add(dropTableDiscipline);
		dropTablePan.add(new JLabel(" "));
		dropTablePan.add(dropTableTag);
		
		JPanel centerPan = new JPanel();
		centerPan.setLayout(new BoxLayout(centerPan, BoxLayout.PAGE_AXIS));
		centerPan.add(auxIndivPan);
		centerPan.add(dropTableInstitStatus);
		centerPan.add(dropTablePan);
		
		// Bottom panel
		// ==============
		// Save button and its action
		svgButton = new JButton("Sauvegarder");
		svgButton.setEnabled(false);
		
		svgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){			
				saveQuestion();
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
	 * Save the current content of this panel.
	 * 
	 * <p>This method mimics the one in 'QuestionTemplate'.</p>
	 * 
	 * @see org.centenaire.main.questionnaire.QuestionTemplate
	 */
	public void saveQuestion() {
		Individual currentIndividual = indivEditor.getObject();
		
		// Save Phd defense year
		((AbstractIndividualDao) daoIndiv).setPhdDefenseYear(
				currentIndividual,
				phdYearField.getIntegerValue());
		
		// Save state of CheckBox
		((AbstractIndividualDao) daoIndiv).setPhdOnGreatWar(
				currentIndividual,
				phdOnGreatWarField.isSelected());
		
		// Save state of CheckBox
		((AbstractIndividualDao) daoIndiv).setHabilitationOnGreatWar(
				currentIndividual,
				habilitationOnGreatWarField.isSelected());
		
		// dropTableInstitStatus.saveContent(currentIndividual);
	
		dropTableTag.saveContent(currentIndividual);
		
		dropTableDiscipline.saveContent(currentIndividual);
		
		// NB: updating individual triggers a 'subscriber update'
		// including (possibly) a reset of the droptables,
		// so it has to be done last!
		daoIndiv.update(currentIndividual);
	}
}
