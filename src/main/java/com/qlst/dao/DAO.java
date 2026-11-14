package com.qlst.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    private static final String URL = "jdbc:mysql://localhost:3306/qlst_dienmay";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    protected Connection con;

    public DAO() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            con = null;
        }
    }

    protected synchronized Connection connection() throws SQLException {
        if (con == null || !con.isValid(2)) {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return con;
    }
}
