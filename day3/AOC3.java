import java.util.*;

public class AOC3 {
  public static void main (String [] args) {
    Scanner scanner = new Scanner(System.in);
    ArrayList<String> input = new ArrayList<>();
    
    try {
      while (scanner.hasNextLine()) {
        input.add(scanner.nextLine());
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    int[][] map = new int[input.size()][input.get(0).length()];

    for (int i = 0; i < input.size(); i++) {
      for (int j = 0; j < input.get(i).length(); j++) {
        map[i][j] = input.get(i).charAt(j) == '#' ? 1 : 0;
      }
    }

    long ans1 = calcAns1 (map);
    System.out.println (ans1);

    long ans2 = calcAns2 (map);
    System.out.println (ans2);

  }

  public static long calcAns1 (int[][] map) {
    return calcTreesHit (map, 1, 3);
  }

  public static long calcAns2 (int[][] map) {
    long a1 = calcTreesHit (map, 1, 1);
    long a2 = calcTreesHit (map, 1, 3);
    long a3 = calcTreesHit (map, 1, 5);
    long a4 = calcTreesHit (map, 1, 7);
    long a5 = calcTreesHit (map, 2, 1);
    return a1 * a2 * a3 * a4 * a5;
  }

  public static long calcTreesHit (int[][] map, int rowInc, int colInc) {
    long trees = 0;
    int curRow = rowInc;
    int curCol = colInc;
    int maxCol = map[0].length;

    while (curRow < map.length) {
       trees += map[curRow][curCol % maxCol];
       curRow += rowInc;
       curCol += colInc;
    }

    return trees;
  }
  
}
