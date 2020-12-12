import java.util.*;

public class AOC5 {
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

    ArrayList<SeatInfo> seatInfo = parseInput(inputs);

    int ans1 = calcAns1 (seatInfo);
    System.out.println (ans1);

    int ans2 = calcAns2 (seatInfo);
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

  public static ArrayList<SeatInfo> parseInput (ArrayList<String> inputs) {
    ArrayList<SeatInfo> seatInfo = new ArrayList<>();

    for (String input : inputs) {
      int row = 0;
      for (int i = 0; i < 7; i++) {
        row <<= 1;
        row += input.substring(i, i+1).equals("B") ? 1 : 0;
      }
      int col = 0;
      for (int i = 7; i < 10; i++) {
        col <<= 1;
        col += input.substring(i, i+1).equals("R") ? 1 : 0;
      }
      int seatId = row * 8 + col;
      seatInfo.add(new SeatInfo(row, col, seatId));
    }

    return seatInfo;
  }

  public static int calcAns1 (ArrayList<SeatInfo> seatInfo) {
    int max = 0;
    for (SeatInfo seat : seatInfo) {
      max = Math.max(max, seat.seatId);
    }
    return max;
  }

  public static int calcAns2 (ArrayList<SeatInfo> seatInfo) {
    HashMap<Integer, SeatInfo> map = new HashMap<>();
    for (SeatInfo seat : seatInfo) {
      map.put(seat.seatId, seat);
    }
    for (int i = 8; i < 127*8; i++) {
      if (map.get(i) == null && map.get(i-1) != null && map.get(i+1) != null)
        return i;
    }
    return -1;
  }

}
