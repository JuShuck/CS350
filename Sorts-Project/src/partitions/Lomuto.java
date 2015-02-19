package partitions;

public class Lomuto extends Partition {
	// implement the Lomuto partition
	public void partition(int[] A, int M, int N, int[] refIndeces) {
		int P = A[M];
		int S = M;
		for (int i = M + 1; i <= N; i++) {
			if (A[i] < P) {
				S++;
				exchange(A, S, i);
			}
		}
		exchange(A, M, S);
		refIndeces[0] = S;
		refIndeces[1] = S + 1;
	}
}
