import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

//-------------------------------------------------------------------------
//|				|Insert		|Quick		|Merge		|Merge		|Selection |
//|				|			|			|Recursive	|Iterative  |		   |
//-------------------------------------------------------------------------
//|10 random	|0.555		|0.49999	|0.673		|0.872		|0.5685666 |
//|				|			|			|			|			|		   |
//-------------------------------------------------------------------------
//|100 random	|0.31006666	|0.1651		|0.75853	|0.419		|0.527899  |
//|				|			|			|			|			|		   |
//-------------------------------------------------------------------------
//|1000 random	|13.7234666	|0.7834		| 0.4332	|0.492		|4.7477	   |
//|				|			|			|			|			|		   |
//-------------------------------------------------------------------------
//|1000 few 	|0.43126666	|0.20946	|0.51086	|0.42933	|1.1789	   |
//|unique		|			|			|			|			|		   |
//-------------------------------------------------------------------------
//|1000 nearly	|0.09563333	|0.24316	|0.14786	|0.2766		|0.958233  |
//|ordered		|			|			|			|			|		   |
//-------------------------------------------------------------------------
//|1000 reverse |0.7666666	| 1.91226	|0.1051333	|0.2394666	|1.4959	   |
//|order		|			|			|			|			|		   |
//-------------------------------------------------------------------------
//|1000 sorted	|0.0071666	|1.92190	|0.106566	|0.1672233	|1.0017	   |
//|				|			|			|			|			|		   |
//-------------------------------------------------------------------------
/**
 * a. Which of the sorting algorithms does the order of input have an impact on? Why?
 * 	  Answer: Quick sort takes the longest if its in reverse order because the partition index is set to the last element so this would be 
 * 			  the same as a brute force method.
   b. Which algorithm has the biggest difference between the best and worst performance, based
	  on the type of input, for the input of size 1000? Why?
	  Answer: Insertion sort has the biggest difference with the worst performance being for 1000 random and the best being for 1000 sorted.	  		  
   c. Which algorithm has the best/worst scalability, i.e., the difference in performance time
	  based on the input size? Please consider only input files with random order for this answer.
	  Insertion sort has the worst scalability. Merge Sort recursive has the best scalability.
   d. Did you observe any difference between iterative and recursive implementations of merge
	  sort?
	  Iterative was slower that the recursive function
   e. Which algorithm is the fastest for each of the 7 input files?
      10 random: Insertion Sort
      100 random: Quick Sort
      1000 random: Merge Recursive
      1000 few unique: Quick Sort
      1000 reverse order: Merge Recursive
      1000 sorted: Insertion Sort
 *  Test class for SortComparison.java
 *
 *  @author Anna Maliakal
 *  @version HT 2019
 */
@RunWith(JUnit4.class)
public class SortComparisonTest
{
    //~ Constructor ........................................................
    @Test
    public void testConstructor()
    {
        SortComparison sorter = new SortComparison();
    }

    //~ Public Methods ........................................................

    // ----------------------------------------------------------
    @Test
    public void testInsertionSort()
    {

    	//one element array
    	double[] value = {23};
    	SortComparison.insertionSort(value);
    	double[] expected = {23};
    	assertArrayEquals(expected, value, 0.0);
    	
    	//multiple element array
    	double [] manyValues = {-13, 0, -15, 26, 5, 2,32};
    	SortComparison.insertionSort(manyValues);
    	double [] valuesExpected = {-15, -13, 0, 2, 5, 26, 32};
    	assertArrayEquals(valuesExpected, manyValues, 0.0);
    	
    	//same elements
    	double [] newValues = {0, 0, 2, 26, 0, 2,32};
    	SortComparison.insertionSort(newValues);
    	double [] values = {0, 0, 0, 2, 2, 26, 32};
    	assertArrayEquals(values, newValues, 0.0);
    	
    }
    
