import java.util.*;

public class AOC4 {
  public static void main (String [] args) {
    Scanner scanner = new Scanner(System.in);
    StringBuilder sb = new StringBuilder();
    
    try {
      while (scanner.hasNextLine()) {
        sb.append(scanner.nextLine());
        sb.append("\n");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    String input = sb.substring(0, sb.length() - 1);
    String[] inputArray = input.split("\n\n");
    ArrayList<HashMap<String,String>> inputRows = parseInputs(inputArray);

    int ans1 = calcAns1 (inputRows);
    System.out.println (ans1);

    int ans2 = calcAns2 (inputRows);
    System.out.println (ans2);

  }

  public static ArrayList<HashMap<String,String>> parseInputs(String[] inputArray) {
    ArrayList<HashMap<String,String>> maps = new ArrayList<>();

    for (String input : inputArray) {
      maps.add(parseInput(input));
    }

    return maps;
  }

  public static HashMap<String,String> parseInput(String input) {
    HashMap<String,String> map = new HashMap<>();
    String[] pieces = input.split("\\s");
    for (String piece : pieces) {
      String[] keyValue = piece.split(":");
      map.put(keyValue[0], keyValue[1]);
    }
    return map;
  }

  public static int calcAns1 (ArrayList<HashMap<String,String>> inputRows) {
    int count = 0;
    for (HashMap input : inputRows) {
      if (input.keySet().size() == 8)
        count++;
      else if (input.keySet().size() == 7 && input.get("cid") == null)
        count++;
    }
    return count;
  }

  public static int calcAns2 (ArrayList<HashMap<String,String>> inputRows) {
    int count = 0;

    try {
      for (HashMap<String, String> input : inputRows) {
        boolean valid = input.get("byr") != null && input.get("iyr") != null && input.get("eyr") != null && input.get("hgt") != null && input.get("hcl") != null && input.get("ecl") != null && input.get("pid") != null;
        if (!valid)
          continue;

        int byr = Integer.parseInt(input.get("byr"));
        int iyr = Integer.parseInt(input.get("iyr"));
        int eyr = Integer.parseInt(input.get("eyr"));
        String hgt = input.get("hgt");
        String hgtType= hgt.substring(hgt.length() - 2, hgt.length());
        int hgtAmt = 0;
        if (hgtType.equals("in") || hgtType.equals("cm"))
          hgtAmt = Integer.parseInt(hgt.substring(0, hgt.length() - 2));
        String hcl = input.get("hcl");
        String ecl = input.get("ecl");
        String pid = input.get("pid");
        String cid = input.get("cid");
        
        valid = valid && (byr >= 1920) && (byr <= 2002);
        valid = valid && (iyr >= 2010) && (iyr <= 2020);
        valid = valid && (eyr >= 2020) && (eyr <= 2030);
        valid = valid && (hgtType.equals("cm") && (hgtAmt >= 150) && (hgtAmt <= 193)) || (hgtType.equals("in") && (hgtAmt >= 59) && (hgtAmt <= 76));
        valid = valid && hcl.matches("\\A#[a-f0-9]{6}");
        valid = valid && ecl.matches("\\A(amb|blu|brn|gry|grn|hzl|oth)");
        valid = valid && pid.matches("\\A[0-9]{9}");

        if (valid)
          count++;
      }
    } catch (Exception e) { e.printStackTrace(); }

    return count;
  }

}
