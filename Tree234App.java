import java.io.*; 
import java.util.Scanner;

class DataItem
{
	public long dData;          // one data item 
	//--------------------------------------------------------------
	public DataItem(long dd)    // constructor
		{ dData = dd; } 
	//--------------------------------------------------------------
	public void displayItem()   // display item, format “/27”
		{ System.out.print("/"+dData); } 
	//--------------------------------------------------------------
}  // end class DataItem ////////////////////////////////////////////////////////////////

////////////////////////////////////////////////////////////////
class Node234
{
	private static final int ORDER = 4; //order of 2-3-4 Tree is 4 (i.e. K=4)
	private int numItems; // # of items in the node
	private Node234 parent;
	private Node234 childArray[] = new Node234[ORDER]; //each node can have up to 4 (=K) child nodes
	private DataItem itemArray[] = new DataItem[ORDER-1]; //each node can contain up to 3 (=K-1) keys
	// -------------------------------------------------------------
	
	public static int getOrder() {
		return ORDER;
	}

	public void setNumItems() {
		this.numItems++;
	}

	public void setParent(Node234 parent) {
		this.parent = parent;
	}

	public void setChildArray(int index, Node234 child) {
		this.childArray[index] = child;
	}


	public void setItemArray(int index, DataItem item) {
		this.itemArray[index] = item;
	}

	public Node234 getChild(int childNum)
	{ return childArray[childNum]; }
	// -------------------------------------------------------------
	
	public Node234 getParent()
	{ return parent; }
	// -------------------------------------------------------------
	
	public boolean isLeaf()
	{ return (childArray[0]==null) ? true : false; }
	// -------------------------------------------------------------
	
	public int getNumItems()
	{ return numItems; }
	// -------------------------------------------------------------
	
	public DataItem getItem(int index)   // get DataItem at index
	{ return itemArray[index]; }
	// -------------------------------------------------------------
	
	public boolean isFull()
	{ return (numItems==ORDER-1) ? true : false; }
	// -------------------------------------------------------------
	
	public void displayNode()           // format “/24/56/74/”
	{
		for(int j=0; j<numItems; j++)
		itemArray[j].displayItem();   // “/56” 
		System.out.println("/");         // final “/”
	}
	// -------------------------------------------------------------
}  // end class Node234 ////////////////////////////////////////////////////////////////

class Tree234 {
	
	private Node234 root = new Node234();            // make root node 
	// -------------------------------------------------------------
	
	public Node234 getRoot() {
		return root;
	}

	public void setRoot(Node234 root) {
		this.root = root;
	}
	// search for a key
	public Node234 search(Node234 node, long key)
	{	
		if(node != null && node.getItem(0) != null){
			if(key == node.getItem(0).dData || key == node.getItem(1).dData || key == node.getItem(2).dData){
				return node;
			}
			if(key < node.getItem(0).dData){
					return search(node.getChild(0), key);
				} else if(node.getItem(1) == null || key < node.getItem(1).dData){
					return search(node.getChild(1), key);
				} else if(node.getItem(2) == null || key < node.getItem(1).dData){
					return search(node.getChild(2), key);
				} else {
					return search(node.getChild(3), key);
			}
	   	}
	   	return null;
	} // end search()
	// -------------------------------------------------------------
	
	// insert a DataItem
	public void insert(Node234 node, long dValue)
	{	
		if(node.isFull()){
			node = split(node, node.getParent());
		}
		if(!node.isLeaf()){
			if(dValue < node.getItem(0).dData){
				insert(node.getChild(0), dValue);
			} else if (node.getItem(1) == null || dValue < node.getItem(1).dData){
				insert(node.getChild(1), dValue);
			} else if (node.getItem(2) == null || dValue < node.getItem(2).dData){
				insert(node.getChild(2), dValue);
			} else {
				insert(node.getChild(3), dValue);
			}
		} else {
			DataItem dValueItem = new DataItem(dValue);
			if(node.getItem(0) == null || dValue < node.getItem(0).dData){
				node.setItemArray(2, node.getItem(1));
				node.setItemArray(1, node.getItem(0));
				node.setItemArray(0, dValueItem);
				node.setNumItems();
			} else if (node.getItem(1) == null || dValue < node.getItem(1).dData){
				node.setItemArray(2, node.getItem(1));
				node.setItemArray(1, dValueItem);
				node.setNumItems();
			} else {
				node.setItemArray(2, dValueItem);
				node.setNumItems();
			}
		}
	}  
	
