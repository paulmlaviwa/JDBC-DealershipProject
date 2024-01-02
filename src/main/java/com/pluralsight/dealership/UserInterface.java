package com.pluralsight.dealership;

import java.util.List;
import java.util.Scanner;

public class UserInterface {
    private Dealership dealership;
    private Scanner scanner;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        initialize();
    }

    private void initialize() {

        this.dealership = loadDealership();
    }

    private Dealership loadDealership() {
        DealershipFileManager dealershipFileManager = new DealershipFileManager();
        return dealershipFileManager.getDealership();
    }
    private void init() {

    }

    private void displayVehicles(List<Vehicle> vehicles) {
        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            for (Vehicle vehicle : vehicles) {
                System.out.println(vehicle);
            }
        }
    }

    private void processAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();
        displayVehicles(allVehicles);
    }

    public void display() {
        System.out.println("\u001B[1mWelcome to the Dealership Management System!\u001B[0m");
        init(); //Loading the dealership
        int choice;

        do {
            displayMenu();
            System.out.print("What would you like to do? ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    processGetByPriceRequest();
                    break;
                case 2:
                    processGetByMakeModelRequest();
                    break;
                case 3:
                    processGetByYearRequest();
                    break;
                case 4:
                    processGetByColorRequest();
                    break;
                case 5:
                    processGetByMileageRequest();
                    break;
                case 6:
                    processGetByVehicleTypeRequest();
                    break;
                case 7:
                    processGetAllVehiclesRequest();
                    break;
                case 8:
                    processAddVehicleRequest();
                    break;
                case 9:
                    processRemoveVehicleRequest();
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 0);

        scanner.close();
    }


    private void displayMenu() {
        System.out.println("1. Get vehicles by price range");
        System.out.println("2. Get vehicles by make and model");
        System.out.println("3. Get vehicles by year");
        System.out.println("4. Get vehicles by color");
        System.out.println("5. Get vehicles by mileage");
        System.out.println("6. Get vehicles by vehicle type");
        System.out.println("7. Get all vehicles");
        System.out.println("8. Add vehicle");
        System.out.println("9. Remove vehicle");
        System.out.println("0. Exit");
    }

    private void processGetByPriceRequest() {
        System.out.print("Enter minimum price: ");
        double minPrice = scanner.nextDouble();
        System.out.print("Enter maximum price: ");
        double maxPrice = scanner.nextDouble();

        List<Vehicle> vehicles = dealership.getVehiclesByPrice(minPrice, maxPrice);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in the specified price range.");
        } else {
            System.out.println("Vehicles in the specified price range:");
            displayVehicles(vehicles);
        }

    }
    private void processGetByMakeModelRequest() {
        System.out.print("Enter make: ");
        String make = scanner.next();
        System.out.print("Enter model: ");
        String model = scanner.next();

        List<Vehicle> vehicles = dealership.getVehiclesByMakeModel(make, model);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found for the specified make and model.");
        } else {
            System.out.println("Vehicles for the specified make and model:");
            displayVehicles(vehicles);
        }
    }
    private void processGetByYearRequest() {
        System.out.print("Enter minimum year: ");
        int minYear = scanner.nextInt();
        System.out.print("Enter maximum year: ");
        int maxYear = scanner.nextInt();

        List<Vehicle> vehicles = dealership.getVehiclesByYear(minYear, maxYear);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in the specified year range.");
        } else {
            System.out.println("Vehicles in the specified year range:");
            displayVehicles(vehicles);
        }

    }
    private void processGetByColorRequest() {
        System.out.print("Enter color: ");
        String color = scanner.next();

        List<Vehicle> vehicles = dealership.getVehiclesByColor(color);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found for the specified color.");
        } else {
            System.out.println("Vehicles for the specified color:");
            displayVehicles(vehicles);
        }
    }
    private void processGetByMileageRequest() {
        System.out.print("Enter minimum mileage: ");
        int minMileage = scanner.nextInt();
        System.out.print("Enter maximum mileage: ");
        int maxMileage = scanner.nextInt();

        List<Vehicle> vehicles = dealership.getVehiclesByMileage(minMileage, maxMileage);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found in the specified mileage range.");
        } else {
            System.out.println("Vehicles in the specified mileage range:");
            displayVehicles(vehicles);
        }
    }
    private void processGetByVehicleTypeRequest() {
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.next();

        List<Vehicle> vehicles = dealership.getVehiclesByType(vehicleType);

        if (vehicles.isEmpty()) {
            System.out.println("No vehicles found for the specified vehicle type.");
        } else {
            System.out.println("Vehicles for the specified vehicle type:");
            displayVehicles(vehicles);
        }
    }
    private void processGetAllVehiclesRequest() {
        List<Vehicle> allVehicles = dealership.getAllVehicles();

        if (allVehicles.isEmpty()) {
            System.out.println("No vehicles found.");
        } else {
            System.out.println("All Vehicles:");
            displayVehicles(allVehicles);
        }
    }

    private void processAddVehicleRequest() {
        System.out.print("Enter VIN: ");
        int vin = scanner.nextInt();
        System.out.print("Enter year: ");
        int year = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter make: ");
        String make = scanner.nextLine();
        System.out.print("Enter model: ");
        String model = scanner.nextLine();
        System.out.print("Enter vehicle type: ");
        String vehicleType = scanner.nextLine();
        System.out.print("Enter color: ");
        String color = scanner.nextLine();
        System.out.print("Enter odometer: ");
        int odometer = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();

        // Creating a new Vehicle object
        Vehicle newVehicle = new Vehicle(vin, year, make, model, vehicleType, color, odometer, price);

        // Adding the new vehicle to the dealership's inventory
        dealership.addVehicle(newVehicle);

        // Saving the updated dealership
        saveDealership();

        System.out.println("\u001B[32mDealership saved successfully!\u001B[0m");
    }

    private void saveDealership() {
        DealershipFileManager dealershipFileManager = new DealershipFileManager();
        dealershipFileManager.saveDealership(dealership);
        System.out.println("Dealership saved successfully!");
    }

    private void processRemoveVehicleRequest() {
        System.out.print("Enter VIN of the vehicle to remove: ");
        int vinToRemove = scanner.nextInt();

        // Finding the vehicle with the given VIN in the dealership's inventory
        Vehicle vehicleToRemove = findVehicleByVIN(vinToRemove);

        if (vehicleToRemove == null) {
            System.out.println("Vehicle with VIN " + vinToRemove + " not found.");
        } else {
            // Removing the vehicle from the dealership's inventory
            dealership.removeVehicle(vehicleToRemove);

            // Saving the updated dealership
            saveDealership();

            System.out.println("Vehicle removed successfully!");
        }
    }

    private Vehicle findVehicleByVIN(int vin) {
        for (Vehicle vehicle : dealership.getAllVehicles()) {
            if (vehicle.getVin() == vin) {
                return vehicle;
            }
        }
        return null; // Vehicle not found
    }

}