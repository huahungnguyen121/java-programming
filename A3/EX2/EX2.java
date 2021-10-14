import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EX2 {
    public static void main(String[] args) {
        // define variables
        Scanner scanner = new Scanner(System.in);
        int[] info = new int[6]; // { index: variable } = { 0: n, 1: P1, 2: P2, 3: P3, 4: T1, 5: T2 }
        int totalConsumption = 0;
        String[] splittedStrs;

        // scan the input file and calculate the total consumption
        while (true) {
            splittedStrs = scanner.nextLine().split(" ");
            if (splittedStrs.length == 6)
                break;
            else
                System.out.println("Input Error. Please try again.");
        }

        for (int i = 0; i < splittedStrs.length; i++)
            info[i] = Integer.parseInt(splittedStrs[i]);

        int lastActive = 0;
        // calculate the consumption of the first period
        String[] periods;
        while (true) {
            periods = scanner.nextLine().split(" ");
            if (periods.length == 2)
                break;
            else
                System.out.println("Input Error. Please try again.");
        }
        lastActive = Integer.parseInt(periods[1]);

        totalConsumption += (lastActive - Integer.parseInt(periods[0])) * info[1];

        for (int i = 1; i < info[0]; i++) {
            while (true) {
                periods = scanner.nextLine().split(" ");
                if (periods.length == 2)
                    break;
                else
                    System.out.println("Input Error. Please try again.");
            }

            int start = Integer.parseInt(periods[0]);
            int end = Integer.parseInt(periods[1]);

            // calculate the consumption of the idle period
            int idlePeriod = start - lastActive;

            if (idlePeriod > info[4] + info[5])
                totalConsumption += info[4] * info[1] + info[5] * info[2] + (idlePeriod - info[4] - info[5]) * info[3];
            else if (idlePeriod > info[4])
                totalConsumption += info[4] * info[1] + (idlePeriod - info[4]) * info[2];
            else
                totalConsumption += idlePeriod * info[1];

            // calculate the consumption of the active period
            totalConsumption += (end - start) * info[1];
            lastActive = end;
        }
        scanner.close();

        // print the result
        System.out.println("--------------");
        System.out.println(totalConsumption);
    }
}