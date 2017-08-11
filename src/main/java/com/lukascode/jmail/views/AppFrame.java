package com.lukascode.jmail.views;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import com.lukascode.jmail.common.Main;
import com.lukascode.jmail.views.helpers.Resources;

public class AppFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tabbedPaneAccounts;
	private JMenuItem mntmAbout;
	private JMenuItem mntmFinish;
	private JMenuItem mntmShowStartWindow;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		create();
	}
	
	public static AppFrame create() {
		AppFrame frame = null;
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Throwable e) {
			Main.logger.log(Level.SEVERE, e.getMessage());
			System.exit(ERROR);
		}
		frame = new AppFrame();
		frame.setVisible(true);
		return frame;
	}

	/**
	 * Create the frame.
	 */
	public AppFrame() {
		initComponents();
		setEvents();
	}
	
	public void initComponents() {
		setIconImage(Resources.getImage("/icons/email.png"));
		setTitle("JMail Client");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1149, 751);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		
		mntmShowStartWindow = new JMenuItem("Show start window");
		
		mntmShowStartWindow.setIcon(Resources.getIcon("/icons/route.png"));
		menuFile.add(mntmShowStartWindow);
		
		mntmFinish = new JMenuItem("Exit");
		
		mntmFinish.setIcon(Resources.getIcon("/icons/exit.png"));
		menuFile.add(mntmFinish);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		
		mntmAbout.setIcon(Resources.getIcon("/icons/about.png"));
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		tabbedPaneAccounts = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPaneAccounts, GroupLayout.DEFAULT_SIZE, 1030, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPaneAccounts, GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void setEvents() {
		
		mntmShowStartWindow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(StartFrame.instance != null) {
					StartFrame.instance.toFront();
					StartFrame.instance.repaint();
				} else {
					StartFrame.create();
				}
			}
		});
		
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AboutDialog.create().setLocationRelativeTo(AppFrame.this);
			}
		});
		
		mntmFinish.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AppFrame.this.dispose();
			}
		});
	}
	
	public void addTab(JPanel panel, String title) {
		tabbedPaneAccounts.addTab(title, panel);
		tabbedPaneAccounts.setBackground(Color.BLUE);
	}
	
	public void removeTab(JPanel panel) {
		tabbedPaneAccounts.remove(panel);
		if(tabbedPaneAccounts.getTabCount() == 0) {
			AppFrame.this.dispose();
		}
	}
	
	
}
