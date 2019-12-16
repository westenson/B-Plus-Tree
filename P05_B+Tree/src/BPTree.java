import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Implementation of a B+ tree to allow efficient access to many different
 * indexes of a large data set. BPTree objects are created for each type of
 * index needed by the program. BPTrees provide an efficient range search as
 * compared to other types of data structures due to the ability to perform
 * log_m N lookups and linear in-order traversals of the data items.
 * 
 * @author Wally Estenson (westenson@wisc.edu), Sapan (sapan@cs.wisc.edu)
 * 
 *         Date: 12/11/2019
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food
 *            item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

	// Root of the tree
	private Node root;
	private int size;

	// Branching factor is the number of children nodes
	// for internal nodes of the tree
	private int branchingFactor;

	/**
	 * Public constructor
	 * 
	 * @param branchingFactor
	 */
	public BPTree(int branchingFactor) {
		if (branchingFactor <= 2) {
			throw new IllegalArgumentException(
					"Illegal branching factor: " + branchingFactor);
		} else {
			this.branchingFactor = branchingFactor;
			root = new LeafNode();
			size = 0;
		}
	}

	/**
	 * Inserts the key and value in the appropriate nodes in the tree If the key
	 * is null, throw IllegalArgumentException
	 * 
	 * Note: key-value pairs with duplicate keys should be able to be inserted
	 * into the tree. However, I realizes this too late, so duplicates currently
	 * cannot be entered.
	 * 
	 * @param key
	 * @param value
	 */
	@Override
	public void insert(K key, V value) {

		// do not allow null key
		if (key == null)
			throw new IllegalArgumentException();

		// insert node by utilizing methods within internalNode and leadNode
		// clases
		root.insert(key, value);
	}

	/**
	 * Gets the values that satisfy the given range search arguments.
	 * 
	 * Value of comparator can be one of these: "<=", "==", ">="
	 * 
	 * Example: If given key = 2.5 and comparator = ">=": return all the values
	 * with the corresponding keys >= 2.5
	 * 
	 * If key is null or not found, return empty list. If comparator is null,
	 * empty, or not according to required form, return empty list.
	 * 
	 * @param key        to be searched
	 * @param comparator is a string
	 * @return list of values that are the result of the range search; if
	 *         nothing found, return empty list
	 */
	@Override
	public List<V> rangeSearch(K key, String comparator) {

		// must have valid comparator argument
		if (!comparator.contentEquals(">=") && !comparator.contentEquals("==")
				&& !comparator.contentEquals("<="))
			return new ArrayList<V>();

		// utilize methods within internalNode and leafNode classes for
		// rangeSearch
		else
			return root.rangeSearch(key, comparator);
	}

	/**
	 * Returns the value of the first leaf with a matching key. If key is null,
	 * return null. If key is not found, return null.
	 *
	 * @param key to find
	 * @return value of the first leaf matching key
	 */
	@Override
	public V get(K key) {
		if (key == null)
			return null;

		return root.getHelper(key);
	}

	/**
	 * Return the number of leaves in the tree.
	 *
	 * @return number of leaves
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns a string representation for the tree This method is provided to
	 * students in the implementation.
	 * 
	 * @return a string representation
	 */
	@Override
	public String toString() {
		Queue<List<Node>> queue = new LinkedList<List<Node>>();
		queue.add(Arrays.asList(root));
		StringBuilder sb = new StringBuilder();
		while (!queue.isEmpty()) {
			Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
			while (!queue.isEmpty()) {
				List<Node> nodes = queue.remove();
				sb.append('{');
				Iterator<Node> it = nodes.iterator();
				while (it.hasNext()) {
					Node node = it.next();
					sb.append(node.toString());
					if (it.hasNext())
						sb.append(", ");
					if (node instanceof BPTree.InternalNode)
						nextQueue.add(((InternalNode) node).children);
				}
				sb.append('}');
				if (!queue.isEmpty())
					sb.append(", ");
				else {
					sb.append('\n');
				}
			}
			queue = nextQueue;
		}
		return sb.toString();
	}

	/**
	 * This abstract class represents any type of node in the tree This class is
	 * a super class of the LeafNode and InternalNode types.
	 * 
	 * @author sapan, Wally Estenson
	 */
	private abstract class Node {

		// List of keys
		List<K> keys;

		/**
		 * Package constructor
		 */
		Node() {
			this.keys = new ArrayList<K>();
		}

		/**
		 * Returns size of node
		 */
		int getSize() {
			return keys.size();
		}

		/**
		 * Inserts key and value in the appropriate leaf node and balances the
		 * tree if required by splitting
		 * 
		 * @param key
		 * @param value
		 */
		abstract void insert(K key, V value);

		/**
		 * Gets the first leaf key of the tree
		 * 
		 * @return key
		 */
		abstract K getFirstLeafKey();

		/**
		 * Gets the new sibling created after splitting the node
		 * 
		 * @return Node
		 */
		abstract Node split();

		/*
		 * (non-Javadoc)
		 * 
		 * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
		 */
		abstract List<V> rangeSearch(K key, String comparator);

		/**
		 * Check if node is overflow based on branch factor
		 * 
		 * @return boolean
		 */
		abstract boolean isOverflow();

		/**
		 * Converts keys to string for testing purposes
		 */
		public String toString() {
			return keys.toString();
		}

		/**
		 * Called from internalNode or leafNode classes if insert caused root
		 * overflow based on branching factor
		 * 
		 * @param Node root node
		 */
		public void rootOverflow(Node node) {

			// split the root
			Node node2 = node.split();

			// create new interalNal node that will be the new root
			InternalNode root2 = new InternalNode();

			// create the new root
			root2.keys.add(node2.getFirstLeafKey());
			root2.children.add(node);

			// add the split node as a child of the new root
			root2.children.add(node2);

			// set the new root node
			root = root2;
		}

		/**
		 * Helper method for finding the correct index of a node that is being
		 * inserted and does not already exist in the tree
		 * 
		 * @param Key of node to be inserted
		 */
		public int getNewIndex(K key) {

			int index = -1;

			// iterator on list of key for current node
			Iterator<K> keysIterator = keys.listIterator();
			boolean found = false;

			while (keysIterator.hasNext() && !found) {

				K currentNode = keysIterator.next();

				int currentNodeIndex = keys.indexOf(currentNode);

				// find position between larger and smaller keys
				if (currentNode.compareTo(key) > 0) {
					index = currentNodeIndex;
					found = true;
				}
			}

			// if the node to be inserted is larger than any other node in the
			// list, get the index at the end
			if (!found) {
				index = keys.size();
				found = true;
			}

			return index;
		}

		/**
		 * Helper method to get value
		 * 
		 * @param Key that we desire
		 */
		abstract V getHelper(K key);

	} // End of abstract class Node

	/**
	 * This class represents an internal node of the tree. This class is a
	 * concrete sub class of the abstract Node class and provides implementation
	 * of the operations required for internal (non-leaf) nodes.
	 * 
	 * @author sapan
	 */
	private class InternalNode extends Node {

		// List of children nodes
		List<Node> children;

		/**
		 * Package constructor
		 */
		InternalNode() {
			super();
			this.children = new ArrayList<Node>();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 */
		K getFirstLeafKey() {
			return children.get(0).getFirstLeafKey();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#isOverflow()
		 */
		boolean isOverflow() {
			if (children.size() > branchingFactor)
				return true;

			else
				return false;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
		 */
		void insert(K key, V value) {

			// get index of key to be inserted
			int index = keys.indexOf(key);
			int childIndex;

			// index will be positive if the key already exists
			if (index >= 0)
				childIndex = index + 1;

			// otherwise the key does not exist and we must find where it should
			// exist
			else
				childIndex = getNewIndex(key);

			Node node = children.get(childIndex);

			// insert the node at correct position
			node.insert(key, value);

			// if inserted node caused overflow, we must split
			if (node.isOverflow()) {

				// split the node
				Node splitNode = node.split();

				// index of the split node
				int splitNodeIndex = keys.indexOf(splitNode.getFirstLeafKey());

				// new index of the split node
				int newSplitNodeIndex;

				// if the split node location already exists, set next to the
				// the the other value
				if (splitNodeIndex >= 0) {

					// set to the right of value in node
					newSplitNodeIndex = splitNodeIndex + 1;
					children.set(newSplitNodeIndex, splitNode);

				}

				else {

					// create new index where node should be
					newSplitNodeIndex = getNewIndex(
							splitNode.getFirstLeafKey());
					// add keys to the list
					keys.add(newSplitNodeIndex, splitNode.getFirstLeafKey());
					// add children to the list
					children.add(newSplitNodeIndex + 1, splitNode);
				}
			}

			// if insert caused the root node to overflow
			if (root.isOverflow()) {
				rootOverflow(root);
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#split()
		 */
		Node split() {

			int begining = this.getSize() / 2 +1;
			int keyEnd = this.getSize();
			int valueEnd = keyEnd + 1;

			// create new split node
			InternalNode node = new InternalNode();

			// add second half of old keys to new node
			List<K> keysList = keys.subList(begining, keyEnd); 
			for (int i = 0; i < keysList.size(); i++) {
				K k = keysList.get(i);
				node.keys.add(k);
			}

			// add second half of old children to new node
			List<Node> childrenList = children.subList(begining, valueEnd);
			for (int i = 0; i < childrenList.size(); i++) {
				Node n = childrenList.get(i);
				node.children.add(n);
			}
		

			// clear the parts of the original nodes keys and children that we
			// just moved
			keys.subList(begining - 1, keyEnd).clear();
			children.subList(begining, valueEnd).clear();
			return node;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
		 */
		List<V> rangeSearch(K key, String comparator) {

			// get the first child of the node
			Node child = children.get(0);

			// call the leafNode method
			return child.rangeSearch(key, comparator);

		}

		V getHelper(K key) {

			// get child of node to be inserted

			int index = keys.indexOf(key);
			int childIndex;

			if (index >= 0)
				childIndex = index + 1;
			else
				childIndex = getNewIndex(key);

			Node child = children.get(childIndex);
			return child.getHelper(key);

		}

	} // End of class InternalNode

	/**
	 * This class represents a leaf node of the tree. This class is a concrete
	 * sub class of the abstract Node class and provides implementation of the
	 * operations that required for leaf nodes.
	 * 
	 * @author sapan
	 */
	private class LeafNode extends Node {

		// List of values
		List<V> values;

		// Reference to the next leaf node
		LeafNode next;

		/**
		 * Package constructor
		 */
		LeafNode() {
			super();
			values = new ArrayList<V>();
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#getFirstLeafKey()
		 */
		K getFirstLeafKey() {
			return keys.get(0);
		}

		/**
		 * (non-Javadoc) Checks if node needs to be split
		 * 
		 * @see BPTree.Node#isOverflow()
		 */
		boolean isOverflow() {
			if (values.size() > (branchingFactor - 1))
				return true;

			else
				return false;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#insert(Comparable, Object)
		 */
		void insert(K key, V value) {

			int preIndex = keys.indexOf(key);
			int newIndex;

			// if the preIndex is greater than 0, then the index exists
			if (preIndex >= 0) {
				newIndex = preIndex;
				// replace the current node with new node
				values.set(newIndex, value);
			}

			// if the key doesn't already exist, add a new node
			else {
				newIndex = getNewIndex(key);
				keys.add(newIndex, key); // add new key to list
				values.add(newIndex, value); // add new value to list
				size++;
			}

			// if insert causes root overflow, call method within abstract class
			if (root.isOverflow()) {
				rootOverflow(root);
			}
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#split()
		 */
		Node split() {

			LeafNode node = new LeafNode();

			int begining = this.getSize() / 2;

	        // transfer key values pairs to new node
	        for (int i=begining; i<keys.size(); i=i) {
	        
	        //add second half of old values to new node
	        node.values.add(this.values.get(begining));
	        node.keys.add(this.keys.get(begining));

	        //clear the old values from the originial node
	        this.values.remove(begining);
	        this.keys.remove(begining);
	        }

			node.next = next;
			next = node;
			return node;
		}

		/**
		 * (non-Javadoc)
		 * 
		 * @see BPTree.Node#rangeSearch(Comparable, String)
		 */
		List<V> rangeSearch(K key, String comparator) {
			List<V> filtered = new ArrayList<V>();
			LeafNode node = this;
			
			// work through the nodes in the graph
			while (node != null) {

				// work through each set of keys for each node
				for (int i = 0; i < node.keys.size(); i++) {

					K key2 = node.keys.get(i);

					int comparison = key.compareTo(key2);

					// check comparator matches up with comparison
					if (comparator.contentEquals("==") && comparison == 0) {
						filtered.add(get(key2));
					}

					// check comparator matches up with comparison
					else if (comparator.contentEquals("<=")
							&& comparison >= 0) {
						filtered.add(get(key2));
					}

					// check comparator matches up with comparison
					else if (comparator.contentEquals(">=")
							&& comparison <= 0) {
						filtered.add(get(key2));
					}
				}
				// move on to the next node
				node = node.next;
			}
			return filtered;
		}

		/**
		 * Returns value of specified key 
		 * 
		 * @return value of specified key
		 */
		V getHelper(K key) {
			int index = keys.indexOf(key);

			if (index >= 0)
				return values.get(index);

			else
				return null;
		}

	} // End of class LeafNode
} // End of class BPTree
