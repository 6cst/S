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
        else { 
            alpha = alt; beta = "ε"; 
        }
        if(common == null) common = alpha;
        if(alpha.equals(common)) 
            betas.add(beta); 
        else gammas.add(alt);
      }
      if(betas.size() > 1){
        System.out.println(lhs + "->" + common + lhs + "'" + (gammas.isEmpty() ? "" : ("|" + String.join("|", gammas))));
        System.out.println(lhs + "'->" + String.join("|", betas));
      } else System.out.println(rule);
    }
  }
}

