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
}
