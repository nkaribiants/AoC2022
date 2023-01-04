package aoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AOC8 {
	  public static void main(String[] args) {
		   String fileName = "C:\\Natalia\\AOC22\\input8.txt";     
	       String[] lines = loadFile(fileName);
	       int res = part1(lines);
	       System.out.println ("Part1 "+ res);
	       res = part2(lines);
	       System.out.println ("Part2" + res);
	  }

	  private static String[]  loadFile (String fName) {
	        File file = new File(fName);
	        String[] lines = null;
	        BufferedReader in = null;
	        if (!file.canRead() || !file.isFile())
	            System.exit(0);
	        try {
	            in = new BufferedReader(new FileReader(fName));	
	            lines = in.lines().toArray(String[]::new);            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (in != null)
	                try {
	                    in.close();
	                } catch (IOException e) {
	            }
	        }
	        return lines;
	    }
	  
	  public static int part1(String[] lines) {	 
		   String firstRow = lines[0];
		   int cols = firstRow.length();
		   int rows = lines.length;
		   String lastRow = lines[rows - 1];

		    boolean[] visible = new boolean[cols * rows];
		    int count = cols * 2 + (rows - 2) * 2;

		    for (int y = 1; y < rows - 1; y++) {
		      String row = lines[y];
		      int off = y * cols;
		      char max = row.charAt(0);
		      int  max_ind = 0;

		      // left->right
		      for (int x = 1; x < cols - 1; x++) {
		       char curr = row.charAt(x);
		        if (curr > max) {
		          max = curr;
		          max_ind = x;
		          visible[x + off] = true;
		          count++;
		        }
		      }

		      // right<-left
		      max = row.charAt(cols - 1);
		      for (int x = cols - 2; x > max_ind; x--) {
		        char curr = row.charAt(x);
		        if (curr > max) {
		          max = curr;
		          visible[x + off] = true;
		          count++;
		        }
		      }
		    }

		    for (int x = 1; x < cols - 1; x++) {
		      char max = firstRow.charAt(x);
		      int  max_idx = 0;

		      // top->down
		      for (int y = 1; y < rows - 1; y++) {
		        char curr = lines[y].charAt(x);

		        if (curr > max) {
		          max = curr;
		          max_idx = y;
		          count += visible[x + y * cols] ? 0 : 1;
		        }
		      }
		      // bottom-> up
		      max = lastRow.charAt(x);
		      for (int y = rows - 2; y > max_idx; y--) {
		        char curr = lines[y].charAt(x);
		        if (curr > max) {
		          max = curr;
		          count += visible[x + y * cols] ? 0 : 1;
		        }
		      }
		    }
		    return count;
		  }
	  
	  public static int part2(String[] lines) {
		int cols = lines[0].length();
		int rows = lines.length;
		int max = -1;
		int[] ref = new int[cols * rows];
		int idx = cols - 1;
		boolean[] skipped = new boolean[cols];
		
        for (int i = 1; i < rows - 1; i++) {
		    idx = i * cols;
		    String row = lines[i];
		    int[] backRef = new int[cols];
		    for (int j = 1; j < cols - 1; j++) {
		      idx++;
		      int uc = row.codePointAt(j);
		      int value = 1;
		            // look right
		      int x = j + 1;
		      while (x < cols) {
			      int t = row.codePointAt(x);
			      if (t >= uc) {
			         if (t == uc) {
			           backRef[x] = j;
			         }
			       x++;
			       break;
		          }
		          backRef[x] = j;
		         x++;
		       }
		       value *= (x - 1) - j;
		      // left
		      value *= j - backRef[j];
		      int maxP = (i - ref[idx]) * ((rows - 1) - i) * value;
		      if (maxP <= max) {
		          skipped[i] = true;
		          continue;
		      }
		      // up
		       int y = i + 1;
		       int refIdx = idx + cols;
		        while (y < rows) {
		          int t = lines[y].codePointAt(j);
		              if (t >= uc) {
		                if (t == uc) {
		                  ref[refIdx] = i;
		                }
		                y++;
		                break;
		              }
		              ref[refIdx] = i;
		              refIdx += cols;
		              y++;
		            }
		            value *= (y - 1) - i;
		            // down
		            if (skipped[i]) {		             
		              for (y = i - 1; y >= ref[idx]; y--) {
		                int t = lines[y].codePointAt(j);
		                if (t >= uc) {
		                  y--;
		                  break;
		                }
		              }
		              value *= i - (y + 1);
		            } else {
		              value *= i - ref[idx];
		            }
		            max = Math.max(max, value);
		            skipped[i] = false;
		          } //j loop
		        }//i loop
		        return max;
		      }  
}
