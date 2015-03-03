package sorts;

import java.util.concurrent.atomic.AtomicLong;

public abstract class Sort
{
	
	// counter for basic operations
	private AtomicLong basicOpCountLastSort = new AtomicLong(0);
	private long totalBasicOpCount = 0;
	
	// increments the count of per-sort basic operations
	protected void incBasicOpCount()
	{
		basicOpCountLastSort.incrementAndGet();
	}
	
	// resets the count of per-sort basic operations
	protected void resetBasicOpCount()
	{
		basicOpCountLastSort.set(0);
	}
	
	// adds to the count of total basic operations
	protected void addTotalOpCount(long countToAdd)
	{
		totalBasicOpCount = countToAdd;
	}
	
	// returns the basic operation count from the last sort
	public long getLastBasicOpCount()
	{
		return basicOpCountLastSort.get();
	}
		
	// returns the total basic operation count
	public long getTotalBasicOpCount()
	{
		return totalBasicOpCount;
	}
	
	// executes a sort
	public abstract void sort(int[] data);
	
	// prints relevant diagnostic info (i.e. number of swaps or merges depending on sort)
	public abstract void printDiagnostics();
	
	// gets the name of sort being used
	public abstract String getSortName();
}
