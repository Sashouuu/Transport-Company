package org.example;

import org.example.repository.DBConnection;
import org.example.ui.ConsoleUI;

public class Main {
    public static void main(String[] args) {
        DBConnection.initDatabase();
        new ConsoleUI().start();
    }
}
