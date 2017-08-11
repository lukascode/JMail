package com.lukascode.jmail.views.helpers;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.lukascode.jmail.common.dao.EMessage;

public class MessagesTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<EMessage> messages;
	
	public MessagesTableModel(List<EMessage> messages) {
		this.messages = messages;
	}
	
	public MessagesTableModel() {
		messages = new ArrayList<EMessage>();
	}
	
	public void addRow(EMessage m) {
		messages.add(m);
		fireTableRowsInserted(messages.size()-1, messages.size()-1);
	}

	@Override
	public int getRowCount() {
		return messages.size();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "From";
		case 1: return "To";
		case 2: return "Subject";
		case 3: return "Date";
		}
		return "";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EMessage message = messages.get(rowIndex);
		switch(columnIndex) {
			case 0: 
				return message.getFrom();
			
			case 1: 
				return message.getTo();
			
			case 2: 
				return message.getSubject();
			
			case 3: 
				return message.getDate().toString();
		}
		return null;
	}

}
