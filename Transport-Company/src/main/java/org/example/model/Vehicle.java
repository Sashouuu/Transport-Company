package org.example.model;

import java.util.Objects;

public class Vehicle {
    private int id;
    private String type;               // Bus, Truck, Tanker
    private String registrationNumber; // напр. CA1234AB
    private int capacity;              // seats или kg (в зависимост от тип)

    public Vehicle() {}

    public Vehicle(int id, String type, String registrationNumber, int capacity) {
        this.id = id;
        setType(type);
        setRegistrationNumber(registrationNumber);
        setCapacity(capacity);
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("Vehicle id cannot be negative.");
        this.id = id;
    }

    public String getType() { return type; }
    public void setType(String type) {
        if (type == null || type.trim().isEmpty())
            throw new IllegalArgumentException("Vehicle type is required.");
        this.type = type.trim();
    }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) {
        if (registrationNumber == null || registrationNumber.trim().isEmpty())
            throw new IllegalArgumentException("Registration number is required.");
        this.registrationNumber = registrationNumber.trim();
    }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) {
        if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive.");
        this.capacity = capacity;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle)) return false;
        Vehicle vehicle = (Vehicle) o;
        return id == vehicle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
