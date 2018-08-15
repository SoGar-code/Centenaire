package org.centenaire.main.editwindow;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import org.centenaire.util.GeneralWindow;

/**
 * Window to edit elements in the database
 * 
 * <p>
 * contains "New line" and "Save/update" buttons,
 * together with their listeners.
 * 
 * NB: GeneralWindow provides a copy of gc (GeneralController)
 * 
 */
public class EditionWindow extends GeneralWindow {

	private JTabbedPane tabbedPane;

	public EditionWindow(){
		super();
		
		// Initialize basic content
		tabbedPane = new JTabbedPane();
		
		// Add individual tab
		IndividualTab indivTab = new IndividualTab();
		tabbedPane.addTab("Personnes", indivTab);
		
		// Add item tab
		ItemTab itemTab = new ItemTab();
		tabbedPane.addTab("Productions", itemTab);
		
		// Add Event tab
		EventTab eventTab = new EventTab();
		tabbedPane.addTab("Evénements", eventTab);
		
		// Add Event tab
		InstitutionTab institTab = new InstitutionTab();
		tabbedPane.addTab("Institutions", institTab);
		
		// Final assembly into a tabbed panel.
		TagLikeTab tagLikeTab = new TagLikeTab();
		tabbedPane.addTab("Marqueurs", tagLikeTab);
		
		//======================
		// Final assembly
		//======================
		this.setLayout(new BorderLayout());
		this.add(tabbedPane, BorderLayout.CENTER);
	}
	
	public JTabbedPane createTabbedView(String name) {
		JTabbedPane tabbedView = new JTabbedPane(JTabbedPane.LEFT);
		
		// Creates 3 tabs with suitable characteristics
		// ============================================
		
		// Tab with list
		String content = String.format("Fenêtre 'liste' de %s", name);
		JLabel listTab = new JLabel(content);
		tabbedView.add("Liste", listTab);
		
		// Tab with edit
		content = String.format("Fenêtre 'modifier' de %s", name);
		JLabel editTab = new JLabel(content);
		tabbedView.add("Modifier", editTab);
		
		return tabbedView;
	}
}
