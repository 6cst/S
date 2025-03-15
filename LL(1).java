import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Read number of rules and the rules
        System.out.print("Enter number of rules: ");
        int numRules = Integer.parseInt(scanner.nextLine());
        String[] rules = new String[numRules];
        for (int i = 0; i < numRules; i++) {
            System.out.print("Enter Rules (only of format A->B): ");
            rules[i] = scanner.nextLine();
        }
        
        // 2. Read nonterminals (nt) and terminals
        System.out.print("Enter nt (space separated): ");
        String[] nts = scanner.nextLine().split("\\s+");
        
        System.out.print("Enter terminals (space separated): ");
        String[] terminals = scanner.nextLine().split("\\s+");
        
        // 3. Create and fill the LL(1) table matrix
        String[][] table = new String[nts.length][terminals.length];
        boolean notLL1 = false;
        System.out.println("Enter matrix form of LL(1) table:");
        for (int i = 0; i < nts.length; i++) {
            for (int j = 0; j < terminals.length; j++) {
                System.out.print("Enter entry for (" + nts[i] + ", " + terminals[j] + "): ");
                String entry = scanner.nextLine();
                table[i][j] = entry;
                // Check if entry contains "|" which indicates a conflict
                if (entry.contains("|")) {
                    notLL1 = true;
                }
            }
        }
        
        // 4. Display the LL(1) table
        System.out.println("\nLL(1) Table:");
        // Print header row (terminals)
        System.out.print("\t");
        for (String term : terminals) {
            System.out.print(term + "\t");
        }
        System.out.println();
        // Print each row: nt and its corresponding entries
        for (int i = 0; i < nts.length; i++) {
            System.out.print(nts[i] + "\t");
            for (int j = 0; j < terminals.length; j++) {
                // Show "-" for empty entries
                System.out.print((table[i][j].isEmpty() ? "-" : table[i][j]) + "\t");
            }
            System.out.println();
        }
        
        // 5. If any entry had a "|" then the grammar is not LL(1)
        if (notLL1) {
            System.out.println("\nNot LL(1) grammar");
        }
        
        scanner.close();
    }
}
