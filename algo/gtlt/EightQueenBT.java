import java.util.*;

public class EightQueenBT {
	public static void backtrack(int col, int n, int[] conf, boolean[] usedRow, boolean[] usedLDiag, boolean[] usedRDiag) {

		for (int row=0; row<n; row++) {
			if (!usedRow[row] && !usedLDiag[row + col] && !usedRDiag[row - col + n - 1]) {
				conf[col] = row;
				if (col == n-1) {
					for (int i=0; i<n; i++) {
						for (int j=0; j<n; j++)
							System.out.print(conf[j] == i ? "X " : ". ");
						System.out.println();
					}
					System.out.println();
				} else {
					usedRow[row] = true;
					usedLDiag[row + col] = true;
					usedRDiag[row - col + n - 1] = true;
					backtrack(col+1, n, conf, usedRow, usedLDiag, usedRDiag);
					usedRow[row] = false;
					usedLDiag[row + col] = false;
					usedRDiag[row - col + n - 1] = false;
				}			 
			}
		}
	}
	
	public static void main(String[] argv) {
		Scanner sc = new Scanner(System.in);

		// read input
		int n = sc.nextInt();

		// config
		int[] conf = new int[n];
		boolean[] usedRow = new boolean[n];
		boolean[] usedLDiag = new boolean[2*n-1]; // r + c
		boolean[] usedRDiag = new boolean[2*n-1];   // r - c [-7..7] -> (+n-1) [0..14]

		backtrack(0, n, conf, usedRow, usedLDiag, usedRDiag);
	}
}
