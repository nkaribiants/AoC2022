package aoc;

import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class AOC15 {

    public static void main(String[] args) throws Exception {     
        List<String> lines = Files.readAllLines(Paths.get("C:\\Natalia\\AOC22\\input15.txt"));
        List<Range> ranges = lines.stream().map(l -> {
            int[] c = parseLine(l);
            // Manhattan distance:
            int distance = Math.abs(c[0]-c[2]) + Math.abs(c[1]-c[3]);
            
            int width = distance - Math.abs(c[1] - 2000000);
            return new Range(c[0] - width, c[0] + width);
        }).filter(r -> r.start<=r.end).collect(Collectors.toList());

        // Sum the lengths of ranges for the row:
        System.out.println("Part 1:");
        System.out.println(simplifyRanges(ranges).stream().mapToInt(r -> r.end-r.start).sum());
        System.out.println("Part 2:");

        List<SensorRange> sensors = lines.stream().map(l -> {
            // Parse input line:
            int[] c = parseLine(l);
            return new SensorRange(c[0], c[1], Math.abs(c[0] - c[2]) + Math.abs(c[1] - c[3]));
        }).collect(Collectors.toList());

        for(SensorRange sensor : sensors) {
            // There is a gap of exactly 1, so it has to lie on the border of a sensors range
            for (int i = -(sensor.distance + 1); i <= sensor.distance; i++) {
                int width = (sensor.distance - Math.abs(i));

                //Check both coordinates around the sensor perimiter:
                if(isCoordinateOutsideSensorRange(sensors, (sensor.x - width - 1), (sensor.y + i)) ||
                    isCoordinateOutsideSensorRange(sensors, (sensor.x + width + 1), (sensor.y + i))) {
                    return;
                }
            }
        }
    }

    private static int[] parseLine(String line) {
        line = line.replace("Sensor at x=", "").replace(": closest beacon is at x=",",").replace(" y=","");
        return Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
    }

    private static boolean isCoordinateOutsideSensorRange(List<SensorRange> sensors, int tx, int ty) {
        if(tx >= 0 && tx <= 4000000 && ty >= 0 && ty <= 4000000
                && sensors.stream().allMatch(sensor -> !sensor.inRange(tx, ty))) {
            System.out.println("Found point outside sensor ranges: " + tx + ", " + ty);
            System.out.println("Frequency: " + BigInteger.valueOf(tx).multiply(BigInteger.valueOf(4000000)).add(BigInteger.valueOf((ty))));
            return true;
        }
        return false;
    }

    private static List<Range> simplifyRanges(List<Range> ranges) {
        Collections.sort(ranges, Comparator.comparingInt(r -> r.start));
        // Initialize the list of simplified ranges with the first range in the input list
        List<Range> simplified = new ArrayList<>();
        simplified.add(ranges.get(0));
        for (int i = 1; i < ranges.size(); i++) {
            Range current = ranges.get(i);
            Range last = simplified.get(simplified.size() - 1);
            // if ranges connect or overlap, update
            if (current.start <= last.end + 1) {
                simplified.set(simplified.size() - 1, new Range(last.start, Math.max(last.end, current.end)));
            } else {
                // Start new range:
                simplified.add(current);
            }
        }
        return simplified;
    }

    private record Range(int start, int end) {}

    private record SensorRange(int x, int y, int distance) {
        private boolean inRange(int tx, int ty) {
            return (Math.abs(tx - x) + Math.abs(ty - y)) <= distance;
        }
    }
}
