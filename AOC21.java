package aoc;


import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;

class AOC21 {
    private static HashMap<String, String> monkeys;
    private static void parse() throws IOException {
        monkeys = new HashMap<>();
        Scanner scanner = new Scanner(Paths.get("C:\\Natalia\\AOC22\\input21.txt"));
        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(": ");
            monkeys.put(tokens[0], tokens[1]);
        }
    }

    public static void main(String[] args) throws Exception {
        parse();
        System.out.println("Part1 " + solve("root"));
        // Heuristic is that the value of solve(left)-solve(right) is a monotone function of the value you put in "humn"
        parse();
        long a = 0;
        long b = Integer.MAX_VALUE * 1000000L;
        String root = monkeys.get("root");
        String[] tokens = root.split(" ");
        String left = tokens[0];
        String right = tokens[2];
        for(;;) {
            parse();
            long mid = (a + b) / 2;
            monkeys.put("humn", Long.toString(mid));
            long ans = solve(left) - solve(right);
            if(ans == 0) {
                System.out.println("Part2 "+mid);
                return;
            }
            if(ans > 0) {
                a = mid + 1;
            }
            else
                b = mid;
        }
    }

   
    private static double solveDouble(String monkey) {
        String job = monkeys.get(monkey);
        String[] tokens = job.split(" ");
        if(tokens.length == 1)
            return Double.parseDouble(tokens[0]);

        double left = solveDouble(tokens[0]);
        String op = tokens[1];
        double right = solveDouble(tokens[2]);

        double ans = switch(op) {
            case "+" -> left + right;
            case "-" -> left - right;
            case "*" -> left * right;
            default -> left / right;
        };

        monkeys.put(monkey, Double.toString(ans));
        return ans;
    }
    private static long solve(String monkey) {
        return (long)solveDouble(monkey);
    }

  
}
