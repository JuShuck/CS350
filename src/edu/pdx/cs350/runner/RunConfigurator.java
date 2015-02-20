package edu.pdx.cs350.runner;

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

	public static RunConfigurator getRunConfigurator(String filename)
	{
		// TODO You know, actually implement this... I'll look into
		// ini4j -- http://ini4j.sourceforge.net/overview.html --
		// it seems rather simple... But who knows!
		return new RunConfigurator();
	}
	
	public String getSortName()
	{
		return sortName;
	}

	public void setSortName(String sortName)
	{
		this.sortName = sortName;
	}

	public String getSortConfig()
	{
		return sortConfig;
	}

	public void setSortConfig(String sortConfig)
	{
		this.sortConfig = sortConfig;
	}

	public long getTotalIterations()
	{
		return totalIterations;
	}

	public void setTotalIterations(long totalIterations)
	{
		this.totalIterations = totalIterations;
	}

	public long getDataSetSize()
	{
		return dataSetSize;
	}

	public void setDataSetSize(long dataSetSize)
	{
		this.dataSetSize = dataSetSize;
	}

	public String getDataSetType()
	{
		return dataSetType;
	}

	public void setDataSetType(String dataSetType)
	{
		this.dataSetType = dataSetType;
	}
}
