package sorts;

import algorithm.Algorithm;

public abstract class Sort implements Algorithm
{
	
	// counters for basic operations
	private long basicOpCountLastSort = 0;
	private long totalBasicOpCount = 0;
	
	// resets the count of per-sort basic operations
	public void resetBasicOpCount()
	{
		basicOpCountLastSort = 0;
	}
	
	// resets the count of total basic operations
	public void resetTotalBasicOpCount()
	{
		totalBasicOpCount = 0;
	}
	
	// increments the count of per-sort basic operations
	public void incBasicOpCount()
	{
		basicOpCountLastSort++;
	}
	
	// adds to the basic operation count for the current sort
	public void addToOpCount(long countToAdd)
	{
		basicOpCountLastSort += countToAdd;
	}	
	
	// adds to the count of total basic operations
	public void addToTotalOpCount(long countToAdd)
	{
		totalBasicOpCount += countToAdd;
	}
	
	// returns the basic operation count from the last sort
	public long getBasicOpCountLastSort()
	{
		return basicOpCountLastSort;
	}
		
	// returns the total basic operation count
	public long getTotalBasicOpCount()
	{
		return totalBasicOpCount;
	}
	
	// executes a sort
	public abstract void sort(int[] data);
	
	// gets the name of sort being used
	public abstract String getSortName();
}
