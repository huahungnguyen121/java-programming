package com.finalExam.database;

import com.finalExam.Student;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    PreparedStatement pStmt = null;
    Statement stmt = null;

    static Driver myDriver = null;
    static Connection conn = null;

    String DB_URL = "jdbc:sqlserver://localhost";
    String DB_USERNAME = "sa";
    String DB_PASSWORD = "123456";

    public Database() {
        try {
            myDriver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
            DriverManager.registerDriver(myDriver);

            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            System.out.println("Connect to DB successfully");
        } catch (SQLException e) {
            System.out.println("Fail to connect to DB");
        }
    }

    public ArrayList<Student> searchDB(String sId, String firstName) {
        boolean mode = firstName.equals("");
        try {
            String sql = mode ? "USE STUDENTS SELECT * FROM Student WHERE Student.StudentID = ?" : "USE STUDENTS SELECT * FROM Student WHERE Student.FirstName LIKE ?";
            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, mode ? sId : "%" + firstName + "%");
            ResultSet res = pStmt.executeQuery();
            ArrayList<Student> students = new ArrayList<>();
            while (true) {
                try {
                    if (!res.next())
                        break;
                    String id = res.getString("StudentID");
                    String fName = res.getString("FirstName");
                    String lName = res.getString("LastName");
                    Date DoB = res.getDate("DoB");
                    String Address = res.getString("Address");
                    Student cur = new Student();
                    cur.id = id;
                    cur.fName = fName;
                    cur.lName = lName;
                    cur.DoB = DoB;
                    cur.Address = Address;
                    students.add(cur);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            pStmt.close();
            return students;
        } catch (SQLException e) {
            return null;
        }
    }
    public ArrayList<Student> queryAll() {
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("USE STUDENTS");
            ResultSet res = stmt.executeQuery("SELECT * FROM Student");
            ArrayList<Student> students = new ArrayList<>();
            while (true) {
                try {
                    if (!res.next())
                        break;
                    String id = res.getString("StudentID");
                    String fName = res.getString("FirstName");
                    String lName = res.getString("LastName");
                    Date DoB = res.getDate("DoB");
                    String Address = res.getString("Address");
                    Student cur = new Student();
                    cur.id = id;
                    cur.fName = fName;
                    cur.lName = lName;
                    cur.DoB = DoB;
                    cur.Address = Address;
                    students.add(cur);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            stmt.close();
            return students;
        } catch (SQLException e) {
            return null;
        }
    }
    public boolean queryDB(String sql) {
        Statement stmt;
        try {
            stmt = conn.createStatement();
            stmt.executeUpdate("USE STUDENTS");
            stmt.executeUpdate(sql);
            stmt.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}