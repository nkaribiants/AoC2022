package aoc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class AOC5 {
	/* private static ArrayList<String> stacks = new ArrayList<String>(
	            Arrays.asList("ZN",
                           "MCD",
                           "P"));*/
	
	  private static ArrayList<String> stacks = new ArrayList<String>(
	            Arrays.asList("SZPDLBFC",
                              "NVGPHWB",
                              "FWBJG",
                              "GJNFLWCS",
                              "WJLTPMSH",
                              "BCWGFS",
                              "HTPMQBW",
                              "FSTW",
                              "RCN"));
	  
/*	          [C]         [S] [H]                
			  [F] [B]     [C] [S]     [W]        
			  [B] [W]     [W] [M] [S] [B]        
			  [L] [H] [G] [L] [P] [F] [Q]        
			  [D] [P] [J] [F] [T] [G] [M] [T]    
			  [P] [G] [B] [N] [L] [W] [P] [W] [R]
			  [Z] [V] [W] [J] [J] [C] [T] [S] [C]
			  [S] [N] [F] [G] [W] [B] [H] [F] [N]*/
	
	 private static ArrayList<String[]>  parce(String datName) {
	        File file = new File(datName);
	        ArrayList<String[]> lines = new ArrayList<>();
	        if (!file.canRead() || !file.isFile())
	            System.exit(0);
	            BufferedReader in = null;
	        try {
	            in = new BufferedReader(new FileReader(datName));
	            String zeile = null;
	            while ((zeile = in.readLine()) != null) {
	            	String[] parts = zeile.split(";");
	            	lines.add(parts);    
	            }  
	            
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
	 
	 private static void part1(ArrayList<String[]> lines) {
		
	   for(int i=0; i<lines.size(); i++) {
		  int ind_move = Integer.valueOf(lines.get(i)[0]);
		  int ind_from = Integer.valueOf(lines.get(i)[1]);
		  int ind_to = Integer.valueOf(lines.get(i)[2]);
		//  System.out.println("Move "+ind_move+" from "+ind_from+" to "+ind_to); 
		  String stringFrom = stacks.get(ind_from-1);
		  String stringTo = stacks.get(ind_to-1);
		  String pack= stringFrom.substring((stringFrom.length() - ind_move), stringFrom.length());
		  String newStringFrom = stringFrom.substring(0,stringFrom.length()-ind_move);
		  stacks.set(ind_from-1, newStringFrom);
		  stacks.set(ind_to-1, stringTo + reverse(pack));
	//	  System.out.println("Stacks at "+i+" is "+stacks);
	   }
	 }
	 
	 private static void part2(ArrayList<String[]> lines) {
			
		   for(int i=0; i< lines.size(); i++) {
			  int ind_move = Integer.valueOf(lines.get(i)[0]);
			  int ind_from = Integer.valueOf(lines.get(i)[1]);
			  int ind_to = Integer.valueOf(lines.get(i)[2]);
		//	  System.out.println("Move "+ind_move+" from "+ind_from+" to "+ind_to); 
			  String stringFrom = stacks.get(ind_from-1);
			  String stringTo = stacks.get(ind_to-1);
			  String pack= stringFrom.substring((stringFrom.length() - ind_move), stringFrom.length());
			  String newStringFrom = stringFrom.substring(0,stringFrom.length()-ind_move);
			  stacks.set(ind_from-1, newStringFrom);
			  stacks.set(ind_to-1, stringTo + pack);
			  System.out.println("Stacks at "+i+" is "+stacks);
		   }
		 }

        private static String reverse (String str) {
        	String res = "";
        	for (int i=0; i<str.length(); i++)
            {
              char ch= str.charAt(i); //extracts each character
              res= ch+res; //adds each character in front of the existing string
            }
            
          return res;
        }
	    public static void main(String[] args) {
	        String dateiName = "C:\\Natalia\\AOC22\\input5.txt";
	        ArrayList<String[]> points = parce(dateiName);
	        part1(points);
	        System.out.println("Part1 "+ stacks);
	        part2(points);
	        System.out.println("Part2 "+ stacks);
	    
	    }
	    
	  
	}

