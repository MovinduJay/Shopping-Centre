package ShoppingManager;

// Class representing a user
public class User {
    protected String userName; // User's unique identifier
    protected String password; // User's password
    protected int purchaseCount; // Count of purchases made by the user

    // Constructors
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
        this.purchaseCount = 0; // Initialize purchase count to 0 for new users
    }

    public User(String userName, String password, int purchaseCount) {
        this.userName = userName;
        this.password = password;
        this.purchaseCount = purchaseCount;
    }

    // Getters and Setters

    // Get the username of the user.
    public String getUserName() {
        return userName;
    }

    // Get the password of the user.
    public String getPassword() {
        return password;
    }

    // Get the purchase count of the user.
    public int getPurchaseCount() {
        return purchaseCount;
    }

    // Set the purchase count of the user.
    public void setPurchaseCount(int purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
}
