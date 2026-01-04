package org.example.model;

public class Driver extends Employee {
    private String qualification; // напр. "Dangerous goods", "Bus > 12"
    private int completedTransports;

    public Driver() {}

    public Driver(int id, String name, double salary, String qualification) {
        super(id, name, salary);
        setQualification(qualification);
        this.completedTransports = 0;
    }

    public String getQualification() { return qualification; }
    public void setQualification(String qualification) {
        if (qualification == null || qualification.trim().isEmpty())
            throw new IllegalArgumentException("Driver qualification is required.");
        this.qualification = qualification.trim();
    }

    public int getCompletedTransports() { return completedTransports; }
    public void setCompletedTransports(int completedTransports) {
        if (completedTransports < 0) throw new IllegalArgumentException("Completed transports cannot be negative.");
        this.completedTransports = completedTransports;
    }

    public void incrementCompletedTransports() {
        this.completedTransports++;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                ", qualification='" + qualification + '\'' +
                ", completedTransports=" + completedTransports +
                '}';
    }
}
