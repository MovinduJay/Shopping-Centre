package ShoppingManager;

// Abstract class representing a product
public abstract class Product {

    // Common attributes for all products
    protected String productId;    // Unique identifier for the product
    protected String productName;  // Name of the product
    protected int availableItems;  // Quantity of available items
    protected double price;        // Price of the product
    protected String type;         // Type of the product (Clothing or Electronics)

    // Constructor for initializing common attributes of products
    public Product(String type, String productId, String productName, int availableItems, double price) {
        this.type = type;
        this.productId = productId;
        this.productName = productName;
        this.availableItems = availableItems;
        this.price = price;
    }

    // Getters and setters for product attributes
    public String getProductId() {
        return productId;
    }

    public String getType() {
        return type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getAvailableItems() {
        return availableItems;
    }

    public void setAvailableItems(int availableItems) {
        this.availableItems = availableItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Abstract methods to be implemented by subclasses Clothing and Electronics
    public abstract String saveData();  // Method to save product data

    public abstract String toString();  // Method to get a string representation of the product

    public abstract String getInfo();   // Method to get detailed information about the product

    // Method to decrease the available quantity of a product
    public void decreaseQuantity(int quantity) {
        this.availableItems -= quantity;
    }
}
