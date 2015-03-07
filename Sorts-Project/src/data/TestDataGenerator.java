package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class TestDataGenerator
{
	/**
	 * Determines the max number of key values for the "fewUniqueKeys" test.
	 * @Integer
	 */
	public static int maxKeyVals = 4;

	public static int[] generate(String dataSetType, long dataSetSize, String dataSetConfig) throws Exception {
		switch(dataSetType.toLowerCase()) {
			case "sorted":
				return generateSorted(dataSetType, dataSetSize);
			case "reversed":
				return generateReversed(dataSetType, dataSetSize);
			case "ushaped":
				return generateUShaped(dataSetType, dataSetSize);
			case "fewunique":
				return generateFewUnique(dataSetType, dataSetSize);
			case "random":
				return generateRandom(dataSetType, dataSetSize);
			default:
				return null;
		}
	}

	/**
	 * Generates Random data from [0-99]. If there is a random data file in data/runs, that
	 * data will be loaded instead of newly generated data
	 * @param dataSetType
	 * @param dataSetSize
	 * @return
	 * @throws Exception 
	 */
	private static int[] generateRandom(String dataSetType, long dataSetSize) throws Exception {
		// Checks to see if there is a reference file to load
		int[] data = loadReferenceFile(dataSetType, dataSetSize);
		//If loadReferenceFile returns null, it didn't find a file to load
		if ( data != null) {
			return data;
		}
		data = new int[(int) dataSetSize];
		PrintWriter writer = getWriter(dataSetType, dataSetSize);
		//Because Random produces negative numbers we specify the range
		// as 0 - Interger.Max_Value
		Random random = new Random();
		for(int i = 0; i < data.length; i++) {
			int num = random.nextInt(Integer.MAX_VALUE)%100;
			data[i] = num;
			writer.println(num);
		}
		writer.close();
		return data;
	}

	/**
	 * Creates a list of unique keys to populate the data array. Again, if there is a generated
	 * file in data/runs then that data will be loaded instead
	 * @param dataSetType
	 * @param dataSetSize
	 * @return
	 * @throws Exception 
	 */
	private static int[] generateFewUnique(String dataSetType, long dataSetSize) throws Exception {
		// Checks to see if there is a reference file to load
		int[] data = loadReferenceFile(dataSetType, dataSetSize);
		//If loadReferenceFile returns null, it didn't find a file to load
		if ( data != null) {
			return data;
		}
		data = new int[(int) dataSetSize];
		PrintWriter writer = getWriter(dataSetType, dataSetSize);
		//Because Random produces negative numbers we specify the range
		// as 0 - Interger.Max_Value
		Random random = new Random();
		int[] list = new int[maxKeyVals];
		// Populate the list of unique keys
		for(int i = 0; i < maxKeyVals; i++) {
			list[i] = random.nextInt(Integer.MAX_VALUE)%100;
		}
		//Populate the data array
		for(int i = 0; i < data.length-1; i++) {
			int index = random.nextInt(Integer.MAX_VALUE)%maxKeyVals;
			data[i] = list[index];
			writer.println(list[index]);
		}
		writer.close();
		return data;
	}

	/**
	 * Generates a sorted array
	 * @param dataSetType
	 * @param dataSetSize
	 * @return
	 * @throws Exception 
	 */
	public static int[] generateSorted(String dataSetType, long dataSetSize) throws Exception {
		// Checks to see if there is a reference file to load
		int[] data = loadReferenceFile(dataSetType, dataSetSize);
		//If loadReferenceFile returns null, it didn't find a file to load
		if ( data != null) {
			return data;
		}
		data = new int[(int) dataSetSize];
		PrintWriter writer = getWriter(dataSetType, dataSetSize);
		for(int i = 0; i < dataSetSize; i++) {
			data[i] = i;
			writer.println(i);
		}
		writer.close();
		return data;
	}

	/**
	 * Generates a reverse ordered array
	 * @param dataSetType
	 * @param dataSetSize
	 * @return
	 * @throws Exception 
	 */
	public static int[] generateReversed(String dataSetType, long dataSetSize) throws Exception {
		// Checks to see if there is a reference file to load
		int[] data = loadReferenceFile(dataSetType, dataSetSize);
		//If loadReferenceFile returns null, it didn't find a file to load
		if ( data != null) {
			return data;
		}
		data = new int[(int) dataSetSize];
		PrintWriter writer = getWriter(dataSetType, dataSetSize);
		for(int i = 0; i < dataSetSize; i++) {
			int size = (int)dataSetSize;
			data[i] =  size - i;
			writer.println(size-i);
		}
		writer.close();
		return data;
	}

	/**
	 * Generates a 'U-Shaped' data array. This is of the form n/2 - 0 and then 0 - n/2
	 * @param dataSetType
	 * @param dataSetSize
	 * @return
	 * @throws Exception 
	 */
	public static int[] generateUShaped(String dataSetType, long dataSetSize) throws Exception {
		// Checks to see if there is a reference file to load
		int[] data = loadReferenceFile(dataSetType, dataSetSize);
		//If loadReferenceFile returns null, it didn't find a file to load
		if ( data != null) {
			return data;
		}
		data = new int[(int) dataSetSize];
		PrintWriter writer = getWriter(dataSetType, dataSetSize);
		int half = (int) dataSetSize / 2;
		//Generates n/2 - 0
		for(int i = 0; i < half; i++) {
			data[i] = half - i;
			writer.println(half - i);
		}
		//Generates 0 - n/2
		for(int i = 0; i < half; i++) {
			data[i] = i;
			writer.println(i);
		}

		writer.close();
		return data;
	}

	/**
	 * Gets the associated printwriter for each generate
	 * @param dataSetType
	 * @return
	 */
	public static PrintWriter getWriter(String dataSetType, long dataSetSize) {
		PrintWriter writer = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd-HH.mm.ss");
			String size = String.valueOf(dataSetSize);
			String pathName = "Sorts-Project\\data\\runs\\" + dataSetType + "(" + size + ")-" + dateFormat.format(new Date()) + ".txt";
			//Create a new file for this run
			writer = new PrintWriter(pathName, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return writer;
	}

	/**
	 * If there is a file in data/runs that matches the set type that we're looking for, then
	 * read in the data and return the array of loaded data. Return null if file isn't found
	 * @param dataSetType
	 * @param dataSetSize
	 * @return
	 * @throws Exception 
	 */
	public static int[] loadReferenceFile(String dataSetType, long dataSetSize) throws Exception {
		int[] data = new int[(int) dataSetSize];
		File dir = new File("Sorts-Project\\data\\runs");
		
		if (!dir.exists() && !dir.mkdirs())
		{
			throw new Exception("The Sorts-Project/data/runs directory does not exist and could not be created automatically.");
		}
		
		File[] dirList = dir.listFiles();
		// Search all files in the directory
		for(File f : dirList) {
			if(f.getAbsolutePath().contains(dataSetType) && f.getAbsolutePath().contains("(" + String.valueOf(dataSetSize) + ")")) {
				//If we found a match attempt to read in the document
				BufferedReader in = null;
				try {
					in = new BufferedReader(new FileReader(f.getAbsoluteFile()));
					int i =0;
					String line = null;
					while((line = in.readLine()) != null) {
						data[i] = Integer.parseInt(line);
						i++;
					}
					
					if (i != dataSetSize)
					{
						throw new Exception ("The obtained array is not of the desired length.");
					}
					
					return data;
				} catch (Exception e) {
					throw e;
				}
				finally {
					if (in != null)
					{
						in.close();
					}
				}
			}
		}
		// Return null if a match isn't found.
		return null;
	}
}
