package aoc;

import java.nio.file.Paths;
import java.util.Scanner;

public class AOC10 {
  static int idx = 0, res = 0, col = 0;
     
 public static void main(String[] args) throws Exception{
	 int i = 1;	
	 Scanner scanner = new Scanner(Paths.get("C:\\Natalia\\AOC22\\input10.txt"));
     while (scanner.hasNext()) {
	    step(i); // every line
	    if ("addx".equals(scanner.next())) {
			step(i); //value changed after step
			i += scanner.nextInt();
		}
	  }
	  System.out.println(); 
	  System.out.println("Part1: "+res);
		 
	  }
static void step(int num) {
  if (0 == (col = idx % 40)) {
    System.out.println(); // muster lines
  }
  if (((num - 1) <= col && col <= (num + 1)) )
   System.out.print ('#'); // muster
  else
    System.out.print ('.');	   	
  if ((++idx) % 40 == 20) {
    res += (idx * num);
  }
}

}
