package com.smarthire.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL =
            "jdbc:mysql://localhost:3306/smarthire";

    private static final String USER =
            "root";

    private static final String PASSWORD =
            "2409";

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            Connection connection =
                    DriverManager.getConnection(
                            URL,
                            USER,
                            PASSWORD
                    );

            System.out.println(
                    "SmartHire Database Connected Successfully!"
            );

            return connection;

        } catch (ClassNotFoundException e) {

            throw new RuntimeException(
                    "MySQL JDBC Driver not found. "
                    + "Check mysql-connector-j JAR.",
                    e
            );

        } catch (SQLException e) {

            throw new RuntimeException(
                    "MySQL connection failed: "
                    + e.getMessage(),
                    e
            );
        }
    }
}