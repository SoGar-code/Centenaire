package gestionBilicence;

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

import gestionBilicence.csvImport.CsvImport;
import gestionBilicence.edition.EditionWindow;
import gestionBilicence.general.GeneralController;
import gestionBilicence.general.GeneralWindow;
import gestionBilicence.statistics.StatisticsWindow;

public class GestionBilicence extends JFrame{
	private GeneralWindow[] vectWindows;

	public GestionBilicence() {
		super();
		this.setTitle("Gestion bilicence");
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
		JMenuItem item2 = new JMenuItem("About: v0.9, 2017 by O. Gabriel.");
		menuBar.add(filesMenu);
		filesMenu.add(item1);
		filesMenu.add(item2);

		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("GestionBilicence: 'Import' of MenuBar activated!");
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
		new GestionBilicence();
	}

}
