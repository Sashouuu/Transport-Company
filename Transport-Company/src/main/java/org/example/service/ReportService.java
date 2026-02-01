package org.example.service;

import org.example.repository.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class ReportService {

    public int getTotalTransportsCount() {
        String sql = "SELECT COUNT(*) AS cnt FROM transports";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt("cnt");
        } catch (Exception e) {
            throw new RuntimeException("Report error: total transports count", e);
        }
    }

    public double getTotalTransportsSum() {
        String sql = "SELECT COALESCE(SUM(price), 0) AS total FROM transports";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getDouble("total");
        } catch (Exception e) {
            throw new RuntimeException("Report error: total transports sum", e);
        }
    }

    /** driverName -> completed transports */
    public Map<String, Integer> getTransportsCountByDriver() {
        String sql = """
            SELECT e.name AS driver_name, COUNT(t.id) AS cnt
            FROM employees e
            LEFT JOIN transports t ON t.driver_id = e.id
            GROUP BY e.id, e.name
            ORDER BY cnt DESC
            """;
        Map<String, Integer> result = new LinkedHashMap<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("driver_name"), rs.getInt("cnt"));
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Report error: transports count by driver", e);
        }
    }

    /** Total revenue in period [from, to] inclusive */
    public double getRevenueForPeriod(LocalDate from, LocalDate to) {
        String sql = """
            SELECT COALESCE(SUM(price), 0) AS total
            FROM transports
            WHERE departure_date >= ? AND departure_date <= ?
            """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(from));
            ps.setDate(2, java.sql.Date.valueOf(to));
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getDouble("total");
            }
        } catch (Exception e) {
            throw new RuntimeException("Report error: revenue for period", e);
        }
    }

    /** driverName -> revenue in period */
    public Map<String, Double> getRevenueByDriverForPeriod(LocalDate from, LocalDate to) {
        String sql = """
            SELECT e.name AS driver_name, COALESCE(SUM(t.price), 0) AS revenue
            FROM employees e
            LEFT JOIN transports t
              ON t.driver_id = e.id
             AND t.departure_date >= ?
             AND t.departure_date <= ?
            GROUP BY e.id, e.name
            ORDER BY revenue DESC
            """;

        Map<String, Double> result = new LinkedHashMap<>();
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, java.sql.Date.valueOf(from));
            ps.setDate(2, java.sql.Date.valueOf(to));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getString("driver_name"), rs.getDouble("revenue"));
                }
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Report error: revenue by driver for period", e);
        }
    }
}
