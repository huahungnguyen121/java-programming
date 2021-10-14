import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class EX2 {
    public static void main(String[] args) {
        try {
            // define variables
            File input = new File("input.txt");
            Scanner scanner = new Scanner(input);
            int[] info = new int[6]; // { index: variable } = { 0: n, 1: P1, 2: P2, 3: P3, 4: T1, 5: T2 }
            int totalConsumption = 0;

            // check if the file is empty
            if (!scanner.hasNextLine()) {
                scanner.close();
                return;
            }

            // scan the input file and calculate the total consumption
            String[] splittedStrs = scanner.nextLine().split(" ");
            for (int i = 0; i < splittedStrs.length; i++)
                info[i] = Integer.parseInt(splittedStrs[i]);

            int lastActive = 0;
            // calculate the consumption of the first period
            String[] periods = scanner.nextLine().split(" ");
            lastActive = Integer.parseInt(periods[1]);

            totalConsumption += (lastActive - Integer.parseInt(periods[0])) * info[1];

            for (int i = 1; i < info[0]; i++) {
                periods = scanner.nextLine().split(" "); // read the input file

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
            System.out.println(totalConsumption);
        } catch (FileNotFoundException e) {
            System.out.println("There was an error while reading the input file!!!");
        }
    }
}