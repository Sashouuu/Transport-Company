package org.example.service;

import org.example.model.Driver;
import org.example.repository.EmployeeRepository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class DriverService {

    private final EmployeeRepository employeeRepo;

    public DriverService(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public int createDriver(String name, double salary, String qualification) throws SQLException {
        Driver d = new Driver(0, name, salary, qualification);
        return employeeRepo.createDriver(d);
    }

    public void updateDriver(int id, String name, double salary, String qualification, int completedTransports) throws SQLException {
        Driver d = new Driver(id, name, salary, qualification);
        d.setCompletedTransports(completedTransports);
        employeeRepo.updateDriver(d);
    }

    public void deleteDriver(int id) throws SQLException {
        employeeRepo.delete(id);
    }

    public List<Driver> getAllDrivers() throws SQLException {
        return employeeRepo.findAllDrivers();
    }

    public List<Driver> sortDriversByQualification(List<Driver> drivers) {
        drivers.sort(Comparator.comparing(Driver::getQualification, String.CASE_INSENSITIVE_ORDER));
        return drivers;
    }

    public List<Driver> sortDriversBySalaryDesc(List<Driver> drivers) {
        drivers.sort(Comparator.comparingDouble(Driver::getSalary).reversed());
        return drivers;
    }
}
