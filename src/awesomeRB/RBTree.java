package awesomeRB;

/**
 * RBTree
 * 
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer values.
 * 
 * All quoted algorithms are from:
 * Cormen, Thomas H., Charles E. Leiserson, and
 * Robert L. Rivest. Introduction to Algorithms.
 * Cambridge, MA: MIT, 2001. Print.
 * 
 * Invariant: getRoot() != null
 * Invariant: isValid()
 * 
 * @author Amir Moualem, amirmoua@mail.tau.ac.il, ID ?????????
 * @author Sagie Maoz, sagiemao@mail.tau.ac.il, ID 021526025
 */
public class RBTree {
	
	/**
	 * Constant marking a key as that of an empty leaf
	 */
	static final int NilValue = -1;
	
	/**
	 * Pointer to root node
	 */
	private RBNode root;
	
	/**
	 * Current size of tree (number of non-nil nodes)
	 */
	private int size;
	
	/**
	 * Creates a new, empty instance
	 */
	public RBTree() {
		this.root = new RBNode(RBTree.NilValue);
		this.size = 0;
	}

	/**
	 * Returns pointer to root node
	 */
	public RBNode getRoot() {
		return this.root;
	}

	/**
	 * Sets root node
	 */
	private void setRoot(RBNode root) {
		this.root = root;
	}

	/**
	 * Returns true if and only if the tree is empty.
	 * 
	 * Time complexity: O(1)
	 * 
	 * postcondition: return true iff the data structure
	 * does not contain any item
	 */
	public boolean empty() {
		return root.isNil();
	}

	/**
	 * Returns true if and only if the tree contains i.
	 * 
	 * Time complexity: O(logn)
	 * 
	 * postcondition: returns true iff i is in the tree
	 */
	public boolean contains(int i) {
		if (!empty()) {
			return root.contains(i);
		} else {
			return false;
		}
	}

	/**
	 * Inserts the integer i into the binary tree; the tree
	 * must remain valid (keep its invariants).
	 * 
	 * Time complexity: O(logn)
	 * 
	 * precondition:  none
	 * postcondition: contains(i) == true (that is, i is in the list)
	 */
	public void insert(int i) {
		RBNode newNode = new RBNode(i);

		if (empty()) {
			setRoot(newNode);
		} else {
			redBlackInsert(newNode);
		}
		this.size++;
	}

	/**
	 * Inserts a node to a Red-Black tree in a valid way.
	 * Based on the RB-Insert algorithm.
	 * 
	 * @param RBNode newNode New node to insert
	 */
	private void redBlackInsert(RBNode newNode) {
		RBNode y;
		
		if (getRoot().insert(newNode)) {
			newNode.setRed();
	
			while (newNode != getRoot() && newNode.getParent().isRed()) {
				if (newNode.getParent() == newNode.getGrandParent().getLeftChild()) {
					y = newNode.getGrandParent().getRightChild();
	
					if (!y.isNil() && y.isRed()) {
						newNode.getParent().setBlack();
						y.setBlack();
						newNode.getGrandParent().setRed();
						newNode = newNode.getGrandParent();
					} else {
						if (newNode == newNode.getParent().getRightChild()) {
							newNode = newNode.getParent();
							leftRotate(newNode);
						}
					
						if (newNode.hasParent()) {
							newNode.getParent().setBlack();
							if (newNode.hasGrandParent()) {
								newNode.getGrandParent().setRed();
								rightRotate(newNode.getGrandParent());
							}
						}
					}
					
				} else {
					y = newNode.getGrandParent().getLeftChild();
	
					if (!y.isNil() && y.isRed()) {
						newNode.getParent().setBlack();
						y.setBlack();
						newNode.getGrandParent().setRed();
						newNode = newNode.getGrandParent();
					} else {
						if (newNode == newNode.getParent().getLeftChild()) {
							newNode = newNode.getParent();
							rightRotate(newNode);
						}
					
						if (newNode.hasParent()) {
							newNode.getParent().setBlack();
							if (newNode.hasGrandParent()) {
								newNode.getGrandParent().setRed();
								leftRotate(newNode.getGrandParent());
							}
						}
					}
				}
	
			}
			
			getRoot().setBlack();
		}
	}

