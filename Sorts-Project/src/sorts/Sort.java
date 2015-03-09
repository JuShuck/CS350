package sorts;

import algorithm.Algorithm;

public abstract class Sort implements Algorithm
{
	
	// counters for basic operations
	private long basicOpCountLastSort = 0;
	private long totalBasicOpCount = 0;
	
	// counters for swaps (specific to quickSort)
	protected long swapsLastSort = 0;
	protected long totalSwaps = 0;
	
	// counters for extra memory needed (specific to mergeSorts)
	protected long extraMemoryLastSort = 0;
	protected long totalExtraMemory = 0;
	
	/**
	 * Resets everything at once.
	 */
	public void resetAll()
	{
		basicOpCountLastSort = 0;
		totalBasicOpCount = 0;
		swapsLastSort = 0;
		totalSwaps = 0;
		extraMemoryLastSort = 0;
		totalExtraMemory = 0;
	}
	
	/**
	 * resets the count of per-sort basic operations
	 */
	public void resetBasicOpCount()
	{
		basicOpCountLastSort = 0;
	}
	
	/**
	 * resets the count of total basic operations
	 */
	public void resetTotalBasicOpCount()
	{
		totalBasicOpCount = 0;
	}
	
	/**
	 * provides a boolean return should the basic operations need to be incremented as part of a loop condition
	 */
	public boolean incBasicOpCountInLoop() {
		basicOpCountLastSort++;
		return true;
	}
	
	/**
	 * increments the count of per-sort basic operations
	 */
	public void incBasicOpCount()
	{
		basicOpCountLastSort++;
	}
	
	/**
	 * adds to the basic operation count for the current sort
	 * @param countToAdd
	 */
	public void addToOpCount(long countToAdd)
	{
		basicOpCountLastSort += countToAdd;
	}	
	
	/**
	 * adds to the count of total basic operations
	 * @param countToAdd
	 */
	public void addToTotalOpCount(long countToAdd)
	{
		totalBasicOpCount += countToAdd;
	}
	
	/**
	 * returns the basic operation count from the last sort
	 * @return
	 */
	public long getBasicOpCountLastSort()
	{
		return basicOpCountLastSort;
	}
		
	/**
	 * returns the total basic operation count
	 * @return
	 */
	public long getTotalBasicOpCount()
	{
		return totalBasicOpCount;
	}
	
	/**
	 * returns the number of swaps from the last sort - or returns 0 if a partitioner was never instantiated (i.e. not a quicksort)
	 * @return
	 */
	public long getSwapsLastSort()
	{
		return swapsLastSort;
	}
	
	/**
	 * returns the number of swaps generated by this sorting instance - or returns 0 if a partitioner was never instantiated (i.e. not a quicksort)
	 * @return
	 */
	public long getTotalSwaps()
	{
		return totalSwaps;
	}
	
	/**
	 * returns the amount of extra memory (represented by array cells) allocated during the last sort
	 * @return
	 */
	public long getExtraMemoryLastSort()
	{
		return extraMemoryLastSort;
	}
	
	/**
	 * returns the amount of extra memory (represented by array cells) allocated during all sorts performed with this instance
	 * @return
	 */
	public long getTotalExtraMemory()
	{
		return totalExtraMemory;
	}
	
	/**
	 * executes a sort
	 * @param data
	 */
	public abstract void sort(int[] data);
	
	/**
	 * gets the name of sort being used
	 * @return
	 */
	public abstract String getSortName();
}
