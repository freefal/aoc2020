import java.util.*;
import java.util.stream.Collectors;

public class AOC9 {
  public static final int WINDOW_SIZE = 25;
  public static void main (String [] args) {
    Scanner scanner = new Scanner(System.in);
    ArrayList<String> inputs = new ArrayList<>();
    
    try {
      while (scanner.hasNextLine()) {
        inputs.add(scanner.nextLine());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    List<Long> inputLongs = inputs.stream().map(Long::parseLong).collect(Collectors.toList()); 

    long ans1 = calcAns1 (inputLongs);
    System.out.println (ans1);

    long ans2 = calcAns2 (inputLongs, ans1);
    System.out.println (ans2);

  }
  
  public static long calcAns1 (List<Long> input) {
    for (int i = WINDOW_SIZE; i < input.size(); i++) {
      HashSet<Long> sums = new HashSet<>();
      for (int j = i-WINDOW_SIZE; j < i; j++) {
        for (int k = j; k < i; k++) {
          sums.add(input.get(j) + input.get(k));
        }
      }
      if (!sums.contains(input.get(i)))
        return input.get(i);
    }
    return -1;
  }

  public static long calcAns2 (List<Long> input, long ans1) {
    for (int i = 0; i < input.size(); i++) {
      for (int j = i+1; j < input.size(); j++) {
        long sum = 0;
        for (int k = i; k < j; k++) {
          sum += input.get(k);
        }
        if (sum > ans1)
          break;
        if (sum == ans1) {
          long min = Long.MAX_VALUE;
          long max = Long.MIN_VALUE;
          for (int k = i; k < j; k++) {
            min = min < input.get(k) ? min : input.get(k);
            max = max > input.get(k) ? max : input.get(k);
          }
          return min + max;
        }
      }
    }
    return -1;
  }
}
