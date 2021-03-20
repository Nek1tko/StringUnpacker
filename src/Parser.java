import exception.InvalidBracketsException;
import exception.InvalidCharacterException;
import exception.InvalidMultiplierException;

import java.util.Deque;
import java.util.LinkedList;

public class Parser {
    private int getMultiplier(StringBuilder string) throws InvalidMultiplierException {
        if (string.isEmpty()) {
            throw new InvalidMultiplierException("Multiplier must precede open bracket");
        }
        if (string.chars().anyMatch((el) -> !Character.isDigit(el))) {
            System.out.println(string);
            throw new InvalidMultiplierException("Invalid symbols in multiplier. Only digits allowed");
        }

        return Integer.parseInt(string.toString());
    }

    private StringBuilder getCharacters(StringBuilder string) {
        StringBuilder stringBuilder = new StringBuilder();
        while(!string.isEmpty()) {
            char currentChar = string.charAt(0);
            if ((currentChar >= 'A' && currentChar <= 'Z') || (currentChar >= 'a' && currentChar <= 'z')) {
                stringBuilder.append(currentChar);
                string.deleteCharAt(0);
            } else
                break;
        }
        return stringBuilder;
    }

    public String parse(String string) throws InvalidBracketsException, InvalidMultiplierException, InvalidCharacterException {
        string = string.trim();
        Deque<Integer> multiplierStack = new LinkedList<>();
        Deque<StringBuilder> substringStack = new LinkedList<>();
        substringStack.push(new StringBuilder());
        StringBuilder currentSubstring = new StringBuilder();
        for (int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) == '[') {
                substringStack.peek().append(getCharacters(currentSubstring));
                substringStack.push(new StringBuilder());
                multiplierStack.push(getMultiplier(currentSubstring));
                currentSubstring.setLength(0);
            } else if (string.charAt(i) == ']') {
                if (multiplierStack.isEmpty()) {
                    throw new InvalidBracketsException("] bracket has no pair before");
                }
                if (currentSubstring.toString().matches("^[A-za-z]*$")) {
                    String repeatSubstring = substringStack.pop().append(currentSubstring)
                            .toString()
                            .repeat(
                                    multiplierStack.pop()
                            );
                    substringStack.peek().append(repeatSubstring);
                } else {
                    throw new InvalidCharacterException("Invalid symbol between brackets. Only latin letters allowed");
                }

                currentSubstring.setLength(0);
            } else {
                currentSubstring.append(string.charAt(i));
            }
        }

        if (substringStack.size() != 1) {
            throw new InvalidBracketsException("[ bracket has no pair after");
        }

        if (currentSubstring.toString().matches("^[A-za-z]*$")) {
            return substringStack.peek().append(currentSubstring).toString();
        } else {
            throw new InvalidCharacterException("Invalid symbol. Only latin letters allowed");
        }
    }
}
