import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String toStringForDisplaying() {
        return "First name: %s\nLast name: %s\nPhone number: %s\n".formatted(firstName, lastName, phoneNumber);
    }

    public String toString() {
        return "%s\t%s\t%s\n".formatted(firstName, lastName, phoneNumber);
    }

    public void modifyContact(String fName, String lName, String pNum) {
        firstName = fName;
        lastName = lName;
        phoneNumber = pNum;
    }

    public boolean isEqual(String fName, String lName) {
        return ((firstName.equals(fName)) && (lastName.equals(lName))) ? true : false;
    }
}

class ContactList {
    private Contact[] list;

    ContactList() {
        list = new Contact[0];
    }

    public void addAContact(String firstName, String lastName, String phoneNum) {
        Contact t = new Contact("%s\t%s\t%s".formatted(firstName, lastName, phoneNum));
        addAContact(t);
    }

    private void addAContact(Contact t) {
        Contact[] newList = new Contact[list.length + 1];
        for (int i = 0; i < list.length; i++)
            newList[i] = list[i];
        newList[list.length] = t;

        list = newList;
    }

    /**
     * Return the contact in the contact list, if it does not exist, return null
     */
    public Contact findAContact(String firstName, String lastName) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isEqual(firstName, lastName))
                return list[i];
        }
        return null;
    }

    private void removeAContact(int index) {
        Contact[] newList = new Contact[list.length - 1];
        int iter = 0;
        while (iter < index)
            newList[iter] = list[iter++];
        while (iter < list.length - 1)
            newList[iter] = list[++iter];

        list = newList;
    }

    /**
    * Return true if delete successfully or false if cannot find out the contact to delete
    */
    public boolean deleteAContact(String firstName, String lastName) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isEqual(firstName, lastName)) {
                removeAContact(i);
                return true;
            }
        }
        return false;
    }

    void readDataFromFile(String filepath) {
        try {
            File file = new File(filepath);
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()) {
                String buffer = scanner.nextLine();
                addAContact(new Contact(buffer));
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("There was an error while reading the input file!!!");
        }
    }

    public int getLength() {
        return list.length;
    }

    public void printList() {
        for (Contact t : list)
            System.out.println(t.toStringForDisplaying());
    }

    public void saveToFile() {
        try {
            File output = new File("output.txt");

            FileWriter writer = new FileWriter(output);

            for (Contact t : list) {
                writer.write(t.toString());
            }

            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error while writing the file.");
        }
    }
}

public class Q3 {
    static void addNewContact(ContactList list) {
        Scanner scanner = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        String phoneNum = "";

        System.out.print("Input First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Input Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Input Phone Number: ");
        phoneNum = scanner.nextLine();
        while (true) {
            if (phoneNum.length() != 10) {
                System.out.print("Invalid Phone Number. Please Re-Input: ");
                phoneNum = scanner.nextLine();
                continue;
            }
            try {
                Integer.parseInt(phoneNum);
            } catch (NumberFormatException e) {
                System.out.print("Invalid Phone Number. Please Re-Input: ");
                phoneNum = scanner.nextLine();
                continue;
            }
            break;
        }

        firstName = firstName.equals("") ? "N/A" : firstName;
        lastName = lastName.equals("") ? "N/A" : lastName;

        list.addAContact(firstName, lastName, phoneNum);
    }

    static void modifyAContact(ContactList list) {
        Scanner scanner = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        String phoneNum = "";

        System.out.print("Input First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Input Last Name: ");
        lastName = scanner.nextLine();

        firstName = firstName.equals("") ? "N/A" : firstName;
        lastName = lastName.equals("") ? "N/A" : lastName;

        Contact t = list.findAContact(firstName, lastName);
        if (t == null) {
            System.out.println("-> Cannot find matching contact");
            return;
        }

        System.out.println("Old Phone Number: " + t.getPhoneNumber());
        System.out.print("Input New Phone Number: ");
        phoneNum = scanner.nextLine();
        while (true) {
            if (phoneNum.length() != 10) {
                System.out.print("Invalid Phone Number. Please Re-Input: ");
                phoneNum = scanner.nextLine();
                continue;
            }
            try {
                Integer.parseInt(phoneNum);
            } catch (NumberFormatException e) {
                System.out.print("Invalid Phone Number. Please Re-Input: ");
                phoneNum = scanner.nextLine();
                continue;
            }
            break;
        }

        t.modifyContact(firstName, lastName, phoneNum);
    }

    static void deleteAContact(ContactList list) {
        Scanner scanner = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        String phoneNum = "";

        System.out.print("Input First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Input Last Name: ");
        lastName = scanner.nextLine();

        firstName = firstName.equals("") ? "N/A" : firstName;
        lastName = lastName.equals("") ? "N/A" : lastName;

        if (list.deleteAContact(firstName, lastName))
            System.out.println("-> Delete the contact successfully");
        else
            System.out.println("-> Cannot find matching contact");
    }

    public static void main(String[] args) {
        ContactList list = new ContactList();
        list.readDataFromFile("input.txt");
        int mode = -1;
        Scanner scanner = new Scanner(System.in);
        boolean exitFlag = false;
        while (!exitFlag) {
            System.out.println("1: Add new contact");
            System.out.println("2: Modify a contact");
            System.out.println("3: Delete a contact");
            System.out.println("4: Exit and save");
            System.out.print("-> Please input mode: ");
            try {
                mode = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.print("-> Invalid mode. Please re-input mode: ");
                scanner.nextLine();
                mode = scanner.nextInt();
            }
            switch (mode) {
                case 1:
                    addNewContact(list);
                    break;
                case 2:
                    modifyAContact(list);
                    break;
                case 3:
                    deleteAContact(list);
                    break;
                case 4:
                    list.saveToFile();
                    exitFlag = true;
                    break;
            }
        }
        scanner.close();
    }
}