import java.util.*;

public class AOC1 {
  public static final int TARGET = 2020;
  public static void main (String [] args) {
    Scanner scanner = new Scanner(System.in);
    ArrayList<Integer> expenses = new ArrayList<>();
    
    try {
      while (scanner.hasNextLine()) {
        expenses.add(Integer.parseInt(scanner.nextLine()));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    int ans1 = calcAns1 (expenses);
    System.out.println (ans1);

    int ans2 = calcAns2 (expenses);
    System.out.println (ans2);

  }

  public static int calcAns1 (ArrayList<Integer> expenses) {
    for (Integer i : expenses) {
      for (Integer j : expenses) {
        if (i + j == TARGET) {
          return i * j;
        }
      }
    }
    return -1;
  }

  public static int calcAns2 (ArrayList<Integer> expenses) {
    for (Integer i : expenses) {
      for (Integer j : expenses) {
        for (Integer k : expenses) {
          if (i + j + k == TARGET) {
            return i * j * k;
          }
        }
      }
    }
    return -1;
  }
}
