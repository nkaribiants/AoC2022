package aoc;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.google.common.collect.Sets;

public class AOC14 {

	public static void main(String[] args) throws Exception {
		List<String> inputList = Files.readAllLines(Paths.get("C:\\Natalia\\AOC22\\input14.txt"));		
		Cave1 cave1 = new Cave1(inputList);
		System.out.println("Part 1: " + cave1.start());
		Cave2 cave2 = new Cave2(inputList);
		System.out.println("Part 2: " + cave2.start());

	}
	private static abstract class Cave {
		private Set<Point> blockedCoordinates;
		private int lowestX, highestX, highestY;
		private boolean abyssReached, caveIsFull;
		private Point start;
	
		record Point(int x, int y) {};
	
		abstract boolean isAbyssReached(Point point);
		abstract boolean isBottom(int currentY);
	
		public Cave(List<String> input) {
			lowestX = Integer.MAX_VALUE;
			highestX = highestY = Integer.MIN_VALUE;
			blockedCoordinates = Sets.newHashSet();
			start = new Point(500, 0);
			abyssReached = caveIsFull = false;
			parseInput(input);
		}
	
		public int start() {
			int count = 0;
			while (!abyssReached && !caveIsFull) {
				Point currentSandLocation = start;
				currentSandLocation = nextLandingSpot(currentSandLocation);
				blockedCoordinates.add(currentSandLocation);
				count++;
			}
			return abyssReached ? count - 1 : count;
		}
	
		private Point nextLandingSpot(Point currentPoint) {
			Point nextSpot = nextFreeCoordinate(currentPoint);
			if (isAbyssReached(nextSpot)) {
				abyssReached = true;
				return nextSpot;
			}
			if (nextSpot.equals(start)) {
				caveIsFull = true;
				return nextSpot;
			}
			if (nextSpot.equals(currentPoint)) // landing spot found
				return nextSpot;
			return nextLandingSpot(nextSpot);
	}

	private Point nextFreeCoordinate(Point currentPoint) {
		int currentX = currentPoint.x();
		int currentY = currentPoint.y();

		if (isBottom(currentY + 1))
			return currentPoint; // floor of cave is reached (part 2)
		// down
		Point nextPoint = new Point(currentX, currentY + 1);
		if (!blockedCoordinates.contains(nextPoint)) {
			return nextPoint;
		}
		// left
		nextPoint = new Point(currentX - 1, currentY + 1);
		if (!blockedCoordinates.contains(nextPoint)) {
			return nextPoint;
		}
		// right
		nextPoint = new Point(currentX + 1, currentY + 1);
		if (!blockedCoordinates.contains(nextPoint)) {
			return nextPoint;
		}
		return currentPoint; 
	}

	private void parseInput(List<String> input) {
		List<String[]> tupleList = input.stream().map(e -> e.split(" -> ")).toList();
		for (String[] oneLine : tupleList) {
			Point previousPoint = null;
			for (int i = 0; i < oneLine.length; i++) {
				int[] p = Arrays.stream(oneLine[i].split(",")).mapToInt(Integer::parseInt).toArray();
				Point point = new Point(p[0], p[1]);
				createRocks(previousPoint, point);
				setCaveEdges(point);
				previousPoint = point;
			}
		}
	}

	private void createRocks(Point previousPoint, Point point) {
		if (previousPoint == null)
			return;
		if (previousPoint.x() == point.x()) {
			int[] sortedY = sort(previousPoint.y(), point.y()); // we need the correct order
			IntStream.range(sortedY[0], sortedY[1] + 1).forEach(y -> blockedCoordinates.add(new Point(point.x(), y)));
		} else {
			int[] sortedX = sort(previousPoint.x(), point.x()); // we need the correct order
			IntStream.range(sortedX[0], sortedX[1] + 1).forEach(x -> blockedCoordinates.add(new Point(x, point.y())));
		}
	}

	private void setCaveEdges(Point point) {
		lowestX = Math.min(lowestX, point.x());
		highestX = Math.max(highestX, point.x());
		highestY = Math.max(highestY, point.y());
	}

	private int[] sort(int a, int b) {
		int[] array = new int[] { a, b };
		Arrays.sort(array);
		return array;
	}

	public int getLowestX() {
		return lowestX;
	}

	public int getHighestX() {
		return highestX;
	}

	public int getHighestY() {
		return highestY;
	}
}
	private static class Cave1 extends Cave {
		public Cave1(List<String> input) {
			super(input);
		}
		@Override
		boolean isAbyssReached(Point point) {
			return point.x() < getLowestX() || point.x() > getHighestX() || point.y() > getHighestY();
		}
		@Override
		boolean isBottom(int currentY) {
			return false;
		}

	}
	static class Cave2 extends Cave {
		public Cave2(List<String> input) {
			super(input);
		}
		@Override
		boolean isAbyssReached(Point point) {
			return false;
		}
		@Override
		boolean isBottom(int y) {
			return y == getHighestY() + 2 ? true : false;
		}
	}
}
	
