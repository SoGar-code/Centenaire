package org.centenaire.statistics;

import java.awt.BorderLayout;
import java.awt.Panel;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;

import org.centenaire.general.Entity;
import org.centenaire.general.GTable;
import org.centenaire.general.GeneralController;
import org.centenaire.general.ListTableModel;

	/**
	 * 
	 * @author Trivy
	 * Basic class for the statistic panel, including:
	 * _ a small panel on the west, including a JLabel and a JList
	 * _ a large JTable in the center
	 * Models for list and table are initialized elsewhere
	 */

public class StatisticsPanel extends Panel {
	protected JPanel westPan;
	protected JList<Entity> westList;
	protected GTable mainTable;

	public StatisticsPanel(ListModel listModel,ListTableModel tableModel) {
		super();
		GeneralController gc = GeneralController.getInstance();
		
		// Definition of westPan
		String textList = "Select:";
		JLabel westLabel = new JLabel(textList);
		westList = new JList<Entity>(listModel);
		westList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane0 = new JScrollPane(westList);
		scrollPane0.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane0.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		westPan = new JPanel();
		westPan.setLayout(new BoxLayout(westPan, BoxLayout.PAGE_AXIS));
		westPan.add(westLabel);
		westPan.add(scrollPane0);
		
		// Definition of the central panel
		mainTable = new GTable(tableModel);
		
		this.setLayout(new BorderLayout());
		this.add(mainTable, BorderLayout.CENTER);
		this.add(westPan, BorderLayout.WEST);
	}

	public JPanel getWestPan() {
		return westPan;
	}

	public void setWestPan(JPanel westPan) {
		this.westPan = westPan;
	}

	public GTable getMainTable() {
		return mainTable;
	}

	public void setMainTable(GTable mainTable) {
		this.mainTable = mainTable;
	}
	
	public JList<Entity> getWestList() {
		return westList;
	}
}
