package runner;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import data.TestDataGenerator;
import sorts.Sort;
import sorts.SortFactory;

/**
 * Runs tests... 
 *
 * @author Ian
 */
public class TestRunner
{
	/**
	 * Runs a sort according to a run configuration.
	 * 
	 * @param config
	 */
	public static void run(RunConfigurator config)
	{
		Sort sorter = SortFactory.getInstance(config.getSortName(), config.getSortConfig());
		
		// TODO Implement some way to get the proper data set type and of size.
	    // (Justin?)
		int[] data = TestDataGenerator.generate(config.getDataSetType(), config.getDataSetSize());
		
		TestResult result = null;
		long totalElapsed = 0;
		int[] dataToSort = new int[data.length];
		
		System.out.println("Run began at:\t\t" + (new Date()));
		System.out.println();
		
		for (long run = 1; run <= config.getTotalIterations(); run++)
		{
			// Always need to copy the data back to it's original state.
			System.arraycopy(data, 0, dataToSort, 0, dataToSort.length);

			System.out.print("\rExecuting Run: " + run + " of " + config.getTotalIterations() + " ");
			System.out.print("(" + (int)Math.floor((run / (double)config.getTotalIterations()) * 100) + "%)");
			
			result = run(sorter, dataToSort);
			
			totalElapsed += result.getElapsedTime();
		
			
			// TODO Store the results (I'll do that).
		}
		
		System.out.println();
		System.out.println();
		
		// TODO something with total elapsed...
		System.out.println("Run finished at:\t" + (new Date()));
		System.out.println("Elapsed time: " + nanosecondsToSeconds(totalElapsed) + " seconds (" + totalElapsed + "ns)");
	}
	
	public static long nanosecondsToSeconds(long ns)
	{
		return (int)((double)ns * 0.000000001);
	}
	
	/**
	 * Runs a sort once on the specified data set.
	 * 
	 * @param sorter
	 * @param data
	 * @return TestResult.
	 */
	private static TestResult run(Sort sorter, int[] data)
	{
		TestResult result = new TestResult();
		result.setRanAt(Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles")));
		
		long start = System.nanoTime();
		
		sorter.sort(data);

		long elapsed = System.nanoTime() - start;
		
		result.setElapsedTime(elapsed);
		result.setSortName(sorter.getSortName());
		result.setBasicOpCount(sorter.getTotalBasicOpCount());
		
		return result;
	}
	
	private static void printArray(int[] data)
	{
		for (int i = 0; i < data.length; i++)
		{
			System.out.printf("%d ", data[i]);
		}
		
		System.out.println();
	}
}
