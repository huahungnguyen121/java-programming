package com.finalExam.controllers;

import com.finalExam.database.models.Student;

public class Controller {
    public boolean addToDB(String idValue, String fNameValue, String lNameValue, String DoBValue, String addressValue) {
        String sql = "INSERT INTO Student(StudentID, FirstName, LastName, DoB, Address) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s')".formatted(idValue, fNameValue, lNameValue, DoBValue, addressValue);
        return new Student().queryDB(sql);
    }
}
