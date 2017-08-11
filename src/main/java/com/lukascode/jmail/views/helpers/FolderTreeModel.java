package com.lukascode.jmail.views.helpers;



import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import com.lukascode.jmail.common.StringTree;
import com.lukascode.jmail.common.StringTree.Node;

public class FolderTreeModel implements TreeModel {
	
	private StringTree tree;
	
	public FolderTreeModel(StringTree tree) {
		this.tree = tree;
	}

	@Override
	public Object getRoot() {
		return tree.root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		Node child = null;
		Node _parent = (Node) parent;
		return _parent.children.get(index);	
	}

	@Override
	public int getChildCount(Object parent) {
		Node _parent = (Node)parent;
		return _parent.children.size();
	}

	@Override
	public boolean isLeaf(Object node) {
		Node _node = (Node)node;
		if(_node.children.size() > 0)
			return false;
		return true;
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
