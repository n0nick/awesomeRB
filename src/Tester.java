
public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RBTree tree = new RBTree();
		tree.insert(1);
		tree.insert(12);
		tree.insert(13);
		tree.insert(2);
		

		System.out.println(tree);
		
		/*
		tree.leftRotate(tree.getRoot().getRightChild());
		System.out.println(tree);
		tree.rightRotate(tree.getRoot().getRightChild());
		System.out.println(tree);
		*/

	}

}
