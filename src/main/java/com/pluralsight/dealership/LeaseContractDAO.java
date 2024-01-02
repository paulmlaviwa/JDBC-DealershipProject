package com.pluralsight.dealership;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.pluralsight.dealership.DataManager.dataSource;
import static com.pluralsight.dealership.DataManager.myScanner;

public class LeaseContractDAO {
    public static void carLeased() {
        DataManager.startConnection();
        System.out.println("Which car would you like to lease? Enter a Vin.");
        String userVin = myScanner.nextLine();
        try (Connection conn = dataSource.getConnection();
             PreparedStatement queryEx = conn.prepareStatement("SELECT * FROM Vehicles WHERE vin = ?");
             PreparedStatement updateEx = conn.prepareStatement("UPDATE Vehicles SET lease = 1 WHERE vin = ?")) {
            updateEx.setString(1, userVin);
            queryEx.setString(1, userVin);
            int rowsAffected = updateEx.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Here is the updated table.");
                System.out.println("Update Successful");
            } else {
                System.out.println("Nothing was updated. Verify the Vin.");
            }
            try (ResultSet rs = queryEx.executeQuery()) {
                while (rs.next()) {
                    System.out.println("VIN: " + rs.getString("vin"));
                    System.out.println("Make: " + rs.getString("Make"));
                    System.out.println("Model: " + rs.getString("Model"));
                    System.out.println("Price: " + rs.getString("Price"));
                    System.out.println("Color: " + rs.getString("Color"));
                    System.out.println("Lease: " + rs.getString("Lease"));
                    System.out.println("----------------------------");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

