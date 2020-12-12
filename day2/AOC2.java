import java.util.*;

public class AOC2 {
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

    ArrayList<InputRow> rows = parseInput(input);

    int ans1 = calcAns1 (rows);
    System.out.println (ans1);

    int ans2 = calcAns2 (rows);
    System.out.println (ans2);

  }

  static class InputRow {
    public int min;
    public int max;
    public String rChar;
    public String password;

    public InputRow (int min, int max, String rChar, String password) {
      this.min = min;
      this.max = max;
      this.rChar = rChar;
      this.password = password;
    }
  }

  public static ArrayList<InputRow> parseInput (ArrayList<String> input) {
    ArrayList<InputRow> inputObjs = new ArrayList<>();
    for (String s : input) {
      inputObjs.add(parseRow(s));
    }
    return inputObjs;
  }

  public static InputRow parseRow (String s) {
    InputRow parseRow = null;
    try {
      String[] pieces = s.split("-");
      int min = Integer.parseInt(pieces[0]);
      pieces = pieces[1].split(" ");
      int max = Integer.parseInt(pieces[0]);
      String rChar = pieces[1].substring(0, 1);
      String password = pieces[2];
      parseRow = new InputRow(min, max, rChar, password);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return parseRow;
  }


  public static int calcAns1 (ArrayList<InputRow> rows) {
    int numValid = 0;
    for (InputRow row : rows) {
      int charCount = 0;
      for (int i = 0; i < row.password.length(); i++) {
        if (row.password.substring(i, i+1).equals(row.rChar))
          charCount++;
      }
      if (charCount >= row.min && charCount <= row.max)
        numValid++;
    }
    return numValid;
  }

  public static int calcAns2 (ArrayList<InputRow> rows) {
    int numValid = 0;
    for (InputRow row : rows) {
      boolean match1 = row.password.substring(row.min - 1, row.min).equals(row.rChar);
      boolean match2 = row.password.substring(row.max - 1, row.max).equals(row.rChar);
      if (match1 ^ match2)
        numValid++;
    }
    return numValid;
  }

}
