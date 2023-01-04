package aoc;
	import java.io.BufferedReader;
	import java.io.FileReader;
	import java.io.IOException;
	import java.util.ArrayDeque;
	import java.util.ArrayList;
	import java.util.Arrays;
	import java.util.Deque;
	import java.util.List;
	import java.util.NoSuchElementException;
	import java.util.Optional;
	import java.util.function.Function;
	import java.util.function.LongBinaryOperator;
	import java.util.function.LongUnaryOperator;
	import java.util.regex.Pattern;
	import java.util.stream.Collectors;

	public class AOC11 {
		
	public static void main(String[] args) throws Exception {
		 long res =	doInspect(20, 3);
		 System.out.println("Part1: " + res);
		 res =	doInspect(10000, 1);
		 System.out.println("Part2: " + res);
	}
    private static long doInspect ( int rounds, int worryDivisor) throws Exception {
	  List<Monkey> monkeys = new ArrayList<Monkey>();	    
	  BufferedReader reader = new BufferedReader(new FileReader("C:\\Natalia\\AOC22\\input11.txt"));
	  {while (Monkey.read(reader).map(monkeys::add).orElse(false));
	  }
	 
	  Monkey.play(rounds, monkeys, worryDivisor);
	  return monkeys.stream()
			        .mapToLong(x -> -x.inspections).sorted().map(Math::negateExact)
			        .limit(2)
			        .reduce(Math::multiplyExact).orElseThrow(NoSuchElementException::new);
	}

     static class Monkey {
	 private int inspections = 0;
     private Deque<Long> worryItems; 
     private LongUnaryOperator update;
	 private int divisor;
	 private int thenMonkey;
	 private int elseMonkey;

    private static Optional<Monkey> read(BufferedReader r) throws IOException {
		if (null == r.readLine()) { // "Monkey N:"
		  return Optional.empty();
		}
	    Monkey monkey = new Monkey();
	    monkey.worryItems = after(r, "  Starting items: ",
	    ns -> Arrays.stream(ns.split(Pattern.quote(", ")))
				              .map(Long::parseLong)
				              .collect(Collectors.toCollection(ArrayDeque::new)));
		monkey.update = after(r, "  Operation: new = old ", Monkey::compileUpdate);
		monkey.divisor = after(r, "  Test: divisible by ", Integer::parseInt);
		monkey.thenMonkey = after(r, "    If true: throw to monkey ", Integer::parseInt);
		monkey.elseMonkey = after(r, "    If false: throw to monkey ", Integer::parseInt);
	    r.readLine(); // consume null or empty
	    return Optional.of(monkey);
	}

   private static <T> T after(BufferedReader r, String pref, Function<String, T> m) throws IOException {
	return m.apply(r.readLine().substring(pref.length()));
  }

 private static void play(int rounds, List<Monkey> monkeys, int worryDivisor) {
	int modulus = monkeys.stream().mapToInt(m -> m.divisor).reduce(1, Math::multiplyExact);
	for (int round = 0; round < rounds; round++) {
	 for  (Monkey monkey : monkeys) {
	  while (!monkey.worryItems.isEmpty()) {
	    monkey.inspections++;
		long worry = monkey.worryItems.pop();
			  worry = monkey.update.applyAsLong(worry);
			  worry = worry / worryDivisor; // no effect when divisor is 1
			  boolean divides =( 0 == worry % monkey.divisor);
			  int targetMonkey = divides ? monkey.thenMonkey : monkey.elseMonkey;
			  worry = worry % modulus; // no effect when modulus is big
			  monkeys.get(targetMonkey).worryItems.add(worry);
	    }
	   }
	 }
	}

  private static LongUnaryOperator compileUpdate(String update) {
	LongBinaryOperator op = update.contains("+") ? Math::addExact : Math::multiplyExact;
	if (update.contains("old")) {
	  return old -> op.applyAsLong(old, old);
	} else {
	  int val = Integer.parseInt(update.substring(2));
	  return old -> op.applyAsLong(old, val);
	}
  }
 }
}
