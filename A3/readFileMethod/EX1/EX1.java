import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EX1 {
    public static void main(String[] args) {
        try {
            File input = new File("input.txt");
            Scanner scanner = new Scanner(input);
            int peopleCount = 0;
            int traffic = 0;

            // scan the input file and calculate the traffic
            while (scanner.hasNextLine()) {
                String buffer = scanner.nextLine();
                if (buffer.charAt(0) == '+')
                    peopleCount++;
                else if (buffer.charAt(0) == '-')
                    peopleCount--;
                else {
                    String[] splittedStrs = buffer.split("\\:");
                    traffic += splittedStrs.length == 2 ? splittedStrs[1].length() * peopleCount : 0;
                }
            }
            scanner.close();

            // print the result
            System.out.println(traffic);
        } catch (FileNotFoundException e) {
            System.out.println("There was an error while reading the input file!!!");
        }
    }
}