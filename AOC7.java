package aoc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.LongConsumer;


	public class AOC7   {
		
	  private static final int STACK_SIZE = 20;
	  private static final int SMALL_DIR_THRESHOLD = 100000;
	  private static final long TOTAL_SPACE = 70000000L;
	  private static final long REQUIRED_SPACE = 30000000L;

	  public static void main(String[] args) {
		   String fileName = "C:\\Natalia\\AOC22\\input7.txt";     
	       String[] lines = loadFile(fileName);
	       long res = part1(lines);
	       System.out.println ("Part1:"+res);
	       res = part2(lines);
	       System.out.println ("Part2:"+res);
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
	  
	  public static Long part1( String[] lines) {
	    var sum = new long[1];
	    walkTree(lines,
	    		__ -> {}, //always
	    		dirSize -> {if (dirSize <= SMALL_DIR_THRESHOLD) {
	                                          sum[0] += dirSize;
	                                         }}
	    );
	    return sum[0];
	  }

	
	  public static Long part2( String[] lines) {
	    long[] required = new long[] { REQUIRED_SPACE - TOTAL_SPACE };
	    ArrayList<Long> potential = new ArrayList<Long>();
	    walkTree(lines,
	        fileSize -> required[0] += fileSize,
	        dirSize -> { if (dirSize >= required[0]) {
	                      potential.add(dirSize);
	                      }
	        			}
	    );

	    long min = Long.MAX_VALUE;
	    for (long size : potential) {
	      if (size > required[0]) {
	        min = Math.min(min, size);
	      }
	    }
	    return min;
	  }

	  private static void walkTree(String[] lines, LongConsumer onFile, LongConsumer onDirectory) {
	   
	    long[] stack = new long[STACK_SIZE];
	    int pointer = stack.length /2;
        //back list method
	    for (int i = lines.length - 1; i >= 0; i--) {
	      String line = lines[i];
	  //    System.out.println(line);
	      char firstChar = line.charAt(0);
	      char thirdChar = line.charAt(2);
	      if (firstChar >= '0' && firstChar <= '9') {
	        int fileSize = Integer.parseInt(line, 0, line.indexOf(" "), 10);
	        stack[pointer] += fileSize;
	   //     System.out.println("at"+ pointer + ":  "+ stack[pointer]);
	        onFile.accept(fileSize);
	      } else if (firstChar == '$' && thirdChar == 'c') {
	        if (line.equals("$ cd ..")) {
	          stack[++pointer] = 0;
	        } else {
	          long dirSizeTotal = stack[pointer--];    
	          stack[pointer] += dirSizeTotal;
	       //   System.out.println("at "+ pointer + ":  "+ stack[pointer]);
	          onDirectory.accept(dirSizeTotal);
	        }
	      }
	    }
	  }
	}
