package com.lukascode.jmail.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import com.lukascode.jmail.common.AccountConfiguration;
import com.lukascode.jmail.common.Main;
import com.lukascode.jmail.common.dao.AccountConfigurationDAO;
import com.lukascode.jmail.views.helpers.AccountsTableModel;
import com.lukascode.jmail.views.helpers.SimpleLinkLabel;


public class StartFrame extends JFrame {

	private JPanel contentPane;
	private JLabel labelConfigureNewAccount;
	private JLabel accountActionLabel;
	private JTable accountsTable;
	private JButton buttonRemove;
	private JMenuBar menuBar;
	private JMenu menuFile;
	private JMenu menuHelp;
	private JMenuItem menuItemImportSettings;
	private JMenuItem menuItemExit;
	private JMenuItem menuItemAbout;
	private JButton buttonLogin;
	private JLabel labelEmailLogo;
	private JButton buttonEdit;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = new StartFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		setResizable(false);
		initComponents();
		setEvents();
	}
	
	private void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(StartFrame.class.getResource("/icons/email.png")));
		setTitle(" JMail Client");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 542);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuItemImportSettings = new JMenuItem("Import Settings");
		menuFile.add(menuItemImportSettings);
		
		menuItemExit = new JMenuItem("Exit");
		
		menuFile.add(menuItemExit);
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		menuItemAbout = new JMenuItem("About");
		menuHelp.add(menuItemAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		labelConfigureNewAccount = new JLabel("<HTML><U>Configure New Account</U></HTML>");
		labelConfigureNewAccount.setToolTipText("");
		labelConfigureNewAccount.setForeground(Color.DARK_GRAY);
		
		accountActionLabel = new JLabel("Choose an Account");
		accountActionLabel.setForeground(new Color(0, 51, 204));
		
		JScrollPane accountsScrollPane = new JScrollPane();
		
		buttonRemove = new JButton("Remove");
		
		buttonLogin = new JButton("Login");
		
		buttonEdit = new JButton("Edit");
		
		labelEmailLogo = new JLabel("");
		labelEmailLogo.setIcon(new ImageIcon(StartFrame.class.getResource("/icons/email_logo2.png")));
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(172)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(accountsScrollPane, GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(accountActionLabel)
							.addPreferredGap(ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
							.addComponent(buttonLogin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonEdit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRemove)))
					.addGap(163))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(320)
					.addComponent(labelConfigureNewAccount, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(338, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(347)
					.addComponent(labelEmailLogo)
					.addContainerGap(359, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(labelEmailLogo)
					.addPreferredGap(ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(buttonRemove)
						.addComponent(buttonEdit)
						.addComponent(buttonLogin)
						.addComponent(accountActionLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(accountsScrollPane, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(labelConfigureNewAccount)
					.addGap(39))
		);
		
		accountsTable = new JTable();
		accountsTable.setBackground(Color.WHITE);
		accountsTable.setRowHeight(22);
		accountsTable.setShowHorizontalLines(false);
		accountsTable.setShowVerticalLines(false);
		accountsTable.setModel(new AccountsTableModel(new AccountConfigurationDAO().getAccounts()));
		accountsScrollPane.setViewportView(accountsTable);
		contentPane.setLayout(gl_contentPane);
	}
	private void setEvents() {
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new AppFrame().setVisible(true);
			}
		});
		
		menuItemExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartFrame.this.dispose();
			}
		});
		
		buttonRemove.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = accountsTable.getSelectedRow();
				if(selected > -1) {
					TableModel model = accountsTable.getModel();
					AccountsTableModel m = (AccountsTableModel)model;
					m.removeRow(selected);
				}
			}
		});
		
		labelConfigureNewAccount.addMouseListener(
				new SimpleLinkLabel(labelConfigureNewAccount, Color.DARK_GRAY, new Color(64, 144, 237)) {
			@Override
			public void mouseClicked(MouseEvent e) {
				AccountDialogForm ad = AccountDialogForm.create(null);
				AccountConfiguration ac = ad.getResult();
				if(ac != null) {
					TableModel model = accountsTable.getModel();
					AccountsTableModel m = (AccountsTableModel)model;
					m.addRow(ac);
				}
			}});
		
		buttonEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = accountsTable.getSelectedRow();
				if(selected > -1) {
					TableModel model = accountsTable.getModel();
					AccountsTableModel m = (AccountsTableModel)model;
					AccountConfiguration ac = m.getRow(selected);
					AccountDialogForm ad = AccountDialogForm.create(ac);
					AccountConfiguration acUpdated = ad.getResult();
					if(acUpdated != null) {
						acUpdated.setId(ac.getId());
						m.updateRow(acUpdated, selected);
					}
				}
			}
		});
	}
}

