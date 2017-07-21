package com.lukascode.jmail.views.helpers;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;

public abstract class SimpleLinkLabel extends MouseAdapter {
	private Color _static, hovered;
	private JLabel label;
	public SimpleLinkLabel(JLabel label, Color _static, Color hovered) {
		this._static = _static;
		this.hovered = hovered;
		this.label = label;
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		label.setForeground(new Color(64, 144, 237));
		label.setCursor(new Cursor(Cursor.HAND_CURSOR));
	}
	@Override
	public void mouseExited(MouseEvent e) {
		label.setForeground(Color.DARK_GRAY);
		label.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}