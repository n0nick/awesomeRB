package awesomeRB.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import awesomeRB.RBTree;


public class RBTreeTest {
	private RBTree createSomeTestTree1() {
		RBTree tree = new RBTree();
		tree.insert(1);
		tree.insert(12);
		tree.insert(13);
		tree.insert(2);
		
		return tree;
	}
	
	private RBTree createSomeTestTree2() {
		RBTree tree = new RBTree();
		tree.insert(13);
		tree.insert(7);
		tree.insert(8);
		tree.insert(16);
		tree.insert(9);
		tree.insert(6);
		tree.insert(5);
		tree.insert(19);
		tree.insert(15);
		tree.insert(4);
		tree.insert(1);
		
		return tree;
	}
	
	@Test public void emptyNewTreeTest() {
		RBTree tree = new RBTree();
		assertEquals(true, tree.empty());
	}

	@Test public void containsTest1() {
		RBTree tree = createSomeTestTree1();
		assertEquals(true, tree.contains(12));
		assertEquals(true, tree.contains(2));
		assertEquals(true, tree.contains(13));
		assertEquals(false, tree.contains(495));
		assertEquals(false, tree.contains(10));
	}
	
	@Test public void containsTest2() {
		RBTree tree = createSomeTestTree2();
		
		assertEquals(true, tree.contains(1));
	}
	
	@Test public void insertTest1() {
		RBTree tree = new RBTree();
		
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		
		assertEquals("<Tree [ 2b [ 1b x x ] [ 3b x [ 4r x x ] ] ]>", tree.toString());
	}
	
	@Test public void insertTest2() {
		RBTree tree = createSomeTestTree1();
		assertEquals("<Tree [ 12b [ 1b x [ 2r x x ] ] [ 13b x x ] ]>", tree.toString());
	}
	
	@Test public void insertTest3() {
		RBTree tree = createSomeTestTree2();
		assertEquals(false, tree.empty());
	}
	
	@Test public void insertTest4() {
		RBTree tree = new RBTree();
		
		tree.insert(1);
		tree.insert(3);
		tree.insert(13);
		tree.insert(15);
		tree.insert(24);
		tree.insert(15);
	}
	
	/*
	@Test public void rotateLeftTest1() {
		RBTree tree = createSomeTestTree1();
		tree.leftRotate(tree.getRoot());
		assertEquals("<Tree [ 13b [ 12b [ 1b x [ 2r x x ] ] x ] x ]>", tree.toString());
	}
		
	@Test public void rotateRightTest1() {
		RBTree tree = createSomeTestTree1();
		tree.rightRotate(tree.getRoot());
		assertEquals("<Tree [ 1b x [ 12b [ 2r x x ] [ 13b x x ] ] ]>", tree.toString());
	}
	*/
	
	@Test public void deleteTest1() {
		RBTree tree = new RBTree();
		tree.insert(3);
		tree.delete(3);
		assertEquals(true, tree.empty());
	}
	
	@Test public void deleteTest2() {
		RBTree tree = createSomeTestTree1();
		tree.delete(13);
		assertEquals(false, tree.contains(13));
		assertEquals(3, tree.size());
		assertEquals(12, tree.max());
		assertEquals(true, tree.isValid());
	}

	@Test public void deleteTest3() {
		RBTree tree = createSomeTestTree1();
		tree.delete(12);
		assertEquals(false, tree.contains(12));
		assertEquals(3, tree.size());
		assertEquals(13, tree.max());
		assertEquals(true, tree.isValid());
	}
	
