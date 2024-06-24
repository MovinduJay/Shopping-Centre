package ShoppingManager;

import GUI.Start;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    private static WestminsterShoppingManager manager;
    public static WestminsterShoppingManager getManagerInstance() {
        return manager;
    }


    public static void main(String[] args) throws IOException {
        // Create an instance of WestminsterShoppingManager
        manager = new WestminsterShoppingManager();
        int choice;
        Scanner input = new Scanner(System.in);

        // Displaying the main menu
        System.out.println("\n-------------------------------------");
        System.out.println("      Westminster Shopping Centre     ");
        System.out.println(" -------------------------------------  ");
        System.out.println("       1. Manager Console               ");
        System.out.println("       2. Shopping Center GUI           ");
        System.out.println("       3. Exit");

        do {
            //Input validation for menu option
            while (true) {
                System.out.print("\nEnter the number of your choice : " );
                try {
                    choice = input.nextInt();
                    break;
                } catch (Exception e) {
                    System.out.println("Invalid Input. Please enter an integer");
                    input.nextLine();   // Consume the invalid input from scanner buffer
                }
            }

            // Handling user's choice
            switch (choice) {
                case 1: {
                    manager.system_menu(); //Accesses the method with the static instance of WestminsterShoppingManager
                    break;
                }
                case 2: {
                    File file = new File("Products.txt");
                    if (file.exists()) {
                        manager.load_file();
                    }
                    else {
                        System.out.println("No saved data found. Starting with an empty product list.");
                    }
                    new Start(); // Creating an instance of the GUI
                    System.out.println("\nOpening GUI.......");
                    break;
                }
                case 3: {
                    System.out.println("\nExiting Westminster Shopping Center --------------");
                    System.exit(0);
                    break;
                }
                default: {
                    //Error message for any integer input that does not fall between 1-3
                    System.out.println("Invalid input. Please enter an integer between 1 and 3");
                }
            }
        }while(choice != 3);

    }

}