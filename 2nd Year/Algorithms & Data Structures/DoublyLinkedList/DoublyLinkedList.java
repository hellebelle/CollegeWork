import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

// -------------------------------------------------------------------------
/**
 *  This class contains the methods of Doubly Linked List.
 *
 *  @author Anna Maliakal  
 *  @version 19/10/18 02:39:22
 */

/**
 * Class DoublyLinkedList: implements a *generic* Doubly Linked List.
 * 
 * @param <T>
 *            This is a type parameter. T is used as a class name in the
 *            definition of this class.
 *
 *            When creating a new DoublyLinkedList, T should be instantiated
 *            with an actual class name that extends the class Comparable. Such
 *            classes include String and Integer.
 *
 *            For example to create a new DoublyLinkedList class containing
 *            String data: DoublyLinkedList<String> myStringList = new
 *            DoublyLinkedList<String>();
 *
 *            The class offers a toString() method which returns a
 *            comma-separated sting of all elements in the data structure.
 * 
 *            This is a bare minimum class you would need to completely
 *            implement. You can add additional methods to support your code.
 *            Each method will need to be tested by your jUnit tests -- for
 *            simplicity in jUnit testing introduce only public methods.
 */
class DoublyLinkedList<T extends Comparable<T>> {

	/**
	 * private class DLLNode: implements a *generic* Doubly Linked List node.
	 */
	private class DLLNode {
		public final T data; // this field should never be updated. It gets its
		// value once from the constructor DLLNode.
		public DLLNode next;
		public DLLNode prev;

		/**
		 * Constructor
		 * 
		 * @param theData
		 *            : data of type T, to be stored in the node
		 * @param prevNode
		 *            : the previous Node in the Doubly Linked List
		 * @param nextNode
		 *            : the next Node in the Doubly Linked List
		 * @return DLLNode
		 */
		public DLLNode(T theData, DLLNode prevNode, DLLNode nextNode) {
			data = theData;
			prev = prevNode;
			next = nextNode;
		}
	}

	// Fields head and tail point to the first and last nodes of the list.
	private DLLNode head, tail;

	/**
	 * Constructor of an empty DLL
	 * 
	 * @return DoublyLinkedList
	 */
	public DoublyLinkedList() {
		head = null;
		tail = null;
	}

	/**
	 * Tests if the doubly linked list is empty
	 * 
	 * @return true if list is empty, and false otherwise
	 *
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *
	 *         Justification: As this is one statement, the running time is constant
	 */
	public boolean isEmpty() {
		return head == null;
	}

	/**
	 * Inserts an element in the doubly linked list
	 * 
	 * @param pos
	 *            : The integer location at which the new data should be inserted in
	 *            the list. We assume that the first position in the list is 0
	 *            (zero). If pos is less than 0 then add to the head of the list. If
	 *            pos is greater or equal to the size of the list then add the
	 *            element at the end of the list.
	 * @param data
	 *            : The new data of class T that needs to be added to the list
	 * @return none
	 *
	 *         Worst-case asymptotic running time cost: Theta(n)
	 *
	 *         Justification: The for loop goes through n elements
	 */
	public void insertBefore(int pos, T data) {
		DLLNode node;
		int count = 0;
		if (pos <= 0) {
			if (head == null) {
				node = new DLLNode(data, null, null);
				head = node;
				tail = node;
			}
			else if (head != null) {
				node = new DLLNode(data, null, head);
				head.prev = node;
				head = node;
			}
		} else if (pos >= getSize()) {
			node = new DLLNode(data, tail, null);
			if (tail == null) {
				tail = node;
				head = node;
			}
			else if (tail != null) {
				tail.next = node;
				tail = node;
			}
		} else if (pos > 0 && pos < getSize()) {
			for (DLLNode curr = head; curr != null; curr = curr.next) {
				if (count == pos) {
					node = new DLLNode(data, curr.prev, curr);
					curr.prev.next = node;
					curr.prev = node;
				}
				count++;
			}

		}
	}

	/**
	 * Returns the data stored at a particular position
	 * 
	 * @param pos
	 *            : the position
	 * @return the data at pos, if pos is within the bounds of the list, and null
	 *         otherwise.
	 *
	 *         Worst-case asymptotic running time cost: Theta(n)
	 *
	 *         Justification: the for-loop is going to iterate through n elements
	 *
	 */
	public T get(int pos) {
		T data = null;
		int count = 0;
		if (pos >= 0 && pos < getSize()) {
			for (DLLNode curr = head; curr != null; curr = curr.next) {
				if (count == pos) {
					data = curr.data;
					return data;
				}
				count++;
			}
		}
		return data;
	}

	/**
	 * Deletes the element of the list at position pos. First element in the list
	 * has position 0. If pos points outside the elements of the list then no
	 * modification happens to the list.
	 * 
	 * @param pos
	 *            : the position to delete in the list.
	 * @return true : on successful deletion, false : list has not been modified.
	 *
	 *         Worst-case asymptotic running time cost: Theta(n)
	 *
	 *         Justification: the while loop iterates through n elements
	 */
	public boolean deleteAt(int pos) {
		boolean ifDeleted = false;
		DLLNode curr = head;
		if (pos >= 0 && pos < getSize()) {
			if (getSize() == 1) {
				head = null;
				tail = null;
				ifDeleted = true;
			} else if (pos == 0) {
				head = head.next;
				ifDeleted = true;
			} else if (pos == getSize() - 1) {
				tail = tail.prev;
				tail.next = null;
				ifDeleted = true;
			} else {
				int count = 0;
				while (count != pos) {
					curr = curr.next;
					count++;
				}
				curr.prev.next = curr.next;
				curr.next.prev = curr.prev;
				ifDeleted = true;
			}
		}
		return ifDeleted;
	}

