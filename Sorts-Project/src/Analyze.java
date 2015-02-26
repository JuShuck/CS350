import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * A class for analyzing the CSV files.
 *
 * @author Ian
 */
public class Analyze
{
	private static final int ELAPSED_TIME_OFFSET = 3;
	private static final int BASIC_OP_COUNT_OFFSET = 4;
	
	/**
	 * The main entry point for the Analyzer portion of the program.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			execute(args);
		}
		catch (Exception e)
		{
			System.err.println();
			System.err.println("An uncaught exception occurred.");
			System.err.println("Exception:   " + e.getClass().getCanonicalName());
			System.err.println("Message:     " + e.getMessage());
			System.err.println("Stack Trace: ");
			e.printStackTrace();
		}
	}
	
	/**
	 * Executes the analyzer.
	 * 
	 * @param args
	 * @throws Exception 
	 */
	private static void execute(String[] args) throws Exception
	{
		if (args.length != 1)
		{
			throw new IllegalArgumentException("Please specify a CSV file to analyze.");
		}
		
		File file = new File(args[0]);
		
		if (!file.exists())
		{
			System.err.println("The file specified does not exist: " + args[0]);
			return;
		}
		
		BufferedReader reader = getReader(file);
		
		// Validate the file.
		validateFile(reader);
		
		long elapsedNs = 0L;
		long totalRows = 0L;
		String row;
		List<Long> elapsedList = new ArrayList<Long>();
		while ((row = reader.readLine()) != null)
		{
			String[] columns = row.split(",");
			
			long ns = Long.parseLong(columns[ELAPSED_TIME_OFFSET].trim());
			
			elapsedNs += ns;
			totalRows++;
			
			elapsedList.add(ns);
		}
		
		System.out.println("Total Rows:\t\t\t" + formatNumber(totalRows));
		System.out.println("Total Elapsed Time (ns):\t" + formatNumber(elapsedNs));
		System.out.println("Total Elapsed Time (sec):\t" + formatNumber(toSeconds(elapsedNs)));
		System.out.println("Average elapsed time (ns):\t" + formatNumber(getAverage(elapsedNs, totalRows)));
		System.out.println("Average elapsed time (sec):\t" + formatNumber(toSeconds(getAverage(elapsedNs, totalRows))));
	
		System.out.println();
		
		// The average.
		double average = getAverage(elapsedNs, totalRows);
		
		// The variance numerator.
		double varianceNum = 0;
		
		for (Long ns : elapsedList)
		{
			varianceNum += Math.pow(((double) ns) - average, 2);
		}
		
		double variance = varianceNum / (double)totalRows;
		System.out.println("Variance:\t\t\t" + formatNumber(variance));
		
		double stdDev = Math.sqrt(variance);
		System.out.println("Standard Deviation:\t\t" + formatNumber(stdDev));
		
		double stdDev2 = stdDev * 2;
		System.out.println("Standard Deviation * 2:\t\t" + formatNumber(stdDev2));
		
		for (Long ns : elapsedList)
		{
			if (stdDev > ns)
			{
				continue;
			}
			
			System.out.println("\t" + ns);
		}
	}
	
	private static double toSeconds(long ns)
	{
		return ((double) ns) * 0.000000001;
	}
	
	private static double toSeconds(double ns)
	{
		return ns * 0.000000001;
	}
	
	private static double getAverage(long sum, long n)
	{
		return ((double) sum) / ((double) n);
	}
	
	private static String formatNumber(double number)
	{
		DecimalFormat formatter = new DecimalFormat("#,##0.00000");

		return formatter.format(number);
	}
	
	private static String formatNumber(long number)
	{
		DecimalFormat formatter = new DecimalFormat("#,###");

		return formatter.format(number);
	}
	
	/**
	 * Returns a reader on the specified file.
	 * 
	 * @param file
	 * @return
	 */
	private static BufferedReader getReader(File file) throws Exception
	{
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		return reader;
	}
	
	/**
	 * Validates the file, simply ensuring that the first line is a header column.
	 * It also validates the defined offsets are correct as well.
	 * 
	 * @param reader
	 * @throws IOException 
	 */
	private static void validateFile(BufferedReader reader) throws IOException
	{
		String line = reader.readLine();
		
		if (!line.contains(","))
		{
			throw new IllegalArgumentException("The specified file is not valid (no commas).");
		}
		
		String[] columns = line.split(",");
		
		if (ELAPSED_TIME_OFFSET >= columns.length && !columns[ELAPSED_TIME_OFFSET].toLowerCase().contains("(ns)"))
		{
			throw new IllegalArgumentException("The elapsed time offset is incorrect.");
		}
		
		if (BASIC_OP_COUNT_OFFSET >= columns.length && !columns[BASIC_OP_COUNT_OFFSET].toLowerCase().contains("basic"))
		{
			throw new IllegalArgumentException("The basic op count offset is incorrect.");
		}
	}
}
