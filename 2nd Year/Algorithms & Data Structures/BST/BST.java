
/*************************************************************************
 *  Binary Search Tree class.
 *  Adapted from Sedgewick and Wayne.
 *
 *  @version 3.0 30/11/2018 16:08
 *
 *  @author Anna Maliakal 
 *
 *************************************************************************/

import java.util.NoSuchElementException;

public class BST<Key extends Comparable<Key>, Value> {
	private Node root; // root of BST

	/**
	 * Private node class.
	 */
	private class Node {
		private Key key; // sorted by key
		private Value val; // associated data
		private Node left, right; // left and right subtrees
		private int N; // number of nodes in subtree

		public Node(Key key, Value val, int N) {
			this.key = key;
			this.val = val;
			this.N = N;
		}
	}

	// is the symbol table empty?
	public boolean isEmpty() {
		return size() == 0;
	}

	// return number of key-value pairs in BST
	public int size() {
		return size(root);
	}

	// return number of key-value pairs in BST rooted at x
	private int size(Node x) {
		if (x == null)
			return 0;
		else
			return x.N;
	}

	/**
	 * Search BST for given key. Does there exist a key-value pair with given
	 * key?
	 *
	 * @param key
	 *            the search key
	 * @return true if key is found and false otherwise
	 */
	public boolean contains(Key key) {
		return get(key) != null;
	}

	/**
	 * Search BST for given key. What is the value associated with given key?
	 *
	 * @param key
	 *            the search key
	 * @return value associated with the given key if found, or null if no such
	 *         key exists.
	 */
	public Value get(Key key) {
		return get(root, key);
	}

	private Value get(Node x, Key key) {
		if (x == null)
			return null;
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			return get(x.left, key);
		else if (cmp > 0)
			return get(x.right, key);
		else
			return x.val;
	}

	/**
	 * Insert key-value pair into BST. If key already exists, update with new
	 * value.
	 *
	 * @param key
	 *            the key to insert
	 * @param val
	 *            the value associated with key
	 */
	public void put(Key key, Value val) {
		if (val == null) {
			delete(key);
			return;
		}
		root = put(root, key, val);
	}

	private Node put(Node x, Key key, Value val) {
		if (x == null)
			return new Node(key, val, 1);
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = put(x.left, key, val);
		else if (cmp > 0)
			x.right = put(x.right, key, val);
		else
			x.val = val;
		x.N = 1 + size(x.left) + size(x.right);
		return x;
	}

	/**
	 * Tree height.
	 *
	 * Asymptotic worst-case running time using Theta notation: O(h) where h is the height. This function makes a recursive function call
	 * to every node until the end of the tree is reached.
	 * 				 
	 *
	 * @return the number of links from the root to the deepest leaf.
	 *
	 *         Example 1: for an empty tree this should return -1.
	 *         Example 2:
	 *         for a tree with only one node it should return 0. 
	 *         Example 3: 
	 *         for the following tree it should return 2. 
	 *         B
     *		  / \
     * 		 A   C
     *      	  \
     *       	   D
     */

	public int height() {
	   	return height(root);
	}

	private int height(Node x) {
		if(x == null)
			return -1;
		
		return 1 + Math.max(height(x.left), height(x.right));
	}

	/**
	 * Median key. If the tree has N keys k1 < k2 < k3 < ... < kN, then their
	 * median key is the element at position (N+1)/2 (where "/" here is integer
	 * division)
	 *
	 * @return the median key, or null if the tree is empty.
	 */
	public Key median() {
		Key key = null;
		if (isEmpty()){
			
		}
		else if (size()>0){
			int medianVal = (size()+1)/2;
			int rank = medianVal-1;
			key  = median(root,rank);
			
		}
		return key;
	}
	
	private Key median(Node x, int val){
		
		if(x==null) return null;
		int t = size(x.left);
		if(t>val) return median(x.left, val);
		else if(t<val) return median(x.right, val-t-1);
		else return x.key;
		
	}


