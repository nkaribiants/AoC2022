package aoc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AOC24 {
    public static void main(String[] args) throws Exception {
        new AOC24().calculate();
    }

   static record Point(int row, int col) {
        Point move(Point direction) {
            return new Point(row + direction.row, col + direction.col);
        }
    }

   static Map<Character, Point> directions = new HashMap<>();
    {
        directions.put('>', new Point(0,1));
        directions.put('v', new Point(1,0));
        directions.put('<', new Point(0,-1));
        directions.put('^', new Point(-1,0));
    }

    public void calculate() throws Exception {

        List<String> lines = Files.readAllLines(Paths.get("C:\\Natalia\\AOC22\\input24.txt"));

        Point mapsize = new Point(lines.size(), lines.get(0).length());

        Point startPoint = new Point(0,1);
        Point exitPoint = new Point(lines.size()-1, lines.get(0).length()-2);

        List<Point> hurricanePositions = new ArrayList<>();
        List<Point> hurricaneDirections = new ArrayList<>();
        for(int l = 1; l < lines.size()-1;l++) {
            String hurricane = lines.get(l);
            for(int c = 1; c < hurricane.length()-1; c++) {
                if(hurricane.charAt(c)!='.') {
                    hurricanePositions.add(new Point(l, c));
                    hurricaneDirections.add(directions.get(hurricane.charAt(c)));
                }
            }
        }

        Set<Point> paths = new HashSet<>();
        int minutes = 0;

        // Find path to exit:
        paths.clear();
        paths.add(startPoint);
        while(!paths.contains(exitPoint)) {
            hurricanePositions = updateHurricanePositions(mapsize, hurricanePositions, hurricaneDirections);
            paths = updatePlayerPositions(mapsize, exitPoint, paths, hurricanePositions);
            minutes++;
        }

        System.out.println("Part1 " + minutes);

        // Find path back to start:
        paths.clear();
        paths.add(exitPoint);
        while(!paths.contains(startPoint)) {
            hurricanePositions = updateHurricanePositions(mapsize, hurricanePositions, hurricaneDirections);
            paths = updatePlayerPositions(mapsize, startPoint, paths, hurricanePositions);
            minutes++;
        }

        System.out.println("Got me some snacks after: " + minutes);

        // Find path to exit again:
        paths.clear();
        paths.add(startPoint);
        while(!paths.contains(exitPoint)) {
            hurricanePositions = updateHurricanePositions(mapsize, hurricanePositions, hurricaneDirections);
            paths = updatePlayerPositions(mapsize, exitPoint, paths, hurricanePositions);
            minutes++;
        }

        System.out.println("Part2 : " + minutes);
    }

    private Set<Point> updatePlayerPositions(final Point mapsize, final Point exitPoint, final Set<Point> paths, final List<Point> hurricanePositions) {
        Set<Point> deeperPaths = new HashSet<>();
        for(Point position : paths) {
            // Include a wait:
            if(!hurricanePositions.contains(position)) {
                deeperPaths.add(position);
            }
            // Update other positions:
            for(Point direction : directions.values()) {
                Point newPosition = position.move(direction);
                // Add specific exit-case
                if(newPosition.equals(exitPoint)) {
                    deeperPaths.add(exitPoint);
                }
                // Ignore borders:
                if(newPosition.row<=0||newPosition.row>= mapsize.row-1||newPosition.col<=0||newPosition.col>= mapsize.col-1)
                    continue;
                if(!hurricanePositions.contains(newPosition)) {
                    deeperPaths.add(newPosition);
                }
            }
        }
        return deeperPaths;
    }

    private static List<Point> updateHurricanePositions(final Point mapsize, final List<Point> hurricanePositions, List<Point> hurricaneDirections) {
        //Update the hurricane:
        List<Point> nextIteration = new ArrayList<>();
        for(int h = 0; h < hurricanePositions.size(); h++) {
            Point position = hurricanePositions.get(h);
            Point direction = hurricaneDirections.get(h);
            Point newLocation = position.move(direction);
            // Conserve energy:
            nextIteration.add(new Point(Math.floorMod(newLocation.row-1,mapsize.row-2)+1,
                    Math.floorMod(newLocation.col-1,mapsize.col-2)+1));
        }
        return nextIteration;
    }
}