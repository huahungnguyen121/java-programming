import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EX3 {
    // swap between uppercase and lowercase for each character in the string
    static String swapUpperLower(String src) {
        char[] result = src.toCharArray();
        for (int i = 0; i < result.length; i++) {
            if (Character.isUpperCase(result[i]))
                result[i] = Character.toLowerCase(result[i]);
            else
                result[i] = Character.toUpperCase(result[i]);
        }
        return String.valueOf(result);
    }

    // reverse the string
    static String reverse(String src) {
        char[] result = src.toCharArray();
        int length = result.length;
        for (int i = 0; i < length / 2; i++) {
            char temp = result[i];
            result[i] = result[length - 1 - i];
            result[length - 1 - i] = temp;
        }
        return String.valueOf(result);
    }

    // shift each character in the string 13 characters after it
    static String ROT13(String src) {
        char[] result = src.toCharArray();
        int length = result.length;
        for (int i = 0; i < result.length; i++) {
            if (((int)result[i] > 77 && (int)result[i] < 91) || ((int)result[i] > 109 && (int)result[i] < 123))
                result[i] = (char)((int)result[i] + 13 - 26);
            else
                result[i] = (char)((int)result[i] + 13);
        }
        return String.valueOf(result);
    }

    static String encodeString(String src) {
        return ROT13(reverse(swapUpperLower(src)));
    }

    public static void main(String[] args) {
        try {
            File input = new File("input.txt");
            Scanner scanner = new Scanner(input);

            // check if the file is empty
            if (!scanner.hasNextLine()) {
                scanner.close();
                return;
            }

            // scan the input file, encode and print the result
            int n = Integer.parseInt(scanner.nextLine());

            for (int i = 0; i < n; i++) {
                String buffer = scanner.nextLine();
                System.out.println(encodeString(buffer));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("There was an error while reading the input file!!!");
        }
    }
}