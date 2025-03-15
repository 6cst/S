import java.util.*;
public class SRP {
  static class Rule {
    String lhs, rhs;
    Rule(String lhs, String rhs) { this.lhs = lhs; this.rhs = rhs; }
  }
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter Number of rules: ");
    int n = Integer.parseInt(sc.nextLine());
    ArrayList<Rule> rules = new ArrayList<>();
    System.out.println("Enter Rules (format: LHS->RHS):");
    for (int i = 0; i < n; i++) {
      String line = sc.nextLine().replaceAll("\\s*->\\s*", "->");
      String[] parts = line.split("->");
      rules.add(new Rule(parts[0].trim(), parts[1].trim()));
    }
    System.out.print("Enter Input String (ending with $): ");
    String input = sc.nextLine();
    if (!input.endsWith("$")) input += "$";
    String stack = "$";
    System.out.println("\n------------------------------------------------------------");
    System.out.printf("%-20s%-20s%-20s\n", "Stack", "Input Buffer", "Parsing Action");
    System.out.println("------------------------------------------------------------");
    while (true) {
      if (input.equals("$") && stack.equals("$" + rules.get(0).lhs)) {
        System.out.printf("%-20s%-20s%-20s\n", stack, input, "Accept");
        System.out.println("Accepted");
        break;
      }
      String action = "";
      boolean reduced = false;
      for (Rule r : rules) {
        if (stack.endsWith(r.rhs)) {
          stack = stack.substring(0, stack.length() - r.rhs.length()) + r.lhs;
          action = "Reduce by " + r.lhs + "->" + r.rhs;
          reduced = true; break;
        }
      }
      if (!reduced) {
        if (!input.equals("$") && !input.isEmpty()) {
          char ch = input.charAt(0);
          input = input.substring(1);
          stack += ch;
          action = "Shift " + ch;
        } else { 
          System.out.printf("%-20s%-20s%-20s\n", stack, input, "Parsing Error");
          break;
        }
      }
      System.out.printf("%-20s%-20s%-20s\n", stack, input, action);
    }
    sc.close();
  }
}
