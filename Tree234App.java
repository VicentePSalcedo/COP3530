import java.io.*; 
import java.util.Scanner;

class DataItem
{
	public long dData;          // one data item 
	//--------------------------------------------------------------
	public DataItem(long dd)    // constructor
		{ dData = dd; } 
	//--------------------------------------------------------------
	public void displayItem()   // display item, format "/27"
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
	public void setNumItems(int numItems) {
		this.numItems = numItems;
	}

	public void setParent(Node234 parent) {
		this.parent = parent;
	}

	public void setChildArray(Node234[] childArray) {
		this.childArray = childArray;
	}

	public void setItemArray(DataItem[] itemArray) {
		this.itemArray = itemArray;
	}

	public Node234 getChild(int childNum){
		return childArray[childNum];
	}
	// -------------------------------------------------------------
	
	public Node234 getParent(){
		return parent;
	}
	// -------------------------------------------------------------
	
	public boolean isLeaf(){ 
		return (childArray[0]==null) ? true : false;
	}
	// -------------------------------------------------------------
	
	public int getNumItems(){
		return numItems;
	}
	// -------------------------------------------------------------
	
	public DataItem getItem(int index){   // get DataItem at index
		return itemArray[index];
	}
	// -------------------------------------------------------------
	
	public boolean isFull(){
		return (numItems==ORDER-1) ? true : false;
	}
	// -------------------------------------------------------------
	
	public void displayNode(){           // format "/24/56/74/"
		for(int j=0; j<numItems; j++)
		itemArray[j].displayItem();   // "/56" 
		System.out.println("/");         // final "/"
	}
	// -------------------------------------------------------------

}  // end class Node234 ////////////////////////////////////////////////////////////////

class Tree234 {
	
	private Node234 root = new Node234();            // make root node 
	// -------------------------------------------------------------
	
	// search for a key
	public Node234 search(long key){	
		Node234 currNode234 = root;
		while (currNode234.getNumItems() != 0){
			if (key == currNode234.getItem(0).dData || key == currNode234.getItem(1).dData || key == currNode234.getItem(2).dData){
				return currNode234;
			}
			if (key < currNode234.getItem(0).dData){
				currNode234 = currNode234.getChild(0);
			} else if (currNode234.getItem(1) == null || key < currNode234.getItem(1).dData){
				currNode234 = currNode234.getChild(1);
			} else if (currNode234.getItem(2) ==  null || key < currNode234.getItem(2).dData){
				currNode234 = currNode234.getChild(2);
			} else {
				currNode234 = currNode234.getChild(3);
			}
		}
		return null;
	} // end search()
	// -------------------------------------------------------------
	
	// insert a DataItem
	public void insert(Node234 node, long dValue){
		int index = 0;
		while(index < node.getNumItems()){
			if(dValue == node.getItem(index).dData){
				return;
			}
			index++;
		}
		if(node.isFull()){
			node = split(node, node.getParent());
		}
		if(!node.isLeaf()){
			if(dValue < node.getItem(0).dData){
				insert(node.getChild(0), dValue);
			} else if(node.getItem(1) == null || dValue < node.getItem(1).dData){
				insert(node.getChild(1), dValue);
			} else if(node.getChild(2) == null || dValue < node.getItem(2).dData){
				insert(node.getChild(2), dValue);
			} else {
				insert(node.getChild(3), dValue);
			}
		} else {
			insertIntoLeaf(node, dValue);
		}
	}  
	public Node234 split(Node234 node, Node234 nodeParent){
		return null;
	}
	// end insert()
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

	public Node234 getRoot() {
		return root;
	}

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
	    		Node234 node = tree.search(data);
	    		if (node==null) System.out.println("Searching for "+data+": not found.");
	    		else System.out.println("Searching for "+data+": found.");
	    		tree.displayTree();
	    	}
			else
				System.out.print("Invalid entry\n");  

			line = scnr.nextLine();
		}
		scnr.close();
		System.out.println("--- Final Tree ---");
		tree.displayTree();
	}

}