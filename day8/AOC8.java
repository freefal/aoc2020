import java.util.*;
import java.util.stream.Collectors;

public class AOC8 {
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

    ArrayList<Instruction> program = inputs.stream().map(AOC8::parseInstruction).collect(Collectors.toCollection(ArrayList::new)); 

    long ans1 = calcAns1 (program);
    System.out.println (ans1);

    long ans2 = calcAns2 (program);
    System.out.println (ans2);

  }
  
  public static class Instruction {
    public String opcode;
    public int arg;

    public Instruction (String opcode, int arg) {
      this.opcode = opcode;
      this.arg = arg;
    }
  }

  public static class Interpreter {
    public int instruction;
    public int acc;
    public ArrayList<Instruction> program;

    public Interpreter (ArrayList<Instruction> program) {
      this.program = program;
      instruction = 0;
      acc = 0;
    }
    
    public void step () {
      Instruction cur = program.get(instruction);
      switch (cur.opcode) {
        case "acc":
          acc += cur.arg;
          instruction++;
          break;
        case "jmp":
          instruction += cur.arg;
          break;
        case "nop":
          instruction++;
        default:
          break;
      }
    }
  }

  public static Instruction parseInstruction (String input) {
    String[] pieces = input.split(" ");

    Integer arg = null;

    try {
      arg = Integer.parseInt(pieces[1]);
    } catch (Exception e) { e.printStackTrace(); }

    return new Instruction(pieces[0], arg);
  }

  public static long calcAns1 (ArrayList<Instruction> program) {
    Interpreter i = new Interpreter(program);
    HashSet<Integer> visited = new HashSet<>();
    while (!visited.contains(i.instruction)) {
      visited.add(i.instruction);
      i.step();
    }
    return i.acc;
  }

  public static long calcAns2 (ArrayList<Instruction> program) {
    for (int j = 0; j < program.size(); j++) {
      Instruction ins = program.get(j);
      String origOpCode = ins.opcode;
      switch (ins.opcode) {
        case "acc":
          continue;
        case "nop":
          ins.opcode = "jmp";
          break;
        case "jmp":
          ins.opcode = "nop";
          break;
      }

      Interpreter i = new Interpreter(program);
      HashSet<Integer> visited = new HashSet<>();
      while (!visited.contains(i.instruction) && i.instruction < i.program.size()) {
        visited.add(i.instruction);
        i.step();
      }

      if (i.instruction == i.program.size())
        return i.acc;

      ins.opcode = origOpCode;
    }
    return -1;
  }
}
