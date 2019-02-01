import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;
import org.junit.Test;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
/**
*  Test class for Doubly Linked List
*
*  @author  Anna Maliakal 
*  @version 19/10/18 13:50
*/
@RunWith(JUnit4.class)
public class DoublyLinkedListTest {

    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
      new DoublyLinkedList<Integer>();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    /**
     * Check if the insertBefore works
     */
    @Test
    public void testInsertBefore()
    {
        // test non-empty list
        DoublyLinkedList<Integer> testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);
        testDLL.insertBefore(1,2);
        testDLL.insertBefore(2,3);

        testDLL.insertBefore(0,4);
        assertEquals( "Checks insertBefore to a list containing 3 elements at position 0", "4,1,2,3", testDLL.toString() );
        testDLL.insertBefore(1,5);
        assertEquals( "Checks insertBefore to a list containing 4 elements at position 1", "4,5,1,2,3", testDLL.toString() );
        testDLL.insertBefore(2,6);       
        assertEquals( "Checks insertBefore to a list containing 5 elements at position 2", "4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(-1,7);        
        assertEquals( "Checks insertBefore to a list containing 6 elements at position -1 - expected the element at the head of the list", "7,4,5,6,1,2,3", testDLL.toString() );
        testDLL.insertBefore(7,8);        
        assertEquals( "Checks insertBefore to a list containing 7 elemenets at position 8 - expected the element at the tail of the list", "7,4,5,6,1,2,3,8", testDLL.toString() );
        testDLL.insertBefore(700,9);        
        assertEquals( "Checks insertBefore to a list containing 8 elements at position 700 - expected the element at the tail of the list", "7,4,5,6,1,2,3,8,9", testDLL.toString() );
        testDLL.insertBefore(3, 10);
        assertEquals( "Checks insertBefore to a list containing 9 elements at position 3 - expected the element at the tail of the list", "7,4,5,10,6,1,2,3,8,9", testDLL.toString() );
        
        // test empty list
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(0,1);        
        assertEquals( "Checks insertBefore to an empty list at position 0 - expected the element at the head of the list", "1", testDLL.toString() );
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(10,1);        
        assertEquals( "Checks insertBefore to an empty list at position 10 - expected the element at the head of the list", "1", testDLL.toString() );
        
        testDLL = new DoublyLinkedList<Integer>();
        testDLL.insertBefore(-10,1);        
        assertEquals( "Checks insertBefore to an empty list at position -10 - expected the element at the head of the list", "1", testDLL.toString() );
     }

    // TODO: add more tests here. Each line of code in DoublyLinkedList.java should
    // be executed at least once from at least one test.
    
    @Test
    public void testget(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.push(1);
    	test.push(2);
    	test.push(3);
    	assertEquals( "Checks get() after adding 3 elements at pos 0", "3", test.get(0).toString());
    	test.push(4);
    	test.push(5);
    	test.push(6);
    	test.push(7);
    	assertEquals( "Checks get() after adding 4 elements at pos 3", "4", test.get(3).toString());
    	assertEquals( "Checks get() for pos greater than size of list(20)", null, test.get(20));
    	assertEquals( "Checks get() for pos less than size of list(-20)", null, test.get(-20));

    	
    }
    
    @Test
    public void testInsert(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.insertBefore(-1,1);
    	test.deleteAt(0);
    	test.insertBefore(100,2);
    	test.insertBefore(100,3);
    	assertEquals("2,3",test.toString());
    }
    @Test
    public void testDeleteAt(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.push(1);
    	test.push(2);
    	test.push(3);
    	test.push(4);
    	assertEquals( "Checks deleteAt(head) after adding 3 elements", true, test.deleteAt(0));
    	test.push(5);
    	test.push(6);
    	test.push(7);
    	test.push(8);
    	assertEquals( "Checks deleteAt(100) after adding 4 elements", false, test.deleteAt(100));
    	assertEquals( "Checks deleteAt(tail) after adding 4 elements", true, test.deleteAt(6));
    	test.push(23);
    	test.push(12);
    	assertEquals( "Checks deleteAt(4) after adding 2 elements", true, test.deleteAt(4));
    	assertEquals( "Checks deleteAt(-10) after adding 2 elements, should return false, outside of list", false, test.deleteAt(-10));
    	test = new DoublyLinkedList<Integer>();
    	assertEquals( "Checks deleteAt(100) on empty list", false, test.deleteAt(100));
    	test.push(1);
    	assertEquals( "Checks deleteAt(1) on list with 1 element", false, test.deleteAt(1));
    	test.push(2);
    	test.push(3);
    	test.push(4);
    	assertEquals( "Checks deleteAt(0) on list with 4 element", true, test.deleteAt(0));

    }
    
