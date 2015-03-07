package data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import runner.Console;

public class Analyzer
{
	private static final int ELAPSED_TIME_OFFSET = 3;
	private static final int BASIC_OP_COUNT_OFFSET = 4;
	private static final int REMOVE_FIRST_X = 3;
	private static String HEADER_LINE = null;
	private static String CSV_FILE = null;
	private static String OUTPUT_DIR = null;
	
	public static void analyze(String file, File directory) throws IOException
	{
		String[] t = file.split(file.contains("/") ? "/" : "\\\\");
		CSV_FILE = t[t.length - 1];
		OUTPUT_DIR = directory.getCanonicalPath();
		
		Console.println();
		Console.println("Running analysis on result data.");
		Console.println();
		
		List<List<String>> rows = getCsvFile(file); 
		
		// total rows, elapsed time (ns and sec)
		// average time (ns and sec)
		Console.println("Basic statistics for all data points:");
		outputBasicStatsFor(rows);
		
		double stdDev = getStdDev(rows);
		Console.println("\tStandard Deviation:\t\t" + stdDev);
		
		// Now remove the first three rows.
		List<List<String>> rowsLess3 = rows.subList(REMOVE_FIRST_X, rows.size());
		Console.println();
		Console.println("Basic statistics with first 3 rows removed:");
		outputBasicStatsFor(rowsLess3);
		
		double stdDevLess3 = getStdDev(rowsLess3);
		Console.println("\tStandard Deviation:\t\t" + stdDevLess3);
		writeCsvFile(rowsLess3, "less-3");
		
		List<List<String>> rowsLessStdDev = removeRowsGreaterThan(rowsLess3, stdDevLess3);
		
		Console.println();
		Console.println("Basic statistics with first 3 rows & std dev removed:");
		outputBasicStatsFor(rowsLessStdDev);
		
		double stdDevLess3AndDev = getStdDev(rowsLessStdDev);
		Console.println("\tStandard Deviation:\t\t" + stdDevLess3AndDev);
		writeCsvFile(rowsLessStdDev, "less-3-std-dev");
	}
	
	private static List<List<String>> removeRowsGreaterThan(List<List<String>> rows, double min)
	{
		List<List<String>> withoutStdDev = new ArrayList<>();

		for (List<String> columns : rows)
		{
			long elapsed = toLong(columns.get(ELAPSED_TIME_OFFSET));
			
			if ((new Long(elapsed)).doubleValue() > min)
			{
				continue;
			}
			
			withoutStdDev.add(columns);
		}
		
		return withoutStdDev;
	}
	
	private static void writeCsvFile(List<List<String>> rows, String suffix) throws IOException
	{
		String path = Paths.get(OUTPUT_DIR, CSV_FILE.replaceAll(".csv", "-" + suffix + ".csv")).toString();

		File file = new File(path);
		if (file.exists())
		{
			file.delete();
		}
		else
		{
			file.createNewFile();
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		
		writer.write(HEADER_LINE);
		writer.newLine();
		
		for (List<String> columns : rows)
		{
			writer.write(StringUtils.join(columns, ","));
			writer.newLine();
		}
		
		writer.close();
	}
	
	private static double getStdDev(List<List<String>> rows)
	{
		// We need the average.
		long elapsedNs = getTotalElapsedNs(rows);
		double average = (new Long(elapsedNs)).doubleValue() / (new Integer(rows.size())).doubleValue();
		
		double variance = 0;
		for (List<String> columns : rows)
		{
			long ns = toLong(columns.get(ELAPSED_TIME_OFFSET));
			
			variance += Math.pow((new Long(ns)).doubleValue() - average, 2);
		}
		
		variance = variance / (new Integer(rows.size())).doubleValue();
		
		return Math.sqrt(variance);
	}
	
	private static long getTotalElapsedNs(List<List<String>> rows)
	{
		long elapsedNs = 0;
		for (List<String> columns : rows)
		{
			long elapsed = toLong(columns.get(ELAPSED_TIME_OFFSET));
			
			elapsedNs += elapsed;
		}
		
		return elapsedNs;
	}
	
	private static void outputBasicStatsFor(List<List<String>> rows)
	{
		Console.println("\tTotal Rows: " + rows.size());
		
		long elapsedNs = getTotalElapsedNs(rows);
		
		Console.println("\tTotal Elapsed Time (ns):\t" + elapsedNs);
		Console.println("\tTotal Elapsed Time (sec):\t" + toSeconds(elapsedNs));
		
		Console.println("\tAverage Elapsed Time (ns):\t" + (elapsedNs / (double)rows.size()));
		Console.println("\tAverage Elapsed Time (sec):\t" + toSeconds(elapsedNs / (double)rows.size()));
		
		long fastestRun = Long.MAX_VALUE;
		long slowestRun = Long.MIN_VALUE;
		for (List<String> columns : rows)
		{
			long elapsed = toLong(columns.get(ELAPSED_TIME_OFFSET));
			
			fastestRun = Math.min(fastestRun, elapsed);
			slowestRun = Math.max(slowestRun, elapsed);
		}
		
		Console.println("\tFastest Run (ns):\t\t" + fastestRun);
		Console.println("\tFastest Run (sec):\t\t" + toSeconds(fastestRun));
		Console.println("\tSlowest Run (ns):\t\t" + slowestRun);
		Console.println("\tSlowest Run (sec):\t\t" + toSeconds(slowestRun));
	}
	
	private static double toSeconds(long ns)
	{
		return ns * 0.000000001;
	}
	
	private static double toSeconds(double ns)
	{
		return ns * 0.000000001;
	}
	
	private static long toLong(String str)
	{
		try
		{
			return Long.valueOf(str);
		}
		catch (Exception e)
		{
			throw new IllegalArgumentException("This isn't a number! --> " + str);
		}
	}
	
	/**
	 * Reads the CSV file into lists.
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	private static List<List<String>> getCsvFile(String fileName) throws IOException
	{
		File file = new File(fileName);
		
		if (!file.exists())
		{
			Console.errprintln("The file specified does not exist: " + fileName);
			return null;
		}
		
		BufferedReader reader = new BufferedReader(new FileReader(file));
		List<List<String>> rows = new ArrayList<List<String>>();
		
		String row = null;
		int rowNumber = 0;
		while ((row = reader.readLine()) != null)
		{
			if (rowNumber == 0)
			{
				HEADER_LINE = row;
				rowNumber++;
				continue;
			}
			
			String[] columns = row.split(",");
			
			List<String> list = new ArrayList<>();
			for (String column : columns)
			{
				list.add(column.trim());
			}

			rows.add(list);
			rowNumber++;
		}
		
		reader.close();
		
		return rows;
	}
}
