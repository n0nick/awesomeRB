package awesomeRB;

import java.util.ArrayList;
import java.util.Random;

public class medidot {
	
	private static boolean contains(int[] ar, int x) {
		for (int y : ar) {
			if (y == x) {
				return true;
			}
		}
		return false;
	}
	
	private static int[] generateRandomSet(int count) {
		int[] ar = new int[count];
		int x;
		Random rnd = new Random();
		
		for (int i=0; i<count; i++) {
			x = rnd.nextInt(count*30)+1;
			
			while (contains(ar, x)) {
				x = rnd.nextInt(count*30)+1;
			}
			
			ar[i] = x;;
		}
		
		return ar;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		for (int count=10000; count<=100000; count+=10000) {
			
			int[] numbers = generateRandomSet(count);
			RBTree tree = new RBTree();
			
			for(int i = 0; i < numbers.length; i++) {
				tree.insert(numbers[i]);
			}
			
			System.out.println("Results for size " + count);
			System.out.println("size:" + tree.size());
			System.out.println("max depth: " + tree.maxDepth());
			System.out.println("max depth / min leaf depth: " + (tree.maxDepth()/tree.minLeafDepth()));
		}
	}

}
