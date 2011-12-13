package awesomeRB;


/**
 * 
 * RBTree
 * 
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer values
 * 
 */

public class RBTree {
	
	//TODO document
	private RBNode root;

	//TODO document
	public RBNode getRoot() {
		return this.root;
	}

	//TODO document
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
		return root == null;
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
	//TODO should we update a 'size' property on INS/DEL ?
	public void insert(int i) {

		RBNode newNode = new RBNode(i);

		if (empty()) {
			setRoot(newNode);
		} else {
			this.redBlackInsert(newNode);
		}
	}

	//TODO document
	public void redBlackInsert(RBNode newNode) {
		RBNode y;

		root.insert(newNode);
		newNode.setRed();

		while ((newNode != root) && (!newNode.getParent().isBlack())) {
			//TODO bug when grandParent does not have leftChild
			if (newNode.getParent() == newNode.getGrandParent().getLeftChild()) {
				y = newNode.getGrandParent().getLeftChild();

				if (!y.isBlack()) {
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
				y = newNode.getGrandParent().getRightChild();

				if (!y.isBlack()) {
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
		return; // to be replaced by student code
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
			return (getRoot().min());
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
			return (getRoot().max());
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
		int[] arr = new int[this.size()];
		this.getRoot().fillIntArray(arr, 0);
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
		return false; // should be replaced by student code
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
			return 0;
		} else {
			return (this.getRoot().maxDepth());
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
			return 0;
		} else {
			return (this.getRoot().minLeafDepth());
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
		if (empty()) {
			return 0;
		} else {
			return (this.getRoot().size());
		}
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
			this.setRoot(y);
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
			this.setRoot(y);
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

			if (leftChild != null) {
				leftChild.setParent(this);
			}
		}

		public RBNode getRightChild() {
			return rightChild;
		}

		public void setRightChild(RBNode rightChild) {
			this.rightChild = rightChild;

			if (rightChild != null) {
				rightChild.setParent(this);
			}
		}

		public boolean isLeaf() {
			return !hasLeftChild() && !hasRightChild();
		}

		public boolean hasLeftChild() {
			return leftChild != null;
		}

		public boolean hasRightChild() {
			return rightChild != null;
		}

		public boolean contains(int i) {
			if (getKey() == i) {
				return true;
			} else {
				if (i < getKey() && hasLeftChild()) {
					return getLeftChild().contains(i);
				} else if (hasRightChild()) {
					return getRightChild().contains(i);
				}
			}
			return false;
		}

		public void insert(RBNode newNode) {
			if (newNode.getKey() < getKey()) {
				if (hasLeftChild()) {
					getLeftChild().insert(newNode);
				} else {
					setLeftChild(newNode);
				}
			} else if (newNode.getKey() > getKey()) {
				if (hasRightChild()) {
					getRightChild().insert(newNode);
				} else {
					setRightChild(newNode);
				}
			}
		}
		
		public int min() {
			if (hasLeftChild()) {
				return getLeftChild().min();
			} else {
				return getKey();
			}
		}
		
		public int max() {
			if (hasRightChild()) {
				return getRightChild().max();
			} else {
				return getKey();
			}
		}
		
		public int size() {
			int size = 1;
			
			if (hasLeftChild()) {
				size+= getLeftChild().size();
			}
			if (hasRightChild()) {
				size+= getRightChild().size();
			}
			
			return size;
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
			
			return String.format("[ %d %s %s ]", getKey(), leftString, rightString);
		}
		
		public int maxDepth() {
			int downMax = 0;
			
			if (hasLeftChild() && hasRightChild()) {
				downMax+= Math.max(getLeftChild().maxDepth(), 
								getRightChild().maxDepth());
			} else if (hasLeftChild()) {
				downMax+= getLeftChild().maxDepth();
			} else if (hasRightChild()) {
				downMax+= getRightChild().maxDepth();
			}

			return 1 + downMax;
		}
		
		public int minLeafDepth() {
			int downMin = 0;
			
			if (hasLeftChild() && hasRightChild()) {
				downMin+= Math.min(getLeftChild().maxDepth(), 
									getRightChild().maxDepth());
			} else if (hasLeftChild()) {
				downMin+= getLeftChild().maxDepth();
			} else if (hasRightChild()) {
				downMin+=  getRightChild().maxDepth();
			}
			
			return 1 + downMin;
		}

	}

	/**
	 * @original author Shai Vardi
	 * Modified for semester 2011/2012 a
	 */

}

