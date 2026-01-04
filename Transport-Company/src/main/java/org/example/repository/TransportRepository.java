package org.example.repository;

import org.example.model.*;
import org.example.model.Driver;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransportRepository {

    private final ClientRepository clientRepo = new ClientRepository();
    private final EmployeeRepository employeeRepo = new EmployeeRepository();
    private final VehicleRepository vehicleRepo = new VehicleRepository();

    public int create(Transport t) throws SQLException {
        String sql = """
            INSERT INTO transports
            (start_point, end_point, departure_date, arrival_date, cargo, weight, price, client_id, driver_id, vehicle_id)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, t.getStartPoint());
            ps.setString(2, t.getEndPoint());
            ps.setString(3, t.getDepartureDate().toString());
            ps.setString(4, t.getArrivalDate().toString());
            ps.setString(5, t.getCargoDescription());
            ps.setDouble(6, t.getCargoWeight());
            ps.setDouble(7, t.getPrice());
            ps.setInt(8, t.getClient().getId());
            ps.setInt(9, t.getDriver().getId());
            ps.setInt(10, t.getVehicle().getId());

            ps.executeUpdate();

            // увеличаваме броя превози на шофьора
            employeeRepo.incrementCompletedTransports(t.getDriver().getId());

            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM transports WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public List<Transport> findAllWithDetails() throws SQLException {
        String sql = "SELECT * FROM transports";
        List<Transport> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Client client = clientRepo.findById(rs.getInt("client_id"));
                Driver driver = employeeRepo.findDriverById(rs.getInt("driver_id"));
                Vehicle vehicle = vehicleRepo.findById(rs.getInt("vehicle_id"));

                Transport t = new Transport(
                        rs.getInt("id"),
                        rs.getString("start_point"),
                        rs.getString("end_point"),
                        LocalDate.parse(rs.getString("departure_date")),
                        LocalDate.parse(rs.getString("arrival_date")),
                        rs.getString("cargo"),
                        rs.getDouble("weight"),
                        rs.getDouble("price"),
                        client,
                        driver,
                        vehicle
                );

                list.add(t);
            }
        }
        return list;
    }

    public List<Transport> findByDestination(String endPoint) throws SQLException {
        String sql = "SELECT * FROM transports WHERE end_point LIKE ?";
        List<Transport> list = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + endPoint + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Client client = clientRepo.findById(rs.getInt("client_id"));
                    Driver driver = employeeRepo.findDriverById(rs.getInt("driver_id"));
                    Vehicle vehicle = vehicleRepo.findById(rs.getInt("vehicle_id"));

                    list.add(new Transport(
                            rs.getInt("id"),
                            rs.getString("start_point"),
                            rs.getString("end_point"),
                            LocalDate.parse(rs.getString("departure_date")),
                            LocalDate.parse(rs.getString("arrival_date")),
                            rs.getString("cargo"),
                            rs.getDouble("weight"),
                            rs.getDouble("price"),
                            client,
                            driver,
                            vehicle
                    ));
                }
            }
        }
        return list;
    }
}
