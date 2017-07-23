package com.lukascode.jmail.views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;

import com.lukascode.jmail.common.AccountConfiguration;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;

public class MailContentViewerPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	
	private AccountConfiguration ac;
	private JTree tree;
	private JButton btnDelete;
	private JButton btnReply;
	private JTextField textField;
	
	public MailContentViewerPanel(AccountConfiguration ac) {
		this.ac = ac;
		initComponents();
		setEvents();
	}
	
	public void initComponents() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		gbl_panelMenuMessages.columnWidths = new int[]{0, 0, 0, 0, 0};
		gbl_panelMenuMessages.rowHeights = new int[]{0, 0, 0, 0, 0};
		gbl_panelMenuMessages.columnWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_panelMenuMessages.rowWeights = new double[]{0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
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
		label.setForeground(new Color(46, 139, 87));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		JButton btnNewMessage = new JButton("New message");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JLabel lblFilter = new JLabel("Filter:");
		GroupLayout gl_panelMenu = new GroupLayout(panelMenu);
		gl_panelMenu.setHorizontalGroup(
			gl_panelMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelMenu.createSequentialGroup()
							.addGap(10)
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
							.addComponent(lblFilter)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, 326, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelMenu.createSequentialGroup()
							.addComponent(lblNumber)
							.addPreferredGap(ComponentPlacement.RELATED, 284, Short.MAX_VALUE)
							.addComponent(btnNewMessage)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnShow)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReply)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnDelete)))
					.addContainerGap())
		);
		gl_panelMenu.setVerticalGroup(
			gl_panelMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMenu.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panelMenu.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumber)
						.addComponent(btnDelete)
						.addComponent(btnReply)
						.addComponent(btnShow)
						.addComponent(btnNewMessage))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelMenu.createParallelGroup(Alignment.LEADING)
						.addComponent(label)
						.addGroup(gl_panelMenu.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblFilter)))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		panelMenu.setLayout(gl_panelMenu);
		
		JScrollPane scrollPaneMessages = new JScrollPane();
		splitPane.setRightComponent(scrollPaneMessages);
		
		JPanel panelMessages = new JPanel();
		scrollPaneMessages.setViewportView(panelMessages);
		
		JLabel lblMessages = new JLabel("Messages");
		GroupLayout gl_panelMessages = new GroupLayout(panelMessages);
		gl_panelMessages.setHorizontalGroup(
			gl_panelMessages.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMessages.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMessages)
					.addContainerGap(762, Short.MAX_VALUE))
		);
		gl_panelMessages.setVerticalGroup(
			gl_panelMessages.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelMessages.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMessages)
					.addContainerGap(485, Short.MAX_VALUE))
		);
		panelMessages.setLayout(gl_panelMessages);
		
		JScrollPane scrollPaneFolderTree = new JScrollPane();
		scrollPaneFolderTree.setMinimumSize(new Dimension(180, 10));
		splitPaneFoldersContent.setLeftComponent(scrollPaneFolderTree);
		
		JPanel panelFolderTree = new JPanel();
		scrollPaneFolderTree.setViewportView(panelFolderTree);
		panelFolderTree.setLayout(new BorderLayout(0, 0));
		
		DefaultMutableTreeNode top = new DefaultMutableTreeNode(ac.getEmail());
		tree = new JTree(top);
		tree.setRowHeight(25);
		tree.setBackground(UIManager.getColor("Button.background"));
		panelFolderTree.add(tree, BorderLayout.CENTER);
		
		JLabel lblFolders = new JLabel("Folders");
		lblFolders.setFont(new Font("Tahoma", Font.BOLD, 18));
		panelFolderTree.add(lblFolders, BorderLayout.NORTH);
	}
	
	public void setEvents() {
		
	}
}
