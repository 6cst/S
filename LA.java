import java.util.Scanner;
import java.util.regex.*;

public class LA {
    public static void main(String[] a) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the input string: "); // prompt for input
        String i = s.nextLine();
        
        String p = "(int|float|char|if|else|while|return|system)"
                 + "|([a-zA-Z_][a-zA-Z0-9_]*)"
                 + "|(\\d+)"
                 + "|([+\\-*/=<>!]+)"
                 + "|([(),;{}\\[\\]])"
                 + "|(\"[^\"]*\")";
        
        Matcher m = Pattern.compile(p).matcher(i);
        int c = 0;
        
        while (m.find()) {
            if (m.group(1) != null)
                System.out.println("Keyword: " + m.group(1));
            else if (m.group(2) != null)
                System.out.println("Identifier: " + m.group(2));
            else if (m.group(3) != null)
                System.out.println("Number: " + m.group(3));
            else if (m.group(4) != null)
                System.out.println("Operator: " + m.group(4));
            else if (m.group(5) != null)
                System.out.println("Separator: " + m.group(5));
            else if (m.group(6) != null)
                System.out.println("String Literal: " + m.group(6));
            c++;
        }
        
        System.out.println("Total Tokens: " + c);
        System.out.println("Tokenization complete.");
    }
}
