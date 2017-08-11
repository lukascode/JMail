package com.lukascode.jmail.views.helpers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import com.lukascode.jmail.common.AccountConfiguration;
import com.lukascode.jmail.common.dao.AccountConfigurationDAO;

public class AccountsTableModel extends AbstractTableModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static AccountConfigurationDAO acdao = new AccountConfigurationDAO();
	
	private List<AccountConfiguration> accounts;
	
	public void sort() {
		accounts.sort(new Comparator<AccountConfiguration>() {
			@Override
			public int compare(AccountConfiguration o1, AccountConfiguration o2) {
				return o2.getLastLogin().compareTo(o1.getLastLogin());
			}
		});
	}
	
	public AccountsTableModel(List<AccountConfiguration> accounts) {
		this.accounts = accounts;
		sort();
	}
	
	public AccountsTableModel() {
		accounts = new ArrayList<>();
	}
	
	@Override
	public int getRowCount() {
		return accounts.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AccountConfiguration ac = accounts.get(rowIndex);
		if(columnIndex == 0) return ac.getEmail();
		return ac.getLastLoginFormated();
	}
	
	@Override
	public String getColumnName(int column) {
		return (column == 0)?"Email":"Last login";
	}
	
	public AccountConfiguration getRow(int idx) {
		return accounts.get(idx);
	}
	
	public void addRow(AccountConfiguration ac) {
		if(acdao.insert(ac)) {
			accounts.add(ac);
			fireTableRowsInserted(0, getRowCount());
		}
	}
	
	public void updateRow(AccountConfiguration ac, int idx) {
		if(acdao.update(ac)) {
			accounts.set(idx, ac);
			fireTableRowsUpdated(idx, idx);
		}
	}
	
	public void refresh() {
		fireTableDataChanged();
		sort();
	}
	
	public void removeRow(int row) {
		AccountConfiguration ac = accounts.get(row);
		if(acdao.delete(ac.getId())) {
			accounts.remove(row);
		}
		fireTableRowsDeleted(row, row);
	}

}
