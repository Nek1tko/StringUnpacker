import exception.InvalidBracketsException;
import exception.InvalidCharacterException;
import exception.InvalidMultiplierException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        try {
            System.out.println(parser.parse(scanner.nextLine()));
        } catch (InvalidBracketsException | InvalidMultiplierException | InvalidCharacterException e) {
            System.out.println(e.getMessage());
        }
    }
}
