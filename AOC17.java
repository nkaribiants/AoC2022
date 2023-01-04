package aoc;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AOC17 {

    private static List<Jet> jets = new ArrayList<>();

    private static boolean[][][] shapes = new boolean[][][] {
        { {true, true, true, true} },
        { {false, true, false}, {true, true, true}, {false, true, false} },
        { {false, false, true}, {false, false, true}, {true, true, true} },
        { {true}, {true}, {true}, {true} },
        { {true, true}, {true, true} }
    };
    enum Jet {
        LEFT,
        RIGHT
    }
    public static void main(String[] args) throws Exception {
     List<String> lines = Files.readAllLines(Paths.get("C:\\Natalia\\AOC22\\input17.txt"));	
     for (char c : lines.get(0).toCharArray()) {
       if (c == '<') {
          jets.add(Jet.LEFT);
       } else if (c == '>') {
          jets.add(Jet.RIGHT);
          }
       }
    
      int res =  fly1();  
      System.out.println("Part1: "+ res);
      long rese = fly2();
      System.out.println("Part1: "+ rese);
     }

    private static int fly1() {
        int height = 0;
        int ins = 0;
        Map<Integer, boolean[]> layers = new HashMap<>();
        for (int rock = 0; rock < 2022; rock++) {
            boolean[][] shape = shapes[rock % shapes.length];
            int x = 2;
            int y = height + 3;
            while (true) {
                Jet jet = jets.get(ins++ % jets.size());
                if (jet == Jet.LEFT && x > 0) {
                    if (checkMove(shape, x - 1, y, layers)) {
                        x--;
                    }
                } else if (jet == Jet.RIGHT && x < 7 - shape[0].length) {
                    if (checkMove(shape, x + 1, y, layers)) {
                        x++;
                    }
                }
                if (checkMove(shape, x, y - 1, layers) && y > 0) {
                    y--;
                } else {
                    for (int sY = 0; sY < shape.length; sY++) {
                        int actualY = y + (shape.length - sY);
                        boolean[] shapeLayer = shape[sY];
                        boolean[] layer = layers.computeIfAbsent(actualY, __ -> new boolean[7]);
                        for (int sX = 0; sX < shapeLayer.length; sX++) {
                            layer[x + sX] |= shapeLayer[sX];
                        }
                    }
                    break;
                }
            }
            height = layers.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();
        }
        return height;
    }

    private static boolean checkMove(boolean[][] shape, int x, int y, Map<Integer, boolean[]> layers) {
        for (int sY = 0; sY < shape.length; sY++) {
            int actualY = y + (shape.length - sY);
            if (!layers.containsKey(actualY)) continue;
            boolean[] shapeLayer = shape[sY];
            boolean[] layer = layers.get(actualY);
            for (int sX = 0; sX < shapeLayer.length; sX++) {
                if (layer[x + sX] && shapeLayer[sX]) return false;
            }
        }
        return true;
    }

    private static long fly2() {
        int height = 0;
        int ins = 0;
        Map<Integer, boolean[]> layers = new HashMap<>();
        Map<SeenKey, SeenValue> seen = new HashMap<>();
        long extraHeight = 0;
        for (long rock = 0; rock < 1000000000000L; rock++) {
            boolean[][] shape = shapes[(int)(rock % shapes.length)];
            int x = 2;
            int y = height + 3;
            while (true) {
                Jet jet = jets.get(ins++ % jets.size());
                if (jet == Jet.LEFT && x > 0) {
                    if (checkMove(shape, x - 1, y, layers)) {
                        x--;
                    }
                } else if (jet == Jet.RIGHT && x < 7 - shape[0].length) {
                    if (checkMove(shape, x + 1, y, layers)) {
                        x++;
                    }
                }
                if (checkMove(shape, x, y - 1, layers) && y > 0) {
                    y--;
                } else {
                    for (int sY = 0; sY < shape.length; sY++) {
                        int actualY = y + (shape.length - sY);
                        boolean[] shapeLayer = shape[sY];
                        boolean[] layer = layers.computeIfAbsent(actualY, __ -> new boolean[7]);
                        for (int sX = 0; sX < shapeLayer.length; sX++) {
                            layer[x + sX] |= shapeLayer[sX];
                        }
                    }
                    break;
                }
            }
            height = layers.keySet().stream().mapToInt(Integer::intValue).max().getAsInt();
            int[] h = layers.keySet().stream().mapToInt(Integer::intValue).sorted().toArray();
            int[] view = new int[7];
            Arrays.fill(view, -1);
            for (int i = 0; i < 7; i++) {
                for (int j = h.length - 1; j >= 0; j--) {
                    if (layers.get(h[j])[i]) {
                        view[i] = (height - h[j]);
                        break;
                    }
                }
            }
            SeenKey key = new SeenKey((ins - 1) % jets.size(), (int)(rock % shapes.length), Arrays.stream(view).boxed().toList());
            if (seen.containsKey(key)) {
                SeenValue old = seen.get(key);
                long repeat = (1_000_000_000_000L - rock) / (rock - old.rock());
                rock += (rock - old.rock()) * repeat;
                extraHeight += repeat * (height - old.height());
            }
            seen.put(key, new SeenValue(rock, height));
        }

      return (height + extraHeight);
    }

    static record SeenKey(int ins, int shape, List<Integer> view) {}
    static record SeenValue(long rock, int height) {}

    
}
