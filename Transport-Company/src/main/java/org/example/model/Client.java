package org.example.model;

import java.util.Objects;

public class Client {
    private int id;
    private String name;
    private String phone;
    private boolean hasPaid;

    public Client() {}

    public Client(int id, String name, String phone, boolean hasPaid) {
        this.id = id;
        setName(name);
        setPhone(phone);
        this.hasPaid = hasPaid;
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("Client id cannot be negative.");
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Client name is required.");
        this.name = name.trim();
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty())
            throw new IllegalArgumentException("Client phone is required.");
        this.phone = phone.trim();
    }

    public boolean isHasPaid() { return hasPaid; }
    public void setHasPaid(boolean hasPaid) { this.hasPaid = hasPaid; }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", hasPaid=" + hasPaid +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return id == client.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
