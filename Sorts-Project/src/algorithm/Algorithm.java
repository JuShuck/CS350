package algorithm;

public interface Algorithm
{
	
	// set the per-execution basic operation count to zero
	public void resetBasicOpCount();
	
	// set the total basic operation count to zero
	public void resetTotalBasicOpCount();
	
	// increments the count of per-execution basic operations
	public void incBasicOpCount();
	
	// provides a boolean return should the basic operations need to be incremented as part of a loop condition
	public boolean incBasicOpCountInLoop();
	
	// adds to the basic operation count for the current execution
	public void addToOpCount(long countToAdd);	
	
	// adds to the count of total basic operations
	public void addToTotalOpCount(long countToAdd);
	
	// returns the basic operation count from the last execution
	public long getBasicOpCountLastSort();
		
	// returns the total basic operation count
	public long getTotalBasicOpCount();
}
