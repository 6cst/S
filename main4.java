import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    // Helper method to compute the longest common prefix between two strings.
    public static String commonPrefix(String s1, String s2) {
        int min = Math.min(s1.length(), s2.length());
        int i = 0;
        while (i < min && s1.charAt(i) == s2.charAt(i))
            i++;
        return s1.substring(0, i);
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Production : A->");
        String gram = sc.nextLine();
        
        // Split the production alternatives
        String[] parts = gram.split("\\|");
        if (parts.length < 2) {
            System.out.println("Invalid production, missing '|'");
            return;
        }
        
        // Group alternatives that share a common prefix with the first alternative
        ArrayList<String> group = new ArrayList<>();
        ArrayList<String> others = new ArrayList<>();
        
        // Use the first alternative as candidate for grouping
        String candidate = parts[0];
        group.add(candidate);
        for (int i = 1; i < parts.length; i++) {
            // If the alternative does not start with candidate's first char, leave it out of the group.
            if (parts[i].charAt(0) == candidate.charAt(0)) {
                group.add(parts[i]);
            } else {
                others.add(parts[i]);
            }
        }
        
        // Compute the longest common prefix (LCP) for the group
        String lcp = group.get(0);
        for (int i = 1; i < group.size(); i++) {
            lcp = commonPrefix(lcp, group.get(i));
        }
        
        // Construct new production for A:
        // For the group, we factor out the common prefix and add a new nonterminal "X".
        // Then, if there are any "others", add them as separate alternatives.
        String factoredA = lcp + "X";
        for (String alt : others) {
            factoredA += "|" + alt;
        }
        
        // Construct production for X from the group alternatives,
        // removing the common prefix (empty remainders are replaced with ε).
        String factoredX = "";
        for (int i = 0; i < group.size(); i++) {
            String rem = group.get(i).substring(lcp.length());
            if (rem.equals("")) {
                rem = "ε";
            }
            if (i == 0) {
                factoredX += rem;
            } else {
                factoredX += "|" + rem;
            }
        }
        
        System.out.println("\nGrammar Without Left Factoring : ");
        System.out.println("A -> " + factoredA);
        System.out.println("X -> " + factoredX);
    }
}
