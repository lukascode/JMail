package com.lukascode.jmail.views.helpers;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;
import javax.swing.table.AbstractTableModel;

public class MessagesTableModel extends AbstractTableModel {
	
	private List<Message> messages;
	
	public MessagesTableModel(List<Message> messages) {
		this.messages = messages;
	}
	
	public MessagesTableModel() {
		messages = new ArrayList<Message>();
	}
	
	public void addRow(Message m) {
		messages.add(m);
		fireTableRowsInserted(messages.size()-1, messages.size()-1);
	}

	@Override
	public int getRowCount() {
		//return messages.size();
		return 20;
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
	//	Message message = messages.get(rowIndex);
		switch(columnIndex) {
			case 0: return "From";
			case 1: return "To";
			case 2: return "Subject";
			case 3: return "Date";
		}
		return null;
	}

}
