import sorts.*;
import partitions.*;

public class Test {

	public static void main(String[] args) {

		Sort sorter1 = new QuickSort(new HoareOriginal());
		Sort sorter2 = new QuickSort(new Lomuto());
		Sort sorter3 = new MergeSort();
		Sort sorter4 = new ParallelMergeSort();
		
		// some arrays to play with
		int[] arrayA = {2,5,6,2,4,6,7,9,6,8,4,5,4,1,4,6,8,3,4,4,8,7,3,4,7,2,6,5,8,3,3};
		int[] arrayACopy1 = new int[arrayA.length];
		int[] arrayACopy2 = new int[arrayA.length];
		System.arraycopy(arrayA, 0, arrayACopy1, 0, arrayA.length);
		System.arraycopy(arrayA, 0, arrayACopy2, 0, arrayA.length);
		int[] arrayB = {0,3,6,7,8,3,1,2,1,3,2,6,9,8,1,1,1,2,1,5,7,2,8,4,8,3,3,7,5,5,4};
		int[] arrayBCopy1 = new int[arrayB.length];
		int[] arrayBCopy2 = new int[arrayB.length];
		System.arraycopy(arrayB, 0, arrayBCopy1, 0, arrayB.length);
		System.arraycopy(arrayB, 0, arrayBCopy2, 0, arrayB.length);
		int[] arrayC = {3,7,3,3,8,8,2,6,3,3,2,6,7,9,7,4,6,3,8,3,8,7,2,1,3,0,6,4,8,6,8};
		int[] arrayD = {2,5,3,6,3,7,9,7,8,9,8,7,8,3,7,3,3,5,3,0,5,7,2,7,9,2,3,8,1,1,3};//{3,7,3,3,8,8,2,6,3,3,2,6,7,9,7,4,6,3,8,3,8,7,2,1,3,0,6,4,8,6,8};
		
		System.out.print("Original array A: ");
		printArray(arrayA);
		System.out.println();
		sorter1.sort(arrayA);
		printArray(arrayA);
		System.out.println("Swaps made last sort using this sorter and partition instance: " + sorter1.getSwapsLastSort());
		System.out.println("Total swaps made during all sorts using this sorter and partition instance: " + sorter1.getTotalSwaps());
		System.out.printf("Cumulative basic operations: %d%n", sorter1.getTotalBasicOpCount());
		sorter1.sort(arrayACopy1);
		printArray(arrayACopy1);
		System.out.println("Swaps made last sort using this sorter and partition instance: " + sorter1.getSwapsLastSort());
		System.out.println("Total swaps made during all sorts using this sorter and partition instance: " + sorter1.getTotalSwaps());
		System.out.printf("Cumulative basic operations: %d%n", sorter1.getTotalBasicOpCount());
		sorter1.sort(arrayACopy2);
		printArray(arrayACopy2);
		System.out.println("Swaps made last sort using this sorter and partition instance: " + sorter1.getSwapsLastSort());
		System.out.println("Total swaps made during all sorts using this sorter and partition instance: " + sorter1.getTotalSwaps());
		System.out.printf("Cumulative basic operations: %d%n", sorter1.getTotalBasicOpCount());
		
		System.out.println();
		
		System.out.print("Original array B: ");
		printArray(arrayB);
		System.out.println();
		sorter2.sort(arrayB);
		printArray(arrayB);
		System.out.println("Swaps made last sort using this sorter and partition instance: " + sorter2.getSwapsLastSort());
		System.out.println("Total swaps made during all sorts using this sorter and partition instance: " + ((QuickSort) sorter2).getTotalSwaps());
		System.out.printf("Cumulative basic operations: %d%n", sorter2.getTotalBasicOpCount());
		sorter2.sort(arrayBCopy1);
		printArray(arrayBCopy1);
		System.out.println("Swaps made last sort using this sorter and partition instance: " + sorter2.getSwapsLastSort());
		System.out.println("Total swaps made during all sorts using this sorter and partition instance: " + ((QuickSort) sorter2).getTotalSwaps());
		System.out.printf("Cumulative basic operations: %d%n", sorter2.getTotalBasicOpCount());
		sorter2.sort(arrayBCopy2);
		printArray(arrayBCopy2);
		System.out.println("Swaps made last sort using this sorter and partition instance: " + sorter2.getSwapsLastSort());
		System.out.println("Total swaps made during all sorts using this sorter and partition instance: " + sorter2.getTotalSwaps());
		System.out.printf("Cumulative basic operations: %d%n", sorter2.getTotalBasicOpCount());
		
		System.out.println();
		
		System.out.print("Original array C: ");
		printArray(arrayC);
		System.out.println();
		sorter3.sort(arrayC);
		printArray(arrayC);
		System.out.println("Total extra memory allocated during all sorts using this sorter instance: " + sorter3.getTotalExtraMemory());
		System.out.printf("Cumulative basic operations: %d%n", sorter3.getTotalBasicOpCount());
		
		System.out.println();
		
		System.out.print("Original array D: ");
		printArray(arrayD);
		System.out.printf("Processing cores available on this system: %d%n", ParallelMergeSort.getAvailableThreads());
		sorter4.sort(arrayD);
		printArray(arrayD);
		System.out.println("Total extra memory allocated during all sorts using this sorter instance: " + sorter4.getTotalExtraMemory());
		System.out.printf("Cumulative basic operations: %d%n", sorter4.getTotalBasicOpCount());
	}
	
	public static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%d ", array[i]);
		}
		System.out.println();
	}
}
