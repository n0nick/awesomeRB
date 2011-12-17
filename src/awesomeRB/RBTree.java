package awesomeRB;
/**
 * 
 * RBTree
 * 
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer values.
 * 
 * All quoted algorithms are from:
 * Cormen, Thomas H., Charles E. Leiserson, and
 * Robert L. Rivest. Introduction to Algorithms.
 * Cambridge, MA: MIT, 2001. Print.
 */

public class RBTree {
	
	/**
	 * Pointer to root node
	 */
	private RBNode root;
	
	//TODO document
	private int size;
	
	public RBTree() {
		this.root = new RBNode(-1);
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
	public void setRoot(RBNode root) {
		this.root = root;
	}

	/**
	 * public boolean empty()
	 * 
	 * returns true if and only if the tree is empty
	 * 
	 * preconditions: none
	 * postcondition: return true iff the data structure
	 * does not contain any item
	 */
	public boolean empty() {
		return root.isNil();
	}

	/**
	 * public boolean contains(int i)
	 * 
	 * returns true if and only if the tree contains i
	 *  
	 * preconditions: none
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
	 * public void insert(int i)
	 * 
	 * inserts the integer i into the binary tree; the tree
	 * must remain valid (keep its invariants).
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
					} else if (newNode == newNode.getParent().getRightChild()) {
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
				} else {
					y = newNode.getGrandParent().getLeftChild();
	
					if (!y.isNil() && y.isRed()) {
						newNode.getParent().setBlack();
						y.setBlack();
						newNode.getGrandParent().setRed();
						newNode = newNode.getGrandParent();
					} else if (newNode == newNode.getParent().getLeftChild()) {
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
			
			getRoot().setBlack();
		}
	}

	/**
	 * public void delete(int i)
	 * 
	 * deletes the integer i from the binary tree, if it is there;
	 * the tree must remain valid (keep its invariants).
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
				y = z;
			} else {
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
	
	//TODO doc
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
	
	//TODO doc
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
	 * public int min()
	 * 
	 * Returns the smallest key in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * precondition: none
	 * postcondition: none
	 */
	public int min() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().minNode().getKey();
		}
	}

	/**
	 * public int max()
	 * 
	 * Returns the largest key in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * precondition: none
	 * postcondition: none
	 */
	public int max() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().maxNode().getKey();
		}
	}

	/**
	 * public int[] toIntArray()
	 * 
	 * returns an int[] array containing the values stored in the tree,
	 * in ascending order.
	 * 
	 * preconditions: none
	 * postconditions: returns an array containing exactly the tree's elements in
	 *                 ascending order.
	 */
	public int[] toIntArray() {
		int[] arr = new int[size()];
		getRoot().fillIntArray(arr, 0);
		return arr;
	}

	/**
	 * public boolean isValid()
	 *
	 * Returns true if and only if the tree is a valid red-black tree.
	 *
	 * precondition: none
	 * postcondition: none
	 *   
	 */
	public boolean isValid() {
		return isBlackValid() && isRedValid();
	}


	//TODO doc
	public boolean isRedValid() {
		if (empty()) {
			return true; 
		} else {
			return getRoot().isRedValid();
		}
	}

	//TODO doc
	public boolean isBlackValid() {
		if (empty()) {
			return true; 
		} else {
			return getRoot().isBlackValid();
		}
	}

	/**
	 * public int maxDepth()
	 * 
	 * Returns the maximum depth of a node in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * precondition: none
	 * postcondition: none
	 */
	public int maxDepth() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().maxDepth();
		}
	}

	/**
	 * public int minLeafDepth()
	 * 
	 * Returns the minimum depth of a leaf in the tree. If the tree
	 * is empty, returns -1;
	 * 
	 * precondition: none
	 * postcondition: none
	 */
	public int minLeafDepth() {
		if (empty()) {
			return -1;
		} else {
			return getRoot().minLeafDepth();
		}
	}

	/**
	 * public int size()
	 * 
	 * Returns the number of nodes in the tree.
	 * 
	 * precondition: none
	 * postcondition: none
	 */
	public int size() {
		return size;
	}

	//TODO document
	public String toString() {
		if (!empty()) {
			return String.format("<Tree %s>", root);
		} else {
			return "<Tree empty>";
		}
	}

	/**
	 * precondition: x != null, x.right != null
	 * postcondition: rotates x to the left
	 * @param x
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
	 * precondition: x != null, x.right != null
	 * postcondition: rotates x to the right
	 * @param x
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
	//TODO document everything!
	public class RBNode {
		private int key;
		private boolean isBlack;
		private RBNode leftChild;
		private RBNode rightChild;
		private RBNode parent;

		public RBNode(int key, boolean isBlack) {
			this.key = key;
			this.isBlack = isBlack;
			
			if (!isNil()) {
				setLeftChild(new RBNode(-1)); //TODO
				setRightChild(new RBNode(-1)); //TODO
			}
		}
		
		public boolean isNil() {
			return this.key == -1; //TODO
		}

		public RBNode(int key) {
			this(key, true);
		}

		public RBNode getParent() {
			return this.parent;
		}
		
		public boolean hasParent() {
			return parent != null;
		}

		public RBNode getGrandParent() {
			return getParent().getParent();
		}
		
		public boolean hasGrandParent() {
			return hasParent() && getParent().hasParent();
		}

		public void setParent(RBNode parent) {
			this.parent = parent;
		}

		public int getKey() {
			return key;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public boolean isBlack() {
			return isBlack;
		}

		public void setBlack() {
			this.isBlack = true;
		}
		
		public void setBlack(boolean isBlack) {
			this.isBlack = isBlack;
		}

		public boolean isRed() {
			return !isBlack();
		}

		public void setRed() {
			this.isBlack = false;
		}

		public RBNode getLeftChild() {
			return leftChild;
		}

		public void setLeftChild(RBNode leftChild) {
			this.leftChild = leftChild;

			if (hasLeftChild()) {
				leftChild.setParent(this);
			}
		}

		public RBNode getRightChild() {
			return rightChild;
		}

		public void setRightChild(RBNode rightChild) {
			this.rightChild = rightChild;

			if (hasRightChild()) {
				rightChild.setParent(this);
			}
		}

		public boolean isLeaf() {
			return !hasLeftChild() && !hasRightChild();
		}

		public boolean hasLeftChild() {
			return !leftChild.isNil();
		}

		public boolean hasRightChild() {
			return !rightChild.isNil();
		}
		
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

		public boolean contains(int i) {
			return search(i) != null;
		}

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
		
		public RBNode minNode() {
			if (hasLeftChild()) {
				return getLeftChild().minNode();
			} else {
				return this;
			}
		}
		
		public RBNode maxNode() {
			if (hasRightChild()) {
				return getRightChild().maxNode();
			} else {
				return this;
			}
		}
		
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

		public String toString() {
			String leftString  = hasLeftChild() ? getLeftChild().toString() : "x";
			String rightString = hasRightChild() ? getRightChild().toString() : "x";
			
			return String.format("[ %d%s %s %s ]", getKey(), isBlack() ? "b" : "r", leftString, rightString);
		}
		
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
		
		private int blackDepth() {
			int me = isBlack() ? 1 : 0;
			if (hasLeftChild()) {
				return me + getLeftChild().blackDepth();
			} else {
				return me;
			}
		}
		
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

