package org.example;

import org.example.repository.DBConnection;
import org.example.service.ReportService;

import java.time.LocalDate;

public class TestService {
    public static void main(String[] args) {
        DBConnection.initDatabase();

        ReportService reports = new ReportService();

        System.out.println("Total transports = " + reports.getTotalTransportsCount());
        System.out.println("Total revenue = " + reports.getTotalTransportsSum());
        System.out.println("Revenue this month = " +
                reports.getRevenueForPeriod(
                        LocalDate.now().withDayOfMonth(1),
                        LocalDate.now()
                ));
    }
}
