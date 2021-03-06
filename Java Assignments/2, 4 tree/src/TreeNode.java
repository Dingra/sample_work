import java.util.ArrayList;

public class TreeNode<K extends Comparable<K>,V> {

	protected ArrayList<TreeEntry<K,V>> entries;
	protected ArrayList<TreeNode<K,V>> children;
	protected TreeNode<K,V> parent;
 	
	//Constructor used to initialise a 2,4 tree
	public TreeNode(TreeEntry<K,V> entry){		
		//Make the new node the root
		parent = null;
		entries = new ArrayList<TreeEntry<K,V>>();
		entries.add(entry);
		
		//Initialise two empty children
		children = new ArrayList<TreeNode<K,V>>();
		children.add(null);
		children.add(null);
	}
	
	//Create a new node with one entry (and two children)
	public TreeNode(TreeEntry<K,V> n1, TreeNode<K,V> c1, TreeNode<K,V> c2){
		
		entries = new ArrayList<TreeEntry<K,V>>();
		children = new ArrayList<TreeNode<K,V>>();
		
		entries.add(n1);
		children.add(c1);
		children.add(c2);
	}
	
	//Returns true if a node is overflowing and false otherwise
	public boolean overflow(){
		if(entries.size() > 3)
			return true;
		else return false;
	}
	
	//Get the list of children for a particular node
	public ArrayList<TreeNode<K,V>> getChildren(){
		return children;
	}
	
	//Get the entries for this node
	public ArrayList<TreeEntry<K,V>> getEntries(){
		return entries;
	}
}