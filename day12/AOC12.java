import java.util.*;
import java.util.stream.Collectors;

public class AOC12 {
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
  
  public static int calcAns1 (ArrayList<String> navList) {
    Ship ship = new Ship(0, 0, 0);
    for (String nav : navList) {
      ship.move(nav);
    }
    return Math.abs(ship.x) + Math.abs(ship.y);
  }

  public static int calcAns2 (ArrayList<String> navList) {
    Ship2 ship = new Ship2(0, 0, 10, 1);
    for (String nav : navList) {
      ship.move(nav);
    }
    return Math.abs(ship.x) + Math.abs(ship.y);
  }

  private static class Direction {
    public int x;
    public int y;

    public Direction (int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  private static class Ship {
    int x;
    int y;
    int heading;
    HashMap<String, Direction> dLookup;

    public Ship (int x, int y, int heading) {
      this.x = x;
      this.y = y;
      this.heading = heading;
      dLookup = new HashMap<>();
      dLookup.put("E", new Direction (1, 0));
      dLookup.put("0", new Direction (1, 0));
      dLookup.put("N", new Direction (0, 1));
      dLookup.put("90", new Direction (0, 1));
      dLookup.put("W", new Direction (-1, 0));
      dLookup.put("180", new Direction (-1, 0));
      dLookup.put("S", new Direction (0, -1));
      dLookup.put("270", new Direction (0, -1));
    }

    public void move (String move) {
      String first = move.substring(0, 1);
      int distOrDeg = Integer.parseInt(move.substring(1));
      Direction d = dLookup.get(first);
      if (first.equals("F")) {
        d = dLookup.get(Integer.toString(this.heading));
      }
      if (d != null) {
        this.x += d.x * distOrDeg;
        this.y += d.y * distOrDeg;
      }
      if (d == null) {
        int rl = first.equals("R") ? -1 : 1;
        this.heading = (this.heading + rl*distOrDeg + 360) % 360;
      }
    }
  }

  private static class Ship2 {
    int x;
    int y;
    int w_x; //waypoint location
    int w_y;
    HashMap<String, Direction> dLookup;

    public Ship2 (int x, int y, int w_x, int w_y) {
      this.x = x;
      this.y = y;
      this.w_x = w_x;
      this.w_y = w_y;
      dLookup = new HashMap<>();
      dLookup.put("E", new Direction (1, 0));
      dLookup.put("N", new Direction (0, 1));
      dLookup.put("W", new Direction (-1, 0));
      dLookup.put("S", new Direction (0, -1));
    }

    public void move (String move) {
      String first = move.substring(0, 1);
      int distOrDeg = Integer.parseInt(move.substring(1));
      Direction d = dLookup.get(first);
      if (d != null ) {
        w_x += d.x * distOrDeg;
        w_y += d.y * distOrDeg;
      }
      else if (first.equals("F")) {
        x += w_x * distOrDeg;
        y += w_y * distOrDeg;
      }
      else {
        int rl = first.equals("R") ? 1 : -1;
        while (distOrDeg > 0) {
          int temp_w_x = w_x;
          w_x = w_y * rl;
          w_y = temp_w_x * -rl;
          distOrDeg -= 90;
        }
      }
    }
  }
}
