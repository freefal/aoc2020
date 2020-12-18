import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.*;

public class AOC15 {
  public static final long STOP_TURN1  = 2020;
  public static final long STOP_TURN2  = 30000000;
  public static void main (String [] args) {
    Scanner scanner = new Scanner(System.in);
    List<Long> initNums = null;

    try {
      initNums = Arrays.stream(scanner.nextLine().split(",")).map(Long::parseLong).collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
    }
    
    long ans1 = calcAns1 (initNums, STOP_TURN1);
    System.out.println (ans1);

    long ans2 = calcAns2 (initNums, STOP_TURN2);
    System.out.println (ans2);

  }
  
  public static class Turn {
    long turn;
    long num;

    public Turn (long turn, long num) {
      this.turn = turn;
      this.num = num;
    }
  }

  public static long calcAns1 (List<Long> initNums, long stopTurn) {
    HashMap<Long,ArrayList<Turn>> history = new HashMap<>();
    long counter = 1;
    Turn lastTurn = null;
    for (Long num : initNums) {
      Turn t = new Turn(counter, num);
      ArrayList<Turn> numHistory = history.get(num);
      if (numHistory == null) {
        numHistory = new ArrayList<>();
        history.put(num, numHistory);
      }
      numHistory.add(t);
      counter++;
      lastTurn = t;
    }
    
    while (counter <= stopTurn) {
      ArrayList<Turn> numHistory = history.get(lastTurn.num);
      long newNum = 0;
      if (numHistory.size() > 1) {
        Turn last = numHistory.get(numHistory.size() - 1);
        Turn secondLast = numHistory.get(numHistory.size() - 2);
        newNum = last.turn - secondLast.turn;
      }
      Turn t = new Turn (counter, newNum);
      ArrayList<Turn> newNumHistory = history.get(newNum);
      if (newNumHistory == null) {
        newNumHistory = new ArrayList<>();
        history.put(newNum, newNumHistory);
      }
      newNumHistory.add(t);
      counter++;
      lastTurn = t;
    }
    
    return lastTurn.num;
  }

  public static long calcAns2 (List<Long> initNums, long stopTurn) {
    return calcAns1(initNums, stopTurn);
  }
}
