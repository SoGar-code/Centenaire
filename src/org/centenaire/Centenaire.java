package org.centenaire;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.centenaire.csvImport.CsvImport;
import org.centenaire.edition.EditionWindow;
import org.centenaire.general.GeneralController;
import org.centenaire.general.GeneralWindow;
import org.centenaire.statistics.StatisticsWindow;

public class Centenaire extends JFrame{
	private GeneralWindow[] vectWindows;

	public Centenaire() {
		/**
		 * Main class of the system
		 * 
		 */
		
		super();
		this.setTitle("BdD Centenaire");
		this.setSize(700, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Color secondColor = Color.lightGray;
		
		// initialise the controller
		GeneralController gc = GeneralController.getInstance();
		
		// ===============================
		// Create the menu bar
		// ===============================
		JMenuBar menuBar = new JMenuBar();
		JMenu filesMenu = new JMenu("File");
		JMenuItem item1 = new JMenuItem("Import...");
		JMenuItem item2 = new JMenuItem("About: © SoGaR! 2018.");
		menuBar.add(filesMenu);
		filesMenu.add(item1);
		filesMenu.add(item2);

		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("Centenaire: 'Import' of MenuBar activated!");
				CsvImport csvImport = new CsvImport();
				csvImport.importCsv();
			}
		});
		
		// ===============================
		// Create the card layout of the interface
		// ===============================
		JPanel content = new JPanel();
		CardLayout cl = new CardLayout();
		content.setLayout(cl);
		
		// Create new windows and populate vectWindows
		vectWindows = new GeneralWindow[2];
		vectWindows[0] = new StatisticsWindow();
		vectWindows[1] = new EditionWindow();
				
		String[] listContents=new String[] {"Statistics", "Edition"};
		
		int j =0;
		for (GeneralWindow windows : vectWindows){
			content.add(windows, listContents[j]);
			j++;
		}
		
		// Selection of page
		JLabel label = new JLabel("Select page:");
		JComboBox<String> selector = new JComboBox<String>(listContents);
		selector.setPreferredSize(new Dimension(200,20));
		// associated action listeners
		// includes an update of the window when selected
		selector.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				vectWindows[selector.getSelectedIndex()].updateWindow();
				cl.show(content, listContents[selector.getSelectedIndex()]);
			}
		});
		
		// ===============================
		// define top panel
		// ===============================
		JPanel topPan = new JPanel();
		topPan.add(label);
		topPan.add(selector);
		topPan.setBackground(secondColor);
		
		// ===============================
		// final assembly
		// ===============================
		this.setJMenuBar(menuBar);
		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(topPan, BorderLayout.NORTH);
		this.getContentPane().add(content, BorderLayout.CENTER);
		
		this.setVisible(true);
	}

	public static void main(String[] args) {
		new Centenaire();
	}

}
