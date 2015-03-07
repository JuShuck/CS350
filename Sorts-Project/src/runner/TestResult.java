package runner;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Represents a single test run.
 *
 * @author Ian
 */
public class TestResult
{
	private String sortName;
	private long swapCount;
	private long basicOpCount;
	private long elapsedTime;
	private long totalExtraMemory;
	private Calendar ranAt;
	private long runId;
	
	public static String getHeaderLine()
	{
		return "Run ID, Ran At, Sort Name, Time (ns), Basic Op Count, Cumulative Basic Op Count, Swap Count, Cumulative Swap Count, Extra Memory Allocated, Cumulative Extra Memory Allocated";
	}
	
	public String getCsvLine()
	{
		List<String> columns = new ArrayList<String>();
		
		columns.add(Long.toString(runId));
		columns.add(Util.format(ranAt));
		columns.add(sortName);
		columns.add(Long.toString(elapsedTime));
		columns.add(Long.toString(basicOpCount));
		// TODO Whats the difference betwen basic op and cumulative?
		columns.add("");
		columns.add(Long.toString(swapCount));
		columns.add(""); // TODO cumulative swap
		columns.add(Long.toString(totalExtraMemory));
		columns.add(""); // TODO cumulative extra memory.
		
		return String.join(", ", columns);
	}
	
	public String getSortName()
	{
		return sortName;
	}
	
	public void setSortName(String sortName)
	{
		this.sortName = sortName;
	}
	
	public long getSwapCount()
	{
		return swapCount;
	}
	
	public void setSwapCount(long swapCount)
	{
		this.swapCount = swapCount;
	}
	
	public long getBasicOpCount()
	{
		return basicOpCount;
	}
	
	public void setBasicOpCount(long basicOpCount)
	{
		this.basicOpCount = basicOpCount;
	}

	public long getElapsedTime()
	{
		return elapsedTime;
	}

	public void setElapsedTime(long elapsedTime)
	{
		this.elapsedTime = elapsedTime;
	}

	public Calendar getRanAt()
	{
		return ranAt;
	}

	public void setRanAt(Calendar ranAt)
	{
		this.ranAt = ranAt;
	}

	public long getRunId()
	{
		return runId;
	}

	public void setRunId(long runId)
	{
		this.runId = runId;
	}

	public long getTotalExtraMemory()
	{
		return totalExtraMemory;
	}

	public void setTotalExtraMemory(long totalExtraMemory)
	{
		this.totalExtraMemory = totalExtraMemory;
	}
}
