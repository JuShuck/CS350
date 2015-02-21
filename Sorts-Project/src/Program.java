import runner.RunConfigurator;
import runner.TestRunner;



public class Program
{
	
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
		System.out.println("\tData Size:\t" + config.getDataSetSize());
		System.out.println("\tIterations:\t" + config.getTotalIterations());
		System.out.println();
		
		TestRunner.run(config);
	}
}
