import java.util.*;
public class Main {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter production (e.g. A->alpha beta1|alpha beta2|gamma1|gamma2):");
    String p = s.nextLine();
    String n = p.split("->")[0].trim();
    String[] a = p.split("->")[1].split("\\|");
    String x = a[0].trim().split(" ")[0];
    List<String> g = new ArrayList<>(), o = new ArrayList<>();
    for (String t : a) {
      t = t.trim();
      if (t.startsWith(x))
        g.add(t.substring(x.length()).trim().isEmpty() ? "ε" : t.substring(x.length()).trim());
      else
        o.add(t);
    }
    if (g.size() < 2) {
      System.out.println(p);
      return;
    }
    String y = n + "'";
    System.out.println(n + "->" + x + y + (o.isEmpty() ? "" : "|" + String.join("|", o)));
    System.out.println(y + "->" + String.join("|", g));
  }
}
