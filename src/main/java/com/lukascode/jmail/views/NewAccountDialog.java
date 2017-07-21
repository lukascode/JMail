package com.lukascode.jmail.views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JCheckBox;
import java.awt.Color;

public class NewAccountDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JButton cancelButton;
	private JButton okButton;
	private JTextField textFieldEmail;
	private JPasswordField passwordField;
	private JTextField textFieldSmtpName;
	private JTextField textFieldSmtpPort;
	private JTextField textFieldImapName;
	private JTextField textFieldImapPort;
	private JLabel labelError;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			NewAccountDialog dialog = new NewAccountDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public NewAccountDialog() {
		initComponents();
		setEvents();
	}
	
	public void initComponents() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(NewAccountDialog.class.getResource("/icons/email.png")));
		setTitle("Add new account");
		setModal(true);
		setBounds(100, 100, 866, 558);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel labelUser = new JLabel("User");
		labelUser.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel labelEmail = new JLabel("Email:");
		
		textFieldEmail = new JTextField();
		textFieldEmail.setColumns(10);
		
		JLabel labelPassword = new JLabel("Password:");
		
		passwordField = new JPasswordField();
		
		JCheckBox chckbxSavePassword = new JCheckBox("Save Password");
		
		JLabel lblSmtpServer = new JLabel("SMTP Server");
		lblSmtpServer.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel labelSmtpName = new JLabel("Server Name:");
		
		textFieldSmtpName = new JTextField();
		textFieldSmtpName.setColumns(10);
		
		JLabel labelSmtpPort = new JLabel("Port:");
		
		textFieldSmtpPort = new JTextField();
		textFieldSmtpPort.setText("465");
		textFieldSmtpPort.setColumns(10);
		
		JLabel lblDefault = new JLabel("Default: 465");
		
		JCheckBox checkBoxSmtpSSL = new JCheckBox("Use secure connection (SSL)");
		checkBoxSmtpSSL.setSelected(true);
		
		JCheckBox checkBoxSmtpTls = new JCheckBox("Use secure authentication");
		
		JLabel lblImapServer = new JLabel("IMAP Server");
		lblImapServer.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel labelImapName = new JLabel("Server Name:");
		
		textFieldImapName = new JTextField();
		textFieldImapName.setColumns(10);
		
		JLabel labelImapPort = new JLabel("Port:");
		
		textFieldImapPort = new JTextField();
		textFieldImapPort.setText("993");
		textFieldImapPort.setColumns(10);
		
		JLabel lblDefault_1 = new JLabel("Default: 993");
		
		JCheckBox checkBoxImapSSL = new JCheckBox("Use secure connection (SSL)");
		checkBoxImapSSL.setSelected(true);
		
		JCheckBox checkBoxImapTls = new JCheckBox("Use secure authentication");
		
		labelError = new JLabel("");
		labelError.setForeground(Color.RED);
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(checkBoxImapTls, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBoxImapSSL, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE)
						.addComponent(checkBoxSmtpTls)
						.addComponent(labelUser)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(labelEmail)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldEmail, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(labelPassword)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE))
						.addComponent(chckbxSavePassword)
						.addComponent(lblSmtpServer)
						.addComponent(checkBoxSmtpSSL)
						.addComponent(lblImapServer)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(labelImapName)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldImapName))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(labelSmtpName)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldSmtpName, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(labelSmtpPort)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldSmtpPort, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPanel.createSequentialGroup()
									.addComponent(labelImapPort)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(textFieldImapPort, 0, 0, Short.MAX_VALUE)))
							.addGap(18)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDefault_1)
								.addComponent(lblDefault)))
						.addComponent(labelError))
					.addContainerGap(276, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(labelUser)
					.addGap(20)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelEmail)
						.addComponent(textFieldEmail, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelPassword)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxSavePassword)
					.addGap(18)
					.addComponent(lblSmtpServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelSmtpName)
						.addComponent(textFieldSmtpName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelSmtpPort)
						.addComponent(textFieldSmtpPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDefault))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(checkBoxSmtpSSL)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(checkBoxSmtpTls)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblImapServer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(labelImapName)
						.addComponent(textFieldImapName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(labelImapPort)
						.addComponent(textFieldImapPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDefault_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(checkBoxImapSSL)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(checkBoxImapTls)
					.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
					.addComponent(labelError)
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				cancelButton = new JButton("Cancel");
				
				
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	public void setEvents() {
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewAccountDialog.this.dispose();
			}
		});
		okButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				labelError.setText(labelError.getText() + "Error ");
			}
		});
	}
}
