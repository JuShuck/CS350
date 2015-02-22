package runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;

/**
 * Saves test data.
 *
 * @author Ian
 */
public class TestDataSaver
{
	/**
	 * The location that the test data will be saved in.
	 */
	private File target;
	
	/**
	 * The time at which the test data saver was created.
	 * This is used to specify a date in the result directory
	 * for a specific run.
	 */
	private String startedAt;
	
	/**
	 * Initializes a Test Data Saver.
	 * 
	 * @param resultDirectory
	 * @throws IOException
	 */
	public TestDataSaver(File resultDirectory) throws IOException
	{
		this.startedAt = Util.format(new Date());
		this.target = Paths.get(resultDirectory.getCanonicalPath(), this.startedAt).toFile();
		
		if (!this.target.exists())
		{
			this.target.mkdirs();
		}
	}
	
	/**
	 * Saves the results into the directory.
	 * 
	 * @param result
	 */
	public void save(TestResult result)
	{
		// TODO store in CSV file in the directory.
	}
	
	/**
	 * The directory the results are stored in.
	 * 
	 * @return
	 */
	public String getDirectory()
	{
		try
		{
			return target.getCanonicalPath();
		}
		catch (IOException e)
		{
			return null;
		}
	}
}
