package com.lukascode.jmail.views.helpers;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import com.lukascode.jmail.common.Main;

public class Resources {
	
	public static Image getImage(String path) {
		InputStream stream = getStream(path);
		Image image = null;
		try {
			if(stream != null)
				image = ImageIO.read(stream);
		} catch (IOException e) {
			Main.logger.log(Level.SEVERE, "Resources.getImage IOException", e);
		}
		return image;
	}
	
	public static Icon getIcon(String path) {
		InputStream stream = getStream(path);
		if(stream == null) stream = Resources.class.getResourceAsStream("/resources" + path);
		ImageIcon icon = null;
		try {
			if(stream != null) {
				Image img = ImageIO.read(stream);
				icon = new ImageIcon(img);
			} else icon = new ImageIcon();
		} catch (IOException e) {
			Main.logger.log(Level.SEVERE, "Resources.getIcon IOException", e);
		}
		return icon;
	}
	
	private static InputStream getStream(String path) {
		InputStream stream = Resources.class.getResourceAsStream(path);
		if(stream == null) stream = Resources.class.getResourceAsStream("/resources" + path);
		return stream;
	}
	
}
