import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays;

public class Main {
    public static void main(String[] a) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the number of grammar rules:");
        int n = Integer.parseInt(s.nextLine());
        String[] r = new String[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter rule #" + (i + 1) + ":");
            r[i] = s.nextLine();
        }
        System.out.println("Enter the non-terminals (space-separated):");
        String[] N = s.nextLine().split("\\s+");
        System.out.println("Enter the terminals (space-separated):");
        String[] T = s.nextLine().split("\\s+");
        String[][] M = new String[N.length][T.length];
        boolean e = false;
        System.out.println("Enter the parsing table entries:");
        for (int i = 0; i < N.length; i++) {
            for (int j = 0; j < T.length; j++) {
                System.out.println("Enter table entry for non-terminal '" + N[i] + "' with terminal '" + T[j] + "':");
                M[i][j] = s.nextLine();
                if (M[i][j].contains("|"))
                    e = true;
            }
        }
        if (e) {
            System.out.println("E");
            s.close();
            return;
        }
        System.out.println("Enter the input string tokens (space-separated, do not include the end marker '$'):");
        String[] I = s.nextLine().split("\\s+");
        if (!I[I.length - 1].equals("$")) {
            String[] t = new String[I.length + 1];
            for (int i = 0; i < I.length; i++) {
                t[i] = I[i];
            }
            t[I.length] = "$";
            I = t;
        }
        Stack<String> S = new Stack<>();
        S.push("$");
        S.push(N[0]);
        int p = 0;
        boolean f = false;
        while (!(S.peek().equals("$") && I[p].equals("$"))) {
            String x = S.peek();
            if (!Arrays.asList(N).contains(x)) {
                if (x.equals(I[p])) {
                    S.pop();
                    p++;
                } else {
                    f = true;
                    break;
                }
            } else {
                int i = Arrays.asList(N).indexOf(x);
                int j = Arrays.asList(T).indexOf(I[p]);
                if (i == -1 || j == -1) {
                    f = true;
                    break;
                }
                String y = M[i][j];
                if (y.equals("ε") || y.equals("''") || y.isEmpty()) {
                    S.pop();
                } else {
                    S.pop();
                    String[] z = y.contains(" ") ? y.split("\\s+") : y.split("");
                    for (int k = z.length - 1; k >= 0; k--) {
                        S.push(z[k]);
                    }
                }
            }
        }
        if (f)
            System.out.println("Error");
        else
            System.out.println("Accept");
        s.close();
    }
}
