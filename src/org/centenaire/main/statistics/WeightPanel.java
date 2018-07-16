package org.centenaire.main.statistics;

import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.centenaire.entity.TagLike;
import org.centenaire.general.GeneralController;

public class WeightPanel extends JPanel implements ListSelectionListener{
	private JLabel totalWeight;
	
	public WeightPanel(){
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		totalWeight = new JLabel("0");
		this.add(new JLabel("Total weight:"));
		this.add(totalWeight);
	}

	@Override
	public void valueChanged(ListSelectionEvent event) {
		JList<TagLike> westList = (JList<TagLike>)event.getSource();
		List<TagLike> listCurrentSemester = westList.getSelectedValuesList();
		
		float weight = GeneralController.getInstance().getExamsDao().getTotalWeight(listCurrentSemester);
		totalWeight.setText(Float.toString(weight));
		
	}
}
