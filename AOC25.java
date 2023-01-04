package aoc;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AOC25 {


    public static void main(String[] args) throws Exception {
      
        List<String> lines = Files.readAllLines(Paths.get("C:\\Natalia\\AOC22\\input25.txt"));
        long sum = lines.stream().mapToLong(s-> {
            long r = 0;
            for(int i = 0; i < s.length(); i++) {
                int x = "=-012".indexOf(s.substring(s.length()-1-i, s.length()-i))-2;
                r += (x*Math.pow(5,(i)));
            }
            return r;
        }).sum();

        // Integer to SNAFU:
        String result = "";
        while(sum > 0) {
            result = "012=-".charAt((int)(sum%5))+result;
            sum -= (((sum+2)%5)-2);
            sum/=5;
        }

        System.out.println(result);
    }
}
