package org.example.ui;

public class Menu {

    public static void printMainMenu() {
        System.out.println("\n=== TRANSPORT COMPANY APP ===");
        System.out.println("1. Clients (CRUD + Mark Paid)");
        System.out.println("2. Drivers (CRUD + Sorting)");
        System.out.println("3. Vehicles (CRUD)");
        System.out.println("4. Transports (Create/List/Filter/Delete)");
        System.out.println("5. Reports");
        System.out.println("0. Exit");
        System.out.print("Choose: ");
    }

    public static void printReportsMenu() {
        System.out.println("\n--- REPORTS ---");
        System.out.println("1. Total transports count");
        System.out.println("2. Total revenue (sum of transports)");
        System.out.println("3. Transports count by driver");
        System.out.println("4. Revenue for period");
        System.out.println("5. Revenue by driver for period");
        System.out.println("0. Back");
        System.out.print("Choose: ");
    }
}
