package com.finalExam.database;

import java.sql.*;

public class Database {
    private static Connection conn = null;

    private static final String DB_URL = "jdbc:sqlserver://localhost";
    private static final String DB_USERNAME = "sa";
    private static final String DB_PASSWORD = "123456";

    public Database() {
        try {
            Driver myDriver = new com.microsoft.sqlserver.jdbc.SQLServerDriver();
            DriverManager.registerDriver(myDriver);

            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (SQLException e) {
            System.out.println("Fail to connect to DB");
        }
    }

    public Connection getConnection() { return conn; }

    public boolean closeConnection() {
        try {
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}