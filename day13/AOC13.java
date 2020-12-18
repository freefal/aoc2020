import java.util.*;
import java.util.stream.Collectors;

public class AOC13 {
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

    long ans2 = calcAns2 (inputs);
    System.out.println (ans2);

  }
  
  public static int calcAns1 (ArrayList<String> inputs) {
    int earliest = Integer.parseInt(inputs.get(0));
    int[] buses = Arrays.stream(inputs.get(1).split(","))
      .filter(bus -> !bus.equals("x"))
      .mapToInt(Integer::parseInt)
      .toArray();
    int minWait = buses[0];
    int minBus = 0;
    for (int bus : buses) {
      int div = earliest / bus;
      int busWait = div * bus + bus - earliest;
      if (busWait < minWait) {
        minWait = busWait;
        minBus = bus;
      }
    }
    return minWait * minBus;
  }

  public static long calcAns2 (ArrayList<String> inputs) {
    ArrayList<Long> remainders = new ArrayList<>();
    ArrayList<Long> modulos = new ArrayList<>();
    String[] buses = inputs.get(1).split(",");
    for (int i = 0; i < buses.length; i++) {
      if (buses[i].equals("x"))
        continue;
      long mod = Long.parseLong(buses[i]);
      long rem = -(long)i;
      while (rem < 0)
        rem += mod;
      remainders.add(rem);
      modulos.add(mod);
    }
    long curTest = remainders.get(remainders.size() - 1);
    long inc = modulos.get(modulos.size() - 1);

    for (int i = modulos.size() - 2; i >=0; i--) {
      long mod = modulos.get(i);
      long rem = remainders.get(i);
     
      while (curTest % mod != rem) {
        curTest += inc;
      }
      
      inc *= mod;
    }      

    return curTest;
  }

}