    @Test
    public void testReverse(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.push(1);
    	test.push(2);
    	test.push(3);
    	test.push(4);
    	test.reverse();
    	assertEquals( "Checks reverse() after adding 4 elements", "1,2,3,4", test.toString());
    	test = new DoublyLinkedList<Integer>();
    	test.reverse();
    	assertEquals( "Checks reverse() on empty list", "", test.toString());
    	test.push(1);
    	assertEquals( "Checks reverse() list with 1 element", "1", test.toString());
    }
    
    @Test
    public void testMakeUnique(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.push(4);
    	test.push(4);
    	test.push(2);
    	test.push(3);
    	test.push(4);
    	test.push(4);
    	test.push(5);
    	test.push(6);
    	test.push(7);
    	test.push(8);
    	test.push(4);
    	test.makeUnique();
    	assertEquals( "Checks makeeUnique() after adding 10 elements", "4,8,7,6,5,3,2", test.toString());
    	test = new DoublyLinkedList<Integer>();
    	test.makeUnique();
    	assertEquals( "Checks makeUnique() on empty list", "", test.toString());
    }
    	
    @Test
    public void testPush(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.push(1);
    	assertEquals( "Checks push() after adding 1 elements", "1", test.toString());
    	test.push(null);
    	assertEquals( "Checks push() after pushing null, list should be unchanged", "1", test.toString());
    	test.push(2);
    	test.push(3);
    	test.push(4);
    	test.push(5);
    	assertEquals( "Checks push() after pushing 4 more elements", "5,4,3,2,1", test.toString());
    	test.push(null);
    	assertEquals( "Checks push() after pushing null, list should be unchanged", "5,4,3,2,1", test.toString());
    }
   
    @Test
    public void testPop(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.push(1);
    	test.push(2);
    	test.push(3);
    	test.push(4);
    	test.push(5);
    	int popped = test.pop();
    	assertEquals("Checks if pop() popped correct element", 5, popped);
    	test = new DoublyLinkedList<Integer>();
    	assertEquals("Checks if pop() returns null when list is empty", null, test.pop());
    	test.push(1);
    	popped = test.pop();
    	assertEquals("Checks if pop() for 1 element list", 1, popped);
    }
    
    @Test
    public void testEnqueue(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.enqueue(1);
    	test.enqueue(2);
    	test.enqueue(3);
    	test.enqueue(4);
    	test.enqueue(5);
    	
    	assertEquals("Checks if items enqueued properly", "1,2,3,4,5", test.toString());
    	test.enqueue(null);
    	assertEquals("Checks if items enqueued properly", "1,2,3,4,5", test.toString());
    }
    
    @Test
    public void testDequeue(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.enqueue(1);
    	test.enqueue(2);
    	test.enqueue(3);
    	test.enqueue(4);
    	test.enqueue(5);
    	test.dequeue();
    	assertEquals("Checks if items enqueued properly", "2,3,4,5", test.toString());
    	System.out.println(test.toString());

    	test = new DoublyLinkedList<Integer>();
    	assertEquals("Checks if dequeue() returned null on empty list", null, test.dequeue());
    	test.enqueue(1);
    	test.enqueue(2);
    	test.enqueue(10);
    	System.out.println(test.toString());
    	test.dequeue();
    	System.out.println(test.toString());

    	test.dequeue();
    	System.out.println(test.toString());

    	assertEquals("Checks if dequeue() last element in 3 element queue", "10", test.dequeue().toString());
    	test = new DoublyLinkedList<Integer>();
    	test.dequeue();
    	System.out.println(test.toString());
    	assertEquals("Checks if dequeue() returns null on empty list", "", test.toString());
    	test.enqueue(10);
    	test.dequeue();
    	assertEquals("Checks if dequeue() returns null on empty list", "", test.toString());
    }
 
    @Test
    public void testGetSize(){
    	DoublyLinkedList<Integer> test = new DoublyLinkedList<Integer>();
    	test.enqueue(1);
    	test.enqueue(2);
    	test.enqueue(3);
    	test.enqueue(4);
    	test.enqueue(5);
    	assertEquals("Checking if getSize() returns correct size of list", 5, test.getSize());
    }
}