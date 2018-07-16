package org.centenaire.main.editwindow;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class NumberInfoPanel extends JPanel {
	private JCheckBox checkBox;
	private JFormattedTextField jtf;
	
	public NumberInfoPanel(String type, boolean bool, String value){
		super();
		
		int height = 15;
		int labelWidth = 100;
		
		JLabel label = new JLabel(type+":");
		label.setPreferredSize( new Dimension(labelWidth,height));
		checkBox = new JCheckBox();
		checkBox.setSelected(bool);
		checkBox.setPreferredSize( new Dimension(20,height));
		jtf = new JFormattedTextField(NumberFormat.getIntegerInstance(Locale.UK));
		if (bool) {
			jtf.setText(value);
		}
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
