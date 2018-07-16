package org.centenaire.main.editwindow;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StringInfoPanel extends JPanel {
	private JCheckBox checkBox;
	private JTextField jtf;
	
	public StringInfoPanel(String type, boolean bool, String value){
		super();
		
		int height = 15;
		int labelWidth = 100;
		
		JLabel label = new JLabel(type+":");
		label.setPreferredSize( new Dimension(labelWidth,height));
		checkBox = new JCheckBox();
		checkBox.setSelected(bool);
		checkBox.setPreferredSize( new Dimension(20,height));
		jtf = new JTextField(value);
		jtf.setEnabled(bool);
		jtf.setPreferredSize(new Dimension(90,height));
		
		this.setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
		this.add(label);
		this.add(checkBox);
		this.add(jtf);
		
		class checkBoxListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				boolean cond = checkBox.isSelected();
				if (!cond){
					jtf.setText("");
				}
				jtf.setEnabled(checkBox.isSelected());
			}
			
		}
		
		checkBox.addActionListener(new checkBoxListener());
	}
	
	public boolean getBool(){
		return this.checkBox.isSelected();
	}
	
	public String getValue(){
		return this.jtf.getText();
	}
}
