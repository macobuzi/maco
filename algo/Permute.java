import java.io.*; 
import java.util.*;
// Add any extra import statements you may need here


class Permute {

  // Add any helper functions you may need here
  private boolean isSorted(char[] arr) {
    for (int i=1; i<arr.length; i++)
      if (arr[i] <= arr[i-1])
        return false;
    return true;
  }
  
  private String permute(char[] a, int i, int j) {
	
    int len = j - i + 1;
    for (int k=0; k<len/2; k++) {
      char t = a[i+k];
      a[i+k] = a[j-k];
      a[j-k] = t;
    }
	String s = new String(a);
	//System.out.printf("i=%d, j=%d, v=%s \n", i, j, s);
    return s;
  }

  int minOperations(int[] arr) {
    // Write your code here
    int n = arr.length;
    Queue<Object[]> q = new LinkedList<>();
    Set<String> marked = new HashSet<>();
    StringBuilder sb = new StringBuilder();
    for (int i=0; i<n; i++)
      sb.append(arr[i]);
    q.offer(new Object[] {sb.toString(), 0});
    while (!q.isEmpty()) {
      Object[] u = q.poll();
      String state = (String) u[0];
      int step = (Integer) u[1];
	  System.out.println(state);
      if (isSorted(state.toCharArray())) {
        return step;
      }
      marked.add(state);
      for (int i=0; i<n; i++)
        for (int j=i+1; j<n; j++) {
          String v = permute(state.toCharArray(), i, j);
          if (!marked.contains(v))
            q.offer(new Object[]{v, step + 1});
        }
    }
    return -1;
  }












  // These are the tests we use to determine if the solution is correct.
  // You can add your own at the bottom, but they are otherwise not editable!
  int test_case_number = 1;
  void check(int expected, int output) {
    boolean result = (expected == output);
    char rightTick = '\u2713';
    char wrongTick = '\u2717';
    if (result) {
      System.out.println(rightTick + " Test #" + test_case_number);
    }
    else {
      System.out.print(wrongTick + " Test #" + test_case_number + ": Expected ");
      printInteger(expected); 
      System.out.print(" Your output: ");
      printInteger(output);
      System.out.println();
    }
    test_case_number++;
  }
  void printInteger(int n) {
    System.out.print("[" + n + "]");
  }
  public void run() {

    int n_1 = 5;
    int[] arr_1 = {1, 2, 5, 4, 3};
    int expected_1 = 1;
    int output_1 = minOperations(arr_1);
    check(expected_1, output_1);

    int n_2 = 3;
    int[] arr_2 = {3, 1, 2};
    int expected_2 = 2;
    int output_2 = minOperations(arr_2);
    check(expected_2, output_2);
    
    // Add your own test cases here
    
  }
  public static void main(String[] args) {
    new Permute().run();
  }
}
