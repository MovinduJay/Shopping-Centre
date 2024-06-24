package GUI;

import ShoppingManager.Product;
import java.util.HashMap;

// Class representing a shopping cart for storing products and quantities
public class ShoppingCart {
    // List to store products in the shopping cart
    private final HashMap<Product, Integer> cartItems;

    // constructor
    public ShoppingCart() {
        cartItems = new HashMap<>();
    }

    // Adds a product to the shopping cart with a specified quantity
    public void addProduct(Product product, int quantity) {
        // Ensure the quantity is greater than 0
        if (quantity > 0) {
            // Add the product to the cart, updating the quantity if it already exists
            cartItems.put(product, cartItems.getOrDefault(product, 0) + quantity);
            System.out.println("Product added to the shopping cart.");
        } else {
            System.out.println("Quantity must be greater than 0.");
        }
    }

    // Clears all items from the shopping cart
    public void clearCart() {
        cartItems.clear();
    }

    // Retrieves the products and their quantities in the shopping cart
    public HashMap<Product, Integer> getProducts() {
        return cartItems;
    }
}