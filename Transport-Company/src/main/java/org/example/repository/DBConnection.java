package org.example.repository;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.stream.Collectors;

public class DBConnection {

    private static final String HOST_URL =
            "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String DB_URL =
            "jdbc:mysql://localhost:3306/transport_company?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

    private static final String USER = "root";       // смени ако е нужно
    private static final String PASSWORD = "Pewguin$123"; // смени ако е нужно

    private DBConnection() {}

    /** Основен entry point – вика се при стартиране */
    public static void initDatabase() {
        createDatabaseIfNotExists();
        createTables();
    }

    /** Връзка към конкретната база */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Cannot connect to database", e);
        }
    }

    /** Създава базата, ако я няма */
    private static void createDatabaseIfNotExists() {
        try (Connection conn = DriverManager.getConnection(HOST_URL, USER, PASSWORD);
             Statement st = conn.createStatement()) {

            st.execute("""
                CREATE DATABASE IF NOT EXISTS transport_company
                CHARACTER SET utf8mb4
                COLLATE utf8mb4_unicode_ci
            """);

        } catch (Exception e) {
            throw new RuntimeException("Cannot create database", e);
        }
    }

    /** Създава таблиците от database.sql */
    private static void createTables() {
        try (Connection conn = getConnection();
             Statement st = conn.createStatement()) {

            String sql = readResource("/database.sql");

            for (String statement : sql.split(";")) {
                String s = statement.trim();
                if (!s.isEmpty()) {
                    st.execute(s);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("Cannot create tables", e);
        }
    }

    /** Чете database.sql от resources */
    private static String readResource(String path) {
        try (InputStream is = DBConnection.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IllegalStateException("Resource not found: " + path);
            }

            try (BufferedReader br =
                         new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return br.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot read SQL file", e);
        }
    }
}