	/**
	 * Reverses the list. If the list contains "A", "B", "C", "D" before the method
	 * is called Then it should contain "D", "C", "B", "A" after it returns.
	 *
	 * Worst-case asymptotic running time cost: theta (n)
	 *
	 * Justification: the while loop iterates through n elements
	 */
	public void reverse() {
		DLLNode tmp = null;
		DLLNode curr = head;
		if (!isEmpty()) {
			if (getSize() == 1) {
				return;
			}
			while (curr != null) {
				tmp = curr.prev;
				curr.prev = curr.next;
				curr.next = tmp;
				curr = curr.prev;
			}
			if (tmp != null) {
				head = tmp.prev;
			}
		}
	}

	/**
	 * Removes all duplicate elements from the list. The method should remove the
	 * _least_number_ of elements to make all elements uniqueue. If the list
	 * contains "A", "B", "C", "B", "D", "A" before the method is called Then it
	 * should contain "A", "B", "C", "D" after it returns. The relative order of
	 * elements in the resulting list should be the same as the starting list.
	 *
	 * Worst-case asymptotic running time cost: theta(n^2)
	 *
	 * Justification: Two for-loops that go through n elements each
	 */
	public void makeUnique() {
		DLLNode iter, iter2, tmp;
		if (isEmpty()) {
			return;
		} else {		
			for (iter = head; iter != null; iter = iter.next) {
				for (iter2 = iter.next; iter2 != null; iter2 = iter2.next) {
					if (iter.data == iter2.data) {
						tmp = iter2;
						if (tmp == tail) {
							tail = tail.prev;
							tail.next = null;
						} else {
							iter2.prev.next = iter2.next;
							if (iter2.next != null) {
								iter2.next.prev = iter2.prev;
								tmp = null;
							}
						}
					}
				}
			}
		}
	}

	/*----------------------- STACK API 
	 * If only the push and pop methods are called the data structure should behave like a stack.
	 */

	/**
	 * This method adds an element to the data structure. How exactly this will be
	 * represented in the Doubly Linked List is up to the programmer.
	 * 
	 * @param item
	 *            : the item to push on the stack
	 *
	 *            Worst-case asymptotic running time cost: Theta(1)
	 *
	 *            Justification: The running time will be constant as it executes one argument
	 */
	public void push(T item) {
		DLLNode newNode = new DLLNode(item, null, head);
		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		} 
		else if(item == null) {
			return;
		}
		else {
			head.prev = newNode;
			head = newNode;
		}

	}

	/**
	 * This method returns and removes the element that was most recently added by
	 * the push method.
	 * 
	 * @return the last item inserted with a push; or null when the list is empty.
	 *
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *
	 *         Justification: The running time will be constant as it executes one argument
	 */
	public T pop() {
		T popped;
		if (isEmpty()) {
			return null;
		}
		if (getSize() == 1) {
			popped = head.data;
			head = null;
		} else {
			popped = head.data;
			head = head.next;
			head.prev = null;
		}
		return popped;
	}

	/*----------------------- QUEUE API
	 * If only the enqueue and dequeue methods are called the data structure should behave like a FIFO queue.
	 */

	/**
	 * This method adds an element to the data structure. How exactly this will be
	 * represented in the Doubly Linked List is up to the programmer.
	 * 
	 * @param item
	 *            : the item to be enqueued to the stack
	 *
	 *            Worst-case asymptotic running time cost: Theta(1)
	 *
	 *            Justification: The running time will be constant as it executes one argument
	 */
	public void enqueue(T item) {
		DLLNode newNode = new DLLNode(item, tail, null);
		if (isEmpty()) {
			head = newNode;
			tail = newNode;
		}
		else if (item == null) {
			return;
		}
		else {
			tail.next = newNode;
			tail = newNode;
		}

	}

	/**
	 * This method returns and removes the element that was least recently added by
	 * the enqueue method.
	 * 
	 * @return the earliest item inserted with an equeue; or null when the list is
	 *         empty.
	 *
	 *         Worst-case asymptotic running time cost: Theta(1)
	 *
	 *         Justification: The running time will be constant as it executes one argument
	 */
	public T dequeue() {
		T elem;
		if (isEmpty()) {
			elem = null;
		} else {
			elem = head.data;
			if (head != null) {
				head = head.next;
			}
			else if (getSize() == 1) {
				elem = head.data;
				head = null;
				tail = null;
			}
		}
		return elem;
	}

	public int getSize() {
		int size = 0;
		for (DLLNode current = head; current != null; current = current.next) {
			size++;
		}
		return size;
	}

	/**
	 * @return a string with the elements of the list as a comma-separated list,
	 *         from beginning to end
	 *
	 *         Worst-case asymptotic running time cost: Theta(n)
	 *
	 *         Justification: We know from the Java documentation that
	 *         StringBuilder's append() method runs in Theta(1) asymptotic time. We
	 *         assume all other method calls here (e.g., the iterator methods above,
	 *         and the toString method) will execute in Theta(1) time. Thus, every
	 *         one iteration of the for-loop will have cost Theta(1). Suppose the
	 *         doubly-linked list has 'n' elements. The for-loop will always iterate
	 *         over all n elements of the list, and therefore the total cost of this
	 *         method will be n*Theta(1) = Theta(n).
	 */
	public String toString() {
		StringBuilder s = new StringBuilder();
		boolean isFirst = true;

		// iterate over the list, starting from the head
		for (DLLNode iter = head; iter != null; iter = iter.next) {
			if (!isFirst) {
				s.append(",");
			} else {
				isFirst = false;
			}
			s.append(iter.data.toString());
		}

		return s.toString();
	}

}
