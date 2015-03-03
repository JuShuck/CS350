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
	
	// run method from Runnable interface
	public void run()
	{
		parallelMergeSort(dataThisThread);
	}
	
	// get the number of available threads
	public int getAvailableThreads()
	{
		return cores;
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
				Thread leftThread = new Thread(new ParallelMergeSort(half1, remainingCores / 2));
				Thread rightThread = new Thread(new ParallelMergeSort(half2, remainingCores / 2));
				leftThread.start();
				rightThread.start();
				try {
					leftThread.join();
					rightThread.join();
				} catch (InterruptedException e) {}
				merge(half1, half2, data);
			}
		}
	}
	
	@Override
	public void sort(int[] data)
	{
		resetBasicOpCount();
		parallelMergeSort(data);
		addTotalOpCount(getLastBasicOpCount());
	}
	
	@Override
	public String getSortName()
	{
		return "Parallel Mergesort";
	}
	
	@Override
	public void printDiagnostics()
	{
		System.out.println("Total extra memory: "/**/);
	}
}
