import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        
        // Read (and ignore) grammar rules
        int r = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < r; i++) {
            sc.nextLine();
        }
        
        // Read nonterminals and terminals
        String[] nonterms = sc.nextLine().split("\\s+");
        String[] terms = sc.nextLine().split("\\s+");
        
        // Build LL(1) table
        String[][] table = new String[nonterms.length][terms.length];
        boolean notLL1 = false;
        for (int i = 0; i < nonterms.length; i++) {
            for (int j = 0; j < terms.length; j++) {
                table[i][j] = sc.nextLine();
                if (table[i][j].contains("|"))
                    notLL1 = true;
            }
        }
        
        // If the grammar is not LL(1), signal a parsing error
        if (notLL1) {
            System.out.println("Parsing Error");
            sc.close();
            return;
        }
        
        // Read input tokens and ensure it ends with "$"
        String[] inputTokens = sc.nextLine().split("\\s+");
        if (!inputTokens[inputTokens.length - 1].equals("$")) {
            String[] temp = new String[inputTokens.length + 1];
            for (int i = 0; i < inputTokens.length; i++)
                temp[i] = inputTokens[i];
            temp[inputTokens.length] = "$";
            inputTokens = temp;
        }
        
        // Initialize stack with "$" at bottom and start symbol at top
        Stack<String> stack = new Stack<>();
        stack.push("$");
        stack.push(nonterms[0]);
        
        int ip = 0;
        boolean error = false;
        
        // Parsing loop (internally processes without printing intermediate steps)
        while (!(stack.peek().equals("$") && inputTokens[ip].equals("$"))) {
            String top = stack.peek();
            if (isTerminal(top, nonterms)) {
                if (top.equals(inputTokens[ip])) {
                    stack.pop();
                    ip++;
                } else {
                    error = true;
                    break;
                }
            } else {
                int row = index(nonterms, top);
                int col = index(terms, inputTokens[ip]);
                if (row == -1 || col == -1) {
                    error = true;
                    break;
                }
                String prod = table[row][col];
                if (prod.equals("ε") || prod.equals("''") || prod.isEmpty()) {
                    stack.pop();
                } else {
                    stack.pop();
                    String[] prodTokens = prod.contains(" ") ? prod.split("\\s+") : prod.split("");
                    for (int i = prodTokens.length - 1; i >= 0; i--) {
                        stack.push(prodTokens[i]);
                    }
                }
            }
        }
        
        // Final result
        if (error)
            System.out.println("Parsing Error");
        else
            System.out.println("String Accepted");
        
        sc.close();
    }
    
    // Returns the index of key in arr, or -1 if not found.
    static int index(String[] arr, String key) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i].equals(key))
                return i;
        }
        return -1;
    }
    
    // Determines if the symbol is terminal (i.e., not among the nonterminals).
    static boolean isTerminal(String symbol, String[] nonterms) {
        for (String nt : nonterms) {
            if (nt.equals(symbol))
                return false;
        }
        return true;
    }
}