	public Node234 split(Node234 node, Node234 nodeParent)
	{
		Node234 splitLeft = new Node234();
		Node234 splitRight = new Node234();
		if(!node.isFull()){
			return null;
		}
		
		splitLeft.setItemArray(0, node.getItem(0));
		splitLeft.setNumItems();
		splitLeft.setChildArray(0, node.getChild(0));
		if(node.getChild(0) !=  null){
			node.getChild(0).setParent(splitLeft);
		}
		splitLeft.setChildArray(1, node.getChild(1));
		if(node.getChild(1) !=  null){
			node.getChild(1).setParent(splitLeft);
		}
		
		splitRight.setItemArray(0, node.getItem(2));
		splitRight.setNumItems();
		splitRight.setChildArray(0, node.getChild(2));
		if(node.getChild(2) !=  null){
			node.getChild(2).setParent(splitLeft);
		}
		splitRight.setChildArray(1, node.getChild(3));
		if(node.getChild(3) !=  null){
			node.getChild(3).setParent(splitLeft);
		}

		if(nodeParent!= null){
			insertKeyWithChildren(nodeParent, node.getItem(1), splitLeft, splitRight);
		} else {
			Node234 temp = new Node234();
			temp.setItemArray(0, node.getItem(1));
			temp.setNumItems();
			temp.setChildArray(0, splitLeft);
			splitLeft.setParent(temp);
			temp.setChildArray(1, splitRight);
			splitRight.setParent(temp);
			nodeParent = temp;
			root = nodeParent;
		}
		return nodeParent;
	}

	public void insertKeyWithChildren(Node234 parent, DataItem key, Node234 leftChild, Node234 rightChild){
		if(key.dData < parent.getItem(0).dData){
			parent.setItemArray(2, parent.getItem(1));
			parent.setItemArray(1, parent.getItem(0));
			parent.setItemArray(0, key);
			parent.setNumItems();
			parent.setChildArray(3, parent.getChild(2));
			parent.setChildArray(2, parent.getChild(1));
			parent.setChildArray(1, rightChild);
			parent.setChildArray(0, leftChild);
		} else if(parent.getItem(1) == null || key.dData < parent.getItem(1).dData){
			parent.setItemArray(2, parent.getItem(1));
			parent.setItemArray(1, key);
			parent.setNumItems();
			parent.setChildArray(3, parent.getChild(2));
			parent.setChildArray(2, rightChild);
			parent.setChildArray(1, leftChild);
		} else {
			parent.setItemArray(2, key);
			parent.setNumItems();
			parent.setChildArray(3, rightChild);
			parent.setChildArray(2, leftChild);
		}
	} // end insert()
	// -------------------------------------------------------------
	
	public void displayTree()
	{
		recDisplayTree(root, 0, 0); 
		System.out.println("......................................................"); 
	}
	// -------------------------------------------------------------
	
	private void recDisplayTree(Node234 thisNode, int level, int childNumber)
	{
		System.out.print("level="+level+" child="+childNumber+" "); 
		thisNode.displayNode();               // display this node
		
		// call ourselves for each child of this node
		int numItems = thisNode.getNumItems();
		for(int j=0; j<numItems+1; j++)
		{
			Node234 nextNode = thisNode.getChild(j); 
			if(nextNode != null)
				recDisplayTree(nextNode, level+1, j); 
			else
				return;
		}
	}  // end recDisplayTree()
	// -------------------------------------------------------------
}  // end class Tree234 ////////////////////////////////////////////////////////////////
	
		
////////////////////////////////////////////////////////////////
public class Tree234App{

	public static void main(String args[]) {
		Scanner scnr = new Scanner(System.in);
		String task="";
		long data = -1;	
		Tree234 tree = new Tree234();

		String line = scnr.nextLine();
		while (!line.equals("-1")) { 

			task = line.split(" ")[0];
			data = Long.parseLong(line.split(" ")[1]); 

			if (task.equals("i")) { //insert
				tree.insert(tree.getRoot(), data); 
				System.out.println("Inserting "+data+": complete."); 
				tree.displayTree(); 
			} 
			else if (task.equals("s")) { //search
	    		Node234 node = tree.search(tree.getRoot(), data);
	    		if (node==null) System.out.println("Searching for "+data+": not found.");
	    		else System.out.println("Searching for "+data+": found.");
	    		tree.displayTree();
	    	}
			else
				System.out.print("Invalid entry\n");  

			line = scnr.nextLine();
		}
		System.out.println("--- Final Tree ---");
		tree.displayTree();

	}
}