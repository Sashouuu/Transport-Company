package org.example.service;

import org.example.model.*;
import org.example.repository.TransportRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class TransportService {

    private final TransportRepository transportRepo;

    public TransportService(TransportRepository transportRepo) {
        this.transportRepo = transportRepo;
    }

    public int createTransport(
            String startPoint,
            String endPoint,
            LocalDate departure,
            LocalDate arrival,
            String cargoDescription,
            double cargoWeight,
            double price,
            Client client,
            Driver driver,
            Vehicle vehicle
    ) throws SQLException {

        // Бизнес-валидации (можеш да ги разширяваш)
        if (arrival.isBefore(departure)) {
            throw new IllegalArgumentException("Arrival date cannot be before departure date.");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Price must be positive.");
        }
        if (cargoWeight < 0) {
            throw new IllegalArgumentException("Cargo weight cannot be negative.");
        }

        Transport t = new Transport(
                0, startPoint, endPoint, departure, arrival,
                cargoDescription, cargoWeight, price,
                client, driver, vehicle
        );

        return transportRepo.create(t);
    }

    public void deleteTransport(int id) throws SQLException {
        transportRepo.delete(id);
    }

    public List<Transport> getAllTransports() throws SQLException {
        return transportRepo.findAllWithDetails();
    }

    public List<Transport> filterTransportsByDestination(String destination) throws SQLException {
        return transportRepo.findByDestination(destination);
    }
}
