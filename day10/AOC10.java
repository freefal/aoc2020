import java.util.*;
import java.util.stream.Collectors;

public class AOC10 {
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

    List<Integer> inputInts = inputs.stream().map(Integer::parseInt).collect(Collectors.toList()); 

    long ans1 = calcAns1 (inputInts);
    System.out.println (ans1);

    long ans2 = calcAns2 (inputInts);
    System.out.println (ans2);

  }
  
  public static int calcAns1 (List<Integer> input) {
    List<Integer> copy = new ArrayList<>();
    copy.addAll(input);
    copy.add(0);
    Collections.sort(copy);
    copy.add(Collections.max(copy)+3);
    int[] diffs = new int[3];
    for (int i = 1; i < copy.size(); i++) {
      diffs[copy.get(i) - copy.get(i-1) - 1]++;
    }
    return diffs[0] * diffs[2];
  }

  public static long calcAns2 (List<Integer> input) {
    int max = Collections.max(input)+3;
    input.add(max);
    HashSet<Integer> inputSet = new HashSet<>(input);
    long[] counts = new long[max+1];
    counts[0] = 1;
    for (int i = 0; i < counts.length; i++) {
      for (int j = 1; j < 4; j++) {
        if (i + j > max)
          continue;
        if (!inputSet.contains(i+j))
          continue;
        counts[i+j] += counts[i];
      }
    }
    return counts[max];
  }
}
