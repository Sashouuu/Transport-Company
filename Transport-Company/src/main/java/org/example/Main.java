package org.example;

import org.example.repository.DBConnection;

public class Main {
    public static void main(String[] args) {
        DBConnection.initDatabase();
        System.out.println("Database initialized successfully!");
    }
}
