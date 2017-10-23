import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
    public static void main(String[] args) {
        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        String input;
        Tokenizer t;
        try {
            System.out.print("Enter expression: ");
            input = buff.readLine();

            while (!input.equals("exit")) {
                try {
                    t = new Tokenizer(input);
                    System.out.println(t);
                } catch (ParseError e) {
                    System.out.println(e.toString());
                }

                System.out.print("\nEnter expression: ");
                input = buff.readLine();
            }
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}
