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
		
		assertEquals("<Tree [ 1 x [ 2 x [ 3 x [ 4 x x ] ] ] ]>", tree.toString());
	}
	
	@Test public void insertTest2() {
		RBTree tree = createSomeTestTree1();
		assertEquals("<Tree [ 1 x [ 12 [ 2 x x ] [ 13 x x ] ] ]>", tree.toString());
	}
	
	@Test public void insertTest3() {
		RBTree tree = createSomeTestTree2();
		assertEquals(false, tree.empty());
	}
	
	@Test public void rotateLeftTest1() {
		RBTree tree = createSomeTestTree1();
		tree.leftRotate(tree.getRoot().getRightChild());
		assertEquals("<Tree [ 1 x [ 13 [ 12 [ 2 x x ] x ] x ] ]>", tree.toString());
	}
		
	@Test public void rotateRightTest1() {
		RBTree tree = createSomeTestTree1();
		tree.rightRotate(tree.getRoot().getRightChild());
		assertEquals("<Tree [ 1 x [ 2 x [ 12 x [ 13 x x ] ] ] ]>", tree.toString());
	}
	
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
		RBTree tree = createSomeTestTree1();
		assertEquals(4, tree.size());
	}
	
	@Test public void insertSizeTest1() {
		RBTree tree = createSomeTestTree1();
		tree.insert(6);
		assertEquals(5, tree.size());
	}
	
	//TODO isValidTest()
	//TODO maxDepthTest()
	//TODO minLeafDepthTest()
}
