package org.example.service;

import org.example.model.Client;
import org.example.repository.ClientRepository;

import java.sql.SQLException;
import java.util.List;

public class ClientService {

    private final ClientRepository clientRepo;

    public ClientService(ClientRepository clientRepo) {
        this.clientRepo = clientRepo;
    }

    public int createClient(String name, String phone, boolean hasPaid) throws SQLException {
        Client c = new Client(0, name, phone, hasPaid);
        return clientRepo.create(c);
    }

    public void updateClient(int id, String name, String phone, boolean hasPaid) throws SQLException {
        Client c = new Client(id, name, phone, hasPaid);
        clientRepo.update(c);
    }

    public void deleteClient(int id) throws SQLException {
        clientRepo.delete(id);
    }

    public List<Client> getAllClients() throws SQLException {
        return clientRepo.findAll();
    }

    public void markClientPaid(int clientId, boolean paid) throws SQLException {
        clientRepo.markPaid(clientId, paid);
    }
}
