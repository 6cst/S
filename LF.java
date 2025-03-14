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
