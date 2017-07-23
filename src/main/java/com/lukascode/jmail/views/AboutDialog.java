package com.lukascode.jmail.views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import com.lukascode.jmail.views.helpers.SimpleLinkLabel;
import javax.swing.UIManager;

public class AboutDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JLabel labelGithubLink;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		create();
	}
	
	public static AboutDialog create() {
		AboutDialog dialog = null;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		try {
			dialog = new AboutDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dialog;
	}

	/**
	 * Create the dialog.
	 */
	public AboutDialog() {
		setTitle("About");
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(AboutDialog.class.getResource("/icons/email.png")));
		setBounds(100, 100, 450, 217);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblJmailClient = new JLabel("JMail Client");
		lblJmailClient.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JLabel lblEmailClientWritten = new JLabel("Email Client written in Java using JavaMail and Swing UI");
		
		JLabel lblAuthorLukascode = new JLabel("Author: lukascode");
		
		labelGithubLink = new JLabel("<HTML><U>https://github.com/lukascode/JMail</U></HTML>");
		labelGithubLink.addMouseListener(new SimpleLinkLabel(labelGithubLink, Color.DARK_GRAY, new Color(64, 144, 237)) {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Desktop.isDesktopSupported()) {
					try {
						URI uri = new URI("https://github.com/lukascode/JMail");
						Desktop.getDesktop().browse(uri);
					} catch(Exception ex) {}
				}
			}
		});
		
		JLabel lblDate = new JLabel("Date: 07.2017");
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(AboutDialog.class.getResource("/icons/github-logo.png")));
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblJmailClient)
						.addComponent(lblEmailClientWritten)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblAuthorLukascode)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDate))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(labelGithubLink)))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblJmailClient)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblEmailClientWritten)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAuthorLukascode)
						.addComponent(lblDate))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(label)
						.addComponent(labelGithubLink))
					.addContainerGap(121, Short.MAX_VALUE))
		);
		contentPanel.setLayout(gl_contentPanel);
	}
}
