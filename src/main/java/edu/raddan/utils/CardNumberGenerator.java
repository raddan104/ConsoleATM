package edu.raddan.utils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Pattern;

public class CardNumberGenerator {

    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            sb.append(generateRandomSegment());
            if (i < 3) {
                sb.append("-");
            }
        }
        return sb.toString();
    }

    private static String generateRandomSegment() {
        StringBuilder segment = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int randomDigit = ThreadLocalRandom.current().nextInt(10);
            segment.append(randomDigit);
        }
        return segment.toString();
    }

    public static boolean isValidNumber(String cc) {
        return Pattern.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}", cc);
    }

}
