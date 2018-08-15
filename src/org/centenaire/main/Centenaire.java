package org.centenaire.main;

import java.awt.Color;
import java.awt.Dialog;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import org.centenaire.entity.Individual;
import org.centenaire.entity.util.EntityDialog;
import org.centenaire.main.editwindow.EditionWindow;
import org.centenaire.main.questionnaire.MainQuestionnaire;
import org.centenaire.util.GeneralController;


/**
 * Main class of the system
 * 
 * <p> Creates two different elements :
 * 	<ul>
 * 		<li>an editor frame</li>
 * 		<li>a questionnaire frame</li>
 * </ul>
 * 
 * Formally, the class itself is the editor frame.
 * 
 */
public class Centenaire extends JFrame{

	public Centenaire() {
		
		super();
		this.setTitle("BdD Centenaire");
		this.setSize(700, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setModalExclusionType(Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
		
		Color secondColor = Color.lightGray;
		
		// initialise the controller
		GeneralController gc = GeneralController.getInstance();
		
		// ===============================
		// Create the menu bar
		// ===============================
		JMenuBar menuBar = new JMenuBar();
		JMenu filesMenu = new JMenu("File");
		JMenuItem item1 = new JMenuItem("Trying stuff...");
		JMenuItem item2 = new JMenuItem("About: © 2018 SoGaR!");
		menuBar.add(filesMenu);
		filesMenu.add(item1);
		filesMenu.add(item2);
		
		// Action associated to the "item1" button
		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("BdD centenaire: 'Trying stuff...' of MenuBar activated!");
				
				// Thing we are currently trying...
				// =================================
				EntityDialog<Individual> ed = new EntityDialog(1);
				
				// Try to get a value from the dialog...
				try {
					Individual finalElt = ed.showEntityDialog();
					
					String aux = String.format("==> contenu finalElt: %s", finalElt.toString());
					System.out.println(aux);
					
					System.out.println(String.format("==> âge : %d", finalElt.getBirth_year()));
				} catch (NullPointerException e) {
					// but perhaps the edition was cancelled before completion...
					System.out.println("Edition of the element cancelled.");
				}
				
			}
		});
		
		// ===============================
		// Edition frame
		// ===============================
		JPanel content = new EditionWindow();
		
		// ===============================
		// final assembly
		// ===============================
		this.setJMenuBar(menuBar);
		this.setContentPane(content);
		
		this.setTopRightLocation();
		this.setVisible(true);
	}
	
	public void setTopRightLocation() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();
        int x = (int) rect.getMaxX() - this.getWidth();
        int y = 0;
        this.setLocation(x, y);
	}

	public static void main(String[] args) {
		new MainQuestionnaire();
		new Centenaire();
	}

}
