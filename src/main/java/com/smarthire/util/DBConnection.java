package com.smarthire.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String getEnv(String name, String defaultValue) {

        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value;
    }

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            String host = getEnv("DB_HOST", "localhost");
            String port = getEnv("DB_PORT", "3306");
            String database = getEnv("DB_NAME", "smarthire");
            String user = getEnv("DB_USER", "root");
            String password = System.getenv("DB_PASSWORD");

            String url =
                    "jdbc:mysql://" + host + ":" + port + "/" + database
                    + "?sslMode=REQUIRED"
                    + "&serverTimezone=UTC";

            Connection connection =
                    DriverManager.getConnection(
                            url,
                            user,
                            password
                    );

            System.out.println(
                    "SmartHire Database Connected Successfully!"
            );

            return connection;

        } catch (ClassNotFoundException e) {

            throw new RuntimeException(
                    "MySQL JDBC Driver not found.",
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