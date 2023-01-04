package aoc;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AOC3 {
	
	 private static void parce(String datName) {
	        File file = new File(datName);
	        if (!file.canRead() || !file.isFile())
	            System.exit(0);
	            BufferedReader in = null;
	        try {
	            in = new BufferedReader(new FileReader(datName));
	            String zeile = null;
	            int result = 0;
	            Map mp = loadHash();
	            while ((zeile = in.readLine()) != null) {	
	            	String part1 = zeile.substring(0, zeile.length()/2);
	            	String part2 = zeile.substring(zeile.length()/2);
	                char ch = getDoubleChar(part1, part2);
	                if(ch != '#')
	                result = result + ((Integer) mp.get(Character.toString(ch))).intValue();
	            
	            }      
	            System.out.println("Result " + result); 
	        } catch (IOException e) {
	            e.printStackTrace();
	        } finally {
	            if (in != null)
	                try {
	                    in.close();
	                } catch (IOException e) {
	                }
	        }
	    }
	 
	 private static ArrayList<String> parce2(String datName) {
	        File file = new File(datName);
	        ArrayList<String> lines = new ArrayList<>();
	        if (!file.canRead() || !file.isFile())
	            System.exit(0);
	            BufferedReader in = null;
	        try {
	            in = new BufferedReader(new FileReader(datName));
	            String zeile = null;
	            while ((zeile = in.readLine()) != null) {  
	            	lines.add(zeile);	
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
	 
	 private static void part2(ArrayList<String> zeilen) {
		int result = 0;
		char ch = '#';
		Map mp = loadHash();
	    for(int i=0; i<zeilen.size(); i += 3) {
	    	ch = getDoubleChar((String)zeilen.get(i), (String)zeilen.get(i+1), (String)zeilen.get(i+2));
	   
	    if(ch != '#')
            result = result + ((Integer) mp.get(Character.toString(ch))).intValue();
	    }
	    System.out.println("Part2 "+ result); 
	 }


	    public static void main(String[] args) {
	        String dateiName = "C:\\Natalia\\AOC22\\input3.txt";
	        parce(dateiName);
	        ArrayList<String> list = parce2(dateiName);
	        part2(list);
	    
	    }
	    
	    private static char getDoubleChar (String str1, String str2) {
	    	char res = '#'; 
	    	for(int i=0; i < str1.length(); i++) {
	    	if (str2.indexOf(str1.charAt(i)) > -1) {
	    			res = str1.charAt(i);
	    	        return res;
	    	}     
	    	}
	    	return res;
	    }
	    
	    private static char getDoubleChar (String str1, String str2, String str3) {
	    	char res = '#';
	    
	    	for(int i=0; i < str1.length(); i++) {
	    	if (str2.indexOf(str1.charAt(i)) > -1 && str3.indexOf(str1.charAt(i))> -1) {
	    			res = str1.charAt(i);
	    	        return res;
	    	}     
	    	}
	    	return res;
	    }
	      
	    private static Map loadHash() {
	    	Map<String, Integer> map = new HashMap<String, Integer>() {
	            {   int i = 1;
	            	for (char ch = 'a'; ch <= 'z'; ++ch) { 
	            		put(String.valueOf(ch), i);
	            		i++;
	            	} 
	                for (char ch = 'A'; ch <= 'Z'; ++ch) { 
	                    put(String.valueOf(ch), i);
	                    i++;
	                }    
	            }
	    	} ; 
	    return map;
	    }
	}

