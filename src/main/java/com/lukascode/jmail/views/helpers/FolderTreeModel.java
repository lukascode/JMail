package com.lukascode.jmail.views.helpers;

import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.lukascode.jmail.common.Main;
import com.lukascode.jmail.common.StringTree;
import com.lukascode.jmail.common.StringTree.Node;

public class FolderTreeModel implements TreeModel {
	
	private Folder folder;
	
	public FolderTreeModel(Folder folder) {
		this.folder = folder;
	}

	@Override
	public Object getRoot() {
		return folder;
	}

	@Override
	public Object getChild(Object parent, int index) {
		Folder child = null;
		Folder _parent = (Folder) parent;
		try {
			Folder[] folders = _parent.list();
			child = folders[index];
		} catch (MessagingException e) {
			Main.logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return child;
	}

	@Override
	public int getChildCount(Object parent) {
		int count = 0;
		Folder _parent = (Folder) parent;
		try {
			count = _parent.list().length;
		} catch (MessagingException e) {
			Main.logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public boolean isLeaf(Object node) {
		Folder _node = (Folder)node;
		try {
			if(_node.list().length == 0)
				return true;
		} catch (MessagingException e) {
			Main.logger.severe(e.getMessage());
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
		// TODO Auto-generated method stub

	}

}
