import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Q3 {
    static int countWord(String filepath) {
        int count = 0;
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNext()) {
                String buffer = scanner.next();
                count++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("There was an error while reading the input file!!!");
        }

        return count;
    }
    public static void main(String[] args) {
        //System.out.println("Number of words: %d".formatted(countWord("input.txt")));
    }
}