import java.io.File;
import java.io.FileDescriptor;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class generator {
    private String[] firstName = {
            "An", "Hoa", "Binh",
            "Mai", "Ngan", "Huy",
            "Hung", "Tuan", "Nghia",
            "Thai", "Thao", "Oanh",
            "Khoi", "Hieu", "Tien" };
    private String[] lastName = {
            "Nguyen", "Hoang", "Phan",
            "Le", "Dang", "Bui", "Dam",
            "Mai", "Pham", "Do", "Tran",
            "Ho", "Ngo", "Vu", "Vo" };

    private String[] middleName = {
            "Van", "Huu", "Thi",
            "Thanh", "Kim", "Quoc",
            "Minh", "The", "Trong" };

    private Random random = new Random(System.currentTimeMillis());

    public static void generateNContact(int n) {
        try {
            File output = new File("output.txt");

            FileWriter writer = new FileWriter(output);

            writer.close();
        } catch (IOException e) {

        }
    }
    public static void main(String[] args) {
        generateNContact(5);
    }
}