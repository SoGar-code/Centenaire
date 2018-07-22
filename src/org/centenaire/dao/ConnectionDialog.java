package org.centenaire.dao;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 * Dialog box to parametrize DB connection 
 * 
 * <p>
 * The user needs to select among predefined elements: 
 * <ul>
 * <li> host <li>
 * <li> name of the database.</li>
 * </ul>
 * 
 * The user also needs to provide:
 * <ul>
 * <li>the username (but a default value is provided),</li>
 * <li>the associated password.</li>
 * </ul>
 * <p>
 * 
 * @author OG
 * 
 */
public class ConnectionDialog extends JDialog{
	private String user, passwd, host, dataBase;
	
	public ConnectionDialog(){
		super();
		this.setTitle("BdD centenaire — paramètres de connexion");
		this.setModal(true);
		this.setSize(400,250);
		this.setLocationRelativeTo(null);
		
		JPanel selectionPan = new JPanel();
		JPanel controlPan = new JPanel();
		
		// Chosing host:
		JLabel hostLabel = new JLabel("Serveur :");
		JComboBox<String> hostCombo = new JComboBox<String>(new String[]{"localhost:5432"});
		
		// Chosing database:
		JLabel dbLabel = new JLabel("Base de données :");
		JComboBox<String> dbCombo = new JComboBox<String>(new String[]{"bdd_centenaire"});
		
		// User:
		JLabel userLabel = new JLabel("Utilisateur :");
		JTextField userJtf = new JTextField("postgres");
		
		// Password:
		JLabel passwdLabel = new JLabel("Mot de passe :");
		JPasswordField passwdJtf = new JPasswordField();
		
		// type:
		JLabel typeLabel = new JLabel("Type of database:");
		String[] listTypes = {"PostgreSQL","MySQL"};
		JComboBox<String> typeCombo = new JComboBox<String>(listTypes);
		
		selectionPan.setLayout(new GridLayout(4,2));
		selectionPan.add(hostLabel);
		selectionPan.add(hostCombo);
		selectionPan.add(dbLabel);
		selectionPan.add(dbCombo);
		selectionPan.add(userLabel);
		selectionPan.add(userJtf);
		selectionPan.add(passwdLabel);
		selectionPan.add(passwdJtf);
		//selectionPan.add(typeLabel);
		//selectionPan.add(typeCombo);
		
	    JButton cancelBouton = new JButton("Cancel");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	// ends the dialog and stops the program:
	    	System.exit(ABORT);
	      }      
	    });
	    
	    JButton okBouton = new JButton("Ok");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  // recover elements:
	    	  user = userJtf.getText();
	    	  passwd = passwdJtf.getText();
	    	  host = (String)hostCombo.getSelectedItem();
	    	  dataBase = (String)dbCombo.getSelectedItem();
	    	  //type = (String)typeCombo.getSelectedItem();
	    		    	
	    	  // ends dialog by making the box invisible
	    	  setVisible(false);
	      }
	    });
	    
	    controlPan.add(cancelBouton);
	    controlPan.add(okBouton);
	    
	    this.getContentPane().add(selectionPan, BorderLayout.CENTER);
	    this.getContentPane().add(controlPan, BorderLayout.SOUTH);
	}
	
	public String[] showConnectionDialog(){
		this.setVisible(true);
		String[] infoConn = new String[] {this.user, this.passwd, this.host, this.dataBase};
		return infoConn;
	}

}