	/**
	 * Print all keys of the tree in a sequence, in-order. That is, for each
	 * node, the keys in the left subtree should appear before the key in the
	 * node. Also, for each node, the keys in the right subtree should appear
	 * before the key in the node. For each subtree, its keys should appear
	 * within a parenthesis.
	 *
	 * Example 1: Empty tree -- output: "()" Example 2: Tree containing only "A"
	 * -- output: "(()A())" Example 3: Tree: B / \ A C \ D
	 *
	 * output: "((()A())B(()C(()D())))"
	 *
	 * output of example in the assignment:
	 * (((()A(()C()))E((()H(()M()))R()))S(()X()))
	 *
	 * @return a String with all keys in the tree, in order, parenthesized.
	 */
	public String printKeysInOrder() {
		String answer;
		if (isEmpty())
		{
			System.out.print("()");
			return "()";	
		}
		else {
			answer = printKeysInOrder(root);
		}
		return answer;
	}

	private String printKeysInOrder(Node x) {
		if (x == null) {
			return "()";
		} else {
			return "(" + printKeysInOrder(x.left) + x.key + printKeysInOrder(x.right) + ")";
		}
		
	}

	/**
	 * Pretty Printing the tree. Each node is on one line -- see assignment for
	 * details.
	 *
	 * @return a multi-line string with the pretty ascii picture of the tree.
	 */
	public String prettyPrintKeys() {
		if (isEmpty()) {
			return "-null";
		}
		String outputStr = prettyPrint(root, "") + "\n";
		return outputStr;
	}

	private String prettyPrint(Node theNode, String stringOne) {
		String result = "";
		if (theNode == null) {
			result = stringOne + "-null";
		} else {
			result = stringOne + "-" + theNode.val + "\n" + prettyPrint(theNode.left, stringOne + " |") + "\n"
					+ prettyPrint(theNode.right, stringOne + "  ");
		}
		return result;
	}

	/**
	 * Deletes a key from a tree (if the key is in the tree). Note that this
	 * method works symmetrically from the Hibbard deletion: If the node to be
	 * deleted has two child nodes, then it needs to be replaced with its
	 * predecessor (not its successor) node.
	 *
	 * @param key
	 *            the key to delete
	 */
	public void delete(Key key) {
		if(root.key == key && root.left == null && root.right ==null)
			root= null;
		else {
			if((!isEmpty())&&contains(key)){
				delete(root,key);
			}
			else{
				System.out.println("This key is not in the tree");
			}
		}

	}
	
	private Node delete(Node x, Key key) {
		if(x==null) {
			
		}
		if(x.key == key && x.left ==null && x.right == null) {
			x = null;
			return  x;
		}
		int cmp = key.compareTo(x.key);
		if (cmp < 0)
			x.left = delete(x.left,key);
		else if (cmp > 0)
			x.right = delete(x.right, key);
		else {
			if(x.right == null)
			{
				return x.left;
			}
			if(x.left == null)
			{
				return x.right;
			}
			Node t = x;
			x = max(t.left, x.key);
			x.left = deleteMax(t.left);
			x.right = t.right;
		}
		x.N = size(x.left) + size(x.right)+1;
		return x;
		
	}

	private Node deleteMax(Node x) {
		if(x.right == null)
		{
			return x.left;
		}
		x.right = deleteMax(x.right);
		x.N = size(x.left) + size(x.right)+1;
		return x;
	}

	private Node max(Node x, Key key) {
		if(x==null)
		{
			return null;
		}
		Node t = max(x.right,key);
		if(t!=null)
		{
			return t;
		}
		return x;
	}

	/*public static void main(String[] args)
	{
		BST<Integer, Integer> bst = new BST<Integer, Integer>();
		bst.put(2, 2);
		bst.put(4, 4);
		bst.put(1, 1);
		bst.put(5, 5);
		bst.put(3, 3);
		
		
		int val = bst.height();
		System.out.println(val);
	}*/
}  