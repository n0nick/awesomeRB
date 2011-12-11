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
	 * preconditions: none postcondition: return true iff the data structure
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
	public void insert(int i) {

		RBNode newNode = new RBNode(i);

		if (root == null) {
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

				newNode.getParent().setBlack();
				newNode.getGrandParent().setRed();
				rightRotate(newNode.getGrandParent());
			} else {

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
		return 42; // to be replaced by student code
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
		return 42; // to be replaced by student code
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
		int[] arr = new int[42]; //
		return arr; // to be replaced by student code
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
		return 42; // to be replaced by student code
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
		return 42; // to be replaced by student code
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
		return 42; // to be replaced by student code
	}

	//TODO document
	public String toString() {
		if (!empty()) {
			return String.format("<Tree %s>", root.toString());
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

		if (y.getLeftChild() != null) {
			y.getLeftChild().setParent(x);
		}

		y.setParent(x.getParent());

		if (x.getParent() == null) {
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

		if (y.getRightChild() != null) {
			y.getRightChild().setParent(x);
		}

		y.setParent(x.getParent());

		if (x.getParent() == null) {
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

		public RBNode getGrandParent() {
			return getParent().getParent();
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
			if (newNode.getKey() < this.getKey()) {
				if (this.hasLeftChild()) {
					this.getLeftChild().insert(newNode);
				} else {
					this.setLeftChild(newNode);
				}
			} else if (newNode.getKey() > this.getKey()) {
				if (this.hasRightChild()) {
					this.getRightChild().insert(newNode);
				} else {
					this.setRightChild(newNode);
				}
			}
		}

		// TODO: remove this
		public String toString() {
			String leftString  = hasLeftChild() ? getLeftChild().toString() : "x";
			String rightString = hasRightChild() ? getRightChild().toString() : "x";
			
			return String.format("[ %d %s %s ]", getKey(), leftString, rightString);
		}

	}

	/**
	 * @original author Shai Vardi
	 * Modified for semester 2011/2012 a
	 */

}