	/**
	 * Deletes the integer i from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
	 * 
	 * Time complexity: O(logn)
	 * 
	 * precondition:  none
	 * postcondition: contains(i) == false (that is, i is in the list)
	 */
	public void delete(int i) {
		RBNode z = getRoot().search(i);
		
		if (z == null) { // i was not found
			return;
		} else {
			RBNode x, y;
			
			if (!z.hasLeftChild() || !z.hasRightChild()) {
				// z doesn't have 2 child nodes
				y = z;
			} else {
				// z has 2 child nodes
				y = successor(z);
			}
			
			if (y.hasLeftChild()) {
				x = y.getLeftChild();
			} else {
				x = y.getRightChild();
			}
			
			x.setParent(y.getParent());
			if (getRoot() == y) {
				setRoot(x);
			} else if (y == y.getParent().getLeftChild()) {
				y.getParent().setLeftChild(x);
			} else {
				y.getParent().setRightChild(x);
			}
			
			if (y != z) {
				z.setKey(y.getKey());
			}
			
			if (y.isBlack()) {
				deleteFixup(x);
			}
			
			this.size--;	
		}
	}
	
	/**
	 * Fixes up tree after a delete action.
	 * Based on the RB-Insert algorithm.
	 * 
	 * precondition: x != null
	 * postcondition: isValid() == true
	 * 
	 * @param x	Child node of the deleted node's successor.
	 */
	private void deleteFixup(RBNode x) {
		RBNode w;
		
		while (getRoot() != x && x.isBlack()) {
			if (x == x.getParent().getLeftChild()) {
				w = x.getParent().getRightChild();
				
				// Case 1
				if (w.isRed()) {
					w.setBlack();
					x.getParent().setRed();
					leftRotate(x.getParent());
					w = x.getParent().getRightChild();
				}
				
				// Case 2
				if (w.getLeftChild().isBlack() && w.getRightChild().isBlack()) {
					w.setRed();
					x = x.getParent();
				} else {
					// Case 3
					if (w.getRightChild().isBlack()) {
						w.getLeftChild().setBlack();
						w.setRed();
						rightRotate(w);
						w = x.getParent().getRightChild();
					}
					
					// Case 4
					w.setBlack(x.getParent().isBlack());
					x.getParent().setBlack();
					w.getRightChild().setBlack();
					leftRotate(x.getParent());
					x = getRoot();
				}
			} else {
				w = x.getParent().getLeftChild();
				
				// Case 1
				if (w.isRed()) {
					w.setBlack();
					x.getParent().setRed();
					rightRotate(x.getParent());
					w = x.getParent().getLeftChild();
				}
				
				// Case 2
				if (w.getRightChild().isBlack() && w.getLeftChild().isBlack()) {
					w.setRed();
					x = x.getParent();
				} else {
					// Case 3
					if (w.getLeftChild().isBlack()) {
						w.getRightChild().setBlack();
						w.setRed();
						leftRotate(w);
						w = x.getParent().getLeftChild();
					}
					
					// Case 4
					w.setBlack(x.getParent().isBlack());
					x.getParent().setBlack();
					w.getLeftChild().setBlack();
					rightRotate(x.getParent());
					x = getRoot();
				}
			}
		}
		
		x.setBlack();
	}
	
	/**
	 * Returns the successor node for a given node in the tree.
	 * Successor being the node with the smallest key greater
	 * than x.getKey().
	 * 
	 * precondition: x != null
	 * precondition: x.hasLeftChild() && x.hasRightChild()
	 * 
	 * @param RBNode x Node to find the successor of
	 */
	private RBNode successor(RBNode x) {
		if (x.hasRightChild()) {
			return x.getRightChild().minNode();
		} else {
			RBNode y = x.getParent();
			while (!y.isNil() && x == y.getRightChild()) {
				x = y;
				y = y.getParent();
			}
			return y;
		}
	}

