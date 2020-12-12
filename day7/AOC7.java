import java.util.*;

public class AOC7 {
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

    HashMap<String, ArrayList<String>> containedBy = parseContainedBy(inputs);
    HashMap<String, ArrayList<String>> contains = parseContains(inputs);

    int ans1 = calcAns1 (containedBy, "shiny gold");
    System.out.println (ans1);

    long ans2 = calcAns2 (contains, "shiny gold") - 1;
    System.out.println (ans2);

  }

  public static HashMap<String, ArrayList<String>> parseContainedBy (ArrayList<String> inputs) {
    HashMap<String, ArrayList<String>> rules = new HashMap<>();

    for (String input : inputs) {
      String[] pieces = input.split(" bags contain ");
      String outerBag = pieces[0];
      String[] innerBags = pieces[1].split(" bags?(,|\\.)\\s?");
      ArrayList<String> parsedInnerBags = new ArrayList<>();
      for (String innerBag : innerBags) {
        String[] pieces2 = innerBag.split(" ");
        if (pieces2[0].equals("no"))
          continue;
        parsedInnerBags.add(pieces2[1] + " " + pieces2[2]);
      }
      for (String parsedInnerBag : parsedInnerBags) {
        ArrayList<String> possibleOuterBags = rules.get(parsedInnerBag);
        possibleOuterBags = possibleOuterBags == null ? new ArrayList<>() : possibleOuterBags;
        if (!possibleOuterBags.contains(outerBag))
          possibleOuterBags.add(outerBag);
        rules.put(parsedInnerBag, possibleOuterBags);
      }
    }
    
    return rules;
  }

  public static HashMap<String, ArrayList<String>> parseContains (ArrayList<String> inputs) {
    HashMap<String, ArrayList<String>> rules = new HashMap<>();

    for (String input : inputs) {
      String[] pieces = input.split(" bags contain ");
      String outerBag = pieces[0];
      String[] innerBags = pieces[1].split(" bags?(,|\\.)\\s?");
      ArrayList<String> contains = new ArrayList<String>();
      for (String innerBag : innerBags) {
        if (innerBag.substring(0,2).equals("no")) continue;
        contains.add(innerBag);
      }
      rules.put(outerBag, contains);
    }
    
    return rules;
  }

  public static int calcAns1 (HashMap<String, ArrayList<String>> rules, String bag) {
    HashMap<String, Integer> visited = new HashMap<>();
    ArrayDeque<String> toInvestigate = new ArrayDeque<>();
    toInvestigate.add(bag);
    while (toInvestigate.size() > 0) {
      String s = toInvestigate.remove();
      if (rules.get(s) == null) continue;
      for (String outerBag : rules.get(s)) {
        if (visited.get(outerBag) == null) {
          visited.put(outerBag, 1);
          toInvestigate.add(outerBag);
        }
      }
    }
    return visited.keySet().size();
  }

  public static long calcAns2 (HashMap<String, ArrayList<String>> rules, String bag) {
    long total = 1;
    ArrayList<String> contained = rules.get(bag);
    if (contained == null)
      return total;
    for (String innerBag : contained) {
      String[] pieces = innerBag.split(" ");
      int num = 0;
      try {
        num = Integer.parseInt(pieces[0]);
      } catch (Exception e) { e.printStackTrace(); }

      total += num * calcAns2(rules, pieces[1] + " " + pieces[2]);
    }
    return total;
  }
}
