package partitions;

public abstract class Partition {
	// main trigger for partitioning a data structure
	public abstract void partition(int[] A, int M, int N, int[] refIndeces);
	// swap method
	public void exchange(int[] A, Integer I, Integer J) {
		int temp = A[I];
		A[I] = A[J];
		A[J] = temp;
	}
}