	/**
	 * Returns the smallest key in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * Time complexity: O(logn)
	 * 
	 * @return Smallest key in tree, -1 if tree is empty
	 */
	public int min() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().minNode().getKey();
		}
	}

	/**
	 * Returns the largest key in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * Time complexity: O(logn)
	 * 
	 * @return Largest key in tree, -1 if tree is empty
	 */
	public int max() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().maxNode().getKey();
		}
	}

	/**
	 * Returns an int[] array containing the values stored in the tree,
	 * in ascending order.
	 * 
	 * Time complexity: O(n)
	 * 
	 * postcondition: returns an array containing exactly the tree's elements in
	 *                 ascending order.
	 *                 
	 * @return An array of the tree's key values, sorted in ascending order.
	 */
	public int[] toIntArray() {
		int[] arr = new int[size()];
		getRoot().fillIntArray(arr, 0);
		return arr;
	}

	/**
	 * Returns true if and only if the tree is a valid red-black tree.
	 *
	 * Time complexity: O(n)
	 * 
	 * @return True iff the tree is a valid Red-Black tree.
	 */
	public boolean isValid() {
		if (root.isNil()) {
			return true;
		} else {
			return getRoot().isBSTValid() &&
					getRoot().isBlackValid() &&
					getRoot().isRedValid();
		}
	}

	/**
	 * Returns the maximum depth of a node in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * Time complexity: O(n)
	 * 
	 * @return Maximum depth of a node in the tree, -1 if tree is empty
	 */
	public int maxDepth() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().maxDepth();
		}
	}

	/**
	 * Returns the minimum depth of a leaf in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * Time complexity: O(n)
	 * 
	 * @return Minimum depth of a leaf in the tree, -1 if tree is empty
	 */
	public int minLeafDepth() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().minLeafDepth();
		}
	}

	/**
	 * Returns the number of nodes in the tree.
	 * 
	 * Time complexity: O(1)
	 * 
	 * @return Number of nodes in the tree
	 */
	public int size() {
		return size;
	}

	/**
	 * Returns a string representation of the tree.
	 * 
	 * @return String represntatino of the tree
	 */
	public String toString() {
		if (!empty()) {
			return String.format("<Tree %s>", root);
		} else {
			return "<Tree empty>";
		}
	}

	/**
	 * Applies the Left Rotate action on a given node.
	 * Based on the LeftRotate algorithm.
	 * 
	 * precondition: x != null, x.right != null
	 * postcondition: rotates x to the left
	 * 
	 * @param x	Node to rotate
	 */
	public void leftRotate(RBNode x) {
		RBNode y = x.getRightChild();
		x.setRightChild(y.getLeftChild());

		if (y.hasLeftChild()) {
			y.getLeftChild().setParent(x);
		}

		y.setParent(x.getParent());

		if (!x.hasParent()) {
			setRoot(y);
		} else if (x == x.getParent().getLeftChild()) {
			x.getParent().setLeftChild(y);
		} else {
			x.getParent().setRightChild(y);
		}

		y.setLeftChild(x);
		x.setParent(y);
	}

	/**
	 * Applies the Right Rotate action on a given node.
	 * Based on the RightRotate algorithm.
	 * 
	 * precondition: x != null, x.right != null
	 * postcondition: rotates x to the right
	 * 
	 * @param x Node to rotate
	 */
	public void rightRotate(RBNode x) {
		RBNode y = x.getLeftChild();
		x.setLeftChild(y.getRightChild());

		if (y.hasRightChild()) {
			y.getRightChild().setParent(x);
		}

		y.setParent(x.getParent());

		if (!x.hasParent()) {
			setRoot(y);
		} else if (x == x.getParent().getRightChild()) {
			x.getParent().setRightChild(y);
		} else {
			x.getParent().setLeftChild(y);
		}

		y.setRightChild(x);
		x.setParent(y);
	}

	/**
	 * public class RBNode
	 * 
	 * If you wish to implement classes other than RBTree
	 * (for example RBNode), do it in this file, not in 
	 * another file 
	 *  
	 */
	public class RBNode {
		/**
		 * Key stored in node (a unique positive integer)
		 */
		private int key;
		
		/**
		 * True iff the node is black
		 */
		private boolean isBlack;
		
		/**
		 * Pointer to a left child node
		 */
		private RBNode leftChild;
		
		/**
		 * Pointer to a right child node
		 */
		private RBNode rightChild;
		
		/**
		 * Pointer to the parent node, if one exists
		 */
		private RBNode parent;

		/**
		 * Creates a new node instance, given a key and color.
		 * 
		 * @param key		Key to store in node
		 * @param isBlack	True if node is black
		 */
		public RBNode(int key, boolean isBlack) {
			this.key = key;
			this.isBlack = isBlack;
			
			if (!isNil()) {
				setLeftChild(new RBNode());
				setRightChild(new RBNode());
			}
		}

		/**
		 * Creates a new black node, given a key.
		 * 
		 * @param key	Key to store in node
		 */
		public RBNode(int key) {
			this(key, true);
		}
		
		/**
		 * Creates a new, empty leaf.
		 */
		public RBNode() {
			this(RBTree.NilValue, true);
		}
		
		/**
		 * Returns true if the node is an empty leaf.
		 * 
		 * @return True if the node is an empty leaf
		 */
		private boolean isNil() {
			return this.key == RBTree.NilValue;
		}

		/**
		 * @return Pointer to parent node
		 */
		public RBNode getParent() {
			return this.parent;
		}
		
		/**
		 * Returns true if the node has a parent.
		 * 
		 * @return True iff the node has a parent
		 */
		public boolean hasParent() {
			return parent != null;
		}

		/**
		 * Returns the pointer to the node's grandparent node
		 * (it's parent node's parent node).
		 * 
		 * precondition: getParent() != null
		 * 
		 * @return Pointer to parent of parent node
		 */
		public RBNode getGrandParent() {
			return getParent().getParent();
		}
		
		/**
		 * Returns true if node has a grandparent node.
		 * 
		 * @return True if node has a parent node that has a parent node
		 */
		public boolean hasGrandParent() {
			return hasParent() && getParent().hasParent();
		}

		/**
		 * Sets the node's parent node
		 * 
		 * @param parent Pointer to new parent
		 */
		public void setParent(RBNode parent) {
			this.parent = parent;
		}

		/**
		 * Returns node's key value.
		 * 
		 * @return Node's key value
		 */
		public int getKey() {
			return key;
		}

		/**
		 * Sets the node's key value.
		 * 
		 * precondition: key > 0
		 * 
		 * @param key New key value
		 */
		public void setKey(int key) {
			this.key = key;
		}

		/**
		 * Returns true if the node is black.
		 * 
		 * @return True iff the node is black
		 */
		public boolean isBlack() {
			return isBlack;
		}

		/**
		 * Sets node's color to be black
		 */
		public void setBlack() {
			this.isBlack = true;
		}
		
		/**
		 * Sets node's blackness.
		 * Accepts True for a black color, and False for red.
		 * 
		 * @param isBlack True for a black node, False for red
		 */
		public void setBlack(boolean isBlack) {
			this.isBlack = isBlack;
		}

		/**
		 * Returns true if node is red.
		 * 
		 * @return True iff node is red
		 */
		public boolean isRed() {
			return !isBlack();
		}

		/**
		 * Sets node's color to be red.
		 */
		public void setRed() {
			this.isBlack = false;
		}

		/**
		 * Returns a pointer to the node's left child.
		 * 
		 * @return Pointer to node's left child
		 */
		public RBNode getLeftChild() {
			return leftChild;
		}

		/**
		 * Sets node's left child.
		 * @param leftChild Pointer to new left child
		 */
		public void setLeftChild(RBNode leftChild) {
			this.leftChild = leftChild;

			if (hasLeftChild()) {
				leftChild.setParent(this);
			}
		}

		/**
		 * Returns a pointer to the node's right child.
		 * 
		 * @return Pointer to node's right child
		 */
		public RBNode getRightChild() {
			return rightChild;
		}

		/**
		 * Sets node's right child.
		 * 
		 * @param rightChild Pointer to new right child
		 */
		public void setRightChild(RBNode rightChild) {
			this.rightChild = rightChild;

			if (hasRightChild()) {
				rightChild.setParent(this);
			}
		}

		/**
		 * Returns true if the node is a leaf.
		 * A node is considered a leaf if both it has no
		 * children, i.e. both its children are empty leaves.
		 * 
		 * @return True iff node is a leaf
		 */
		public boolean isLeaf() {
			return !hasLeftChild() && !hasRightChild();
		}

		/**
		 * Returns true if node has a left child,
		 * i.e. its left child is a non-nli node.
		 * 
		 * @return True iff node has a left child
		 */
		public boolean hasLeftChild() {
			return !leftChild.isNil();
		}

		/**
		 * Returns true if node has a right child,
		 * i.e. its right child is a non-nli node.
		 * 
		 * @return True iff node has a right child
		 */
		public boolean hasRightChild() {
			return !rightChild.isNil();
		}
		
		/**
		 * Returns pointer to node containing a requested key.
		 * 
		 * @param i Key to look up
		 * @return Node instance containing i, null if not found
		 */
		public RBNode search(int i) {
			if (isNil()) {
				return null;
			}
			else if (getKey() == i) {
				return this;
			} else {
				if (i < getKey() && hasLeftChild()) {
					return getLeftChild().search(i);
				} else if (hasRightChild()) {
					return getRightChild().search(i);
				}
			}
			return null;
		}

		/**
		 * Returns true iff the requested key is contained
		 * in the node or its offsprings.
		 * 
		 * @param i Key to look up
		 * @return True iff i is contained in node's tree
		 */
		public boolean contains(int i) {
			return search(i) != null;
		}

		/**
		 * Inserts a new node below this node.
		 * 
		 * @param newNode Node to insert.
		 * @return True if node inserted, False if key already existed.
		 */
		public boolean insert(RBNode newNode) {
			if (newNode.getKey() < getKey()) {
				if (hasLeftChild()) {
					return getLeftChild().insert(newNode);
				} else {
					setLeftChild(newNode);
					return true;
				}
			} else if (newNode.getKey() > getKey()) {
				if (hasRightChild()) {
					return getRightChild().insert(newNode);
				} else {
					setRightChild(newNode);
					return true;
				}
			} else { // key already exists, skip
				return false;
			}
		}
		
		/**
		 * Returns a pointer to the node containing the smallest key.
		 * 
		 * @return Node of smallest key in the tree
		 */
		private RBNode minNode() {
			if (hasLeftChild()) {
				return getLeftChild().minNode();
			} else {
				return this;
			}
		}
		
		/**
		 * Returns a pointer to the node containing the largest key.
		 * 
		 * @return Node of largest key in the tree
		 */
		private RBNode maxNode() {
			if (hasRightChild()) {
				return getRightChild().maxNode();
			} else {
				return this;
			}
		}
		
		/**
		 * Recursively fill tree's keys in an array.
		 * 
		 * @param arr	Values array to fill with the tree's keys
		 * @param loc	Current location in array
		 * @return	Array index of last number inserted.
		 */
		public int fillIntArray(int[] arr, int loc) {
			if (hasLeftChild()) {
				loc = getLeftChild().fillIntArray(arr, loc);
			}
			
			arr[loc++] = getKey();
			
			if (hasRightChild()) {
				loc = getRightChild().fillIntArray(arr, loc);
			}
			
			return loc;
		}

		/**
		 * Returns a string representation of the node and its offsprings.
		 */
		public String toString() {
			String leftString  = hasLeftChild() ? getLeftChild().toString() : "x";
			String rightString = hasRightChild() ? getRightChild().toString() : "x";
			
			return String.format("[ %d%s %s %s ]", getKey(), isBlack() ? "b" : "r", leftString, rightString);
		}
		
		/**
		 * Returns the maximum depth of a node in the tree.
		 * 
		 * @return Maximum depth of a node in the tree.
		 */
		public int maxDepth() {
			if (isLeaf()) {
				return 0;
			} else {
				if (hasLeftChild() && hasRightChild()) {
					return 1 + Math.max(getLeftChild().maxDepth(),
							getRightChild().maxDepth());
				} else if (hasLeftChild()) {
					return 1 + getLeftChild().maxDepth();
				} else { // hasRightChild()
					return 1 + getRightChild().maxDepth();
				}
			}
		}
		
		/**
		 * Returns the minimum depth of a leaf in the tree.
		 * 
		 * @return Minimum depth of a leaf in the tree.
		 */
		public int minLeafDepth() {
			if (isLeaf()) {
				return 0;
			} else {
				if (hasLeftChild() && hasRightChild()) {
					return 1 + Math.min(getLeftChild().minLeafDepth(),
							getRightChild().minLeafDepth());
				} else if (hasLeftChild()) {
					return 1 + getLeftChild().minLeafDepth();
				} else { // hasRightchild()
					return 1 + getRightChild().minLeafDepth();
				}
			}
		}
		

		/**
		 * Returns true if and only if the tree is a valid BST,
		 * i.e., every node's key is greater than its left child's key
	 	 * and smaller than its right child's key.
	 	 * 
	 	 * @return True iff node is a valid BST.
		 */
		public boolean isBSTValid() {
			if (isNil()) {
				return true;
			} else {
				if (hasLeftChild() && getKey() < getLeftChild().getKey()) {
					return false;
				} else if (hasRightChild() && getKey() > getRightChild().getKey()) {
					return false;
				} else {
					return getLeftChild().isBSTValid() &&
							getRightChild().isBSTValid();
				}
			}
		}

		/**
		 * Returns true iff node and its offsprings follow the Red rule,
		 * i.e., no red node is followed by another red node.
		 * 
		 * @return True iff node follows the Red rule
		 */
		public boolean isRedValid() {
			if (isLeaf()) {
				return true;
			} else {
				if (isBlack()) {
					if (hasLeftChild() && hasRightChild()) {
						return getLeftChild().isRedValid() && getRightChild().isRedValid();
					} else if (hasLeftChild()) {
						return getLeftChild().isRedValid();
					} else { // hasRightChild()
						return getRightChild().isRedValid();
					}
				} else { // isRed()
					if (hasLeftChild() && hasRightChild()) {
						return getLeftChild().isBlack() && getLeftChild().isRedValid() &&
							getRightChild().isBlack() && getRightChild().isRedValid();
					} else if (hasLeftChild()) {
						return getLeftChild().isBlack() && getLeftChild().isRedValid();
					} else { // hasRightChild()
						return getRightChild().isBlack() && getRightChild().isRedValid();
					}
				}
			}
		}
		
		/**
		 * Returns the node's black depth.
		 * 
		 * @return Black depth of current node
		 */
		private int blackDepth() {
			int me = isBlack() ? 1 : 0;
			if (hasLeftChild()) {
				return me + getLeftChild().blackDepth();
			} else {
				return me;
			}
		}
		
		/**
		 * Returns true iff node and its offsprings follow the Black rule,
		 * i.e., every path from root to a leaf passes through the same
		 * number of black nodes.
		 * 
		 * @return True iff node follows the Black rule
		 */
		public boolean isBlackValid() {
			if (isLeaf()) {
				return true;
			} else {
				if (hasRightChild() && hasLeftChild()) {
					return getRightChild().blackDepth() == getLeftChild().blackDepth();
				} else if (hasLeftChild()) {
					return getLeftChild().blackDepth() == 0;
				} else { // hasRightChild()
					return getRightChild().blackDepth() == 0;
				}
			}
		}
	}

	/**
	 * @original author Shai Vardi
	 * Modified for semester 2011/2012 a
	 */

}