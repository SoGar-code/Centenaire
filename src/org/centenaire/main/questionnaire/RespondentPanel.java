/**
 * 
 */
package org.centenaire.main.questionnaire;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.centenaire.dao.Dao;
import org.centenaire.entity.EntityEnum;
import org.centenaire.entity.Individual;
import org.centenaire.entityeditor.IndividualEditor;
import org.centenaire.general.EntityCombo;
import org.centenaire.general.EntityDialog;
import org.centenaire.general.GTable;
import org.centenaire.general.GeneralController;
import org.centenaire.general.ListTableModel;
import org.centenaire.general.editorsRenderers.Delete;
import org.centenaire.general.pubsub.Subscriber;

/**
 * Panel describing the current respondent.
 * 
 * <p>To be included in the 'Questionnaire' section.</p>
 *
 */
public class RespondentPanel extends JPanel implements Subscriber{
	private EntityCombo<Individual> entityCombo ;
	private ActionListener comboListener;
	private IndividualEditor indivEditor;
	private ListTableModel tagListTableModel;
	private JButton svgButton;
	
	RespondentPanel(){
		super();
		
		GeneralController gc = GeneralController.getInstance();
		
		Dao dao = gc.getIndividualDao();
		
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
					
				} catch (ClassCastException except) {
					String msg = "UpdateEntityPanel -- error when casting entity,\n"
							+ "not updating the panel!";
					System.out.println(msg);
				}
			}
		};
		
		// Link entityCombo and listener
		entityCombo.addActionListener(comboListener);
		
		// *New entity* button
		JButton newEntity = new JButton("Nouvel individu");
		
		// associated action
		newEntity.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("RespondentPanel.newEntity activated!");
				
				Individual tl = Individual.defaultElement();

				EntityDialog<Individual> ed = new EntityDialog<Individual>(EntityEnum.INDIV.getValue());
				
				// Try to get a value from the dialog...
				try {
					Individual finalElt = ed.showEntityDialog();
				} catch (NullPointerException e) {
					// edition was cancelled before completion...
					System.out.println("Edition of the element cancelled.");
				}
				
			}
		});
		
		// Creating and populating top panel
		JPanel topPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		topPan.add(respondentLab);
		topPan.add(entityCombo);
		topPan.add(newEntity);
		
		// Center panel
		// ======================
		
		// left: editor for the individual
		indivEditor = new IndividualEditor();
		// Choose size (width, height)
		indivEditor.setSize(100, 200);
		
		// right: table of tags
		// NB: needs suitable DAO method
		tagListTableModel = new ListTableModel(
				new Class[] {String.class, Delete.class},
				new String[] {"Etiquette", "Retirer"},
				gc.getDao(EntityEnum.TAG.getValue()).findAll()
				);
		GTable entityList = new GTable(tagListTableModel);
		// Choose size (width, height)
		entityList.setSize(50, 200);
		
		JPanel centerPan = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPan.add(indivEditor);
		centerPan.add(entityList);
		
		// Bottom panel
		// ==============
		// Save button and its action
		svgButton = new JButton("Sauvegarder");
		svgButton.setEnabled(false);
		
		svgButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				System.out.println("Calling save button");
				
				Individual obj = indivEditor.getObject();
				
				dao.update(obj);
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

	@Override
	public void updateSubscriber() {
		// Disable svgButton 
		svgButton.setEnabled(false);
		
		// unplug listener
		entityCombo.removeActionListener(comboListener);
		
		// Update entityCombo
		entityCombo.updateSubscriber();
		
		// replug listener
		entityCombo.addActionListener(comboListener);
		
		// Reset indivEditor
		indivEditor.reset();
		
		// Should do something about tagListTableModel ...
	}
}
