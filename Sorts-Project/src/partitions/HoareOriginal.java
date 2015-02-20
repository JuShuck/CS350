/* my version of the Hoare partition - basically as close of an interpretation as I could get to the original ACM publication using Java */

package partitions;

import java.util.Random;

public class HoareOriginal extends Partition
{
	// random number generator
	private static Random random = new Random();
	
	// implement the original Hoare partition
	public void partition(int[] A, int M, int N, int[] refIndeces)
	{
		swaps = 0;
		int F = random.nextInt(N - M) + M;
		int X = A[F];
		int I = M;
		int J = N;
		while (true) {
			do {
				if(X < A[I])
				{
					break;
				}
				I++;
			} while (I < N);
			do {
				if(A[J] < X) {
					break;
				}
				J--;
			} while (J > M);
			if (I < J) {
				exchange(A, I, J);
				I++;
				J--;
			} else if (I < F) {
				exchange(A, I, F);
				I++;
				break;
			} else if (F < J) {
				exchange(A, F, J);
				J--;
				break;
			} else break;
		}
		refIndeces[0] = J;
		refIndeces[1] = I;
		totalSwaps += swaps;
	}
}
