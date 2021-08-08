import java.util.*;
import java.io.*;

public class IOI2009C {
	static class Kattio extends PrintWriter {
		private BufferedReader rd;
		private StringTokenizer st;
		private String line;

		public Kattio() {
			super(System.out);
			rd = new BufferedReader(new InputStreamReader(System.in));
		}

		public String next() {
			try {
				while (st == null || !st.hasMoreElements()) {
					line = rd.readLine();
					if (null == line)
						return null;
					st = new StringTokenizer(line);
				}
				return st.nextToken();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		public int nextInt() {
			return Integer.parseInt(next());
		}

		public long nextLong() {
			return Long.parseLong(next());
		}

		public double nextDouble() {
			return Double.parseDouble(next());
		}
	}
	
	private static class Contestant {
		public int id;
		public int score;
		public int solvedNum;

		public Contestant(int id, int score, int solvedNum) {
			this.id = id;
			this.score = score;
			this.solvedNum = solvedNum;
		}
	}

	public static void main(String[] argv) {
		Kattio sc = new Kattio();
		int contestantNum = sc.nextInt();
		int taskNum = sc.nextInt();
		int philipId = sc.nextInt();
		
		int[] taskScores = new int[taskNum];
		Arrays.fill(taskScores, contestantNum);
		List<Integer>[] taskSolves = new ArrayList[contestantNum];
		for (int i=0; i<contestantNum; i++) {
			taskSolves[i] = new ArrayList<Integer>();
			for (int j=0; j<taskNum; j++) {
				boolean solved = sc.nextInt() == 1;
				if (solved) {
					taskScores[j]--;
					taskSolves[i].add(j);
				}
			}
		}
		
		Contestant[] contestants = new Contestant[contestantNum];
		for (int i=0; i<contestantNum; i++) {
			int score = 0;
			for (int task : taskSolves[i]) {
				score += taskScores[task];
			}
			contestants[i] = new Contestant(i, score, taskSolves[i].size());
		}
		Arrays.sort(contestants, (c1, c2) -> {
			return (c1.score != c2.score) ? c2.score - c1.score : 
				(c1.solvedNum != c2.solvedNum) ? c2.solvedNum - c1.solvedNum :
					c1.id - c2.id;
		});
		for (int k=0; k<contestantNum; k++) {
			if (contestants[k].id == (philipId-1)) {
				System.out.println(contestants[k].score + " " + (k+1));
				break;
			}
		}
	}
}