    @Test
    public void testQuickSort()
    {
    	//one element array
    	double[] value = {23};
    	SortComparison.quickSort(value);
    	double[] expected = {23};
    	assertArrayEquals(expected, value, 0.0);
    	
    	//multiple element array
    	double [] manyValues = {-13, 0, -15, 26, 5, 2,32};
    	SortComparison.quickSort(manyValues);
    	double [] valuesExpected = {-15, -13, 0, 2, 5, 26, 32};
    	assertArrayEquals(valuesExpected, manyValues, 0.0);
    	
    	//same elements
    	double [] newValues = {0, 0, 2, 26, 0, 2,32};
    	SortComparison.quickSort(newValues);
    	double [] values = {0, 0, 0, 2, 2, 26, 32};
    	assertArrayEquals(values, newValues, 0.0);
    	
    }
    
    @Test
    public void testMergeSortIterative()
    {

    	//one element array
    	double[] value = {23};
    	SortComparison.mergeSortIterative(value);
    	double[] expected = {23};
    	assertArrayEquals(expected, value, 0.0);
    	
    	//multiple element array
    	double [] manyValues = {-13, 0, -15, 26, 5, 2,32};
    	SortComparison.mergeSortIterative(manyValues);
    	double [] valuesExpected = {-15, -13, 0, 2, 5, 26, 32};
    	assertArrayEquals(valuesExpected, manyValues, 0.0);
    	
    	//same elements
    	double [] newValues = {0, 0, 2, 26, 0, 2,32};
    	SortComparison.mergeSortIterative(newValues);
    	double [] values = {0, 0, 0, 2, 2, 26, 32};
    	assertArrayEquals(values, newValues, 0.0);
    	
    }
    
    @Test
    public void testMergeSortRecursive()
    {
    	//one element array
    	double[] value = {23};
    	SortComparison.mergeSortRecursive(value);
    	double[] expected = {23};
    	assertArrayEquals(expected, value, 0.0);
    	
    	//multiple element array
    	double [] manyValues = {-13, 0, -15, 26, 5, 2,32};
    	SortComparison.mergeSortRecursive(manyValues);
    	double [] valuesExpected = {-15, -13, 0, 2, 5, 26, 32};
    	assertArrayEquals(valuesExpected, manyValues, 0.0);
    	
    	//same elements
    	double [] newValues = {0, 0, 2, 26, 0, 2,32};
    	SortComparison.mergeSortRecursive(newValues);
    	double [] values = {0, 0, 0, 2, 2, 26, 32};
    	assertArrayEquals(values, newValues, 0.0);
    	
    }
    
    @Test
    public void testSelectionSort()
    {
    	//one element array
    	double[] value = {23};
    	SortComparison.selectionSort(value);
    	double[] expected = {23};
    	assertArrayEquals(expected, value, 0.0);
    	
    	//multiple element array
    	double [] manyValues = {-13, 0, -15, 26, 5, 2,32};
    	SortComparison.selectionSort(manyValues);
    	double [] valuesExpected = {-15, -13, 0, 2, 5, 26, 32};
    	assertArrayEquals(valuesExpected, manyValues, 0.0);
    	
    	//same elements
    	double [] newValues = {0, 0, 2, 26, 0, 2,32};
    	SortComparison.selectionSort(newValues);
    	double [] values = {0, 0, 0, 2, 2, 26, 32};
    	assertArrayEquals(values, newValues, 0.0);
    	
    }
    /**
     * Check that the methods work for empty arrays
     */
    @Test
    public void testEmpty()
    {
    	double[] orig = new double[0];
    	double[] empty = orig.clone();
    	SortComparison.insertionSort(empty);
    	assertTrue(Arrays.equals(orig, empty));
    	SortComparison.quickSort(empty);
    	assertTrue(Arrays.equals(orig, empty));
    	SortComparison.mergeSortIterative(empty);
    	assertTrue(Arrays.equals(orig, empty));
    	SortComparison.mergeSortRecursive(empty);
    	assertTrue(Arrays.equals(orig, empty));
    	SortComparison.selectionSort(empty);
    	assertTrue(Arrays.equals(orig, empty));
    }


    // TODO: add more tests here. Each line of code and ech decision in Collinear.java should
    // be executed at least once from at least one test.

    // ----------------------------------------------------------
    /**
     *  Main Method.
     *  Use this main method to create the experiments needed to answer the experimental performance questions of this assignment.
     *
     */
    public static void main(String[] args) throws FileNotFoundException 
    {

	}



	


}
