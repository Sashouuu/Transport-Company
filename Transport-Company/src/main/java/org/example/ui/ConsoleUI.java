package org.example.ui;

import org.example.model.Client;
import org.example.model.Driver;
import org.example.model.Transport;
import org.example.model.Vehicle;
import org.example.repository.*;
import org.example.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {

    private final Scanner sc = new Scanner(System.in);

    // repos
    private final TransportCompanyRepository companyRepo = new TransportCompanyRepository();
    private final ClientRepository clientRepo = new ClientRepository();
    private final EmployeeRepository employeeRepo = new EmployeeRepository();
    private final VehicleRepository vehicleRepo = new VehicleRepository();
    private final TransportRepository transportRepo = new TransportRepository();

    // services
    private final TransportCompanyService companyService = new TransportCompanyService(companyRepo);
    private final ClientService clientService = new ClientService(clientRepo);
    private final DriverService driverService = new DriverService(employeeRepo);
    private final VehicleService vehicleService = new VehicleService(vehicleRepo);
    private final TransportService transportService = new TransportService(transportRepo);
    private final ReportService reportService = new ReportService();

    public void start() {
        while (true) {
            Menu.printMainMenu();
            int choice = readInt();

            switch (choice) {
                case 1 -> System.out.println("Companies UI: ще го добавим след малко (CRUD).");
                case 2 -> clientsMenu();
                case 3 -> driversMenu();
                case 4 -> vehiclesMenu();

                case 5 -> transportsMenu();
                case 6 -> reportsMenu();
                case 0 -> {
                    System.out.println("Bye!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    // -------------------- TRANSPORTS --------------------

    private void transportsMenu() {
        while (true) {
            System.out.println("\n--- TRANSPORTS ---");
            System.out.println("1. List all transports");
            System.out.println("2. Create transport");
            System.out.println("3. Filter by destination (end point)");
            System.out.println("4. Delete transport");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = readInt();
            try {
                switch (choice) {
                    case 1 -> listAllTransports();
                    case 2 -> createTransportFlow();
                    case 3 -> filterTransportByDestinationFlow();
                    case 4 -> deleteTransportFlow();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private void listAllTransports() throws Exception {
        List<Transport> transports = transportService.getAllTransports();
        if (transports.isEmpty()) {
            System.out.println("No transports.");
            return;
        }
        transports.forEach(System.out::println);
    }

    private void createTransportFlow() throws Exception {
        // Need at least 1 client, 1 driver, 1 vehicle
        List<Client> clients = clientService.getAllClients();
        List<Driver> drivers = driverService.getAllDrivers();
        List<Vehicle> vehicles = vehicleService.getAllVehicles();

        if (clients.isEmpty() || drivers.isEmpty() || vehicles.isEmpty()) {
            System.out.println("You need at least 1 client, 1 driver, and 1 vehicle before creating a transport.");
            System.out.println("Add them first (Clients/Drivers/Vehicles menus).");
            return;
        }

        System.out.print("Start point: ");
        String start = readLine();

        System.out.print("End point (destination): ");
        String end = readLine();

        System.out.print("Departure date (YYYY-MM-DD): ");
        LocalDate dep = LocalDate.parse(readLine());

        System.out.print("Arrival date (YYYY-MM-DD): ");
        LocalDate arr = LocalDate.parse(readLine());

        System.out.print("Cargo description: ");
        String cargo = readLine();

        System.out.print("Cargo weight (0 for passengers): ");
        double weight = readDouble();

        System.out.print("Price: ");
        double price = readDouble();

        Client client = chooseClient(clients);
        Driver driver = chooseDriver(drivers);
        Vehicle vehicle = chooseVehicle(vehicles);

        int id = transportService.createTransport(
                start, end, dep, arr, cargo, weight, price,
                client, driver, vehicle
        );

        System.out.println("Transport created with id = " + id);
    }

    private void filterTransportByDestinationFlow() throws Exception {
        System.out.print("Destination contains: ");
        String dest = readLine();
        List<Transport> transports = transportService.filterTransportsByDestination(dest);
        if (transports.isEmpty()) {
            System.out.println("No transports found for destination filter: " + dest);
            return;
        }
        transports.forEach(System.out::println);
    }

    private void deleteTransportFlow() throws Exception {
        System.out.print("Transport id to delete: ");
        int id = readInt();
        transportService.deleteTransport(id);
        System.out.println("Deleted (if id existed).");
    }

    private Client chooseClient(List<Client> clients) {
        System.out.println("\nChoose client:");
        for (Client c : clients) {
            System.out.println(c.getId() + " - " + c.getName() + " (paid=" + c.isHasPaid() + ")");
        }
        System.out.print("Client id: ");
        int id = readInt();
        return clients.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid client id."));
    }

    private Driver chooseDriver(List<Driver> drivers) {
        System.out.println("\nChoose driver:");
        for (Driver d : drivers) {
            System.out.println(d.getId() + " - " + d.getName() + " (" + d.getQualification() + ")");
        }
        System.out.print("Driver id: ");
        int id = readInt();
        return drivers.stream()
                .filter(d -> d.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid driver id."));
    }

    private Vehicle chooseVehicle(List<Vehicle> vehicles) {
        System.out.println("\nChoose vehicle:");
        for (Vehicle v : vehicles) {
            System.out.println(v.getId() + " - " + v.getType() + " [" + v.getRegistrationNumber() + "]");
        }
        System.out.print("Vehicle id: ");
        int id = readInt();
        return vehicles.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid vehicle id."));
    }

    // -------------------- CLIENTS --------------------

    private void clientsMenu() {
        while (true) {
            System.out.println("\n--- CLIENTS ---");
            System.out.println("1. List all clients");
            System.out.println("2. Create client");
            System.out.println("3. Update client");
            System.out.println("4. Delete client");
            System.out.println("5. Mark client paid/unpaid");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = readInt();
            try {
                switch (choice) {
                    case 1 -> {
                        List<Client> clients = clientService.getAllClients();
                        if (clients.isEmpty()) System.out.println("No clients.");
                        else clients.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Name: ");
                        String name = readLine();
                        System.out.print("Phone: ");
                        String phone = readLine();
                        System.out.print("Has paid? (1=yes, 0=no): ");
                        boolean paid = readInt() == 1;

                        int id = clientService.createClient(name, phone, paid);
                        System.out.println("Client created with id = " + id);
                    }
                    case 3 -> {
                        System.out.print("Client id: ");
                        int id = readInt();
                        System.out.print("New name: ");
                        String name = readLine();
                        System.out.print("New phone: ");
                        String phone = readLine();
                        System.out.print("Has paid? (1=yes, 0=no): ");
                        boolean paid = readInt() == 1;

                        clientService.updateClient(id, name, phone, paid);
                        System.out.println("Client updated.");
                    }
                    case 4 -> {
                        System.out.print("Client id to delete: ");
                        int id = readInt();
                        clientService.deleteClient(id);
                        System.out.println("Client deleted (if id existed).");
                    }
                    case 5 -> {
                        System.out.print("Client id: ");
                        int id = readInt();
                        System.out.print("Set paid? (1=yes, 0=no): ");
                        boolean paid = readInt() == 1;
                        clientService.markClientPaid(id, paid);
                        System.out.println("Client payment status updated.");
                    }
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

// -------------------- DRIVERS --------------------

    private void driversMenu() {
        while (true) {
            System.out.println("\n--- DRIVERS ---");
            System.out.println("1. List all drivers");
            System.out.println("2. Create driver");
            System.out.println("3. Update driver");
            System.out.println("4. Delete driver");
            System.out.println("5. Sort by qualification (A-Z)");
            System.out.println("6. Sort by salary (DESC)");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = readInt();
            try {
                switch (choice) {
                    case 1 -> {
                        List<Driver> drivers = driverService.getAllDrivers();
                        if (drivers.isEmpty()) System.out.println("No drivers.");
                        else drivers.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Name: ");
                        String name = readLine();
                        System.out.print("Salary: ");
                        double salary = readDouble();
                        System.out.print("Qualification: ");
                        String q = readLine();

                        int id = driverService.createDriver(name, salary, q);
                        System.out.println("Driver created with id = " + id);
                    }
                    case 3 -> {
                        System.out.print("Driver id: ");
                        int id = readInt();
                        System.out.print("New name: ");
                        String name = readLine();
                        System.out.print("New salary: ");
                        double salary = readDouble();
                        System.out.print("New qualification: ");
                        String q = readLine();
                        System.out.print("Completed transports (number): ");
                        int completed = readInt();

                        driverService.updateDriver(id, name, salary, q, completed);
                        System.out.println("Driver updated.");
                    }
                    case 4 -> {
                        System.out.print("Driver id to delete: ");
                        int id = readInt();
                        driverService.deleteDriver(id);
                        System.out.println("Driver deleted (if id existed).");
                    }
                    case 5 -> {
                        List<Driver> drivers = driverService.getAllDrivers();
                        if (drivers.isEmpty()) {
                            System.out.println("No drivers.");
                            break;
                        }
                        driverService.sortDriversByQualification(drivers).forEach(System.out::println);
                    }
                    case 6 -> {
                        List<Driver> drivers = driverService.getAllDrivers();
                        if (drivers.isEmpty()) {
                            System.out.println("No drivers.");
                            break;
                        }
                        driverService.sortDriversBySalaryDesc(drivers).forEach(System.out::println);
                    }
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

// -------------------- VEHICLES --------------------

    private void vehiclesMenu() {
        while (true) {
            System.out.println("\n--- VEHICLES ---");
            System.out.println("1. List all vehicles");
            System.out.println("2. Create vehicle");
            System.out.println("3. Update vehicle");
            System.out.println("4. Delete vehicle");
            System.out.println("0. Back");
            System.out.print("Choose: ");

            int choice = readInt();
            try {
                switch (choice) {
                    case 1 -> {
                        List<Vehicle> vehicles = vehicleService.getAllVehicles();
                        if (vehicles.isEmpty()) System.out.println("No vehicles.");
                        else vehicles.forEach(System.out::println);
                    }
                    case 2 -> {
                        System.out.print("Type (Bus/Truck/Tanker...): ");
                        String type = readLine();
                        System.out.print("Registration (unique): ");
                        String reg = readLine();
                        System.out.print("Capacity (int): ");
                        int cap = readInt();

                        int id = vehicleService.createVehicle(type, reg, cap);
                        System.out.println("Vehicle created with id = " + id);
                    }
                    case 3 -> {
                        System.out.print("Vehicle id: ");
                        int id = readInt();
                        System.out.print("New type: ");
                        String type = readLine();
                        System.out.print("New registration (unique): ");
                        String reg = readLine();
                        System.out.print("New capacity: ");
                        int cap = readInt();

                        vehicleService.updateVehicle(id, type, reg, cap);
                        System.out.println("Vehicle updated.");
                    }
                    case 4 -> {
                        System.out.print("Vehicle id to delete: ");
                        int id = readInt();
                        vehicleService.deleteVehicle(id);
                        System.out.println("Vehicle deleted (if id existed).");
                    }
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }


    // -------------------- REPORTS --------------------

    private void reportsMenu() {
        while (true) {
            Menu.printReportsMenu();
            int choice = readInt();

            try {
                switch (choice) {
                    case 1 -> System.out.println("Total transports = " + reportService.getTotalTransportsCount());
                    case 2 -> System.out.println("Total revenue = " + reportService.getTotalTransportsSum());
                    case 3 -> printMapInt(reportService.getTransportsCountByDriver());
                    case 4 -> revenueForPeriodFlow();
                    case 5 -> revenueByDriverForPeriodFlow();
                    case 0 -> { return; }
                    default -> System.out.println("Invalid choice.");
                }
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private void revenueForPeriodFlow() {
        System.out.print("From date (YYYY-MM-DD): ");
        LocalDate from = LocalDate.parse(readLine());
        System.out.print("To date (YYYY-MM-DD): ");
        LocalDate to = LocalDate.parse(readLine());

        double revenue = reportService.getRevenueForPeriod(from, to);
        System.out.println("Revenue for period = " + revenue);
    }

    private void revenueByDriverForPeriodFlow() {
        System.out.print("From date (YYYY-MM-DD): ");
        LocalDate from = LocalDate.parse(readLine());
        System.out.print("To date (YYYY-MM-DD): ");
        LocalDate to = LocalDate.parse(readLine());

        Map<String, Double> map = reportService.getRevenueByDriverForPeriod(from, to);
        if (map.isEmpty()) {
            System.out.println("No data.");
            return;
        }
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    private void printMapInt(Map<String, Integer> map) {
        if (map.isEmpty()) {
            System.out.println("No data.");
            return;
        }
        map.forEach((k, v) -> System.out.println(k + " -> " + v));
    }

    // -------------------- INPUT HELPERS --------------------

    private int readInt() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Integer.parseInt(s);
            } catch (Exception e) {
                System.out.print("Enter a valid integer: ");
            }
        }
    }

    private double readDouble() {
        while (true) {
            String s = sc.nextLine().trim();
            try {
                return Double.parseDouble(s);
            } catch (Exception e) {
                System.out.print("Enter a valid number: ");
            }
        }
    }

    private String readLine() {
        String s = sc.nextLine();
        return s == null ? "" : s.trim();
    }
}
