import java.util.*;
import java.util.stream.Collectors;
import java.util.regex.*;

public class AOC14 {
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

    long ans1 = calcAns1 (inputs);
    System.out.println (ans1);

    long ans2 = calcAns2 (inputs);
    System.out.println (ans2);

  }
  
  public static long calcAns1 (ArrayList<String> inputs) {
    long orMask = 0;
    long andMask = (long)Math.pow(2, 37) - 1;
    HashMap<Integer, Long> mem = new HashMap<>();
    for (String input : inputs) {
      String[] pieces = input.split(" = ");
      if (pieces[0].equals("mask")) {
        orMask = 0;
        andMask = (long)Math.pow(2, 37) - 1;
        for (char c : pieces[1].toCharArray()) {
          orMask = orMask << 1;
          andMask = andMask << 1;
          if (c == 'X') {
            andMask += 1;
          }
          else if (c == '1') {
            andMask += 1;
            orMask += 1;
          }
        }
      }
      else {
        String pattern = "mem\\[([0-9]*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(pieces[0]);
        m.matches();
        int memLoc = Integer.parseInt(m.group(1));
        long value = Long.parseLong(pieces[1]);
        value = value & andMask;
        value = value | orMask;
        mem.put(memLoc, value);
      }
    }
   
    long result = 0;
    for (Long val : mem.values()) {
      result += val;
    }
    return result;
  }

  public static class MaskPair {
    long or;
    long and;

    public MaskPair (long or, long and) {
      this.or = or;
      this.and = and;
    }

    public MaskPair (MaskPair old) {
      this.or = old.or;
      this.and = old.and;
    }
  }

  public static class Masks {
    ArrayList<MaskPair> masks;
    
    public Masks () {
      initialize();
    }

    private void initialize() {
      masks = new ArrayList<>();
      masks.add(new MaskPair(0, 0));
    }

    public void updateMasks (String maskInput) {
      initialize();
      for (char c : maskInput.toCharArray()) {
        int len = masks.size();
        for (int i = 0; i < len; i++) {
          MaskPair pair = masks.get(i);
          pair.or = pair.or << 1;
          pair.and = pair.and << 1;
          if (c == '0') {
            pair.and += 1;
          }
          else if (c == '1') {
            pair.and += 1;
            pair.or += 1;
          }
          else if (c == 'X') {
            MaskPair newPair = new MaskPair(pair);
            newPair.and += 1;
            newPair.or += 1;
            masks.add(newPair);
          }
        }
      }
    }
  }

  public static long calcAns2 (ArrayList<String> inputs) {
    Masks masks = new Masks();
    HashMap<Long, Long> mem = new HashMap<>();
    for (String input : inputs) {
      String[] pieces = input.split(" = ");
      if (pieces[0].equals("mask")) {
        masks.updateMasks(pieces[1]);
      }
      else {
        String pattern = "mem\\[([0-9]*)\\]";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(pieces[0]);
        m.matches();
        long value = Long.parseLong(pieces[1]);
        long memLoc = 0;
        for (MaskPair maskPair : masks.masks) {
          memLoc = Integer.parseInt(m.group(1));
          memLoc = memLoc & maskPair.and;
          memLoc = memLoc | maskPair.or;
          mem.put(memLoc, value);
        }
      }
    }
   
    long result = 0;
    for (Long val : mem.values()) {
      result += val;
    }
    return result;
  }

}
