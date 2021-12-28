package com.finalExam.database.models;

import com.finalExam.database.Database;

import java.sql.*;
import java.util.ArrayList;

public class Student {
    public String id;
    public String fName;
    public String lName;
    public Date DoB;
    public String Address;

    public ArrayList<Student> getAll() {
        Connection conn = new Database().getConnection();

        try {
            Statement stmt = conn.createStatement();
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

    public ArrayList<Student> searchDB(String sId, String firstName) {
        boolean mode = firstName.equals("");
        Connection conn = new Database().getConnection();

        try {
            String sql = mode ? "USE STUDENTS SELECT * FROM Student WHERE Student.StudentID = ?" : "USE STUDENTS SELECT * FROM Student WHERE Student.FirstName LIKE ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
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

    public boolean queryDB(String sql) {
        Statement stmt;
        Connection conn = new Database().getConnection();

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
