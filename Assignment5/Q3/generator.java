import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class generator {
    private static String[] firstName = {
            "An", "Hoa", "Binh",
            "Mai", "Ngan", "Huy",
            "Hung", "Tuan", "Nghia",
            "Thai", "Thao", "Oanh",
            "Khoi", "Hieu", "Tien" };
    private static String[] lastName = {
            "Nguyen", "Hoang", "Phan",
            "Le", "Dang", "Bui", "Dam",
            "Mai", "Pham", "Do", "Tran",
            "Ho", "Ngo", "Vu", "Vo" };

    private static String[] middleName = {
            "Van", "Huu", "Thi",
            "Thanh", "Kim", "Quoc",
            "Minh", "The", "Trong" };

    private static Random random = new Random(System.currentTimeMillis());

    private static String generatePhone() {
        String result = "0";
        for (int i = 0; i < 9; i++)
            result += String.valueOf(random.nextInt(10));
        return result;
    }

    public static void generateNContact(int n) {
        try {
            File output = new File("input.txt");

            FileWriter writer = new FileWriter(output);

            for (int i = 0; i < n; i++) {
                String[] contact = {
                        firstName[random.nextInt(firstName.length)],
                        "%s %s".formatted(lastName[random.nextInt(lastName.length)], middleName[random.nextInt(middleName.length)]),
                        generatePhone()
                };
                writer.write("%s\t%s\t%s\n".formatted(contact[0], contact[1], contact[2]));
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error while writing the file.");
        }
    }
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("You have to pass a number of contact you want to generate after calling the program.");
        } else {
            try {
                int n = Integer.parseInt(args[0]);
                generateNContact(n);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number");
            }
        }
    }
}