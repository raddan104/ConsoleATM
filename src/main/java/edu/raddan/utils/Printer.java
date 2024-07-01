package edu.raddan.utils;

public class Printer {

    public static void printBeautifulLabel() {
        int labelLength = 9; // Length of "B A N K"
        int borderLength = labelLength + 4; // Length of label + 2 '*' on each side

        // Print top border
        for (int i = 0; i < borderLength + 4; i++) {
            System.out.print("*");
        }
        System.out.println();

        // Print label with spaces
        System.out.println("* B   A   N   K *");

        // Print bottom border
        for (int i = 0; i < borderLength + 4; i++) {
            System.out.print("*");
        }
        System.out.println();
    }

    public static void printOptions() {
        System.out.println("1. Check balance");
        System.out.println("2. Withdraw");
        System.out.println("3. Deposit");
        System.out.print("Type option here: ");
    }

}
