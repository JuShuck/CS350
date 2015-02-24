package runner;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Paths;

public class Console
{
	private static PrintStream log;

	public static void init(File directory) throws IOException
	{
		File output = Paths.get(directory.getCanonicalPath(), "output.log").toFile();
		
		Console.log = new PrintStream(output);
	}
	
	public static void println(String s)
	{
		System.out.println(s);
		log.println(s);
	}
	
	public static void errprintln(String s)
	{
		System.err.println(s);
		
		if (log == null)
		{
			return;
		}
		
		log.println("!!! " + s);
	}
	
	public static void println()
	{
		System.out.println();
		log.println();
	}
	
	public static void errprintln()
	{
		System.err.println();
		
		if (log == null)
		{
			return;
		}
		
		log.println("!!!");
	}
	
	public static void print(String s)
	{
		System.out.print(s);
		log.print(s);
	}
	
	public static final PrintStream getPrintStream()
	{
		return log;
	}
}
