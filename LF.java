import java.util.*;
public class LF {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        do {
            System.out.print("Enter number of productions: ");
            int n = Integer.parseInt(sc.nextLine().trim());
            List<String> nonTerminals = new ArrayList<>(), rules = new ArrayList<>();
            System.out.println("Enter productions (e.g., A->ab1|abn|c|d|e):");
            for (int i = 0; i < n; i++) {
                String[] parts = sc.nextLine().split("->");
                nonTerminals.add(parts[0].trim());
                rules.add(parts[1].trim());
            }
            System.out.println("\nResult:");
            for (int i = 0; i < nonTerminals.size(); i++) {
                eliminateLeftFactoring(nonTerminals.get(i), rules.get(i).split("\\|"));
            }
            System.out.print("\nDo you need to continue to enter productions again? (yes/no): ");
        } while(sc.nextLine().trim().equalsIgnoreCase("yes"));
    }
    static void eliminateLeftFactoring(String nt, String[] prods) {
        // Group alternatives by the first symbol
        Map<String, List<String>> groups = new LinkedHashMap<>();
        for (String prod : prods) {
            prod = prod.trim();
            if(prod.isEmpty()) continue;
            String key = prod.substring(0, 1);
            groups.computeIfAbsent(key, k -> new ArrayList<>()).add(prod);
        }
        List<String> newProds = new ArrayList<>();
        Map<String, List<String>> newNTs = new LinkedHashMap<>();
        // For groups with more than one alternative, factor out the common first symbol.
        for (Map.Entry<String, List<String>> entry : groups.entrySet()) {
            List<String> list = entry.getValue();
            if (list.size() > 1) {
                newProds.add(entry.getKey() + nt + "'");
                List<String> remainders = new ArrayList<>();
                for (String s : list) {
                    String rem = s.substring(1);
                    remainders.add(rem.isEmpty() ? "ε" : rem);
                }
                newNTs.put(nt + "'", remainders);
            } else {
                newProds.add(list.get(0));
            }
        }
        System.out.println(nt + "->" + String.join("|", newProds));
        for (Map.Entry<String, List<String>> entry : newNTs.entrySet()) {
            System.out.println(entry.getKey() + "->" + String.join("|", entry.getValue()));
        }
    }
}
