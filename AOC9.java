package aoc;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class AOC9 {
	  public static void main(String[] args) throws Exception {
		int i = 0;
		i = process (2, Paths.get("C:\\Natalia\\AOC22\\input9.txt"));
	    System.out.println ("Part1: " + i);
	    i = process (10, Paths.get("C:\\Natalia\\AOC22\\input9.txt"));
		System.out.println ("Part2: " + i);
	  }

	  public static int process(int counts, Path p) throws IOException {
	    int[][] knots = new int[counts][2]; // coords initialized to 0
	    Set<String> tailPositions = new HashSet<>();
	    tailPositions.add(Arrays.toString(knots[counts - 1]));
	    try (Scanner s = new Scanner(p)) {
	      while (s.hasNext()) {
	        for (int dir[] = deltas[s.next("[UDLR]").charAt(0)], n = s.nextInt(); n > 0; n--) {
	          increm(knots[0], dir); // move head
	          for (int k = 1; k < counts; k++) {
	            chase(knots[k], knots[k - 1]);
	          }
	          tailPositions.add(Arrays.toString(knots[counts - 1]));
	        }
	      }
	    }
	    return tailPositions.size();
	  }
	  private static void chase(int[] tail, int[] head) {
	    int row = head[0] - tail[0];
	    int col = head[1] - tail[1];
	    int distance = Math.abs(row) + Math.abs(col);
	    boolean sameRowOrCol = tail[0] == head[0] || tail[1] == head[1];
	    if ((sameRowOrCol && distance > 1) ||distance > 2) {
	     increm (tail, new int[] {Integer.signum(row), Integer.signum(col)});
	    }
	  }
	  private static void increm(int[] coord, int[] delta) {
		    coord[0] += delta[0];
		    coord[1] += delta[1];
		  }
	  private static int[][] deltas = new int[100][];
	  
	 static {
	    deltas['U'] = new int[] {+1, +0};
	    deltas['D'] = new int[] {-1, +0};
	    deltas['L'] = new int[] {+0, -1};
	    deltas['R'] = new int[] {+0, +1};
	  }

}
