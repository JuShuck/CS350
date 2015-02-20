package edu.pdx.cs350.runner;

import java.util.Calendar;
import java.util.TimeZone;

import edu.pdx.cs350.Sort;

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
	public void run(RunConfigurator config)
	{
		// TODO Implement some SortFactory or something to get an instance
		// of the specified sort (Aaron?).
		Sort sorter = SortFactory.getInstance(config.getSortName(), config.getSortConfig());
		
		// TODO Implement some way to get the proper data set type and of size.
	    // (Justin?)
		int[] data = TestDataGenerator.generate(config.getDataSetType(), config.getDataSetSize());
		
		TestResult result = null;
		long totalElapsed = 0;
		for (long run = 1; run <= config.getTotalIterations(); run++)
		{
			result = run(sorter, data);
			
			totalElapsed += result.getElapsedTime();
		
			// TODO Store the results (I'll do that).
		}
		
		// TODO something with total elapsed...
	}
	
	/**
	 * Runs a sort once on the specified data set.
	 * 
	 * @param sorter
	 * @param data
	 * @return TestResult.
	 */
	private TestResult run(Sort sorter, int[] data)
	{
		TestResult result = new TestResult();
		result.setRanAt(Calendar.getInstance(TimeZone.getTimeZone("America/Los_Angeles")));
		
		long start = System.nanoTime();
		
		sorter.sort(data);
		
		long elapsed = System.nanoTime() - start;
		
		result.setElapsedTime(elapsed);
		result.setSortName(sorter.getSortName());
		result.setBasicOpCount(sorter.getBasicOpCount());
		result.setSwapCount(sorter.getSwapCount());
		
		return result;
	}
}