	@Test public void deleteTest4() {
		RBTree tree = createSomeTestTree2();
		
		tree.delete(8);
		assertEquals(10, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(16);
		assertEquals(9, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(7);
		assertEquals(8, tree.size());
		assertEquals(true, tree.isValid());
		
		tree.delete(4);
		assertEquals(7, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(9);
		assertEquals(6, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(5);
		assertEquals(5, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(19);
		assertEquals(4, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(13);
		assertEquals(3, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(6);
		assertEquals(2, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(1);
		assertEquals(1, tree.size());
		assertEquals(true, tree.isValid());

		tree.delete(5);
		assertEquals(1, tree.size());
		assertEquals(true, tree.isValid());
		
		tree.delete(15);
		assertEquals(0, tree.size());
		assertEquals(true, tree.isValid());
		assertEquals(true, tree.empty());
	}
	
	@Test public void deleteTest5() {
		RBTree tree = new RBTree();
		tree.delete(1);
		
		assertEquals(true, tree.isValid());
		assertEquals(true, tree.empty());
	}
	
	@Test public void minTest1() {
		RBTree tree = createSomeTestTree1();
		assertEquals(1, tree.min());
	}
	
	@Test public void maxTest1() {
		RBTree tree = createSomeTestTree1();
		assertEquals(13, tree.max());
	}
	
	@Test public void toIntArrayTest1() {
		RBTree tree = createSomeTestTree1();
		int[] expected = {1, 2, 12, 13};
		assertArrayEquals(expected, tree.toIntArray());
	}
	
	@Test public void toIntArrayTest2() {
		RBTree tree = new RBTree();
		tree.insert(7);
		tree.insert(3);
		tree.insert(300);
		tree.insert(5);
		tree.insert(6);
		
		int[] expected = {3, 5, 6, 7, 300};
		assertArrayEquals(expected, tree.toIntArray());
	}
	
	@Test public void sizeTest1() {
		RBTree tree = new RBTree();
		tree.insert(1);
		assertEquals(1, tree.size());
	}
	
	@Test public void sizeTest2() {
		RBTree tree = createSomeTestTree1();
		assertEquals(4, tree.size());
	}
	
	@Test public void insertSizeTest1() {
		RBTree tree = createSomeTestTree1();
		tree.insert(6);
		assertEquals(5, tree.size());
	}
	
	@Test public void maxDepthTest1() {
		RBTree tree = new RBTree();
		tree.insert(6);
		assertEquals(0, tree.maxDepth());
	}
	
	@Test public void maxDepthTest2() {
		RBTree tree = new RBTree();
		tree.insert(6);
		tree.insert(3);
		tree.insert(5);
		tree.insert(1);
		assertEquals(2, tree.maxDepth());
		tree.insert(7);
		assertEquals(2, tree.maxDepth());
	}
	
	@Test public void maxDepthTest3() {
		RBTree tree = createSomeTestTree1();
		assertEquals(2, tree.maxDepth());
	}
	
	@Test public void minLeafDepthTest1() {
		RBTree tree = createSomeTestTree1();
		assertEquals(1, tree.minLeafDepth());
		tree.insert(0);
		assertEquals(1, tree.minLeafDepth());
	}

	@Test public void minLeafDepthTest2() {
		RBTree tree = new RBTree();
		assertEquals(-1, tree.minLeafDepth());
		tree.insert(1);
		assertEquals(0, tree.minLeafDepth());
		tree.insert(2);
		assertEquals(1, tree.minLeafDepth());
	}
	
	@Test public void isValidTest1() {
		RBTree tree = createSomeTestTree1();
		assertEquals(true, tree.isValid());
	}
	
	@Test public void isValidTest2() {
		RBTree tree = createSomeTestTree1();
		tree.getRoot().getRightChild().setRed();
		assertEquals(false, tree.getRoot().isBlackValid());
		assertEquals(false, tree.isValid());
	}

	@Test public void isValidTest3() {
		RBTree tree = createSomeTestTree1();
		tree.getRoot().getLeftChild().setRed();
		assertEquals(false, tree.getRoot().isBlackValid());
		assertEquals(false, tree.getRoot().isRedValid());
		assertEquals(false, tree.isValid());
	}
	
	@Test public void isValidTest4() {
		RBTree tree = createSomeTestTree1();
		tree.getRoot().setKey(50);
		assertEquals(false, tree.getRoot().isBSTValid());
		assertEquals(false, tree.isValid());
	}
	
	@Test public void deleteMax() {
		RBTree tree = new RBTree();
		tree.insert(8);
		tree.insert(3);
		tree.delete(8);
		assertEquals("<Tree [ 3b x x ]>", tree.toString());
	}
}
