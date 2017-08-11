package com.lukascode.jmail.views;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import com.lukascode.jmail.common.AccountConfiguration;
import com.lukascode.jmail.common.MailUtils;
import com.lukascode.jmail.common.Main;
import com.lukascode.jmail.common.dao.AccountConfigurationDAO;
import com.lukascode.jmail.common.dao.JMailDatabaseCreator;
import com.lukascode.jmail.views.helpers.AccountsTableModel;
import com.lukascode.jmail.views.helpers.Resources;
import com.lukascode.jmail.views.helpers.SimpleLinkLabel;
import com.lukascode.jmail.views.helpers.WorkerDialog;


public class StartFrame extends JFrame {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	
	private AppFrame appFrame = null;
	
	public static StartFrame instance = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		create();
	}
	
	public static void create() {
		if(instance != null) return;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					instance = new StartFrame();
					instance.setVisible(true);
					instance.addWindowListener(new WindowAdapter() {
						@Override
						public void windowClosed(WindowEvent ev) {
							StartFrame.instance = null;
						}
					});
				} catch (Exception e) {
					Main.logger.log(Level.SEVERE, e.getMessage());
					throw e;
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartFrame() {
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				Main.logger.log(Level.SEVERE, "JMail Error", e);
				if(e.getMessage() != null) {
					JOptionPane.showMessageDialog(StartFrame.this, "[" + e.getClass().getSimpleName() + "] " + e.getMessage(), "JMail Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					JOptionPane.showMessageDialog(StartFrame.this, e.toString(), "JMail Error", JOptionPane.ERROR_MESSAGE);
				}
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					Main.logger.log(Level.SEVERE, "Thread.sleep error", e1);
				} finally {
					System.exit(ERROR);
				}
			}
		});
		
		initComponents();
		setEvents();
	}
	
	private void initComponents() {	
		setResizable(false);
		setIconImage(Resources.getImage("/icons/email.png"));
		setTitle(" JMail Client");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 850, 542);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		menuItemImportSettings = new JMenuItem("Import Settings");
		
		menuItemImportSettings.setIcon(Resources.getIcon("/icons/import.png"));
		menuFile.add(menuItemImportSettings);
		
		menuItemExit = new JMenuItem("Exit");
		menuItemExit.setIcon(Resources.getIcon("/icons/exit.png"));

		menuFile.add(menuItemExit);
		
		menuHelp = new JMenu("Help");
		menuBar.add(menuHelp);
		
		menuItemAbout = new JMenuItem("About");
		
		menuItemAbout.setIcon(Resources.getIcon("/icons/about.png"));
		menuHelp.add(menuItemAbout);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		labelConfigureNewAccount = new JLabel("<HTML><U>Configure New Account</U></HTML>");
		labelConfigureNewAccount.setToolTipText("");
		labelConfigureNewAccount.setForeground(Color.DARK_GRAY);
		
		accountActionLabel = new JLabel("Choose an Account");
		accountActionLabel.setForeground(new Color(0, 51, 204));
		
		JScrollPane accountsScrollPane = new JScrollPane();
		accountsScrollPane.setBackground(Color.WHITE);
		
		buttonRemove = new JButton("Remove");
		
		buttonLogin = new JButton("Login");
		
		buttonEdit = new JButton("Edit");
		
		labelEmailLogo = new JLabel("");
		labelEmailLogo.setIcon(Resources.getIcon("/icons/email_logo2.png"));
		
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
		accountsTable.setFillsViewportHeight(true);
		accountsTable.setBackground(Color.WHITE);
		accountsTable.setSelectionBackground(new Color(50, 228, 181));
		accountsTable.setRowHeight(22);
		accountsTable.setShowHorizontalLines(false);
		accountsTable.setShowVerticalLines(false);
		accountsTable.setModel(new AccountsTableModel
				(new AccountConfigurationDAO().getAccounts()));
		accountsScrollPane.setViewportView(accountsTable);
		contentPane.setLayout(gl_contentPane);
	}
	private void setEvents() {
		
		menuItemAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog.create().setLocationRelativeTo(StartFrame.this);
			}
		});
		
		menuItemImportSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					String filepath = chooser.getSelectedFile().getAbsolutePath();
					if(filepath != null) {
						JMailDatabaseCreator.createInstance(filepath);
						accountsTable.setModel(new AccountsTableModel
								(new AccountConfigurationDAO().getAccounts()));
					}
				}
			}
		});
		
		buttonLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selected = accountsTable.getSelectedRow();
				if(selected > -1) {
					TableModel model = accountsTable.getModel();
					AccountsTableModel m = (AccountsTableModel)model;
					AccountConfiguration ac = m.getRow(selected);
					if(!ac.isSavePassword()) {
					      GetPassDialog dialog = GetPassDialog.create(StartFrame.this);
					      if(dialog.OK_CLICKED) {
					    	  ac.setPassword(dialog.getPassword());
					      } else return;
					}
					
					//check credentials
					WorkerDialog wd = new WorkerDialog(StartFrame.this, "Authorization...") {
						@Override
						protected Object doInBackground() {
							return new MailUtils(ac).credentialsCorrect();
						}
					};
					wd.execute();
					boolean result = (boolean) wd.getValue();
					
					if(result) {
						if(appFrame == null) {
							appFrame = AppFrame.create();
							appFrame.addWindowListener(new WindowAdapter() {
								@Override
								public void windowClosed(WindowEvent e) {
									appFrame = null;
								}
							});
						}
						ac.setLastLogin(LocalDateTime.now());
						new AccountConfigurationDAO().update(ac);
						m.refresh();
						appFrame.addTab(new MailContentViewerPanel(ac, appFrame), ac.getEmail());
					} else {
						JOptionPane.showMessageDialog(StartFrame.this, "Logon attempt failed", 
								"Authorization failed", JOptionPane.ERROR_MESSAGE);
					}
				}
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
				AccountFormDialog ad = AccountFormDialog.create(null, StartFrame.this);
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
					AccountFormDialog ad = AccountFormDialog.create(ac, StartFrame.this);
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

