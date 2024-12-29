// Abstract Class for Abstraction
public abstract class Vehicle {
    // Encapsulated Fields
    private final String vehicleId;
    private final String model;
    private double baseRentalRate;
    private boolean isAvailable;

    // Constructor with Validation
    public Vehicle(String vehicleId, String model, double baseRentalRate) {
        if (baseRentalRate < 0) throw new IllegalArgumentException("Base rental rate must be non-negative.");
        this.vehicleId = vehicleId;
        this.model = model;
        this.baseRentalRate = baseRentalRate;
        this.isAvailable = true;
    }

    // Getters and Setters
    public String getVehicleId() {
        return vehicleId;
    }

    public String getModel() {
        return model;
    }

    public double getBaseRentalRate() {
        return baseRentalRate;
    }

    public void setBaseRentalRate(double baseRentalRate) {
        if (baseRentalRate < 0) throw new IllegalArgumentException("Base rental rate must be non-negative.");
        this.baseRentalRate = baseRentalRate;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    // Abstract Methods for Polymorphism
    public abstract double calculateRentalCost(int days);

    public abstract boolean isAvailableForRental();
}

// Rentable Interface for Polymorphism
interface Rentable {
    void rent(Customer customer, int days);

    void returnVehicle();
}

// Concrete Subclasses Demonstrating Inheritance
class Car extends Vehicle implements Rentable {
    private static final double INSURANCE_RATE = 20.0;

    public Car(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        return (getBaseRentalRate() + INSURANCE_RATE) * days;
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }

    @Override
    public void rent(Customer customer, int days) {
        if (!isAvailableForRental()) throw new IllegalStateException("Car is not available for rental.");
        setAvailable(false);
        System.out.println("Car rented to " + customer.getName() + " for " + days + " days.");
    }

    @Override
    public void returnVehicle() {
        setAvailable(true);
        System.out.println("Car returned.");
    }
}

class Motorcycle extends Vehicle implements Rentable {
    private static final double HELMET_FEE = 5.0;

    public Motorcycle(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        return (getBaseRentalRate() + HELMET_FEE) * days;
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }

    @Override
    public void rent(Customer customer, int days) {
        if (!isAvailableForRental()) throw new IllegalStateException("Motorcycle is not available for rental.");
        setAvailable(false);
        System.out.println("Motorcycle rented to " + customer.getName() + " for " + days + " days.");
    }

    @Override
    public void returnVehicle() {
        setAvailable(true);
        System.out.println("Motorcycle returned.");
    }
}

class Truck extends Vehicle implements Rentable {
    private static final double LOAD_FEE = 50.0;

    public Truck(String vehicleId, String model, double baseRentalRate) {
        super(vehicleId, model, baseRentalRate);
    }

    @Override
    public double calculateRentalCost(int days) {
        return (getBaseRentalRate() + LOAD_FEE) * days;
    }

    @Override
    public boolean isAvailableForRental() {
        return isAvailable();
    }

    @Override
    public void rent(Customer customer, int days) {
        if (!isAvailableForRental()) throw new IllegalStateException("Truck is not available for rental.");
        setAvailable(false);
        System.out.println("Truck rented to " + customer.getName() + " for " + days + " days.");
    }

    @Override
    public void returnVehicle() {
        setAvailable(true);
        System.out.println("Truck returned.");
    }
}

// Customer Class for Composition
class Customer {
    private final String name;
    private final String licenseNumber;

    public Customer(String name, String licenseNumber) {
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public String getName() {
        return name;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
}

// RentalAgency Class for Management
class RentalAgency {
    private final List<Vehicle> fleet = new ArrayList<>();

    public void addVehicle(Vehicle vehicle) {
        fleet.add(vehicle);
    }

    public void listAvailableVehicles() {
        System.out.println("Available Vehicles:");
        for (Vehicle vehicle : fleet) {
            if (vehicle.isAvailable()) {
                System.out.println(vehicle.getModel());
            }
        }
    }

    public void rentVehicle(String vehicleId, Customer customer, int days) {
        for (Vehicle vehicle : fleet) {
            if (vehicle.getVehicleId().equals(vehicleId) && vehicle.isAvailable()) {
                if (vehicle instanceof Rentable rentable) {
                    rentable.rent(customer, days);
                    return;
                }
            }
        }
        System.out.println("Vehicle not available for rental.");
    }

    public void returnVehicle(String vehicleId) {
        for (Vehicle vehicle : fleet) {
            if (vehicle.getVehicleId().equals(vehicleId) && !vehicle.isAvailable()) {
                if (vehicle instanceof Rentable rentable) {
                    rentable.returnVehicle();
                    return;
                }
            }
        }
        System.out.println("Vehicle not found or already available.");
    }
}
