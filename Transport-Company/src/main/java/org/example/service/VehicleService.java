package org.example.service;

import org.example.model.Vehicle;
import org.example.repository.VehicleRepository;

import java.sql.SQLException;
import java.util.List;

public class VehicleService {

    private final VehicleRepository vehicleRepo;

    public VehicleService(VehicleRepository vehicleRepo) {
        this.vehicleRepo = vehicleRepo;
    }

    public int createVehicle(String type, String registration, int capacity) throws SQLException {
        Vehicle v = new Vehicle(0, type, registration, capacity);
        return vehicleRepo.create(v);
    }

    public void updateVehicle(int id, String type, String registration, int capacity) throws SQLException {
        Vehicle v = new Vehicle(id, type, registration, capacity);
        vehicleRepo.update(v);
    }

    public void deleteVehicle(int id) throws SQLException {
        vehicleRepo.delete(id);
    }

    public List<Vehicle> getAllVehicles() throws SQLException {
        return vehicleRepo.findAll();
    }
}
