package sorts;

public class MergeSort extends Sort
{
	
	// counters for extra memory needed (specific to merge sorts)
	private long extraMemoryLastSort = 0;
	private long totalExtraMemory = 0;
	
	protected void resetExtraMemory()
	{
		extraMemoryLastSort = 0;
	}
	
	protected void addToExtraMemory(long size)
	{
		extraMemoryLastSort += size;
	}
	
	protected void addToTotalExtraMemory(long size)
	{
		totalExtraMemory += size;
	}
	
	public long getExtraMemoryLastSort()
	{
		return extraMemoryLastSort;
	}
	
	public long getTotalExtraMemory()
	{
		return totalExtraMemory;
	}
	
	// implements a standard MergeSort
	public void mergeSort(int[] data)
	{
		if (data.length > 1) {
			int lengthFirst = data.length / 2;
			int lengthSecond = data.length - lengthFirst;
			int[] half1 = new int[lengthFirst];
			int[] half2 = new int[lengthSecond];
			System.arraycopy(data, 0, half1, 0, lengthFirst);
			System.arraycopy(data, lengthFirst, half2, 0, lengthSecond);
			// increment basic operations once here for each split
			incBasicOpCount();
			// and record the amount of extra memory that was just allocated
			addToExtraMemory(lengthFirst);
			addToExtraMemory(lengthSecond);
			mergeSort(half1);
			mergeSort(half2);
			merge(half1, half2, data);
		}	
	}
	
	public void sort(int[] data)
	{
		resetBasicOpCount();
		resetExtraMemory();
		mergeSort(data);
		addToTotalOpCount(getBasicOpCountLastSort());
		addToTotalExtraMemory(getExtraMemoryLastSort());
	}
	
	// merges (and sorts) the contents of two source arrays into one destination array
	protected void merge(int[] inData1, int[] inData2, int[] outData)
	{
		int i = 0;
		int j = 0;
		int k = 0;
		while ((i < inData1.length) && (j < inData2.length)) {
			if (inData1[i] <= inData2[j]) {
				outData[k] = inData1[i];
				i++;
			} else {
				outData[k] = inData2[j];
				j++;
			}
			k++;
			// merge step basic operations accumulated here - i.e. key comparisons
			incBasicOpCount();
		}
		if (i == inData1.length) {
			System.arraycopy(inData2, j, outData, k, inData2.length - j);
		} else {
			System.arraycopy(inData1, i, outData, k, inData1.length - i);
		}
	}

	@Override
	public String getSortName()
	{
		return "Mergesort";
	}
}
