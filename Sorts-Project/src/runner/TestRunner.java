package runner;

import java.util.Calendar;
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
	public void run(RunConfigurator config)
	{
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
		
		return result;
	}
}
