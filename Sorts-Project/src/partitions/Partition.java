package partitions;

import algorithm.Algorithm;

public abstract class Partition implements Algorithm
{
	
	// counters for basic operations
	private long basicOpCountLastPartition = 0;
	private long totalBasicOpCount = 0;
	
	// swap counters - per partition and total
	protected long swaps = 0;
	protected long totalSwaps = 0;
	
	// resets the count of per-partition basic operations
	public void resetBasicOpCount()
	{
		basicOpCountLastPartition = 0;
	}
	
	// resets the count of total basic operations
	public void resetTotalBasicOpCount()
	{
		totalBasicOpCount = 0;
	}
	
	// increments the count of per-partition basic operations
	public void incBasicOpCount()
	{
		basicOpCountLastPartition++;
	}
	
	// adds to the basic operation count for the current partition
	public void addToOpCount(long countToAdd)
	{
		basicOpCountLastPartition += countToAdd;
	}	
	
	// adds to the count of total basic operations
	public void addToTotalOpCount(long countToAdd)
	{
		totalBasicOpCount += countToAdd;
	}
	
	// returns the basic operation count from the last partition
	public long getBasicOpCountLastSort()
	{
		return basicOpCountLastPartition;
	}
		
	// returns the total basic operation count
	public long getTotalBasicOpCount()
	{
		return totalBasicOpCount;
	}
	
	// returns the number of swaps from the last partition
	public long getSwapsLastPartition()
	{
		return swaps;
	}
		
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
	
	// Returns the name of the partition (eg Hoare).
	public abstract String getPartitionerName();
}
