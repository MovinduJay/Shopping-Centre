package ShoppingManager;

// Interface defining the contract for a Shopping Manager
public interface ShoppingManager {

    // Display the system menu
    void system_menu();

    // Validate integer input with a prompt
    int int_validation(String prompt);

    // Add a new product to the system
    void add_product();

    // Remove a product from the system
    void remove_product();

    // Update the details of an existing product
    void update_product(String productId);

    // Display the details of all products
    void display_product();

    // Save the product list to a file
    void save_file();

    // Load the product list from a file
    void load_file();
}
