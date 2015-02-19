import sorts.*;
import partitions.*;

public class Test {

	public static void main(String[] args) {

		QuickSort sorter1 = new QuickSort(new HoareOriginal());
		QuickSort sorter2 = new QuickSort(new Lomuto());
		int[] array1 = {2,5,6,2,4,6,7,9,6,8,4,5,4,1,4,6,8,3,4,4,8,7,3,4,7,2,6,5,8,3,3};
		int[] array2 = {6,3,6,7,8,3,1,2,1,3,2,6,9,8,0,4,7,2,1,5,7,2,8,4,8,3,3,7,5,5,4};
		
		printArray(array1);
		sorter1.sort(array1, 0, array1.length - 1);
		printArray(array1);
		System.out.println();
		printArray(array2);
		sorter2.sort(array2, 0, array2.length - 1);
		printArray(array2);
	}
	
	public static void printArray(int[] array) {
		for (int i = 0; i < array.length; i++) {
			System.out.printf("%d ", array[i]);
		}
		System.out.println();
	}
}
