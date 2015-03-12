package runner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
	 * Used for writing to the file.
	 */
	private BufferedWriter writer;
	
	private String fileName;
	
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
		else if (this.target.exists())
		{
			int tryCount = 2;
			while (this.target.exists())
			{
				this.target = Paths.get(resultDirectory.getCanonicalPath(), this.startedAt + "-" + tryCount).toFile();
				tryCount++;
			}
			
			if (!this.target.exists())
			{
				this.target.mkdirs();
			}
		}
	}
	
	/**
	 * Saves the results into the directory.
	 * 
	 * @param result
	 * @throws IOException 
	 */
	public void save(TestResult result) throws IOException
	{
		boolean writeHeader = this.writer == null;
		BufferedWriter writer = getWriter(result.getSortName());
		
		if (writeHeader)
		{
			writer.append(TestResult.getHeaderLine());
			writer.newLine();
		}
		
		writer.append(result.getCsvLine());
		writer.newLine();
		writer.flush();
	}
	
	/**
	 * Closes the output stream.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException
	{
		if (this.writer == null)
		{
			return;
		}
		
		this.writer.close();
	}
	
	/**
	 * Returns the file writer.
	 * 
	 * @param suffix
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter getWriter(String suffix) throws IOException
	{
		if (this.writer != null)
		{
			return this.writer;
		}
		
		File destination = new File(Paths.get(target.getAbsolutePath(), "data-" + suffix + ".csv").toString());
		FileWriter fileWriter = new FileWriter(destination, true);
		
		
		this.fileName = destination.getCanonicalPath();
		this.writer = new BufferedWriter(fileWriter);
		
		return this.writer;
	}
	
	public String getFileName()
	{
		return fileName;
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
