package data;

public class TestDataGenerator
{

	public static int[] generate(String dataSetType, long dataSetSize)
	{
		// TODO actually generate data
		//int[] data = { 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };

		int[] data = new int[10000];
		for (int i = 0; i < data.length; i++)
		{
			data[i] = data.length - i;
		}
		
		return data;
	}
	
}
