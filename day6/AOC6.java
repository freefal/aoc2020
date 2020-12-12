import java.util.*;

public class AOC6 {
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

    int ans1 = calcAns1 (inputs);
    System.out.println (ans1);

    int ans2 = calcAns2 (inputs);
    System.out.println (ans2);

  }

  private static class SeatInfo {
    public int row;
    public int col;
    public int seatId;

    public SeatInfo (int row, int col, int seatId) {
      this.row = row;
      this.col = col;
      this.seatId = seatId;
    }
  }

  public static int calcAns1 (ArrayList<String> inputs) {
    int totalCount = 0;
    HashMap<Character,Integer> groupMap = new HashMap<>();
    for (String input : inputs) {
      if (input.equals("")) {
        totalCount += groupMap.keySet().size();
        groupMap = new HashMap<>();
      }
      else {
        for (Character c : input.toCharArray()) {
          groupMap.put(c, 1);
        }
      }
    }
    totalCount += groupMap.keySet().size();
    return totalCount;
  }

  public static int calcAns2 (ArrayList<String> inputs) {
    int totalCount = 0;
    int groupCount = 0;
    HashMap<Character,Integer> groupMap = new HashMap<>();
    for (String input : inputs) {
      if (input.equals("")) {
        for (Character c : groupMap.keySet()) {
          if (groupMap.get(c) == groupCount)
            totalCount++;
        }
        groupMap = new HashMap<>();
        groupCount = 0;
      }
      else {
        for (Character c : input.toCharArray()) {
          Integer curCount = groupMap.get(c);
          if (curCount == null)
            curCount = 0;
          groupMap.put(c, curCount + 1);
        }
        groupCount++;
      }
    }
    for (Character c : groupMap.keySet()) {
      if (groupMap.get(c) == groupCount)
        totalCount++;
    }
    return totalCount;
  }
}
