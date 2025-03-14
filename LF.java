import java.util.Scanner;
public class LF {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter production rule (e.g., S-> a|a bc|a bcd|a bcd):");
        String rule = s.nextLine();
        String[] parts = rule.split("->");
        String nt = parts[0].trim();
        String[] alts = parts[1].split("\\|");
        for(int i = 0; i < alts.length; i++) alts[i] = alts[i].trim();
        String prefix = alts[0];
        for(String alt : alts) prefix = commonPrefix(prefix, alt);
        String newNT = nt + "'";
        StringBuilder factored = new StringBuilder();
        for(int i = 0; i < alts.length; i++){
            String suf = alts[i].substring(prefix.length());
            factored.append(suf.isEmpty() ? "ε" : suf).append(i < alts.length - 1 ? " | " : "");
        }
        System.out.println(nt + " -> " + prefix + newNT);
        System.out.println(newNT + " -> " + factored);
        s.close();
    }
    static String commonPrefix(String a, String b) {
        int i = 0, len = Math.min(a.length(), b.length());
        while(i < len && a.charAt(i)==b.charAt(i)) i++;
        return a.substring(0,i);
    }
}
-------------------------------------------------------------------------------------------------------------------------------------------------------------------
import java.util.*;
public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter number of production rules: ");
    int n = Integer.parseInt(sc.nextLine().trim());
    String[] rules = new String[n];
    for (int i = 0; i < n; i++) {
      System.out.print("Enter production rule" + (i+1) + ": ");
      rules[i] = sc.nextLine().trim();
    }
    System.out.println("RESULT:");
    for (String rule : rules) {
      String[] parts = rule.split("->");
      if(parts.length != 2){ 
          System.out.println("Invalid rule format: " + rule); 
      continue;
    }
    String lhs = parts[0].trim(); 
    String[] alts = parts[1].split("\\|");
      List<String> betas = new ArrayList<>(), gammas = new ArrayList<>();
      String common = null;
      for (String alt : alts) {
        alt = alt.trim(); String alpha, beta;
        if (alt.contains(" ")) {
            String[] tok = alt.split(" ", 2); 
        alpha = tok[0].trim(); 
        beta = tok[1].trim(); 
        if(beta.isEmpty()) beta = "ε"; }
        else { alpha = alt; beta = "ε"; }
        if(common == null) common = alpha;
        if(alpha.equals(common)) betas.add(beta); 
        else gammas.add(alt);
      }
      if(betas.size() > 1){
        System.out.println(lhs + "->" + common + lhs + "'" + (gammas.isEmpty() ? "" : ("|" + String.join("|", gammas))));
        System.out.println(lhs + "'->" + String.join("|", betas));
      } else System.out.println(rule);
    }
  }
}

