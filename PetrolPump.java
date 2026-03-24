package demo;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Desktop;
import java.io.File;
import java.util.Scanner;

public class PetrolPump {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice;
        float amount, quantity, price;
        float petrolPrice = 102;
        float petrolWithOilPrice = 110;
        float dieselPrice = 94;
        String fileName = "fuel_history.txt";

 
        try {
            FileWriter fw = new FileWriter(fileName, false);
            fw.write("");
            fw.close();
        } catch (IOException e) {
            System.out.println("Error initializing file: " + e.getMessage());
        }

        do {
            System.out.println("\n--- Fuel Station Menu ---");
            System.out.println("1. Petrol");
            System.out.println("2. Petrol with Oil");
            System.out.println("3. Diesel");
            System.out.println("4. Current Day History");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("You selected Petrol.");
                    System.out.print("Enter the amount (Rs): ");
                    amount = scanner.nextFloat();
                    quantity = amount / petrolPrice;
                    System.out.printf("Dispensed: %.2f Liters\n", quantity);
                    writeToFile(fileName, "Petrol", amount, quantity);
                    break;

                case 2:
                    System.out.println("You selected Petrol with Oil.");
                    System.out.print("Enter the amount (Rs): ");
                    amount = scanner.nextFloat();
                    quantity = amount / petrolWithOilPrice;
                    System.out.printf("Dispensed: %.2f Liters\n", quantity);
                    writeToFile(fileName, "Petrol with Oil", amount, quantity);
                    break;

                case 3:
                    System.out.println("You selected Diesel.");
                    System.out.print("Enter the amount (Rs): ");
                    amount = scanner.nextFloat();
                    quantity = amount / dieselPrice;
                    System.out.printf("Dispensed: %.2f Liters\n", quantity);
                    writeToFile(fileName, "Diesel", amount, quantity);
                    break;

                case 4:
                	System.out.println("\n--- Current Day History ---");
                    displayHistory(fileName);
                    try {
                        File file = new File(fileName);
                        if (file.exists()) {
                            Desktop desktop = Desktop.getDesktop();
                            desktop.open(file); 
                        }
                    } catch (IOException e) {
                        System.out.println("Unable to open file: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Exiting. Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        scanner.close();
    }


    public static void writeToFile(String fileName, String fuelType, float amount, float quantity) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("Fuel Type: " + fuelType + ", Amount: Rs" + amount + ", Quantity: " + String.format("%.2f", quantity) + " Liters\n");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

  
    public static void displayHistory(String fileName) {
        try {
            File file = new File(fileName);
            Scanner fileScanner = new Scanner(file);
            float totalAmount = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                System.out.println(line);
           
                String[] parts = line.split(", ");
                for (String part : parts) {
                    if (part.startsWith("Amount")) {
                        totalAmount += Float.parseFloat(part.replaceAll("[^0-9.]", ""));
                    }
                }
            }
            System.out.printf("Total Amount for the Day: Rs%.2f\n", totalAmount);
            fileScanner.close();
        } catch (IOException e) {
            System.out.println("Error reading history: " + e.getMessage());
        }
    }
}
