import java.util.*;

public class Uva661 {
	public static void main(String argv[]) {
		Scanner sc = new Scanner(System.in);

		int numDev, numOp, fuseCap;
		numDev = sc.nextInt();
		numOp = sc.nextInt();
		fuseCap = sc.nextInt();
		int tc = 0;

		while (!(numDev == 0 && numOp == 0 && fuseCap == 0)) {
			tc++;
			
			int[] consumes = new int[numDev];
			for (int i = 0; i < numDev; i++) {
				consumes[i] = sc.nextInt();
			}
			long usage = 0;
			long maxUsage = 0;
			boolean[] turnOns = new boolean[numDev];
			boolean blown = false;
			for (int j = 0; j < numOp; j++) {
				int dev = sc.nextInt() - 1;
				if (dev >= 0 && dev < numDev) {
					turnOns[dev] = !turnOns[dev];
					if (turnOns[dev]) {
						usage += consumes[dev];
					} else {
						usage -= consumes[dev];
					}
					if (usage > fuseCap) {
						blown = true;
					}
					maxUsage = Math.max(maxUsage, usage);
				}
			}
			System.out.println("Sequence " + tc);
			if (blown) {
				System.out.println("Fuse was blown.");
			} else {
				System.out.println("Fuse was not blown.");
				System.out.printf("Maximal power consumption was %d amperes.\n", maxUsage);
			}

			numDev = sc.nextInt();
			numOp = sc.nextInt();
			fuseCap = sc.nextInt();

			System.out.println();
		}
	}
}