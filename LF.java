import java.util.*;

public class Main {
  public static void main(String[] args) {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter production (e.g. A->alpha beta1|alpha beta2|gamma1|gamma2):");
    String pr = s.nextLine();
    String nt = pr.split("->")[0].trim();
    String[] a = pr.split("->")[1].split("\\|");
    String b = a[0].trim().split(" ")[0];
    List<String> p = new ArrayList<>(), c = new ArrayList<>();
    
    for (String d : a) {
      d = d.trim();
      if (d.startsWith(b))
        p.add(d.substring(b.length()).trim().isEmpty() ? "ε" : d.substring(b.length()).trim());
      else
        c.add(d);
    }
    
    if (p.size() < 2) {
      System.out.println(pr);
      return;
    }
    
    String newnt = nt + "'";
    System.out.println(nt + "->" + b + newnt + (c.isEmpty() ? "" : "|" + String.join("|", c)));
    System.out.println(newnt + "->" + String.join("|", p));
  }
}
