import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Parser {
    public static void main(String[] args) {

        BufferedReader buff = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter expression: ");

        String input;
        try {
            input = buff.readLine();
        } catch (IOException e) {
            return;
        }

        while(input.equals("exit")) {
            Tokenizer t;
            try {
                t = new Tokenizer(input);
                System.out.println(t);
                System.out.print("Enter expression: ");
            } catch (ParseError e) {
                System.out.println(e.toString());
            }

            try {
                input = buff.readLine();
            } catch (IOException e) {
                return;
            }
        }

    }
}
