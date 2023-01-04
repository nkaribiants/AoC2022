package aoc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.FileReader;

public class AOC12 {
    public static void main(String[] args) throws Exception {
        String filename = "C:\\Natalia\\AOC22\\input12.txt";
        BufferedReader br = new BufferedReader(new FileReader(filename));
        Elevations elevations = new Elevations();
        String s;
        while ((s = br.readLine()) != null) {
            elevations.addRow(s);
        }
        br.close();
        Pos start = elevations.findStart();
        Pos end = elevations.findEnd();
       // System.out.println("start = " + start + "; end = " + end);
       // part 1: Start to End
        int dist = minDistance(elevations, start, end);
        System.out.println("Part1 " + dist);
        // part 2: all a
        int bestDist = dist;
        for (Pos potential : elevations.getAllA()) {
            bestDist = Math.min(bestDist, minDistance(elevations, potential, end));
        }
        System.out.println(bestDist);
    }

    private static int minDistance(Elevations elevations, Pos start, Pos end) {
        elevations.resetPos();
        start.distance = 0;
        PriorityQueue<Pos> queue = new PriorityQueue<>();
        queue.add(start);
        while (!queue.isEmpty()) {
            Pos cur = queue.remove();

            if (cur.row == end.row && cur.col == end.col) {
                return cur.distance;
            }
            visit(cur, cur.row, cur.col - 1, elevations, queue);
            visit(cur, cur.row, cur.col + 1, elevations, queue);
            visit(cur, cur.row - 1, cur.col, elevations, queue);
            visit(cur, cur.row + 1, cur.col, elevations, queue);
        }
        return Integer.MAX_VALUE;
    }

    private static void visit(Pos cur, int row, int col, Elevations elevations, PriorityQueue<Pos> queue) {
        if (row == -1 || col == -1 || row == elevations.numRows() || col == elevations.numCols()) {
            return;
        }
        Pos neighbor = elevations.get(row, col);
        if (neighbor.elevation - cur.elevation > 1) {
            return;
        }
        int pathLen = cur.distance + 1;
        if (pathLen < neighbor.distance) {
            neighbor.distance = pathLen;
            queue.remove(neighbor);
            queue.add(neighbor);
        }
    }

    private static class Elevations {
        private List<List<Pos>> rows = new ArrayList<>();
        private Pos start;
        private Pos end;
        private int nCols = -1;

        public void addRow(String s) {
            if (nCols == -1) {
                nCols = s.length();
            }
            int r = rows.size();
            List<Pos> row = new ArrayList<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                Pos p = new Pos(r, i, c);
                if (c == 'S') 
                    start = p;
                if (c == 'E') 
                    end = p;
                row.add(p);
            }
            rows.add(row);
        }

        public Pos get(int row, int col) {
            return rows.get(row).get(col);
        }

        public int numRows() {
            return rows.size();
        }

        public int numCols() {
            return nCols;
        }

        public Pos findStart() {
            return start;
        }

        public Pos findEnd() {
            return end;
        }

        public List<Pos> getAllA() {
            List<Pos> ret = new LinkedList<>();
            for (List<Pos> row : rows) {
                for (Pos pos : row) {
                    if (pos.elevation == 'a') {
                        ret.add(pos);
                    }
                }
            }
            return ret;
        }

        public void resetPos() {
            rows.stream().flatMap(Collection::stream).forEach(Pos::resetDistance);
        }
    }

    private static class Pos implements Comparable<Pos> {
        int row;
        int col;
        char elevation;
        int distance;

        public Pos(int row, int col, char c) {
            this.row = row;
            this.col = col;
            elevation = switch (c) {
                case 'S' -> 'a';
                case 'E' -> 'z';
                default -> c;
            };
        }

        public int compareTo(Pos o) {
            return Integer.compare(distance, o.distance);
        }

        public void resetDistance() {
            distance = Integer.MAX_VALUE;
        }

        public String toString() {
            return "Pos{" +
                    "row=" + row +
                    ", col=" + col +
                    ", distance=" + distance +
                    '}';
        }
    }
}