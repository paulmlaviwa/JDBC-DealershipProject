package com.pluralsight.dealership;

import static com.pluralsight.dealership.DataManager.myScanner;

public class Program {
    public static void main(String[] args) {
        DataManager.startConnection();
        while (true) {
            System.out.println("""
                    Welcome to PJ Dealership!
                    What would you like to do?
                    1) Search through Dealership
                    2) Add Cars to Dealership
                    3) Remove Cars from Dealership
                    4) Make a Sale
                    5) Lease a Car
                    0) Exit
                    """);
            String choice = myScanner.nextLine();
            switch (choice) {
                case "1":
                    DataManager.searchCar();
                    break;
                case "2":
                    DataManager.newCar();
                    break;
                case "3":
                    DataManager.deleteCar();
                    break;
                case "4":
                    SalesContractDAO.carSold();
                    break;
                case "5":
                    LeaseContractDAO.carLeased();
                    break;
                case "0":
                    myScanner.close();
                    System.exit(0);
            }
        }
    }
}