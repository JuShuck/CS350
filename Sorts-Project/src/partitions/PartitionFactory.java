package partitions;

/**
 * This factory will make an instance of the proper partition
 * algorithm to be used within a Quicksort instance.
 *
 * @author Ian
 */
public class PartitionFactory
{
	/**
	 * Returns an instance of the specified partition algorithm implementation.
	 * 
	 * @param partitionName
	 * @return
	 */
	public static Partition getInstance(String partitionName)
	{
		switch (partitionName.toLowerCase())
		{
			case "lomuto":
				return new Lomuto();
				
			case "hoare":
				return new HoareOriginal();
				
			default:
				throw new IllegalArgumentException("Unknown partition: " + partitionName + ".");
		}
	}
}
