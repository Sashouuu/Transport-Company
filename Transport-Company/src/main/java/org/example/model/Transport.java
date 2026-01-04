package org.example.model;

import java.time.LocalDate;
import java.util.Objects;

public class Transport {
    private int id;

    private String startPoint;
    private String endPoint;
    private LocalDate departureDate;
    private LocalDate arrivalDate;

    private String cargoDescription; // какво се превозва
    private double cargoWeight;      // ако е стока (може да е 0 за хора)

    private double price;

    private Client client;
    private Driver driver;
    private Vehicle vehicle;

    public Transport() {}

    public Transport(int id,
                     String startPoint,
                     String endPoint,
                     LocalDate departureDate,
                     LocalDate arrivalDate,
                     String cargoDescription,
                     double cargoWeight,
                     double price,
                     Client client,
                     Driver driver,
                     Vehicle vehicle) {
        this.id = id;
        setStartPoint(startPoint);
        setEndPoint(endPoint);
        setDepartureDate(departureDate);
        setArrivalDate(arrivalDate);
        setCargoDescription(cargoDescription);
        setCargoWeight(cargoWeight);
        setPrice(price);
        setClient(client);
        setDriver(driver);
        setVehicle(vehicle);
    }

    public int getId() { return id; }
    public void setId(int id) {
        if (id < 0) throw new IllegalArgumentException("Transport id cannot be negative.");
        this.id = id;
    }

    public String getStartPoint() { return startPoint; }
    public void setStartPoint(String startPoint) {
        if (startPoint == null || startPoint.trim().isEmpty())
            throw new IllegalArgumentException("Start point is required.");
        this.startPoint = startPoint.trim();
    }

    public String getEndPoint() { return endPoint; }
    public void setEndPoint(String endPoint) {
        if (endPoint == null || endPoint.trim().isEmpty())
            throw new IllegalArgumentException("End point is required.");
        this.endPoint = endPoint.trim();
    }

    public LocalDate getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDate departureDate) {
        if (departureDate == null) throw new IllegalArgumentException("Departure date is required.");
        this.departureDate = departureDate;
    }

    public LocalDate getArrivalDate() { return arrivalDate; }
    public void setArrivalDate(LocalDate arrivalDate) {
        if (arrivalDate == null) throw new IllegalArgumentException("Arrival date is required.");
        // ако вече имаме departureDate, проверяваме
        if (this.departureDate != null && arrivalDate.isBefore(this.departureDate))
            throw new IllegalArgumentException("Arrival date cannot be before departure date.");
        this.arrivalDate = arrivalDate;
    }

    public String getCargoDescription() { return cargoDescription; }
    public void setCargoDescription(String cargoDescription) {
        if (cargoDescription == null || cargoDescription.trim().isEmpty())
            throw new IllegalArgumentException("Cargo description is required.");
        this.cargoDescription = cargoDescription.trim();
    }

    public double getCargoWeight() { return cargoWeight; }
    public void setCargoWeight(double cargoWeight) {
        if (cargoWeight < 0) throw new IllegalArgumentException("Cargo weight cannot be negative.");
        this.cargoWeight = cargoWeight;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        if (price <= 0) throw new IllegalArgumentException("Price must be positive.");
        this.price = price;
    }

    public Client getClient() { return client; }
    public void setClient(Client client) {
        if (client == null) throw new IllegalArgumentException("Client is required.");
        this.client = client;
    }

    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) {
        if (driver == null) throw new IllegalArgumentException("Driver is required.");
        this.driver = driver;
    }

    public Vehicle getVehicle() { return vehicle; }
    public void setVehicle(Vehicle vehicle) {
        if (vehicle == null) throw new IllegalArgumentException("Vehicle is required.");
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", startPoint='" + startPoint + '\'' +
                ", endPoint='" + endPoint + '\'' +
                ", departureDate=" + departureDate +
                ", arrivalDate=" + arrivalDate +
                ", cargoDescription='" + cargoDescription + '\'' +
                ", cargoWeight=" + cargoWeight +
                ", price=" + price +
                ", client=" + (client != null ? client.getName() : "null") +
                ", driver=" + (driver != null ? driver.getName() : "null") +
                ", vehicle=" + (vehicle != null ? vehicle.getRegistrationNumber() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Transport)) return false;
        Transport transport = (Transport) o;
        return id == transport.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

