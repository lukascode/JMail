package com.lukascode.jmail.views.helpers;

import javax.swing.table.AbstractTableModel;

public class AccountsTableModel extends AbstractTableModel {

	private Object[][] values;
	
	public AccountsTableModel() {
		values = new String[4][2];
		values[0][0] = "***REMOVED***";
		values[0][1] = "12.08.2017 16:40";
		values[1][0] = "lukasz.sakowicz@lukascodeweb.pl";
		values[1][1] = "12.08.2017 16:40";
		values[2][0] = "lukato@gmail.com";
		values[2][1] = "12.08.2017 16:40";
	}
	
	@Override
	public int getRowCount() {
		//return values.length;
		return 20;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		//return values[rowIndex][columnIndex];
		if(columnIndex == 0) return "somefunnyemail";
		return "date";
	}
	
	@Override
	public String getColumnName(int column) {
		return (column == 0)?"email":"last login";
	}

}
