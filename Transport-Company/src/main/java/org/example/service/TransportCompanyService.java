package org.example.service;

import org.example.model.TransportCompany;
import org.example.repository.TransportCompanyRepository;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

public class TransportCompanyService {

    private final TransportCompanyRepository companyRepo;

    public TransportCompanyService(TransportCompanyRepository companyRepo) {
        this.companyRepo = companyRepo;
    }

    public int createCompany(String name, String address) throws SQLException {
        TransportCompany c = new TransportCompany(0, name, address);
        return companyRepo.create(c);
    }

    public void updateCompany(int id, String name, String address) throws SQLException {
        TransportCompany c = new TransportCompany(id, name, address);
        companyRepo.update(c);
    }

    public void deleteCompany(int id) throws SQLException {
        companyRepo.delete(id);
    }

    public List<TransportCompany> getAllCompanies() throws SQLException {
        return companyRepo.findAll();
    }

    public List<TransportCompany> sortCompaniesByName(List<TransportCompany> companies) {
        companies.sort(Comparator.comparing(TransportCompany::getName, String.CASE_INSENSITIVE_ORDER));
        return companies;
    }

    // Приходи: ще се смятат от ReportService/TransportService (по период), затова тук не пазим revenue в таблица.
}
