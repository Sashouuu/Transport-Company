package org.example.repository;

import org.example.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRepository {

    public int create(Vehicle v) throws SQLException {
        String sql = "INSERT INTO vehicles(type, registration, capacity) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, v.getType());
            ps.setString(2, v.getRegistrationNumber());
            ps.setInt(3, v.getCapacity());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public void update(Vehicle v) throws SQLException {
        String sql = "UPDATE vehicles SET type=?, registration=?, capacity=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getType());
            ps.setString(2, v.getRegistrationNumber());
            ps.setInt(3, v.getCapacity());
            ps.setInt(4, v.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM vehicles WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Vehicle findById(int id) throws SQLException {
        String sql = "SELECT id, type, registration, capacity FROM vehicles WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new Vehicle(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("registration"),
                        rs.getInt("capacity")
                );
            }
        }
    }

    public List<Vehicle> findAll() throws SQLException {
        String sql = "SELECT id, type, registration, capacity FROM vehicles";
        List<Vehicle> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Vehicle(
                        rs.getInt("id"),
                        rs.getString("type"),
                        rs.getString("registration"),
                        rs.getInt("capacity")
                ));
            }
        }
        return list;
    }
}

