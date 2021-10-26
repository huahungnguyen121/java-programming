import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q1 {
    static int countLine(String filepath) {
        int count = 0;
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String buffer = scanner.nextLine();
                if (buffer.length() != 0)
                    count++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("There was an error while reading the input file!!!");
        }

        return count;
    }
    public static void main(String[] args) {
        System.out.println("Number of lines: %d".formatted(countLine("input.txt")));
    }
}