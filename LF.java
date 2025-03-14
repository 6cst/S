import java.util.*; // U: utilities import

public class Main { // Main class
  public static void main(String[] args) { // m: main method
    Scanner s = new Scanner(System.in); // s: Scanner for input
    System.out.println("Enter production (e.g. A->alpha beta1|alpha beta2|gamma1|gamma2):"); // prompt
    String p = s.nextLine(); // p: production rule string input
    String n = p.split("->")[0].trim(); // n: nonterminal (LHS of production)
    String[] a = p.split("->")[1].split("\\|"); // a: array of alternatives (RHS split by '|')
    String x = a[0].trim().split(" ")[0]; // x: common prefix (first token of first alternative)
    List<String> g = new ArrayList<>(), o = new ArrayList<>(); // g: alternatives with prefix x; o: others
    for (String t : a) { // t: each alternative in a
      t = t.trim(); // trim whitespace from t
      if (t.startsWith(x)) // if t starts with x (common prefix)
        g.add(t.substring(x.length()).trim().isEmpty() ? "ε" : t.substring(x.length()).trim()); // add remainder (or 'ε' if empty) to g
      else
        o.add(t); // else add t to o
    }
    if (g.size() < 2) { // if fewer than 2 alternatives share x, no left factoring
      System.out.println(p); // print original production p
      return; // exit program
    }
    String y = n + "'"; // y: new nonterminal (n with a prime symbol)
    System.out.println(n + "->" + x + y + (o.isEmpty() ? "" : "|" + String.join("|", o))); // print left-factored production: n->x y (plus others)
    System.out.println(y + "->" + String.join("|", g)); // print production for new nonterminal y: y->(alternatives from g)
  }
}
