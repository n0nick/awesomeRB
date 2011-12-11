/**
 * 
 * RBTree
 * 
 * An implementation of a Red Black Tree with
 * non-negative, distinct integer values
 * 
 */

public class RBTree {
	
	// TODO: change to private
	private RBNode root;

  /**
   * public boolean empty()
   * 
   * returns true if and only if the tree is empty
   *  
   * preconditions: none
   * postcondition: return true iff the data structure does not contain any item
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
  public boolean contains(int i)
  {
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
	   if (root == null) {
		   root = new RBNode(i);
	   }
	   else {
		   root.insert(i);
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
   public void delete(int i)
   {
	   return;	// to be replaced by student code 
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
   public int min()
   {
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
   public int max()
   {
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
  public int[] toIntArray()
  {
	 int[] arr = new int[42]; //
	 return arr; //	 to be replaced by student code
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
    public boolean isValid() 
    {
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
   public int maxDepth()
   {
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
   public int minLeafDepth()
   {
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
   public int size()
   {
	   return 42; // to be replaced by student code
   }
   
   public String toString()
   {
	   return root.toString();
   }

  /**
   * public class RBNode
   * 
   * If you wish to implement classes other than RBTree
   * (for example RBNode), do it in this file, not in 
   * another file 
   *  
   */
  public class RBNode{
	private int key;
	private boolean isBlack;
	private RBNode leftChild;
	private RBNode rightChild;
	
	public RBNode(int key, boolean isBlack) {
		this.key = key;
		this.isBlack = isBlack;
	}
	
	public RBNode(int key) {
		this(key, true);
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
	
	public void setBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}
	
	public RBNode getLeftChild() {
		return leftChild;
	}
	
	public void setLeftChild(RBNode leftChild) {
		this.leftChild = leftChild;
	}
	
	public RBNode getRightChild() {
		return rightChild;
	}
	
	public void setRightChild(RBNode rightChild) {
		this.rightChild = rightChild;
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
	
	// TODO: ask about keys being unique
	public void insert(int i) {
		if(i < this.getKey()) {
			if(this.hasLeftChild()) {
				this.getLeftChild().insert(i);
			}
			else {
				this.setLeftChild(new RBNode(i));
			}
		}
		else if(i > this.getKey()) {
			if(this.hasRightChild()) {
				this.getRightChild().insert(i);
			}
			else {
				this.setRightChild(new RBNode(i));
			}
		}
	}
	
	// TODO: remove this
	public String toString() {
		String st;
		st = "[ "+getKey()+" ";
		st+= hasLeftChild() ? getLeftChild().toString() : "x";
		st+= " ";
		st+= hasRightChild() ? getRightChild().toString() : "x";
		st+= " ]";
		return st;
	}
	
  }
  
  /**
 * @original author Shai Vardi
 * Modified for semester 2011/2012 a
 */

}
  
