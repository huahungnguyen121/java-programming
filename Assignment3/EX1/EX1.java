import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EX1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int peopleCount = 0;
        int traffic = 0;

        // scan the input and calculate the traffic
        do {
            String buffer = scanner.nextLine();
            if (buffer.charAt(0) == '+')
                peopleCount++;
            else if (buffer.charAt(0) == '-')
                peopleCount--;
            else {
                String[] splittedStrs = buffer.split("\\:");
                traffic += splittedStrs.length == 2 ? splittedStrs[1].length() * peopleCount : 0;
            }
        } while (peopleCount > 0);
        scanner.close();

        // print the result
        System.out.println("--------------");
        System.out.println(traffic);
    }
}
