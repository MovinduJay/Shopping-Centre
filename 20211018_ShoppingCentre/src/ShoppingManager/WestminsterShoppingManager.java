package ShoppingManager;

import GUI.Start;

import java.io.*;
import java.util.*;


public class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> productList; // List to store products

    private static Scanner input = new Scanner(System.in); // Common scanner for user input

    public WestminsterShoppingManager() {
        // Constructor initializes productList as an ArrayList
        this.productList = new ArrayList<>();
    }

    // Getter for productList
    public List<Product> getProductList() {
        return productList;
    }

    //Manager Console system menu
    @Override
    public void system_menu() {
        // Loading saved data if the file exists
        File file = new File("Products.txt");
        if (file.exists()) {
            load_file();
        }
        else {
            System.out.println("No saved data found. Starting with an empty product list.");
        }

        int option;
        do {
            System.out.println("\n--------- Manger Console ---------\n");
            System.out.println("   1. Add a new product");
            System.out.println("   2. Remove product");
            System.out.println("   3. Display product List");
            System.out.println("   4. Save product list to file");
            System.out.println("   5. Load product list from file ");
            System.out.println("   6. Open GUI");
            System.out.println("   0. Exit");

            // Getting user input for the menu option
            option = int_validation("\nEnter the relevant 'Number' of your Option Here: ");
            switch (option) {
                case 1: {
                    System.out.println("\n-------------- Add Product ---------------");
                    add_product();
                    break;
                }
                case 2: {
                    System.out.println("\n------------- Remove Product -------------");
                    remove_product();
                    break;
                }
                case 3: {
                    System.out.println("\n------------  Product Details ------------");
                    display_product();
                    break;
                }
                case 4: {
                    System.out.println("\n------------- Save To File ---------------");
                    save_file();
                    break;
                }
                case 5: {
                    System.out.println("\n--------------- Load File ----------------");
                    load_file();
                    break;
                }
                case 6: {
                    System.out.println("\nOpening GUI.......");
                    new Start();
                    break;
                }
                case 0: {
                    // Save data before exiting
                    save_file();
                    System.out.println("\nQuitting program......");
                    System.exit(0);
                }
                default: {
                    //Error message for any integer input that does not fall between 0-6
                    System.out.println("Invalid input. Please Enter a valid choice between 0 - 6");
                    break;
                }
            }
        } while (option != 0);
    }

    //A method to validate integer inputs from user
    @Override
    public int int_validation(String prompt) {
        int value;
        while (true) {
            try {
                System.out.print(prompt);
                value = input.nextInt();
                input.nextLine(); // Consume newline character
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                input.nextLine(); // Consume the invalid input from the scanner buffer
            }
        }
        return value;
    }



    // Method to add a new product to the product list
    @Override
    public void add_product() {
        // Check if the maximum number of products (50) has been reached
        if (productList.size() >= 50) {
            System.out.println("Maximum number of products reached (50). Cannot add more products.");
            return;
        }

        // Variable for validating product type input
        boolean productType_validation = false;
        int productType;

        // Loop to ensure valid product type input
        do {
            System.out.println("Select Product Type \n1. Electronics \n2. Clothing");
            productType = int_validation("\nEnter Choice: ");
            if (productType > 2 || productType < 1) {
                System.out.println("Please choose a number between 1 and 1 and 2\n");
            } else {
                productType_validation = true;
            }
        } while (!productType_validation);

        // Get product ID from the user
        String productId;
        boolean isValidProductId;

        // Loop until a valid product ID is entered
        do {
            System.out.print("\nEnter Product ID (1 alphabet letter + 4 digits): ");
            productId = input.next().toUpperCase();

            // Validate the productId format
            isValidProductId = productId.matches("[A-Z]\\d{4}");

            if (!isValidProductId) {
                System.out.println("Invalid Product ID format. Please enter again.");
            }
        } while (!isValidProductId);

        // Check if the product with the entered ID already exists
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                System.out.println("Product with ID " + productId + " already exists.");

                // If the product with the same ID exists, an option is given to update the product details
                System.out.print("\nDo you want to update product details (Y/N) : ");
                String update = input.next().trim().toLowerCase();
                if (update.equals("y")) {
                    System.out.println("\n------- Product Update ------\n");
                    update_product(productId);
                }
                return;
            }
        }
        // Get additional product details
        System.out.print("Enter Product Name: ");
        String productName = input.next();

        int availableItems = int_validation("Enter the number of available items: ");

        //validate user input price
        double price;
        while (true) {
            try {
                System.out.print("Enter price of one " + productName + ": £ ");
                price = input.nextDouble();
                input.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid price.");
                input.nextLine(); // Clear the buffer
            }
        }
        // Getting details related to the selected type
        if (productType == 1) {
            String type = "Electronics";
            System.out.print("Enter the brand of " + productName + " : ");
            String productBrand = input.next();
            int warrantyPeriod = int_validation("Enter the warranty period for " + productName + " (in months) : ");

            //Creating a electronics object and adding it to the arraylist
            Electronics electronicProduct = new Electronics(type,productId, productName, availableItems, price, productBrand, warrantyPeriod);
            productList.add(electronicProduct);

        } else {
            String type = "Clothing";
            String clothSize;

            // Loop until a valid size is entered
            do {
                System.out.print("Enter the size (S/M/L) of " + productName + " : ");
                clothSize = input.next().toUpperCase();

                // Validate the clothSize format
                if (!clothSize.matches("[SML]")) {
                    System.out.println("Invalid size. Please enter 'S', 'M', or 'L'.");
                }
            } while (!clothSize.matches("[SML]"));

            System.out.print("Enter the color of " + productName + " : ");
            String clothColor = input.next();

            // Creating a clothing object and adding it to the array list
            Clothing clothingProduct = new Clothing(type, productId, productName, availableItems, price, clothSize, clothColor);
            productList.add(clothingProduct);
        }

        System.out.println("\nProduct added successfully");
    }

    // Method to update details of an existing product
    // Method to update details of an existing product
    @Override
    public void update_product(String productId) {
        Product updateProduct = null;

        // Check if the product with the entered ID exists and assign it to a new variable
        for (Product product : productList) {
            if (product.getProductId().equals(productId)) {
                updateProduct = product;
                break;
            }
        }

        // If the product exists, proceed with updating details
        if (updateProduct != null) {
            System.out.println("Product details for verification: \n");
            System.out.println(updateProduct);

            // Provide options to update different details
            boolean continueUpdating = true;
            do {
                System.out.println("\nSelect detail to update:");
                System.out.println("1. Product Name");
                System.out.println("2. Available Items");
                System.out.println("3. Price");

                // Additional options based on the product type
                if (updateProduct instanceof Electronics) {
                    System.out.println("4. Brand");
                    System.out.println("5. Warranty Period");
                } else if (updateProduct instanceof Clothing) {
                    System.out.println("4. Size");
                    System.out.println("5. Color");
                }
                System.out.println("0. Finish updating");

                // Get user choice for the update and validating it through the method
                int option = int_validation("\nEnter your choice: ");

                // Updating details according to the user's choice of update category
                switch (option) {
                    case 1:
                        // Update product name
                        System.out.print("Enter new Product Name: ");
                        String newName = input.next();
                        updateProduct.setProductName(newName);
                        break;
                    case 2:
                        // Update available items
                        int newAvailableItems = int_validation("Enter new number of available items: ");
                        updateProduct.setAvailableItems(newAvailableItems);
                        break;
                    case 3:
                        // Update price
                        double newPrice;
                        while (true) {
                            try {
                                System.out.print("Enter new price: £ ");
                                newPrice = input.nextDouble();
                                input.nextLine();
                                break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a valid price.");
                                input.nextLine(); // Clear the buffer
                            }
                        }
                        updateProduct.setPrice(newPrice);
                        break;
                    case 4:
                        // Additional updates based on product type
                        if (updateProduct instanceof Electronics) {
                            System.out.print("Enter new Brand: ");
                            String newBrand = input.next();
                            ((Electronics) updateProduct).setBrand(newBrand);
                        } else if (updateProduct instanceof Clothing) {
                            System.out.print("Enter new Size (S/M/L): ");
                            String newSize = input.next();
                            ((Clothing) updateProduct).setSize(newSize);
                        }
                        break;
                    case 5:
                        // Additional updates based on product type
                        if (updateProduct instanceof Electronics) {
                            int newWarrantyPeriod = int_validation("Enter new Warranty Period (in months): ");
                            ((Electronics) updateProduct).setWarrantyPeriod(newWarrantyPeriod);
                        } else if (updateProduct instanceof Clothing) {
                            System.out.print("Enter new Color: ");
                            String newColor = input.next();
                            ((Clothing) updateProduct).setColor(newColor);
                        }
                        break;
                    case 0:
                        // Finish updating
                        continueUpdating = false;
                        break;
                    default:
                        System.out.println("Invalid option. Please enter a valid choice.");
                        break;
                }
            } while (continueUpdating);
            System.out.println("\nProduct updated successfully.");
        }
    }

    // Method to remove a product from the product list
    @Override
    public void remove_product() {
        // Display all product IDs for user reference
        System.out.println("\nProduct IDs in the system:");
        for (Product product : productList) {
            System.out.println(product.getProductId());
        }

        // Get the Product ID of the product to remove
        System.out.print("\nEnter the Product ID to remove: ");
        String removeProductID = input.next();
        Product removeProduct = null;

        // Find the product with the specified ID
        for (Product product : productList) {
            if (product.getProductId().equals(removeProductID)) {
                removeProduct = product;
                break;
            }
        }
        // If the product is found, display the details and get confirmation
        if (removeProduct != null) {
            System.out.println("Product details for verification: \n");
            // Printing product details
            if (removeProduct instanceof Electronics) {
                System.out.println(" Type: Electronic");
                System.out.println(removeProduct);
            } else if (removeProduct instanceof Clothing) {
                System.out.println(" Type: Clothing");
                System.out.println(removeProduct);
            }

            // Taking user confirmation for the deletion of the product
            while (true) {
                System.out.print("\nDo you want to delete this product? (Y/N): ");
                String confirmation = input.next().trim().toLowerCase();

                if (confirmation.equals("y")) {
                    productList.remove(removeProduct);
                    System.out.println("Product deleted successfully.");
                    break;
                } else if (confirmation.equals("n")) {
                    System.out.println("Product not deleted.");
                    break;
                } else {
                    System.out.println("Invalid input. Please enter Y or N.");
                }
            }
        } else {
            System.out.println("Product with ID " + removeProductID + " not found.");
        }
        // Displaying the total No of products in the system
        System.out.println("\nTotal number of products left in the system: " + productList.size());
    }

    // Method to display product details
    @Override
    public void display_product() {
        // Check if the product list is empty
        if (productList.isEmpty()) {
            System.out.println("\nThere is no Products in the System");
            return;
        }
        // Create a new ArrayList to store a copy of the original products
        List<Product> sortedProducts = new ArrayList<>(productList);

        // Sort the new ArrayList alphabetically by product name
        Collections.sort(sortedProducts, Comparator.comparing(Product::getProductId));

        for (Product product : sortedProducts) {
            System.out.println(product);

            System.out.println(); // Separate each product's details
        }
    }

    // Method to save product data in the arraylist to a file
    @Override
    public void save_file() {
        try (FileWriter writer = new FileWriter("Products.txt")) {
            for (Product product : productList) {
                writer.write(product.saveData() + "\n");
            }
            System.out.println("\nData successfully saved");

        } catch (IOException e) {
            System.out.println("\nError saving data to file ");
        }
    }

    // Method to load product data from a file
    @Override
    public void load_file() {
        try (BufferedReader reader = new BufferedReader(new FileReader("Products.txt"))) {
            String line;

            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Split the line into individual data elements
                String[] data = line.split(", ");

                // Extracting data for creating a product
                String type = data[0];
                String productId = data[1];
                String productName = data[2];
                double price = Double.parseDouble(data[3]);
                int availableItems = Integer.parseInt(data[4]);

                // Check the product type and create the corresponding object
                if (type.equals("Electronics")) {
                    String brand = data[5];
                    int warrantyPeriod = Integer.parseInt(data[6]);
                    Electronics electronicProduct = new Electronics(type, productId, productName, availableItems, price, brand, warrantyPeriod);
                    productList.add(electronicProduct);
                } else if (type.equals("Clothing")) {
                    String size = data[5];
                    String color = data[6];
                    Clothing clothingProduct = new Clothing(type, productId, productName, availableItems, price, size, color);
                    productList.add(clothingProduct);
                }
            }
            System.out.println("\nData successfully loaded from the file");

        } catch (IOException e) {
            // Handle file not found or other IO exceptions and display error message
            System.out.println("\nThe File does not exist: " + e.getMessage());
        }
    }

}
