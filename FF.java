import java.util.*;
public class FF {
  static Map<String, List<List<String>>> g = new HashMap<>(); // grammar: non-terminal -> list of productions
  static List<String> nts = new ArrayList<>();                // list of non-terminals (order preserved)
  static Set<String> ts = new HashSet<>();                     // terminals
  static Map<String, Set<String>> first = new HashMap<>();     // FIRST sets
  static Map<String, Set<String>> follow = new HashMap<>();    // FOLLOW sets
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter Non-terminals (space separated):");
    for (String s : sc.nextLine().split("\\s+")) {
      nts.add(s); 
      g.put(s, new ArrayList<>()); 
      follow.put(s, new HashSet<>());
    }
    System.out.println("Enter Terminals (space separated):");
    for (String s : sc.nextLine().split("\\s+"))
      ts.add(s);
    // Read productions (in reverse order)
    for (int i = nts.size()-1; i >= 0; i--) {
      String nt = nts.get(i);
      System.out.println("Enter number of productions for " + nt + ":");
      int n = sc.nextInt(); 
      sc.nextLine();
      List<List<String>> prods = new ArrayList<>();
      for (int j = 0; j < n; j++) {
        System.out.println("Enter production rule (space separated, use ∈ for epsilon):");
        prods.add(Arrays.asList(sc.nextLine().split("\\s+")));
      }
      g.put(nt, prods);
    }
    // Compute FIRST sets for each non-terminal (no memoization)
    for (String nt : nts)
      first.put(nt, computeFirst(nt));
    for (String nt : nts)
      System.out.println("First(" + nt + ") = " + join(first.get(nt)));
    
    // For start symbol add '$' to FOLLOW
    follow.get(nts.get(0)).add("$");
    boolean changed = true;
    while(changed) {
      changed = false;
      for (String A : g.keySet())
        for (List<String> prod : g.get(A))
          for (int i = 0; i < prod.size(); i++) {
            String B = prod.get(i);
            if (nts.contains(B)) {
              Set<String> firstBeta = new HashSet<>();
              boolean allEps = true;
              for (int j = i+1; j < prod.size(); j++) {
                String sym = prod.get(j);
                Set<String> fs = nts.contains(sym) ? first.get(sym) : new HashSet<>(Arrays.asList(sym));
                firstBeta.addAll(fs);
                if (!fs.contains("∈")) { 
                allEps = false; break; 
                }
                firstBeta.remove("∈");
              }
              if (follow.get(B).addAll(firstBeta)) changed = true;
              if (i == prod.size()-1 || allEps)
                if (follow.get(B).addAll(follow.get(A))) changed = true;
            }
          }
    }
    for (String nt : nts)
      System.out.println("Follow(" + nt + ") = " + join(follow.get(nt)));
    sc.close();
  }

  // Recursively compute FIRST for symbol s
  static Set<String> computeFirst(String s) {
    Set<String> res = new HashSet<>();
    if (!nts.contains(s)) { res.add(s); 
    return res; 
    }
    for (List<String> prod : g.get(s)) {
      if (prod.size() == 1 && prod.get(0).equals("∈")) { 
          res.add("∈"); 
          continue; 
      }
      boolean allEps = true;
      for (String sym : prod) {
        Set<String> fs = nts.contains(sym) ? computeFirst(sym) : new HashSet<>(Arrays.asList(sym));
        res.addAll(fs);
        if (!fs.contains("∈")) { allEps = false; break; } else res.remove("∈");
      }
      if (allEps) res.add("∈");
    }
    return res;
  }
  // Join set elements with a comma (no braces)
  static String join(Set<String> set) {
    return String.join(" , ", set);
  }
}
