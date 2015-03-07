package runner;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import sorts.Sort;
import sorts.SortFactory;
import data.TestDataGenerator;

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
	 * @throws Exception 
	 */
	public static void run(RunConfigurator config, TestDataSaver saver) throws Exception
	{
		Sort sorter = SortFactory.getInstance(config.getSortName(), config.getSortConfig());
		
		// Generates a data array based on the config setup
		int[] data = TestDataGenerator.generate(config.getDataSetType(), config.getDataSetSize(), config.getDataSetConfig());
		
		TestResult result = null;
		long totalElapsed = 0;
		int[] dataToSort = new int[data.length];
		
		Console.println("Run began at:\t\t" + (new Date()));
		Console.println();
		
		for (long run = 1; run <= config.getTotalIterations(); run++)
		{
			// Always need to copy the data back to it's original state.
			System.arraycopy(data, 0, dataToSort, 0, dataToSort.length);

			Console.print("\rExecuting Run: " + run + " of " + config.getTotalIterations() + " ");
			Console.print("(" + (int)Math.floor((run / (double)config.getTotalIterations()) * 100) + "%)");
			
			result = run(sorter, dataToSort);
			result.setSortName(config.getSortName() + " (" + config.getSortConfig() + ")");
			result.setRunId(run);
			
			totalElapsed += result.getElapsedTime();
		
			saver.save(result);
		}
		
		Console.println();
		Console.println();
		
		// TODO something with total elapsed...
		Console.println("Run finished at:\t" + (new Date()));
		Console.println("Elapsed time: " + nanosecondsToSeconds(totalElapsed) + " seconds (" + totalElapsed + "ns)");
		
		saver.close();
	}
	
	/**
	 * Converts nanoseconds to seconds.
	 * 
	 * @param ns
	 * @return
	 */
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
		result.setSwapCount(sorter.getTotalSwaps());
		result.setTotalExtraMemory(sorter.getTotalExtraMemory());
		
		sorter.resetAll();
		
		return result;
	}
}
