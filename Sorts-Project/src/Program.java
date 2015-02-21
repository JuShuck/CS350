import java.io.File;

import runner.RunConfigurator;
import runner.TestRunner;



public class Program
{
	
	public static final String TEST_RESULT_DIRECTORY = "./results";
	
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
			System.err.println("Exception: " + e.getClass().getCanonicalName());
			System.err.println("Message:   " + e.getMessage());
		}
	}
	
	private static void execute(String[] args) throws Exception
	{
		if (args.length != 1)
		{
			System.err.println("Please specify the run configuration file path to use.");
			return;
		}
		
		RunConfigurator config = RunConfigurator.getRunConfigurator(args[0]);
		
		System.out.println("Test Runner Configuration:");
		System.out.println("\tSource:\t\t" + config.getSourceFile());
		System.out.println();
		System.out.println("\tSort Name:\t" + config.getSortName());
		System.out.println("\tSort Config:\t" + config.getSortConfig());
		System.out.println();
		System.out.println("\tData Type:\t" + config.getDataSetType());
		System.out.println("\tData Config:\t" + config.getDataSetConfig());
		System.out.println("\tData Size:\t" + config.getDataSetSize());
		System.out.println("\tIterations:\t" + config.getTotalIterations());
		System.out.println();
		
		File resultDirectory = getResultDirectory();
		System.out.println("\tTest Results will be stored in the directory:");
		System.out.println("\t" + resultDirectory.getCanonicalPath());
		
		TestRunner.run(config);
	}
	
	private static File getResultDirectory()
	{
		File resultDirectory = new File(TEST_RESULT_DIRECTORY);
		
		if (resultDirectory.exists() && resultDirectory.isDirectory())
		{
			return resultDirectory;
		}
		else if (resultDirectory.exists() && !resultDirectory.isDirectory())
		{
			throw new RuntimeException("The specified result path is not a directory: " + resultDirectory.getAbsolutePath());
		}
		
		if (resultDirectory.mkdirs())
		{
			return resultDirectory;
		}
		
		throw new RuntimeException("Attempted to create result directory, but failed: " + TEST_RESULT_DIRECTORY);
	}
}
