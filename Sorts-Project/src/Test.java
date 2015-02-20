import sorts.*;
import partitions.*;

public class Test {

	public static void main(String[] args) {

		QuickSort sorter1 = new QuickSort(new HoareOriginal());
		QuickSort sorter2 = new QuickSort(new Lomuto());
		
		// some arrays to play with
		int[] arrayA = {2,5,6,2,4,6,7,9,6,8,4,5,4,1,4,6,8,3,4,4,8,7,3,4,7,2,6,5,8,3,3};
		int[] arrayACopy1 = new int[arrayA.length];
		int[] arrayACopy2 = new int[arrayA.length];
		System.arraycopy(arrayA, 0, arrayACopy1, 0, arrayA.length);
		System.arraycopy(arrayA, 0, arrayACopy2, 0, arrayA.length);
		int[] arrayB = {6,3,6,7,8,3,1,2,1,3,2,6,9,8,0,4,7,2,1,5,7,2,8,4,8,3,3,7,5,5,4};
		int[] arrayBCopy1 = new int[arrayB.length];
		int[] arrayBCopy2 = new int[arrayB.length];
		System.arraycopy(arrayB, 0, arrayBCopy1, 0, arrayB.length);
		System.arraycopy(arrayB, 0, arrayBCopy2, 0, arrayB.length);
		
		System.out.print("Original array A: ");
		printArray(arrayA);
		System.out.println();
		sorter1.sort(arrayA);
		printArray(arrayA);
		System.out.println("Total number of swaps made during all sorts using this sorter and partition instance: " + sorter1.getTotalSwaps());
		sorter1.sort(arrayACopy1);
		printArray(arrayACopy1);
		System.out.println("Total number of swaps made during all sorts using this sorter and partition instance: " + sorter1.getTotalSwaps());
		sorter1.sort(arrayACopy2);
		printArray(arrayACopy2);
		System.out.println("Total number of swaps made during all sorts using this sorter and partition instance: " + sorter1.getTotalSwaps());
		
		System.out.println();
		
		System.out.print("Original array B: ");
		printArray(arrayB);
		System.out.println();
		sorter2.sort(arrayB);
		printArray(arrayB);
		System.out.println("Total number of swaps made during all sorts using this sorter and partition instance: " + sorter2.getTotalSwaps());
		sorter2.sort(arrayBCopy1);
		printArray(arrayBCopy1);
		System.out.println("Total number of swaps made during all sorts using this sorter and partition instance: " + sorter2.getTotalSwaps());
		sorter2.sort(arrayBCopy2);
		printArray(arrayBCopy2);
		System.out.println("Total number of swaps made during all sorts using this sorter and partition instance: " + sorter2.getTotalSwaps());
	}
	
	public static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%d ", array[i]);
		}
		System.out.println();
	}
}
