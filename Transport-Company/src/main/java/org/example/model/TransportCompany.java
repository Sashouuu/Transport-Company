package org.example.model;

import java.util.Objects;

public class TransportCompany {
    private int id;
    private String name;
    private String address;

    public TransportCompany() {}

    public TransportCompany(int id, String name, String address) {
        this.id = id;
        setName(name);
        setAddress(address);
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("Company id cannot be negative.");
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Company name is required.");
        this.name = name.trim();
    }

    public String getAddress() { return address; }
    public void setAddress(String address) {
        if (address == null || address.trim().isEmpty())
            throw new IllegalArgumentException("Company address is required.");
        this.address = address.trim();
    }

    @Override
    public String toString() {
        return "TransportCompany{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransportCompany)) return false;
        TransportCompany that = (TransportCompany) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
