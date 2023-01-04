package aoc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AOC6 {
	
	 private static String  loadFile (String fName) {
	        File file = new File(fName);
	        String line = "";
	        if (!file.canRead() || !file.isFile())
	            System.exit(0);
	            BufferedReader in = null;
	        try {
	            in = new BufferedReader(new FileReader(fName));	          
	            line = in.readLine();
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (in != null)
	                try {
	                    in.close();
	                } catch (IOException e) {
	                }
	        }
	        return line;
	    }
	 	 
	 private static int process1(String input) {
		String cr = "";
		int res = 0;
		 for (int i=0; i< input.length() - 4; i++) {
			cr = input.substring(i,i+4);
			if (checkRepeat(cr) == 1) {
			    return i+4;		
			}			
		}
	    return res;
	 }
	 
	 private static int process2(String input) {
		String cr = "";
		int res = 0;
		for (int i=0; i< input.length() - 14; i++) {
		 cr = input.substring(i,i+14);
		 if (checkRepeat(cr) == 1) {
			return i+14;		
		 }			
		}
	 return res;
	}
	 
	 private static int checkRepeat(String input) {
		 System.out.println(input);
		  Pattern p = Pattern.compile("^(?:([A-Za-z])(?!.*\\1))*$");
		  Matcher matcher = p.matcher(input);
		if (matcher.find())
			 return 1; //without duplicated chars
		 else
			 return 0; 
	 }

	  public static void main(String[] args) {
	        String fileName = "C:\\Natalia\\AOC22\\input6.txt";     
	        String line = loadFile(fileName);
	        System.out.println("Part1: "+process1(line));
	        System.out.println("Part2: "+process2(line));
	    }
	    	  
	}

