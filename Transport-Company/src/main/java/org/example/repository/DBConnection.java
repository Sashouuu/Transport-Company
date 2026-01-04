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

    private static final String URL =
            "jdbc:mysql://localhost:3306/transport_company?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";        // смени при нужда
    private static final String PASSWORD = "password"; // смени при нужда

    private DBConnection() {}

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            throw new RuntimeException("Cannot connect to MySQL: " + e.getMessage(), e);
        }
    }

    /**
     * Executes database.sql from resources.
     * Safe to call on app startup.
     */
    public static void initDatabase() {
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
            throw new RuntimeException("DB init failed: " + e.getMessage(), e);
        }
    }

    private static String readResource(String path) {
        try (InputStream is = DBConnection.class.getResourceAsStream(path)) {
            if (is == null)
                throw new IllegalStateException("Resource not found: " + path);

            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                return br.lines().collect(Collectors.joining("\n"));
            }
        } catch (Exception e) {
            throw new RuntimeException("Cannot read resource " + path, e);
        }
    }
}
