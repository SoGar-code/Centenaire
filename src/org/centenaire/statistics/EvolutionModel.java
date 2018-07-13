package org.centenaire.statistics;

import java.util.LinkedList;

import org.centenaire.general.Entity;
import org.centenaire.general.ListTableModel;

public class EvolutionModel extends ListTableModel {

	public EvolutionModel(Class[] listClass, String[] title, LinkedList<Entity> data) {
		super(listClass, title, data);
	}

	@Override
	public boolean isCellEditable(int row, int col){
		return false;
	}
	
	@Override
	public Object getValueAt(int row, int column){
		// Two active columns:
		switch (column){
			case 0: 		// column == 0, exam
				return getData().get(row).getEntry(0);
			case 1:			// column == 1, mark
				return getData().get(row).getEntry(2);
			default:
				return null;
		}
		
	}
}
