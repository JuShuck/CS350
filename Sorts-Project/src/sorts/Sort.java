package sorts;

public abstract class Sort
{
	// counter for basic operations
	private long basicOpCount = 0;
	
	// increments the count of basic operations
	protected void incBasicOpCount()
	{
		basicOpCount++;
	}
	
	// returns the basic operation count
	public long getBasicOpCount()
	{
		return basicOpCount;
	}
	
	// executes a sort
	public abstract void sort(int[] data);
	
	// prints relevant diagnostic info (i.e. number of swaps or merges depending on sort)
	public abstract void printDiagnostics();
	
	// gets the name of sort being used
	public abstract String getSortName();
}
