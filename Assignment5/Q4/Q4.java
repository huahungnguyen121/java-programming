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

    public void addAStudent(String firstName, String lastName, String phoneNum) {
        Student t = new Student("%s\t%s\t%s".formatted(firstName, lastName, phoneNum));
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
            writer.write("%.2f".formatted(total / list.length));
            writer.close();
        } catch (IOException e) {
            System.out.println("There was an error while writing the file.");
        }
    }
}

public class Q4 {
    public static void main(String[] args) {
        StudentList list = new StudentList();
        list.readDataFromFile("input.txt");
        list.printList();
        list.saveToFile();
    }
}