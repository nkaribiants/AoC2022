package aoc;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AOC19  {
  
  static int[] cost;
  static int geodeBest;
  static int maxTime;
  
  public static void main(String[] args) throws Exception {
 	 List<String> lines = Files.readAllLines(Paths.get("C:\\Natalia\\AOC22\\input19.txt"));
 	 String[] input = new String[lines.size()];
 	 lines.toArray(input);
 	 int res = part1(input);
 	 System.out.println(res);
 	 res = part2(input);
 	 System.out.println(res);
 }
   
    private static int part1(String[] lines) {
        int quality = 0;
        for(int i = 0; i < lines.length; i++) {
            String s = lines[i];
            cost = new int[6];
            geodeBest = 0;
            String[] bots = s.split(": |\\. ");
            //ore bot
            cost[0] = Integer.parseInt(bots[1].split(" ")[4]);
            //clay bot
            cost[1] = Integer.parseInt(bots[2].split(" ")[4]);
            //obsidian bot
            cost[2] = Integer.parseInt(bots[3].split(" ")[4]);
            cost[3] = Integer.parseInt(bots[3].split(" ")[7]);
            //geode bot
            cost[4] = Integer.parseInt(bots[4].split(" ")[4]);
            cost[5] = Integer.parseInt(bots[4].split(" ")[7]);

            maxTime = 24;

            quality += (i+1) * mostGeodes(0,0,0,0,1,0,0,0,0);
        }
        return quality;
    }

    //ore, clay, obsidian, geodes, ore robots, clay robots, obsidian robots, geode robots, current time
    static int mostGeodes(int o, int c, int ob, int g, int or, int cr, int obr, int gr, int time) {
        if(time == maxTime) {
            geodeBest = Math.max(geodeBest,g);
            return g;
        }
        int minsLeft = maxTime - time;
        int maxGeodesPossible = g;
        for(int i = 0; i < minsLeft; i++)
            maxGeodesPossible += gr + i;
        if(maxGeodesPossible < geodeBest)
            return 0;
        //calculate new materials 
        int no = o + or;
        int nc = c + cr;
        int nob = ob + obr;
        int ng = g + gr;

        if(o >= cost[4] && ob >= cost[5])
            return mostGeodes(no-cost[4], nc, nob - cost[5], ng, or, cr, obr, gr + 1, time + 1);
        if(cr >= cost[3] && obr < cost[5] && o >= cost[2] && c >= cost[3])
            return mostGeodes(no-cost[2], nc - cost[3], nob, ng, or, cr, obr + 1, gr, time + 1);

        int best = 0;

        if(obr < cost[5] && o >= cost[2] && c >= cost[3])
            best = Math.max(best, mostGeodes(no-cost[2], nc - cost[3], nob, ng, or, cr, obr + 1, gr, time + 1));
        
        if(cr < cost[3] && o >= cost[1])
            best = Math.max(best, mostGeodes(no-cost[1], nc, nob, ng, or, cr+1, obr, gr,time + 1));
        
        if(or < 4 && o >= cost[0])
            best = Math.max(best, mostGeodes(no-cost[0], nc, nob, ng, or+1, cr, obr, gr, time+1));
       
        if(o <= 4)
            best = Math.max(best, mostGeodes(no, nc, nob, ng, or, cr, obr, gr, time+1));

        return best;
    }


    private static int part2(String[] lines) {
        int quality = 1;
        for(int i = 0; i < 3; i++) {
            String s = lines[i];
            cost = new int[6];
            geodeBest = 0;
            String[] bots = s.split(": |\\. ");
            //ore bot
            cost[0] = Integer.parseInt(bots[1].split(" ")[4]);
            //clay bot
            cost[1] = Integer.parseInt(bots[2].split(" ")[4]);
            //obsidian bot
            cost[2] = Integer.parseInt(bots[3].split(" ")[4]);
            cost[3] = Integer.parseInt(bots[3].split(" ")[7]);
            //geode bot
            cost[4] = Integer.parseInt(bots[4].split(" ")[4]);
            cost[5] = Integer.parseInt(bots[4].split(" ")[7]);
            maxTime = 32;
            quality *= mostGeodes(0,0,0,0,1,0,0,0,0);
        }
        return quality;
    }

  
       
    }