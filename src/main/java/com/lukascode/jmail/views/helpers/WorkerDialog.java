package com.lukascode.jmail.views.helpers;

import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Window;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

public abstract class WorkerDialog extends SwingWorker {

	public WorkerDialog(Window owner, String txt) {
	      final JDialog dialog = new JDialog(owner, "Processing", ModalityType.APPLICATION_MODAL);

	      this.addPropertyChangeListener(new PropertyChangeListener() {

	         @Override
	         public void propertyChange(PropertyChangeEvent evt) {
	            if (evt.getPropertyName().equals("state")) {
	               if (evt.getNewValue() == SwingWorker.StateValue.DONE) {
	                  dialog.dispose();
	               }
	            }
	         }
	      });
	      this.execute();

	      JProgressBar progressBar = new JProgressBar();
	      progressBar.setIndeterminate(true);
	      JPanel panel = new JPanel(new BorderLayout());
	      panel.add(progressBar, BorderLayout.CENTER);
	      panel.add(new JLabel(txt), BorderLayout.PAGE_START);
	      dialog.add(panel);
	      dialog.setSize(new Dimension(300, 100));
	      dialog.setLocationRelativeTo(owner);
	      dialog.setVisible(true);
	}
	
	@Override
	protected abstract Object doInBackground();
	
}
