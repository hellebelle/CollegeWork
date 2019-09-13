import java.util.Arrays;

// -------------------------------------------------------------------------

/**
 *  This class contains static methods that implementing sorting of an array of numbers
 *  using different sort algorithms.
 *
 *  @author Anna Maliakal
 *  @version HT 2019
 */

 class SortComparison {

    /**
     * Sorts an array of doubles using InsertionSort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order.
     *
     */
    static double [] insertionSort (double a[]){

		for(int i = 0; i<a.length;i++) {
			double key = a[i];
			int j = i-1;
			while(j>=0 && a[j]>key) {
				a[j+1] = a[j];
				j = j-1;
			}
			a[j+1] = key;
		}
    	return a;
    }

    /**
     * Sorts an array of doubles using Quick Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static void quickSort (double a[]){
    	 quickSort(a,0,a.length-1);
    }
    
    private static double [] quickSort(double[] a, int low, int high) {
		if(low<high) {
			int pIndex = partition(a,low,high);
			quickSort(a, low, pIndex-1);
			quickSort(a, pIndex+1, high);
		}
		return a;
	}

	static int partition(double a[], int low, int high) {
    	double pivot = a[high];
    	int i = (low-1);
    	for(int j = low; j<high; j++) {
    		if(a[j]<=pivot) {
    			i++;
    			double temp = a[i];
    			a[i] = a[j];
    			a[j] = temp;
    		}
    	}
    	double temp = a[i+1];
    	a[i+1] = a[high];
    	a[high] = temp;
    	
    	return i+1;
    }

    /**
     * Sorts an array of doubles using Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    /**
     * Sorts an array of doubles using iterative implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */
 
    static double[] mergeSortIterative (double a[]) {
    	int low = 0;
    	int high = a.length-1;
    	double [] temp = a.clone();
    	
    	for(int m = 1; m<=high-low; m = 2*m) {
    		for (int i = low; i<high; i+=2*m) {
    			int from = i;
    			int mid = i + m - 1;
    			//int to = Math.min(i+2*m-1, high);
    			merge(a, temp, from , mid ,Math.min(i + m + m-1, a.length - 1));
    		}
    	}
    	
		return a;

    }
    
    private static void merge(double[] a, double[] temp, int from, int mid, int to) {
		int k = from;
		int i = from;
		int j = mid +1;
		
		while(i<=mid && j<= to) {
			if(a[i]<a[j]) {
				temp[k++] = a[i++];
			}
			else {
				temp[k++] = a[j++];
			}
		}
		
		while(i<=mid) {
			temp[k++] = a[i++];
		}
		
		for(i = from; i<= to; i++) {
			a[i] = temp[i];
		}
	}


	/**
     * Sorts an array of doubles using recursive implementation of Merge Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     *
     * @param a: An unsorted array of doubles.
     * @return after the method returns, the array must be in ascending sorted order.
     */
    static double[] mergeSortRecursive (double a[]) {
    	double [] temp = a.clone();
    	int low = 0;
    	int high = a.length-1;
    	
    	mergeSortRecursive(a, temp, low, high);	
		return a;

   }
    	
    
    private static void mergeSortRecursive(double[] a, double[] temp, int low, int high) {
		if(high<=low) {
			return;
		}
		int mid =low+(high-low)/2;
		mergeSortRecursive(a,temp, low, mid);
		mergeSortRecursive(a,temp,mid+1,high);
		merge(a, temp, low, mid, high);
		
	}

	/**
     * Sorts an array of doubles using Selection Sort.
     * This method is static, thus it can be called as SortComparison.sort(a)
     * @param a: An unsorted array of doubles.
     * @return array sorted in ascending order
     *
     */
    static double [] selectionSort (double a[]){

        for (int i = 0; i < a.length-1; i++) 
        { 
            int low = i; 
            for (int j = i+1; j < a.length; j++) 
                if (a[j] < a[low]) 
                    low = j; 
            double temp = a[low]; 
            a[low] = a[i]; 
            a[i] = temp; 
        } 
		return a;

    }


    

 }//end class
