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

        String host = getEnv("DB_HOST", "localhost");
        String port = getEnv("DB_PORT", "3306");
        String database = getEnv("DB_NAME", "smarthire");
        String user = getEnv("DB_USER", "root");

        String password = System.getenv("DB_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new RuntimeException(
                "DB_PASSWORD environment variable is not set."
            );
        }

        String url =
                "jdbc:mysql://" + host + ":" + port + "/" + database
                + "?useSSL=false&allowPublicKeyRetrieval=true"
                + "&serverTimezone=UTC";

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

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