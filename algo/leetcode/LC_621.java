/*
  A contains {A..Z}
  n
  same task = same letter
  permuation of A with min time to finish
  
  BF: each step choose 1 in 27 option {A..Z,IDLE} max step = (|A|+1) * n.
      |A| <= 10^4 -> not good
      Looking for O(n) or O(nlogn)
  DP: 
  Greedy: O(m * n) | m = |A|
      PriorityQueue -> log27 -> O(1)
 */
class LC_621 {
	private class Task implements Comparable<Task> {
		int num = 0;
		int readyTime = 1;

		public int compareTo(Task other) {
			return readyTime == other.readyTime ? other.num - num : readyTime - other.readyTime;
		}
	}
	
	public int leastInterval(char[] tasks, int waitTime) {
		int n = tasks.length;
		// O(1)
		Task[] taskArr = new Task[26];
		for (int i = 0; i <26; i++) {
			taskArr[i] = new Task();
		}
		for (int i=0; i<n; i++) {
			taskArr[tasks[i] - 'A'].num++;
		}

		// O(1)
		PriorityQueue<Task> pqueue = new PriorityQueue<>();
		for (int i=0; i<taskArr.length; i++) {
			if (taskArr[i].num > 0) {
				pqueue.offer(taskArr[i]);
			}
		}
		
		// O(n)
		int time = 0;
		while(pqueue.size() > 0) {
			Task t = pqueue.poll();
			if (time < t.readyTime) {
				time = t.readyTime;
			} else {
				time++;
			}
			t.num--;
			if (t.num > 0) {
				t.readyTime = time + waitTime + 1;
				pqueue.offer(t);
			}
		}
		return time;
	}
}
