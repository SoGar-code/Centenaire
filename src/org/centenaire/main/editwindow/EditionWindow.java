package org.centenaire.main.editwindow;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

import org.centenaire.entity.EntityEnum;
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
		tabbedPane.addTab("Ev�nements", eventTab);
		
		// Add Institution tab
		InstitutionTab institTab = new InstitutionTab();
		tabbedPane.addTab("Institutions", institTab);
		
		// Add TaxChrono Tab
		TaxTab taxChronoTab = new TaxTab(EntityEnum.TAXCHRONO.getValue());
		tabbedPane.addTab("Chrono.", taxChronoTab);
		
		// Add TaxGeo Tab
		TaxTab taxGeoTab = new TaxTab(EntityEnum.TAXGEO.getValue());
		tabbedPane.addTab("G�o.", taxGeoTab);
		
		// Add TaxTheme Tab
		TaxTab taxThemeTab = new TaxTab(EntityEnum.TAXTHEME.getValue());
		tabbedPane.addTab("Th�m.", taxThemeTab);
		
		// Add TagLike tab
		TagLikeTab tagLikeTab = new TagLikeTab();
		tabbedPane.addTab("Marqueurs", tagLikeTab);
		
		// Add TagLike tab
		TypeLikeTab typeLikeTab = new TypeLikeTab();
		tabbedPane.addTab("Types", typeLikeTab);
		
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
		String content = String.format("Fen�tre 'liste' de %s", name);
		JLabel listTab = new JLabel(content);
		tabbedView.add("Liste", listTab);
		
		// Tab with edit
		content = String.format("Fen�tre 'modifier' de %s", name);
		JLabel editTab = new JLabel(content);
		tabbedView.add("Modifier", editTab);
		
		return tabbedView;
	}
}
