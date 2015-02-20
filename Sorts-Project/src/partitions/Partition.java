package partitions;

public abstract class Partition
{
	// swap counter - per partition
	protected long swaps = 0;
	
	// swap counter - total
	protected long totalSwaps = 0;
	
	// returns the number of total swaps - package private
	public long getTotalSwaps()
	{
		return totalSwaps;
	}
	
	// swap method
	public void exchange(int[] A, Integer I, Integer J)
	{
		int temp = A[I];
		A[I] = A[J];
		A[J] = temp;
		swaps++;
	}
		
	// main trigger for partitioning a data structure
	public abstract void partition(int[] A, int M, int N, int[] refIndeces);
	
}
