import java.util.*;

public class SRP {
    // Rule class holds a grammar rule in the form LHS -> RHS
    static class Rule {
        String lhs, rhs;
        Rule(String lhs, String rhs) {
            this.lhs = lhs;
            this.rhs = rhs;
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read number of rules
        System.out.print("Enter Number of rules: ");
        int n = Integer.parseInt(sc.nextLine());
        List<Rule> rules = new ArrayList<>();
        
        // Read the rules (format: LHS->RHS)
        System.out.println("Enter Rules (format: LHS->RHS):");
        for (int i = 0; i < n; i++) {
            String line = sc.nextLine();
            // Remove extra spaces around '->'
            line = line.replaceAll("\\s*->\\s*", "->");
            String[] parts = line.split("->");
            if (parts.length < 2) continue;
            rules.add(new Rule(parts[0].trim(), parts[1].trim()));
        }
        
        // Read input string (must end with $)
        System.out.print("Enter Input String (ending with $): ");
        String input = sc.nextLine();
        if (!input.endsWith("$")) {
            input += "$";
        }
        
        // Initialize stack with $
        String stack = "$";
        
        // Print header for the table
        System.out.println("\n------------------------------------------------------------");
        System.out.printf("%-20s%-20s%-20s\n", "Stack", "Input Buffer", "Parsing Action");
        System.out.println("------------------------------------------------------------");
        
        // The start symbol is assumed to be the LHS of the first rule.
        String startSymbol = rules.get(0).lhs;
        
        // Main simulation loop
        while (true) {
            String currAction = "";
            
            // Check accept condition BEFORE any move:
            if (input.equals("$") && stack.equals("$" + startSymbol)) {
                currAction = "Accept";
                System.out.printf("%-20s%-20s%-20s\n", stack, input, currAction);
                System.out.println("Accepted");
                break;
            }
            
            boolean acted = false;
            // Try to perform a reduction: if the stack ends with any rule's RHS, reduce.
            for (Rule r : rules) {
                if (stack.endsWith(r.rhs)) {
                    stack = stack.substring(0, stack.length() - r.rhs.length()) + r.lhs;
                    currAction = "Reduce by " + r.lhs + "->" + r.rhs;
                    acted = true;
                    break; // perform one reduction at a time.
                }
            }
            
            // If no reduction is possible, try a shift.
            if (!acted) {
                if (!input.equals("$") && !input.isEmpty()) {
                    char next = input.charAt(0);
                    input = input.substring(1);
                    stack += next;
                    currAction = "Shift " + next;
                    acted = true;
                } else {
                    currAction = "Parsing Error";
                    System.out.printf("%-20s%-20s%-20s\n", stack, input, currAction);
                    break;
                }
            }
            
            // Print the current configuration row immediately after performing the action.
            System.out.printf("%-20s%-20s%-20s\n", stack, input, currAction);
        }
        
        sc.close();
    }
}
