package edu.pdx.cs350;

/**
 * This is a dummy, can be deleted once replaced.
 *
 * @author Ian
 */
public interface Sort
{
	public void sort(int[] data);
	
	public long getSwapCount();
	
	public long getBasicOpCount();
	
	// A unique name for the sort, such as Linear Mergesort, Quicksort (Hoare), etc.
	public String getSortName();
}
