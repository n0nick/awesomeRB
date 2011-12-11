package awesomeRB.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import awesomeRB.RBTree;


public class RBTreeTest {
	
	@Test public void emptyNewTreeTest() {
		RBTree tree = new RBTree();
		assert(tree.empty());
	}
	
	@Test public void insertTest1() {
		RBTree tree = new RBTree();
		
		tree.insert(1);
		tree.insert(2);
		tree.insert(3);
		tree.insert(4);
		
		assertEquals(tree.toString(), "<Tree [ 1 x [ 2 x [ 3 x [ 4 x x ] ] ] ]>");
	}
	
	private RBTree createSomeTestTree() {
		RBTree tree = new RBTree();
		tree.insert(1);
		tree.insert(12);
		tree.insert(13);
		tree.insert(2);
		
		return tree;
	}
	
	@Test public void insertTest2() {
		RBTree tree = createSomeTestTree();
		assertEquals(tree.toString(), "<Tree [ 1 x [ 12 [ 2 x x ] [ 13 x x ] ] ]>");
	}
	
	@Test public void rotateLeftTest1() {
		RBTree tree = createSomeTestTree();
		tree.leftRotate(tree.getRoot().getRightChild());
		assertEquals(tree.toString(), "<Tree [ 1 x [ 13 [ 12 [ 2 x x ] x ] x ] ]>");
	}
		
	@Test public void rotateRightTest1() {
		RBTree tree = createSomeTestTree();
		tree.rightRotate(tree.getRoot().getRightChild());
		assertEquals(tree.toString(), "<Tree [ 1 x [ 2 x [ 12 x [ 13 x x ] ] ] ]>");
	}
	
	@Test public void sizeTest1() {
		RBTree tree = createSomeTestTree();
		assertEquals(tree.size(), 4);
	}
	
	@Test public void insertSizeTest1() {
		RBTree tree = createSomeTestTree();
		tree.insert(6);
		assertEquals(tree.size(), 5);
	}
	
	@Test public void minTest1() {
		RBTree tree = createSomeTestTree();
		assertEquals(tree.min(), 1);
	}
	
	@Test public void maxTest1() {
		RBTree tree = createSomeTestTree();
		assertEquals(tree.min(), 13);
	}
	
	@Test public void containsTest1() {
		RBTree tree = createSomeTestTree();
		assert(tree.contains(12));
		assert(tree.contains(2));
		assert(tree.contains(13));
		assert(!tree.contains(495));
		assert(!tree.contains(10));
	}
	
	@Test public void deleteTest1() {
		RBTree tree = new RBTree();
		tree.insert(3);
		tree.delete(3);
		assert(tree.empty());
	}
	
	@Test public void deleteTest2() {
		RBTree tree = createSomeTestTree();
		tree.delete(13);
		assert(!tree.contains(13));
		assertEquals(tree.size(), 3);
		assertEquals(tree.max(), 12);
	}
	
	@Test public void toIntArrayTest1() {
		RBTree tree = createSomeTestTree();
		int[] expected = {1, 2, 12, 13};
		assertArrayEquals(tree.toIntArray(), expected);
	}
	
	@Test public void toIntArrayTest2() {
		RBTree tree = new RBTree();
		tree.insert(7);
		tree.insert(3);
		tree.insert(300);
		tree.insert(5);
		tree.insert(6);
		
		int[] expected = {3, 5, 6, 7, 300};
		assertArrayEquals(tree.toIntArray(), expected);
	}
	
	//TODO isValidTest()
	//TODO maxDepthTest()
	//TODO minLeafDepthTest()
}
