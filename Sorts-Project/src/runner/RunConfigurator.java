package runner;

import java.io.File;
import java.io.IOException;

import org.ini4j.InvalidFileFormatException;
import org.ini4j.Wini;

/**
 * Contains run configurations.
 *
 * @author Ian
 */
public class RunConfigurator
{
	/**
	 * The name of the sort's class.
	 */
	private String sortName;
	
	/**
	 * If the sort has any options, they're all in this here string.
	 */
	private String sortConfig;
	
	/**
	 * The total number of iterations the sort should be run on the data set.
	 */
	private long totalIterations;
	
	/**
	 * The size of the data set to sort.
	 */
	private long dataSetSize;
	
	/**
	 * The data set type to use.
	 */
	private String dataSetType;
	
	/**
	 * If the data set type has any configuration options, they're in here.
	 */
	private String dataSetConfig;
	
	/**
	 * The file that contains this configuration.
	 */
	private File source;

	public static RunConfigurator getRunConfigurator(String filename) throws InvalidFileFormatException, IOException
	{
		File file = new File(filename);
		
		if (!file.exists())
		{
			throw new IllegalArgumentException("The file was not found: " + filename);
		}
		
		return new RunConfigurator(file);
	}
	
	private RunConfigurator(File file) throws InvalidFileFormatException, IOException
	{
		Wini config = new Wini(file);

		sortName = config.get("Sort", "Name");
		sortConfig = config.get("Sort", "Config");
		
		totalIterations = config.get("Data", "Iterations", long.class);
		dataSetSize = config.get("Data", "Size", long.class);
		dataSetType = config.get("Data", "Type");
		dataSetConfig = config.get("Data", "Config");
		
		source = file;
	}
	
	public String getSortName()
	{
		return sortName;
	}

	public String getSortConfig()
	{
		return sortConfig;
	}

	public long getTotalIterations()
	{
		return totalIterations;
	}

	public long getDataSetSize()
	{
		return dataSetSize;
	}

	public String getDataSetType()
	{
		return dataSetType;
	}
	
	public String getDataSetConfig()
	{
		return dataSetConfig;
	}
	
	public String getSourceFile()
	{
		return source.getAbsolutePath();
	}
}
