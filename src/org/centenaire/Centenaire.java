package org.centenaire;

import java.awt.Color;
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

import org.centenaire.edition.entities.taglike.TagLike;
import org.centenaire.editor.EditionWindow;
import org.centenaire.general.EntityDialog;
import org.centenaire.general.EntityEditor;
import org.centenaire.questionnaire.Questionnaire;


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
		
		Color secondColor = Color.lightGray;
		
		// initialise the controller (triggers the request for password!) 
		//GeneralController gc = GeneralController.getInstance();
		
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
		
		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("BdD centenaire: 'Trying stuff...' of MenuBar activated!");
				
				// Thing we are currently trying...
				TagLike tl = TagLike.defaultElement();
				EntityEditor<TagLike> editorTL = tl.editionForm();
				
				EntityDialog<TagLike, EntityEditor<TagLike>> ed = new EntityDialog<TagLike, EntityEditor<TagLike>>(tl, editorTL);
				TagLike finalElt = ed.showEntityDialog();
				
				String aux = String.format("==> contenu finalElt: %s", finalElt.getName());
				System.out.println(aux);
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
		new Centenaire();
		new Questionnaire();
	}

}
