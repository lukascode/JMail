package com.lukascode.jmail.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

import javax.mail.MessagingException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.TableModel;

import com.lukascode.jmail.common.AccountConfiguration;
import com.lukascode.jmail.common.JMailException;
import com.lukascode.jmail.common.MailUtils;
import com.lukascode.jmail.common.Main;
import com.lukascode.jmail.common.StringTree;
import com.lukascode.jmail.common.dao.EMessage;
import com.lukascode.jmail.views.helpers.CellRenderer;
import com.lukascode.jmail.views.helpers.FolderTreeModel;
import com.lukascode.jmail.views.helpers.MessagesTableModel;
import com.lukascode.jmail.views.helpers.WorkerDialog;

public class MailContentViewerPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Create the panel.
	 */

	private AccountConfiguration ac;
	private MailUtils mailUtils;
	private JTree tree;
	private JButton btnDelete;
	private JButton btnReply;
	private JTextField textField;
	private JTable tableMessages;
	private AppFrame appFrame;

	private HashMap<String, List<EMessage>> messagesSets;
	private JButton btnLogout;
	private JCheckBox chckbxSelectdeselectAll;

	private List<EMessage> getMessagesSmart(String folder) {
		if (messagesSets.containsKey(folder)) {
			return messagesSets.get(folder);
		}
		List<EMessage> msgs = mailUtils.getMessages(folder);
		messagesSets.put(folder, msgs);
		return msgs;
	}

	public MailContentViewerPanel(AccountConfiguration ac, AppFrame appFrame) {
		this.appFrame = appFrame;
		messagesSets = new HashMap<>();
		this.ac = ac;
		mailUtils = new MailUtils(ac);
		initComponents();
		setEvents();
	}

	public void initComponents() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		JSplitPane splitPaneFoldersContent = new JSplitPane();
		GridBagConstraints gbc_splitPaneFoldersContent = new GridBagConstraints();
		gbc_splitPaneFoldersContent.gridwidth = 6;
		gbc_splitPaneFoldersContent.gridheight = 8;
		gbc_splitPaneFoldersContent.fill = GridBagConstraints.BOTH;
		gbc_splitPaneFoldersContent.gridx = 0;
		gbc_splitPaneFoldersContent.gridy = 0;
		add(splitPaneFoldersContent, gbc_splitPaneFoldersContent);

		JPanel panelMenuMessages = new JPanel();
		splitPaneFoldersContent.setRightComponent(panelMenuMessages);
		GridBagLayout gbl_panelMenuMessages = new GridBagLayout();
		gbl_panelMenuMessages.columnWidths = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelMenuMessages.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panelMenuMessages.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_panelMenuMessages.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelMenuMessages.setLayout(gbl_panelMenuMessages);

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GridBagConstraints gbc_splitPane = new GridBagConstraints();
		gbc_splitPane.gridheight = 4;
		gbc_splitPane.gridwidth = 4;
		gbc_splitPane.fill = GridBagConstraints.BOTH;
		gbc_splitPane.gridx = 0;
		gbc_splitPane.gridy = 0;
		panelMenuMessages.add(splitPane, gbc_splitPane);

		JPanel panelMenu = new JPanel();
		panelMenu.setMinimumSize(new Dimension(10, 100));
		splitPane.setLeftComponent(panelMenu);

		btnDelete = new JButton("Delete");

		btnReply = new JButton("Reply");

		JButton btnShow = new JButton("Show");

		JLabel lblNumber = new JLabel("Number of messages:");

		JLabel label = new JLabel("0");
		label.setForeground(new Color(0, 0, 0));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Tahoma", Font.BOLD, 18));

		JButton btnNewMessage = new JButton("New message");

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblFilter = new JLabel("Filter:");

		btnLogout = new JButton("Logout");

		chckbxSelectdeselectAll = new JCheckBox("select/deselect all");
		GroupLayout gl_panelMenu = new GroupLayout(panelMenu);
		gl_panelMenu.setHorizontalGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMenu.createSequentialGroup().addContainerGap()
						.addGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMenu.createSequentialGroup().addComponent(lblNumber)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(label, GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
								.addComponent(chckbxSelectdeselectAll))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panelMenu
								.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMenu.createSequentialGroup().addComponent(btnNewMessage)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnShow)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnReply)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnDelete)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(btnLogout))
								.addGroup(gl_panelMenu.createSequentialGroup().addComponent(lblFilter)
										.addPreferredGap(ComponentPlacement.RELATED).addComponent(textField,
												GroupLayout.PREFERRED_SIZE, 418, GroupLayout.PREFERRED_SIZE)))
						.addContainerGap()));
		gl_panelMenu.setVerticalGroup(gl_panelMenu.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelMenu.createSequentialGroup().addGap(8)
						.addGroup(gl_panelMenu.createParallelGroup(Alignment.BASELINE).addComponent(btnDelete)
								.addComponent(btnReply).addComponent(btnShow).addComponent(btnNewMessage)
								.addComponent(btnLogout).addComponent(lblNumber).addComponent(label))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelMenu.createParallelGroup(Alignment.BASELINE).addComponent(lblFilter)
										.addComponent(chckbxSelectdeselectAll))
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
										GroupLayout.PREFERRED_SIZE))
						.addContainerGap(21, Short.MAX_VALUE)));
		panelMenu.setLayout(gl_panelMenu);

		JScrollPane scrollPaneMessages = new JScrollPane();
		scrollPaneMessages.setFont(new Font("Tahoma", Font.PLAIN, 16));
		splitPane.setRightComponent(scrollPaneMessages);

		tableMessages = new JTable();
		tableMessages.setSelectionBackground(Color.DARK_GRAY);
		tableMessages.setShowVerticalLines(false);
		tableMessages.setRowHeight(40);
		tableMessages.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 18));
		tableMessages.getTableHeader().setBackground(new Color(66, 244, 200));
		scrollPaneMessages.setViewportView(tableMessages);

		JScrollPane scrollPaneFolderTree = new JScrollPane();
		scrollPaneFolderTree.setBackground(Color.WHITE);
		scrollPaneFolderTree.setMinimumSize(new Dimension(220, 10));
		splitPaneFoldersContent.setLeftComponent(scrollPaneFolderTree);

		JPanel panelFolderTree = new JPanel();
		panelFolderTree.setBackground(Color.WHITE);
		scrollPaneFolderTree.setViewportView(panelFolderTree);
		panelFolderTree.setLayout(new BorderLayout(0, 0));

		tree = new JTree();
		// Processing
		JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
		new WorkerDialog(topFrame, "Collecting information") {
			@Override
			protected Object doInBackground() {
				tree.setModel(new FolderTreeModel(mailUtils.getFolders()));
				tableMessages.setModel(new MessagesTableModel(mailUtils, getMessagesSmart("INBOX")));
				return null;
			}
		}.execute();
		// ----------------
		tree.setRowHeight(25);
		tree.setBackground(Color.WHITE);
		panelFolderTree.add(tree, BorderLayout.CENTER);

		JLabel lblFolders = new JLabel("Folders");
		lblFolders.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelFolderTree.add(lblFolders, BorderLayout.NORTH);
	}

	public void setEvents() {

		chckbxSelectdeselectAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableModel model = tableMessages.getModel();
				MessagesTableModel m = (MessagesTableModel) model;
				m.matchSelected(chckbxSelectdeselectAll.isSelected());
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableModel model = tableMessages.getModel();
				MessagesTableModel m = (MessagesTableModel) model;
				if(m.getSelectedCount() == 0) return;
				int n = JOptionPane.showConfirmDialog(MailContentViewerPanel.this,
						"This process will remove selected messages. Do you want to continue ?\n ", "Warning",
						JOptionPane.YES_NO_OPTION);
				if(n == 0) {
					JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(MailContentViewerPanel.this);
					new WorkerDialog(topFrame, "Removing selected messages...") {
						@Override
						protected Object doInBackground() {
							try {
								m.removeSelected();
							} catch (MessagingException e1) {
								Main.logger.log(Level.SEVERE, "Delete message error", e1);
								JOptionPane.showMessageDialog(MailContentViewerPanel.this, e1.getMessage(),
										"Deleting message error", JOptionPane.ERROR_MESSAGE);
							}
							return null;
						}
					}.execute();
				}
			}
		});

		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				appFrame.removeTab(MailContentViewerPanel.this);
			}
		});

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				StringTree.Node node = (StringTree.Node) tree.getLastSelectedPathComponent();
				if (node == null)
					return;
				if (node.children.size() > 0)
					return;
				String path = node.getPath().substring(1);
				JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(MailContentViewerPanel.this);
				new WorkerDialog(topFrame, "Collecting information") {
					@Override
					protected Object doInBackground() {
						try {
							tableMessages.setModel(new MessagesTableModel(mailUtils, getMessagesSmart(path)));
							tableMessages.getColumnModel().getColumn(2).setCellRenderer(new CellRenderer());
						} catch (JMailException e) {
							Main.logger.log(Level.SEVERE, e.getMessage());
							JOptionPane.showMessageDialog(topFrame, e.getMessage(), "Retriving messages error",
									JOptionPane.ERROR_MESSAGE);
						}
						return null;
					}
				}.execute();
			}
		});
	}
}
