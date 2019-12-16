import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test Class for BALST.java
 * 
 * 
 * @author Wally Estenson
 */
//@SuppressWarnings("rawtypes")
public class BPTree_Tests {

	//instance of the balst tree 
	BPTree<Double, Double> bptree;

	/**
	 * Before each test, create tree
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		bptree = createInstance();
	}

	/**
	 * After each test, destroy tree
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		bptree = null;
	}

	protected BPTree<Double, Double> createInstance() {
		return new BPTree<>(3);
	}



	/**
	 * Insert three values in sorted order and then check the root, left, and right
	 * keys to see if rebalancing occurred.
	 */
	@Test
	void testBALST_001_insert_sorted_order_simple() {

		try {
		// create a pseudo random number generator
		//Random rnd1 = new Random();

		// some value to add to the BPTree
		Double[] dd = { 0.0d, 0.5d, 0.2d, 0.8d };

		// build an ArrayList of those value and add to BPTree also
		// allows for comparing the contents of the ArrayList
		// against the contents and functionality of the BPTree
		// does not ensure BPTree is implemented correctly
		// just that it functions as a data structure with
		// insert, rangeSearch, and toString() working.
		List<Double> list = new ArrayList<>();
		
		//for (int i = 0; i < 400; i++) {
			//Double j = dd[rnd1.nextInt(4)];
			//list.add(j);
			
		bptree.insert(1.0, 2.0);
		bptree.insert(2.0, 2.0);
		bptree.insert(3.0, 2.0);
		bptree.insert(4.0, 2.0);
		bptree.insert(5.0, 2.0);
		bptree.insert(6.0, 2.0);
		//	System.out.println("\n\nTree structure:\n" + bptree.toString());
		//}
		//List<Double> filteredValues = bptree.rangeSearch(0.2d, ">=");
		//System.out.println("Filtered values: " + filteredValues.toString());
			//if (!balst2.getKeyAtRoot().equals(10))
				//fail("avl insert at root does not work");

			// IF rebalancing is working,
			// the tree should have 20 at the root
			// and 10 as its left child and 30 as its right child

			//Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
			//Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
			//Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));


		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception AVL 000: " + e.getMessage());
		}
	}
	

	/**
	 * Insert three values in sorted order and then check the root, left, and right
	 * keys to see if rebalancing occurred.
	 */
	@Test
	void testBALST_002_insert_sorted_order_simple() {

		try {

			
		bptree.insert(1.0, 2.0);
		bptree.insert(2.0, 2.0);
		bptree.insert(3.0, 2.0);
		bptree.insert(4.0, 2.0);
		bptree.insert(5.0, 2.0);
		bptree.insert(6.0, 2.0);
		bptree.insert(7.0, 2.0);
		bptree.insert(8.0, 2.0);
		bptree.insert(9.0, 2.0);
		bptree.insert(10.0, 2.0);
		
			System.out.println("\n\nTree structure:\n" + bptree.toString());
			System.out.println(bptree.size());
		


		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception AVL 000: " + e.getMessage());
		}
	}
	/**
	 * Insert three values in sorted order and then check the root, left, and right
	 * keys to see if rebalancing occurred.
	 */
	@Test
	void testBALST_003_insert_sorted_order_simple() {

		try {

		Double[] dd = { 0.0d, 0.5d, 0.2d, 0.8d };

		List<Double> list = new ArrayList<>();

			
		bptree.insert(100.0, 2.0);
		bptree.insert(20.0, 2.0);
		bptree.insert(300.0, 2.0);
		bptree.insert(40.0, 2.0);
		bptree.insert(500.0, 2.0);
		bptree.insert(60.0, 2.0);
		bptree.insert(70.0, 2.0);
		bptree.insert(800.0, 2.0);
		bptree.insert(90.0, 2.0);
		bptree.insert(10.0, 2.0);
			System.out.println("\n\nTree structure:\n" + bptree.toString());



		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception AVL 000: " + e.getMessage());
		}
	}
	
	@Test
	void testBALST_004_insert_sorted_order_simple() {

		try {


			
		bptree.insert(6.0, 6.0);
		bptree.insert(3.0, 3.0);
		bptree.insert(5.0, 5.0);
		bptree.insert(4.0, 4.0);
		bptree.insert(0.0, 0.0);
		//bptree.insert(2.0, 2.0);
		//bptree.insert(1.0, 1.0);

			System.out.println("\n\nTree structure:\n" + bptree.toString());
			System.out.println(bptree.rangeSearch(4.0, ">="));

			

		} catch (Exception e) {
			e.printStackTrace();
			fail("Unexpected exception AVL 000: " + e.getMessage());
		}
	}
		
		@Test
		void testBALST_005_insert_sorted_order_simple() {

			try {


			
			bptree.insert(0.0, 0.0);
			bptree.insert(0.1, 8.0);
			bptree.insert(0.2, 10.0);
			bptree.insert(0.2, 13.0);
			bptree.insert(0.2, 18.0);
			
			bptree.insert(0.5, 4.0);
			bptree.insert(0.5, 5.0);
			bptree.insert(0.5, 6.0);
			bptree.insert(0.5, 9.0);
			bptree.insert(0.5, 11.0);
			bptree.insert(0.5, 12.0);
			bptree.insert(0.5, 14.0);
			bptree.insert(0.5, 15.0);
			bptree.insert(0.5, 16.0);
			bptree.insert(0.5, 17.0);
			bptree.insert(0.8, 7.0);
			bptree.insert(0.8, 19.0);
			bptree.insert(1.0, 1.0);
			bptree.insert(2.0, 2.0);
			bptree.insert(3.0, 3.0);
		

				System.out.println("\n\nTree structure:\n" + bptree.toString());
				System.out.println(bptree.rangeSearch(0.1, ">="));

				

			} catch (Exception e) {
				e.printStackTrace();
				fail("Unexpected exception AVL 000: " + e.getMessage());
			}
	}
}