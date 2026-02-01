package org.example;

import org.example.model.Client;
import org.example.model.Driver;
import org.example.model.Vehicle;
import org.example.repository.*;
import org.example.service.ReportService;
import org.example.service.TransportService;

import java.time.LocalDate;

public class SeedDataTest {
    public static void main(String[] args) throws Exception {
        DBConnection.initDatabase();

        // repositories
        ClientRepository clientRepo = new ClientRepository();
        EmployeeRepository employeeRepo = new EmployeeRepository();
        VehicleRepository vehicleRepo = new VehicleRepository();
        TransportRepository transportRepo = new TransportRepository();

        // 1) create client
        int clientId = clientRepo.create(new Client(0, "Ivan Petrov", "0888123456", true));
        Client client = clientRepo.findById(clientId);

        // 2) create driver
        int driverId = employeeRepo.createDriver(new Driver(0, "Georgi Georgiev", 2200, "Bus > 12"));
        Driver driver = employeeRepo.findDriverById(driverId);

        // 3) create vehicle
        int vehicleId = vehicleRepo.create(new Vehicle(0, "Bus", "CA1234AB", 50));
        Vehicle vehicle = vehicleRepo.findById(vehicleId);

        // 4) create transport (via service)
        TransportService transportService = new TransportService(transportRepo);
        int transportId = transportService.createTransport(
                "Sofia",
                "Plovdiv",
                LocalDate.now(),
                LocalDate.now().plusDays(1),
                "Passengers",
                0,
                500,
                client,
                driver,
                vehicle
        );

        System.out.println("Inserted transport id = " + transportId);

        // reports
        ReportService reports = new ReportService();
        System.out.println("Total transports = " + reports.getTotalTransportsCount());
        System.out.println("Total revenue = " + reports.getTotalTransportsSum());
        System.out.println("Revenue this month = " +
                reports.getRevenueForPeriod(LocalDate.now().withDayOfMonth(1), LocalDate.now()));
        System.out.println("Transports by driver = " + reports.getTransportsCountByDriver());
    }
}
