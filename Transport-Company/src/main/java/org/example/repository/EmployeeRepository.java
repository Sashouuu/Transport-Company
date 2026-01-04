package org.example.repository;

import org.example.model.Driver;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository {

    public int createDriver(Driver d) throws SQLException {
        String sql = "INSERT INTO employees(name, salary, qualification, completed_transports) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, d.getName());
            ps.setDouble(2, d.getSalary());
            ps.setString(3, d.getQualification());
            ps.setInt(4, d.getCompletedTransports());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public void updateDriver(Driver d) throws SQLException {
        String sql = "UPDATE employees SET name=?, salary=?, qualification=?, completed_transports=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, d.getName());
            ps.setDouble(2, d.getSalary());
            ps.setString(3, d.getQualification());
            ps.setInt(4, d.getCompletedTransports());
            ps.setInt(5, d.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Driver findDriverById(int id) throws SQLException {
        String sql = "SELECT id, name, salary, qualification, completed_transports FROM employees WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;

                Driver d = new Driver(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary"),
                        rs.getString("qualification"));
                d.setCompletedTransports(rs.getInt("completed_transports"));
                return d;
            }
        }
    }

    public List<Driver> findAllDrivers() throws SQLException {
        String sql = "SELECT id, name, salary, qualification, completed_transports FROM employees";
        List<Driver> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Driver d = new Driver(rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("salary"),
                        rs.getString("qualification"));
                d.setCompletedTransports(rs.getInt("completed_transports"));
                list.add(d);
            }
        }
        return list;
    }

    public void incrementCompletedTransports(int driverId) throws SQLException {
        String sql = "UPDATE employees SET completed_transports = completed_transports + 1 WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, driverId);
            ps.executeUpdate();
        }
    }
}
