package com.pluralsight.dealership;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class DataManager {
    static PreparedStatement pStmt = null;
    static Connection conn = null;
    static BasicDataSource dataSource = new BasicDataSource();
    static Scanner myScanner = new Scanner(System.in);

    public static void startConnection() {
        dataSource.setUrl("jdbc:mysql://localhost:3306/cardealership");
        dataSource.setUsername("root");
        dataSource.setPassword("**********");
    }

    public static void searchCar() {
        String userChoice = "";
        while(!userChoice.equalsIgnoreCase("0")) {
            System.out.println("""
                    Select your search option:
                    1) By Price Range
                    2) By Make
                    3) By Model
                    4) By Color
                    5) By Mileage Range
                    6) By Type
                    0) Back to Main Screen
                    """);
            userChoice = myScanner.nextLine().trim();
            switch (userChoice) {
                case "1":
                    byPriceRange();
                    break;
                case "2":
                    byMake();
                    break;
                case "3":
                    byModel();
                    break;
                case "4":
                    byColor();
                    break;
                case "5":
                    byMileageRange();
                    break;
                case "6":
                    byType();
                    break;
                case "0":
                    break;
                default:
                    System.out.println("Please choose a valid option.");
            }
        }
    }

    public static void newCar() {
        System.out.println("Hello User, please insert the following data: vin,year,make,model,type,color,mileage,price,sold.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE vin = ?");
             PreparedStatement pStmt = conn.prepareStatement("INSERT INTO Vehicles(vin,year,make,model,type,color,mileage,price,sold) VALUES (?,?,?,?,?,?,?,?,?)")) {
            pStmt.setString(1, inputSplit[0]);
            pStmt.setString(2, inputSplit[1]);
            pStmt.setString(3, inputSplit[2]);
            pStmt.setString(4, inputSplit[3]);
            pStmt.setString(5, inputSplit[4]);
            pStmt.setString(6, inputSplit[5]);
            pStmt.setString(7, inputSplit[6]);
            pStmt.setString(8, inputSplit[7]);
            pStmt.setString(9, inputSplit[8]);
            int rows = pStmt.executeUpdate();
            queryEx.setString(1, inputSplit[0]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("make"));
                    System.out.println("Color: " + rs.getString("color"));
                    System.out.println("----------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void deleteCar() {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles")) {
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("----------------------------");
                }
            }
            System.out.println("Please insert the Vin of the Vehicle that you want to delete.");
            String userInput = myScanner.nextLine().trim();
            try (PreparedStatement deleteEx = conn.prepareStatement("DELETE FROM Vehicles WHERE vin = ?")) {
                deleteEx.setString(1, userInput);
                int rowsDeleted = deleteEx.executeUpdate();
                if (rowsDeleted > 0) {
                    try (ResultSet rs = queryEx.executeQuery()) {
                        while (rs.next()) {
                            System.out.println("Vin: " + rs.getString("vin"));
                            System.out.println("Make: " + rs.getString("Make"));
                            System.out.println("Model: " + rs.getString("Model"));
                            System.out.println("----------------------------");
                            System.out.println("----------------------------");
                        }
                        System.out.println("Here is the updated table.");
                        System.out.println("Delete Successful");
                    }
                } else {
                    System.out.println("Nothing was deleted. Verify the Shipper ID.");
                }


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void byPriceRange() {
        System.out.println("Hello! please insert the following data, Min Price and Max Price.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE price BETWEEN ? AND ?")) {
            queryEx.setString(1, inputSplit[0]);
            queryEx.setString(2, inputSplit[1]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Price: " + rs.getString("Price"));
                    System.out.println("----------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void byMake() {
        System.out.println("Hello! please insert the following data you want to search for, Make.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE make = ?")) {
            queryEx.setString(1, inputSplit[0]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Price: " + rs.getString("Price"));
                    System.out.println("----------------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void byModel() {
        System.out.println("Hello! please insert the following data you want to search for, Model.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE model = ?")) {
            queryEx.setString(1, inputSplit[0]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Price: " + rs.getString("Price"));
                    System.out.println("----------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void byYearRange() {
        System.out.println("Hello! please insert the following data, Min Year and Max Year.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE year BETWEEN ? AND ?")) {
            queryEx.setString(1, inputSplit[0]);
            queryEx.setString(2, inputSplit[1]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Year: " + rs.getString("Year"));
                    System.out.println("----------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void byColor() {
        System.out.println("Hello! please insert the following data you want to search for, Color.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE model = ?")) {
            queryEx.setString(1, inputSplit[0]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Price: " + rs.getString("Price"));
                    System.out.println("Color: " + rs.getString("Color"));
                    System.out.println("----------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void byMileageRange() {
        System.out.println("Hello! please insert the following data, Min Mileage and Max Mileage.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE mileage BETWEEN ? AND ?")) {
            queryEx.setString(1, inputSplit[0]);
            queryEx.setString(2, inputSplit[1]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Year: " + rs.getString("Year"));
                    System.out.println("Mileage: " + rs.getString("Mileage"));
                    System.out.println("----------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void byType() {
        System.out.println("Hello! please insert the following data you want to search for, Type.");
        String userInput = myScanner.nextLine().trim();
        String[] inputSplit = userInput.split(" ");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE type = ?")) {
            queryEx.setString(1, inputSplit[0]);
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("Vin: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Price: " + rs.getString("Price"));
                    System.out.println("Color: " + rs.getString("Color"));
                    System.out.println("Type: " + rs.getString("Type"));
                    System.out.println("----------------------------");
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}