/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.centenaire.dao.Dao;
import org.centenaire.dao.abstractDao.AbstractIndividualDao;
import org.centenaire.entity.Discipline;
import org.centenaire.entity.DoubleEntity;
import org.centenaire.entity.Entity;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entity.InstitStatus;
import org.centenaire.entity.Institution;
import org.centenaire.entity.Tag;
import org.centenaire.entityeditor.IndividualEditor;
import org.centenaire.util.GIntegerField;
import org.centenaire.util.GeneralController;
import org.centenaire.util.dragndrop.DropListTableModel;
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
	private IndividualEditor indivEditor;
	private GIntegerField phdYearField;
	private JCheckBox phdOnGreatWarField;
	private JCheckBox habilitationOnGreatWarField;
	
	private DropTable<Individual, DoubleEntity<Institution, InstitStatus>> dropTableInstitStatus;
	
	private DropTable<Individual, Tag> dropTableTag;
	private DropTable<Individual, Discipline> dropTableDiscipline;
	
	private JButton svgButton;

	// Inner workings
	GeneralController gc = GeneralController.getInstance();
	private Dao daoIndiv;
	
	RespondentPanel(){
		super();
		
		// Generic data objects
		daoIndiv = gc.getIndividualDao();
		
		// Center panel
		// ======================
		
		// left: editor for the individual
		indivEditor = new IndividualEditor();
		// Choose size (width, height)
		indivEditor.setSize(100, 200);
		// disable the editor 
		indivEditor.setEnabled(false);
		
		// Institution panel
		JPanel institPan = new JPanel(new GridLayout(3, 2, 10, 10));
		
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
				new String[] {"Institution", "Statut", "Retirer"}
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
				new String[] {"Discipline", "Retirer"}
				);
		
		// Table of tags
		dropTableTag = new DropTable<Individual, Tag>(
				EntityEnum.INDIV.getValue(),
				EntityEnum.TAG.getValue(),
				EntityEnum.INDIVTAG.getValue(),
				new Class[] {String.class, Delete.class},
				new String[] {"Mot-clef", "Retirer"}
				);
		
		JPanel dropTablePan = new JPanel(new GridLayout(1,2,10,10));
		dropTablePan.add(dropTableDiscipline);
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
		
		svgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){			
				savePanel();
			}
		});
		
		JPanel bottomPan = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPan.add(svgButton);
		
		// Final assembly
		// ===============
		this.setLayout(new BorderLayout());
		this.add(centerPan, BorderLayout.CENTER);
		this.add(bottomPan, BorderLayout.SOUTH);
		
		// subscribe to 'Individual' channel
		gc.getChannel(EntityEnum.INDIV.getValue()).addSubscriber(this);
	}
	
	/**
	 * Method implementing the Subscriber interface in the current class.
	 * 
	 * <p>In this method, we are only concerned with updates from the 'Individual' channel.</p>
	 * 
	 * @see org.centenaire.util.pubsub.Subscriber
	 */
	@Override
	public void updateSubscriber(int channelIndex) {
		
		// only (directly) concerned with updates on 'Individual' channel
		if (channelIndex == EntityEnum.INDIV.getValue()) {
			// In that case, update content
			Individual currentIndividual = gc.getCurrentIndividual();
			
			if (currentIndividual != null) {
				this.setPanel();
			} else {
				this.resetPanel();
			}
		}
	}
	
	/**
	 * Save the current content of this panel.
	 * 
	 * <p>This method mimics the one in 'QuestionTemplate'.</p>
	 * 
	 * @see org.centenaire.main.questionnaire.QuestionTemplate
	 */
	public void savePanel() {
		//printDataState();
		// Recover currentIndividual
		Individual currentIndividual = gc.getCurrentIndividual();
		
		// It seems that saving the drop tables should be done first 
		// (in order to avoid inopportune updates?)
		dropTableInstitStatus.saveContent(currentIndividual);
		
		dropTableTag.saveContent(currentIndividual);
		
		dropTableDiscipline.saveContent(currentIndividual);
		
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
	}
	
	/**
	 * Method called to set the content of the panel (in particular when the currentIndividual changes).
	 */
	public void setPanel() {
		// Recover currentIndividual
		Individual currentIndividual = gc.getCurrentIndividual();
		
		// update the panel accordingly
		indivEditor.setObject(currentIndividual);
		
		// Set Phd defense year
		int phdYear = ((AbstractIndividualDao) daoIndiv).getPhdDefenseYear(
				currentIndividual);
		phdYearField.setIntegerValue(phdYear);
		
		// Set state of CheckBox
		boolean phdOnGreatWar = ((AbstractIndividualDao) daoIndiv).getPhdOnGreatWar(
				currentIndividual);
		phdOnGreatWarField.setSelected(phdOnGreatWar);
		
		// Set state of CheckBox
		boolean habilitationOnGreatWar = ((AbstractIndividualDao) daoIndiv).getHabilitationOnGreatWar(
				currentIndividual);
		habilitationOnGreatWarField.setSelected(habilitationOnGreatWar);
		
		// update dropTableInstitStatus
		dropTableInstitStatus.updateEntity(currentIndividual);
		
		// update dropTableTag
		dropTableTag.updateEntity(currentIndividual);
		
		// update dropTableDiscipline
		dropTableDiscipline.updateEntity(currentIndividual);
		
	}
	
	public void resetPanel() {
		indivEditor.reset();
		
		phdYearField.setIntegerValue(0);
		
		phdOnGreatWarField.setSelected(false);
		
		habilitationOnGreatWarField.setSelected(false);
		
		dropTableInstitStatus.reset();
		
		dropTableTag.reset();
		
		dropTableDiscipline.reset();
	}
	
	public void printDataState() {
		DropListTableModel model = (DropListTableModel) dropTableInstitStatus.getModel(); 
		LinkedList<Entity> data = model.getData();
		String msg0 = "[";
		for (Entity row: data) {
			msg0 += row.toString()+",";
		}
		msg0 += "]";
		
		System.out.println("dropTableInstitStatus: "+msg0);
	}
}
