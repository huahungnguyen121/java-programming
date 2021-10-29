import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Student {
    private String firstName;
    private String lastName;
    private String grade;

    Student(String info) {
        String[] temp = info.split("\t");
        firstName = temp[0];
        lastName = temp[1];
        grade = temp[2];
    }

    public String getGrade() {
        return grade;
    }

    public String toStringForDisplaying() {
        return "First name: %s\nLast name: %s\nGrade: %s\n".formatted(firstName, lastName, grade);
    }

    public String toString() {
        return "%s\t%s\t%s\n".formatted(firstName, lastName, grade);
    }

    public void modifyStudent(String fName, String lName, String sGrade) {
        firstName = fName;
        lastName = lName;
        grade = sGrade;
    }

    public void modifyGrade(String sGrade) {
        grade = sGrade;
    }

    public boolean isEqual(String fName, String lName) {
        return ((firstName.equals(fName)) && (lastName.equals(lName))) ? true : false;
    }
}

class StudentList {
    private Student[] list;

    StudentList() {
        list = new Student[0];
    }

    public void addAStudent(String firstName, String lastName, String grade) {
        Student t = new Student("%s\t%s\t%s".formatted(firstName, lastName, grade));
        addAStudent(t);
    }

    private void addAStudent(Student t) {
        Student[] newList = new Student[list.length + 1];
        for (int i = 0; i < list.length; i++)
            newList[i] = list[i];
        newList[list.length] = t;

        list = newList;
    }

    /**
     * Return the Student in the Student list, if it does not exist, return null
     */
    public Student findAStudent(String firstName, String lastName) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isEqual(firstName, lastName))
                return list[i];
        }
        return null;
    }

    private void removeAStudent(int index) {
        Student[] newList = new Student[list.length - 1];
        int iter = 0;
        while (iter < index)
            newList[iter] = list[iter++];
        while (iter < list.length - 1)
            newList[iter] = list[++iter];

        list = newList;
    }

    /**
     * Return true if delete successfully or false if cannot find out the Student to delete
     */
    public boolean deleteAStudent(String firstName, String lastName) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].isEqual(firstName, lastName)) {
                removeAStudent(i);
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
                addAStudent(new Student(buffer));
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
        for (Student t : list)
            System.out.println(t.toStringForDisplaying());
    }

    public void saveToFile() {
        try {
            File output = new File("output.txt");

            FileWriter writer = new FileWriter(output);

            double total = 0;
            for (Student t : list) {
                writer.write(t.toString());
                total += Double.parseDouble(t.getGrade());
            }
            writer.write("Average: %.2f".formatted(total / list.length));
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error while writing the file.");
        }
    }
}

public class Q4 {
    static void addNewStudent(StudentList list) {
        Scanner scanner = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        String grade = "";
        double mark = 0;

        System.out.print("Input First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Input Last Name: ");
        lastName = scanner.nextLine();
        System.out.print("Input Grade: ");
        grade = scanner.nextLine();
        while (true) {
            try {
                mark = Double.parseDouble(grade);
                if (mark > 10 || mark < 0) {
                    System.out.print("Invalid Grade. Please Re-Input: ");
                    grade = scanner.nextLine();
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid Grade. Please Re-Input: ");
                grade = scanner.nextLine();
                continue;
            }
            break;
        }

        firstName = firstName.equals("") ? "N/A" : firstName;
        lastName = lastName.equals("") ? "N/A" : lastName;

        list.addAStudent(firstName, lastName, "%.2f".formatted(mark));
    }

    static void modifyAStudent(StudentList list) {
        Scanner scanner = new Scanner(System.in);
        String firstName = "";
        String lastName = "";
        String grade = "";
        double mark = 0;

        System.out.print("Input First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Input Last Name: ");
        lastName = scanner.nextLine();

        firstName = firstName.equals("") ? "N/A" : firstName;
        lastName = lastName.equals("") ? "N/A" : lastName;

        Student s = list.findAStudent(firstName, lastName);
        if (s == null) {
            System.out.println("-> Cannot find matching student");
            return;
        }

        System.out.println("==Current information==");
        System.out.println("First Name: " + firstName);
        System.out.println("Last Name: " + lastName);
        System.out.println("Grade: " + s.getGrade());
        System.out.println("* Leave the input field of the corresponding information empty if you do not want to change");
        System.out.print("Input New First Name: ");
        String newFirstName = scanner.nextLine();
        System.out.print("Input New Last Name: ");
        String newLastName = scanner.nextLine();
        System.out.print("Input New Grade: ");
        grade = scanner.nextLine();
        while (true) {
            if (grade.equals(""))
                break;
            try {
                mark = Double.parseDouble(grade);
                if (mark > 10 || mark < 0) {
                    System.out.print("Invalid Grade. Please Re-Input: ");
                    grade = scanner.nextLine();
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid Grade. Please Re-Input: ");
                grade = scanner.nextLine();
                continue;
            }
            break;
        }

        firstName = newFirstName.equals("") ? firstName : newFirstName;
        lastName = newLastName.equals("") ? lastName : newLastName;
        grade = grade.equals("") ? s.getGrade() : grade;

        s.modifyStudent(firstName, lastName, "%.2f".formatted(Double.parseDouble(grade)));
    }

    static void deleteAStudent(StudentList list) {
        Scanner scanner = new Scanner(System.in);
        String firstName = "";
        String lastName = "";

        System.out.print("Input First Name: ");
        firstName = scanner.nextLine();
        System.out.print("Input Last Name: ");
        lastName = scanner.nextLine();

        firstName = firstName.equals("") ? "N/A" : firstName;
        lastName = lastName.equals("") ? "N/A" : lastName;

        if (list.deleteAStudent(firstName, lastName))
            System.out.println("-> Delete the student successfully");
        else
            System.out.println("-> Cannot find matching student");
    }

    public static void main(String[] args) {
        StudentList list = new StudentList();
        list.readDataFromFile("input.txt");
        int mode = -1;
        Scanner scanner = new Scanner(System.in);
        boolean exitFlag = false;
        while (!exitFlag) {
            System.out.println("1: Add new student");
            System.out.println("2: Modify student and grade");
            System.out.println("3: Delete a student");
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
                    addNewStudent(list);
                    break;
                case 2:
                    modifyAStudent(list);
                    break;
                case 3:
                    deleteAStudent(list);
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