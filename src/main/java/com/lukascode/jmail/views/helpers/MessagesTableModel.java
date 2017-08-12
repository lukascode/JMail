package com.lukascode.jmail.views.helpers;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.swing.table.AbstractTableModel;

import com.lukascode.jmail.common.MailUtils;
import com.lukascode.jmail.common.dao.EMessage;


public class MessagesTableModel extends AbstractTableModel {
	
	public static class EMessageTableElement {
		public EMessage message;
		public Boolean selected;
		EMessageTableElement(EMessage m, Boolean selected) {
			message = m;
			this.selected = selected;
		}
		EMessageTableElement(EMessage m) {
			message = m;
			this.selected = false;
		}
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<EMessageTableElement> messages;
	private MailUtils mu;
	
	public MessagesTableModel(List<EMessageTableElement> messages, MailUtils mu) {
		this.mu = mu;
		this.messages = messages;
	}
	
	public MessagesTableModel(MailUtils mu, List<EMessage> messages) {
		this.mu = mu;
		this.messages = new ArrayList<>();
		for(EMessage m: messages) this.messages.add(new EMessageTableElement(m, false));
	}
	
	public MessagesTableModel(MailUtils mu) {
		this.mu = mu;
		messages = new ArrayList<EMessageTableElement>();
	}
	
	public void addRow(EMessage m, boolean selected) {
		messages.add(new EMessageTableElement(m, selected));
		fireTableRowsInserted(messages.size()-1, messages.size()-1);
	}

	@Override
	public int getRowCount() {
		return messages.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}
	
	@Override
	public String getColumnName(int column) {
		switch(column) {
		case 0: return "Select";
		case 1: return "From";
		case 2: return "To";
		case 3: return "Subject";
		case 4: return "Date";
		}
		return "";
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		EMessageTableElement message = messages.get(rowIndex);
		switch(columnIndex) {
			case 0:
				return message.selected;
			case 1: 
				return message.message.getFrom();
			
			case 2: 
				return message.message.getTo();
			
			case 3: 
				return message.message.getSubject();
			
			case 4: 
				return message.message.getDate().toString();
		}
		return null;
	}
	
	@Override
	public Class getColumnClass(int columnIndex) {
		switch(columnIndex) {
			case 0:
				return Boolean.class;
			case 1:
				return String.class;
			case 2:
				return String.class;
			case 3:
				return String.class;
			case 4:
				return String.class;
		}
		return Object.class;
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if(column == 0) return true;
		return false;
	}
	
	@Override
	public void setValueAt(Object value, int row, int col) {
		if(col == 0) {
			EMessageTableElement m = messages.get(row);
			m.selected = (boolean) value;
			fireTableCellUpdated(row, col);
		}
	}
	
	public void removeSelected() throws MessagingException {
		for(int i=0; i<messages.size(); ++i) {
			if(messages.get(i).selected) {
				removeRow(i);
				--i;
			}
		}
	}
	
	public int getSelectedCount() {
		int result = 0;
		for(int i=0; i<messages.size(); ++i) {
			if(messages.get(i).selected) {
				++result;
			}
		}
		return result;
	}
	
	public void removeRow(int row) throws MessagingException {
		EMessageTableElement m = messages.remove(row);
		fireTableRowsDeleted(row, row);
		mu.removeMessage(m.message.getMessage());
	}
	
	public void matchSelected(boolean state) {
		for(int i=0; i<messages.size(); ++i) {
			messages.get(i).selected = state;
		}
		fireTableDataChanged();
	}

}
