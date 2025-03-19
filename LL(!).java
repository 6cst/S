Enter the number of rules to skip: 5
Enter rule 1: S->aABb
Enter rule 2: A->c
Enter rule 3: A->ε
Enter rule 4: B->d
Enter rule 5: B->ε
Enter nonterminals (space separated): S A B 
Enter terminals (space separated): a b c d
Enter the parsing table entries:
Entry for nonterminal 'S', terminal 'a': aABb
Entry for nonterminal 'S', terminal 'b': 
Entry for nonterminal 'S', terminal 'c': 
Entry for nonterminal 'S', terminal 'd': 
Entry for nonterminal 'A', terminal 'a': 
Entry for nonterminal 'A', terminal 'b': ε
Entry for nonterminal 'A', terminal 'c': c
Entry for nonterminal 'A', terminal 'd': ε
Entry for nonterminal 'B', terminal 'a': 
Entry for nonterminal 'B', terminal 'b': ε
Entry for nonterminal 'B', terminal 'c': 
Entry for nonterminal 'B', terminal 'd': d
Enter input tokens (space separated, end with $): a c d b $
String Accepted


import java.util.Scanner;
import java.util.Stack;
import java.util.Arrays;

public class Main {
    public static void main(String[] a) {
        Scanner s = new Scanner(System.in);
        System.out.print("n: ");
        int n = Integer.parseInt(s.nextLine());
        for (int i = 0; i < n; i++) {
            System.out.print("r: ");
            s.nextLine();
        }
        System.out.print("N: ");
        String[] N = s.nextLine().split("\\s+");
        System.out.print("T: ");
        String[] T = s.nextLine().split("\\s+");
        String[][] M = new String[N.length][T.length];
        boolean e = false;
        System.out.println("M:");
        for (int i = 0; i < N.length; i++) {
            for (int j = 0; j < T.length; j++) {
                System.out.print("M[" + i + "][" + j + "]: ");
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
        System.out.print("I: ");
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
            System.out.println("E");
        else
            System.out.println("A");
        s.close();
    }
}

    
