package sorts;

import partitions.*;

public class QuickSort {
	private Partition partitioner;
	
	public QuickSort(Partition partitionMethod) {
		partitioner = partitionMethod;
	}

	// implement QuickSort here and use partitioner.partition()
	public void sort(int[] A, int M, int N) {
		int[] refIndeces = new int[2];
		if (M < N) {
			partitioner.partition(A, M, N, refIndeces);
			sort(A, M, refIndeces[0]);
			sort(A, refIndeces[1], N);
		}
	}
}
