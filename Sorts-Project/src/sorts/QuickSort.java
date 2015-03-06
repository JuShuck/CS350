package sorts;

import partitions.*;

public class QuickSort extends Sort
{
		
	// the partition technique
	private Partition partitioner;
	
	/**
	 * default constructor
	 * @param partitionMethod
	 */
	public QuickSort(Partition partitionMethod)
	{
		partitioner = partitionMethod;
	}
	
	/**
	 * set a partitioning technique (in case this wasn't done during instantiation)
	 * @param partitionMethod
	 */
	public void setPartitionTechnique(Partition partitionMethod)
	{
		partitioner = partitionMethod;
	}
	
	// implements QuickSort and uses partitioner.partition()
	private void quickSort(int[] A, int M, int N)
	{
		int[] refIndeces = new int[2];
		if (M < N) {
			partitioner.partition(A, M, N, refIndeces);
			// increment the basic operation per each partition and add to the per-sort swaps
			incBasicOpCount();
			swapsLastSort += partitioner.getSwapsLastPartition();
			// recursively quickSort
			quickSort(A, M, refIndeces[0]);
			quickSort(A, refIndeces[1], N);
		}
	}
	
	/**
	 * concrete sort method
	 */
	@Override
	public void sort(int[] data)
	{
		resetBasicOpCount();
		swapsLastSort = 0;
		if (partitioner != null) {
			partitioner.resetTotalBasicOpCount();
			quickSort(data, 0, data.length - 1);
			addToOpCount(partitioner.getTotalBasicOpCount());
			addToTotalOpCount(getBasicOpCountLastSort());
		} else {
			System.out.println("No partition method was set!");
		}
	}

	@Override
	public String getSortName()
	{
		return "Quicksort (" + partitioner.getPartitionerName() + ")";
	}
}
