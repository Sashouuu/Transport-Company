package org.example.repository;

import org.example.model.TransportCompany;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransportCompanyRepository {

    public int create(TransportCompany c) throws SQLException {
        String sql = "INSERT INTO companies(name, address) VALUES (?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    }

    public void update(TransportCompany c) throws SQLException {
        String sql = "UPDATE companies SET name=?, address=? WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getAddress());
            ps.setInt(3, c.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM companies WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public TransportCompany findById(int id) throws SQLException {
        String sql = "SELECT id, name, address FROM companies WHERE id=?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return null;
                return new TransportCompany(rs.getInt("id"), rs.getString("name"), rs.getString("address"));
            }
        }
    }

    public List<TransportCompany> findAll() throws SQLException {
        String sql = "SELECT id, name, address FROM companies";
        List<TransportCompany> list = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new TransportCompany(rs.getInt("id"), rs.getString("name"), rs.getString("address")));
            }
        }
        return list;
    }
}

