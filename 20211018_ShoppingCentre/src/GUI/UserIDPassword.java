package GUI;

import ShoppingManager.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

//Manages user IDs and passwords.

public class UserIDPassword {

    // HashMap to store login information (username -> password)
    static HashMap<String, String> loginInfo = new HashMap<>();

    // HashMap to store user objects (username -> User object)
    static HashMap<String, User> users = new HashMap<>();

    // Constructor to initialize and load users.
    public UserIDPassword() {
        // Load users from file
        loadUsers(users);
        // Populate loginInfo from the loaded users
        for (Map.Entry<String, User> entry : users.entrySet()) {
            loginInfo.put(entry.getValue().getUserName(), entry.getValue().getPassword());
        }
    }

    //Save users to file.

    public static void saveUsers() {
        try (FileWriter writer = new FileWriter("Users.txt")) {
            // Iterate through users and write their information to file
            for (Map.Entry<String, User> entry : users.entrySet()) {
                User user = entry.getValue();
                writer.write(user.getUserName() + ":" + user.getPassword() + ":" + user.getPurchaseCount() + "\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //Load users from file into the provided usersMap.
    public static void loadUsers(Map<String, User> usersMap) {
        usersMap.clear(); // Clear existing data
        File file = new File("Users.txt");

        try (Scanner scanner = new Scanner(file)) {
            // Read each line from the file
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(":");

                // Check if the line has the expected format
                if (parts.length == 3) {
                    String username = parts[0];
                    String password = parts[1];
                    // Trim the purchase count string before parsing
                    int purchaseCount = Integer.parseInt(parts[2].trim());
                    // Create a new User object and add it to the usersMap
                    usersMap.put(username, new User(username, password, purchaseCount));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved users found. Starting with an empty user list.");
        }
    }

    //Get the purchase count for a given username
    public static int getUserPurchaseCount(String username) {
        User user = users.get(username);
        if (user != null) {
            return user.getPurchaseCount();
        }
        return 0; // Default to 0 if user not found
    }

    //Increment the purchase count for a given username.
    public static void incrementPurchaseCount(String username) {
        User user = users.get(username);

        if (user != null) {
            // Increment the purchase count
            user.setPurchaseCount(user.getPurchaseCount() + 1);

            // Update the users map
            users.put(username, user);
            saveUsers(); // Save the updated users to the file

            loginInfo.put(username, user.getPassword());
        }
    }

    //Get the login information HashMap.
    protected HashMap<String, String> getLoginInfo() {
        return loginInfo;
    }
}
