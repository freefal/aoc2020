import java.util.*;
import java.util.stream.Collectors;

public class AOC11 {
  public static final char F = '.'; // floor
  public static final char E = 'L'; // empty
  public static final char O = '#'; // occupied

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
    
    
    char[][] initState = parseInput (inputs);

    long ans1 = calcAns1 (initState);
    System.out.println (ans1);

    long ans2 = calcAns2 (initState);
    System.out.println (ans2);

  }
  
  public static char[][] parseInput(ArrayList<String> inputs) {
    int height = inputs.size();
    int width = inputs.get(0).length();
    char[][] state = new char[height][width];
    for (int i = 0; i < height; i++) {
      String row = inputs.get(i);
      for (int j = 0; j < width; j++) {
        state[i][j] = row.charAt(j);
      }
    }
    return state;
  }

  public static int calcAns1 (char[][] initState) {
    Simulator1 sim = new Simulator1(initState);
    while (!sim.stabilized)
      sim.next();
    return sim.getNumOccupied();
  }

  public static int calcAns2 (char[][] initState) {
    Simulator2 sim = new Simulator2(initState);
    while (!sim.stabilized)
      sim.next();
    return sim.getNumOccupied();
  }

  public static class Simulator1 {
    public char[][] curState;
    public int height;
    public int width;
    public boolean stabilized = false;

    public Simulator1 (char[][] init) {
      curState = init;
      height = init.length;
      width = init[0].length;
    }

    public void next () {
      char[][] oldState = curState;
      char[][] newState = new char[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          newState[i][j] = oldState[i][j];
          if (oldState[i][j] == AOC11.F)
            continue;
          char count = neighborsCount (oldState, i, j);
          if (oldState[i][j] == AOC11.E && count == 0)
            newState[i][j] = AOC11.O;
          if (oldState[i][j] == AOC11.O && count >= 4)
            newState[i][j] = AOC11.E;
        }
      }

      stabilized = true;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          stabilized &= (oldState[i][j] == newState[i][j]);
        }
      }

      curState = newState;
    }

    private char neighborsCount (char[][] state, int y, int x) {
      char count = 0;
      for (int i = Math.max(y-1,0); i < Math.min(y+2, state.length); i++) {
        for (int j = Math.max(x-1,0); j < Math.min(x+2, state[0].length); j++) {
          if (i == y && j == x)
            continue;
          if (state[i][j] == AOC11.O)
            count++;
        }
      }
      return count;
    }

    public int getNumOccupied () {
      int count = 0;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (curState[i][j] == AOC11.O)
            count++;
        }
      }
      return count;
    }
  }
  public static class Simulator2 {
    public char[][] curState;
    public int height;
    public int width;
    public boolean stabilized = false;

    public Simulator2 (char[][] init) {
      curState = init;
      height = init.length;
      width = init[0].length;
    }

    public void next () {
      char[][] oldState = curState;
      char[][] newState = new char[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          newState[i][j] = oldState[i][j];
          if (oldState[i][j] == AOC11.F)
            continue;
          char count = neighborsCount (oldState, i, j);
          if (oldState[i][j] == AOC11.E && count == 0)
            newState[i][j] = AOC11.O;
          if (oldState[i][j] == AOC11.O && count >= 5)
            newState[i][j] = AOC11.E;
        }
      }

      stabilized = true;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          stabilized &= (oldState[i][j] == newState[i][j]);
        }
      }

      curState = newState;
    }

    private char neighborsCount (char[][] state, int y, int x) {
      char count = 0;
      int height = state.length;
      int width = state[0].length;
      
      for (int yInc = -1; yInc <= 1; yInc++) {
        for (int xInc = -1; xInc <=1; xInc++) {
          if (yInc == 0 && xInc == 0)
            continue;
          int i = y + yInc;
          int j = x + xInc;
          while (i >= 0 && i < height && j >= 0 && j < width) {
            if (state[i][j] == AOC11.O) {
              count++;
              break;
            }
            if (state[i][j] == AOC11.E)
              break;
            i += yInc;
            j += xInc;
          }
        }
      }

      return count;
    }

    public int getNumOccupied () {
      int count = 0;
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          if (curState[i][j] == AOC11.O)
            count++;
        }
      }
      return count;
    }
  }
}
