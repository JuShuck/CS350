package partitions;

public class PartitionFactory
{
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
