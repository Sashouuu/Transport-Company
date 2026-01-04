package org.example;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestMySQL {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/transport_company?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String user = "root";
        String pass = "Pewguin$123";

        try (Connection c = DriverManager.getConnection(url, user, pass)) {
            System.out.println("CONNECTED OK");
        } catch (Exception e) {
            e.printStackTrace(); // <-- това ще покаже истинската причина
        }
    }
}
