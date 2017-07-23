package com.lukascode.jmail.common;

import java.util.ArrayList;
import java.util.List;

public class StringTree {
	
	public Node root;
	
	public StringTree(String rootData) {
		root = new Node(rootData, null);
	}
	
	public static class Node {
		public String data;
		public Node parent;
		public List<Node> children;
		
		public Node(String data, Node parent) {
			this.data = data;
			this.parent = parent;
			children = new ArrayList<Node>();
		}
		
		public void addChild(String data) {
			if(data != null)
				children.add(new Node(data, this));
		}
		
		public String toString() {
			return data;
		}
		
	}
	
}
