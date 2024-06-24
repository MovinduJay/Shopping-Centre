package GUI;

import ShoppingManager.User;
import ShoppingManager.Product;
import ShoppingManager.WestminsterShoppingManager;
import ShoppingManager.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static GUI.UserIDPassword.users;

// Class representing the login page GUI
public class LoginPage implements ActionListener {

    // Static variables for user ID and user object
    private static String userID;
    private static User user;

    // Components for the login page
    JFrame frame = new JFrame();
    JButton logInButton = new JButton("Log In");
    JButton signUpButton = new JButton("Register");
    JTextField userIDField = new JTextField();
    JPasswordField userPasswordField = new JPasswordField();
    JPanel topPanel = new JPanel();
    JLabel shoppingCenterLabel = new JLabel("Westminster Shopping Centre");
    JLabel loginForm = new JLabel("Login / Register ");
    JLabel userIDLabel  = new JLabel("  User ID ");
    JLabel userPasswordLabel = new JLabel("Password ");
    JLabel newUser = new JLabel("New user : ");
    JLabel messageLabel = new JLabel("");
    HashMap<String, String> loginInfo;

    // Constructor
    LoginPage(HashMap<String, String> loginInfoOriginal) {

        // Initialize loginInfo and load users from file
        loginInfo = loginInfoOriginal;
        UserIDPassword.loadUsers(users);

        // Populate loginInfo with existing users
        for (Map.Entry<String, User> entry : users.entrySet()) {
            loginInfo.put(entry.getValue().getUserName(), entry.getValue().getPassword());
        }

        // Configure top panel layout
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBounds(0, 10, 360, 70);

        // Set properties for shopping center label
        shoppingCenterLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        shoppingCenterLabel.setFont(new Font("Calibre", Font.ITALIC, 20));

        // Set properties for login form label
        loginForm.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginForm.setFont(new Font("Calibre", Font.BOLD, 18));

        // Set bounds for user ID and password labels
        userIDLabel.setBounds(35, 100, 75, 25);
        userPasswordLabel.setBounds(30, 150, 75, 25);

        // Set bounds for message label
        messageLabel.setBounds(120, 300, 300, 25);
        messageLabel.setFont(new Font("POPPINS", Font.ITALIC, 17));

        // Set bounds for user ID field and password field
        userIDField.setBounds(100, 100, 180, 30);
        userPasswordField.setBounds(100, 150, 180, 30);

        // Set bounds for log in button
        logInButton.setBounds(100, 200, 180, 25);
        logInButton.setFocusable(false);
        logInButton.addActionListener(this);

        // Set properties for new user label
        newUser.setBounds(30, 240, 200, 25);

        // Set bounds for sign up button
        signUpButton.setBounds(100, 240, 180, 25);
        signUpButton.setFocusable(false);
        signUpButton.addActionListener(this);

        // Configure top panel layout
        topPanel.add(Box.createVerticalGlue());
        topPanel.add(shoppingCenterLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        topPanel.add(loginForm);
        frame.add(topPanel);

        // Add components to the frame
        frame.add(userIDLabel);
        frame.add(userPasswordLabel);
        frame.add(messageLabel);
        frame.add(userIDField);
        frame.add(userPasswordField);
        frame.add(newUser);
        frame.add(logInButton);
        frame.add(signUpButton);

        // Configure frame properties
        frame.setTitle("User Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(360, 400);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    // Action performed method to handle button clicks
    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle log in button click
        if (e.getSource() == logInButton) {
            // Retrieve user ID and password from the input fields
            userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            // Check if the user ID exists in the loginInfo map
            if (loginInfo.containsKey(userID)) {
                // Check if the entered password matches the stored password for the user ID
                if (loginInfo.get(userID).equals(password)) {
                    // Create a new User object and close the login frame
                    messageLabel.setForeground(Color.BLUE);
                    messageLabel.setText("Successfully Logged");

                    user = new User(userID, password);
                    frame.dispose();

                    // Open the shopping page
                    shoppingPage();

                } else {
                    // Display an error message for incorrect password
                    messageLabel.setForeground(Color.RED);
                    messageLabel.setText("Wrong Password");
                }
            } else {
                // Display an error message for non-existent user ID
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("UserID Not Found");
            }
        }
        // Handle sign up button click
        if (e.getSource() == signUpButton) {
            // Retrieve user ID and password from the input fields
            String userID = userIDField.getText();
            String password = String.valueOf(userPasswordField.getPassword());

            if (UserIDPassword.loginInfo.containsKey(userID)) {
                // Display an error message for existing user ID
                messageLabel.setForeground(Color.RED);
                messageLabel.setText("Username exists");
            } else {
                // Add the new user to loginInfo and users maps
                UserIDPassword.loginInfo.put(userID, password);
                users.put(userID, new User(userID, password, 0));

                // Save the updated user information to file
                UserIDPassword.saveUsers();

                messageLabel.setForeground(Color.GREEN);
                messageLabel.setText("Click Login Now");

            }
        }
    }

    // Method to increment purchase count for the current user
    public static void saveCounter() {
        UserIDPassword.incrementPurchaseCount(userID);
    }

    // Method to open the shopping page GUI
    public void shoppingPage() {
        WestminsterShoppingManager managerInstance = Main.getManagerInstance();
        int userPurchaseCount = UserIDPassword.getUserPurchaseCount(userID);
        new GUI((ArrayList<Product>) managerInstance.getProductList(), user, userPurchaseCount);
    }
}

