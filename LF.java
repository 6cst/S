import java.util.Scanner;
public class LF {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print("Enter number of production rules: ");
        int n = Integer.parseInt(s.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.print("Enter Production rule: ");
            String r = s.nextLine();
            String[] x = r.split("->");
            String t = x[0].trim(); // nonterminal
            String[] y = x[1].split("\\|");
            System.out.println("Original: " + r);
            if(y.length < 2) {
                System.out.println("No left factoring needed: " + r);
                continue;
            }
            String c = y[0]; // candidate
            String g = "", o = "";
            for(String a : y) {
                if(a.startsWith(c.substring(0, 1)))
                    g += a + " ";
                else
                    o += "|" + a;
            }
            String[] z = g.trim().split(" ");
            String q = z[0]; // common prefix
            for(String a : z) {
                int m = Math.min(q.length(), a.length()), j = 0;
                while(j < m && q.charAt(j) == a.charAt(j))
                    j++;
                q = q.substring(0, j);
            }
            System.out.println("Common prefix: " + q);
            System.out.println("Grouped alternatives: " + g);
            System.out.println("Other alternatives: " + o);
            System.out.println("Modified production:");
            System.out.println(t + " -> " + q + "X" + o);
            String X = "X -> ";
            for (int j = 0; j < z.length; j++){
                String rj = z[j].substring(q.length());
                if(rj.equals(""))
                    rj = "ε";
                X += (j == 0 ? rj : "|" + rj);
            }
            System.out.println(X);
        }
    }
}
