package edu.raddan.utils;

import java.util.ArrayList;
import java.util.List;

public class StringSpliterator {

    public static List<String> split(String input, char delimiter) {
        List<String> result = new ArrayList<>();
        StringBuilder currentToken = new StringBuilder();

        for (char ch : input.toCharArray()) {
            if (ch == delimiter) {
                if (currentToken.length() > 0) {
                    result.add(currentToken.toString());
                    currentToken.setLength(0);
                }
            } else {
                currentToken.append(ch);
            }
        }

        // Add the last token if there is any remaining data
        if (currentToken.length() > 0) {
            result.add(currentToken.toString());
        }

        return result;
    }
}
