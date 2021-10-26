import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;

    Contact(String info) {
        String[] temp = info.split("\t");
        firstName = temp[0];
        lastName = temp[1];
        phoneNumber = temp[2];
    }

    public String toString() {
        return "First name: %s\nLast name: %s\nPhone number: %s\n".formatted(firstName, lastName, phoneNumber);
    }

    public void modifyContact(String fName, String lName, String pNum) {
        firstName = fName;
        lastName = lName;
        phoneNumber = pNum;
    }
}

class ContactList {
    private int numberOfContacts;
    private List<Contact> list = new ArrayList<Contact>();

    ContactList() {
        numberOfContacts = 0;
    }

    void readDataFromFile(String filepath) {
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String buffer = scanner.nextLine();
                list.add(new Contact(buffer));
            }

            scanner.close();

            numberOfContacts = list.size();
        } catch (FileNotFoundException e) {
            System.out.println("There was an error while reading the input file!!!");
        }
    }

    public void printList() {
        for (Contact str : list)
            System.out.println(str.toString());
    }
}

public class Q3 {
    public static void main(String[] args) {
        ContactList list = new ContactList();
        list.readDataFromFile("input.txt");
        list.printList();
    }
}