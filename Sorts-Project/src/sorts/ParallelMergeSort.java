package sorts;

public class ParallelMergeSort extends MergeSort implements Runnable
{
	
	// access the number of processors available to the current JVM
	private static int cores = Runtime.getRuntime().availableProcessors();
	
	private int[] dataThisThread;
	private int remainingCores;
	
	// default constructor
	public ParallelMergeSort()
	{
		remainingCores = cores;
	}
	
	// constructor used for passing data to new threads
	public ParallelMergeSort(int[] dataThisThread, int remainingCores)
	{
		this.dataThisThread = dataThisThread;
		this.remainingCores = remainingCores;
	}
	
	// get the number of available threads
	public static int getAvailableThreads()
	{
		return cores;
	}
	
	// run method from Runnable interface
	public void run()
	{
		parallelMergeSort(dataThisThread);
	}
	
	// implements a MergeSort that takes advantage of parallelism
	private void parallelMergeSort(int[] data)
	{
		if (data.length > 1) {
			if (remainingCores <= 1) {
				super.sort(data);
			} else {
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
				// now kick off the concurrent sorts
				ParallelMergeSort leftSort = new ParallelMergeSort(half1, remainingCores / 2);
				ParallelMergeSort rightSort = new ParallelMergeSort(half2, remainingCores / 2);
				Thread leftThread = new Thread(leftSort);
				Thread rightThread = new Thread(rightSort);
				leftThread.start();
				rightThread.start();
				// wait for concurrent threads to finish execution
				try {
					leftThread.join();
					rightThread.join();
				} catch (InterruptedException e) {}
				// accumulate the basic operations and extra memory totals from each concurrent thread
				addToOpCount(leftSort.getBasicOpCountLastSort());
				addToOpCount(rightSort.getBasicOpCountLastSort());
				addToExtraMemory(leftSort.getExtraMemoryLastSort());
				addToExtraMemory(rightSort.getExtraMemoryLastSort());
				// merge and sort the sub arrays
				merge(half1, half2, data);
			}
		}
	}
	
	@Override
	public void sort(int[] data)
	{
		resetBasicOpCount();
		resetExtraMemory();
		parallelMergeSort(data);
		addToTotalOpCount(getBasicOpCountLastSort());
		addToTotalExtraMemory(getExtraMemoryLastSort());
	}
	
	@Override
	public String getSortName()
	{
		return "Parallel Mergesort";
	}
}
