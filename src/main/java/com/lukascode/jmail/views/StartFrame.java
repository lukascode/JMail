package com.lukascode.jmail.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import com.lukascode.jmail.common.dao.JMailDatabaseCreator;
import com.lukascode.jmail.views.helpers.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;


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
		
		
		JLabel logoLabel = new JLabel("");
		logoLabel.setIcon(new ImageIcon(StartFrame.class.getResource("/icons/email_logo_128.png")));
		
		JButton buttonEdit = new JButton("Edit");
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(172, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(accountsScrollPane, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(accountActionLabel)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(buttonLogin)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonEdit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(buttonRemove)))
					.addGap(163))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(335, Short.MAX_VALUE)
					.addComponent(labelConfigureNewAccount, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
					.addGap(323))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(334)
					.addComponent(logoLabel)
					.addContainerGap(372, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(logoLabel)
					.addGap(29)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(buttonRemove)
							.addComponent(buttonLogin)
							.addComponent(buttonEdit))
						.addComponent(accountActionLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(accountsScrollPane, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(labelConfigureNewAccount)
					.addGap(39))
		);
		
		accountsTable = new JTable();
		accountsTable.setBackground(Color.CYAN);
		accountsTable.setRowHeight(22);
		accountsTable.setShowHorizontalLines(false);
		accountsTable.setShowVerticalLines(false);
		accountsTable.setModel(new AccountsTableModel());
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
			//	new JMailDatabaseCreator("test.db");
			}
		});
		
		labelConfigureNewAccount.addMouseListener(
				new SimpleLinkLabel(labelConfigureNewAccount, Color.DARK_GRAY, new Color(64, 144, 237)) {
			@Override
			public void mouseClicked(MouseEvent e) {
				new NewAccountDialog().setVisible(true);
			}
		});
	}
}

