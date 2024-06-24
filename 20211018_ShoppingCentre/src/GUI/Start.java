package GUI;

// Class to initiate the GUI and user login
public class Start {
    // Main method to start the program
    public static void main(String[] args) {
        new Start();
    }

    // Constructor to initialize the program
    public Start(){
        // Create an instance of UserIDPassword to manage user IDs and passwords
        UserIDPassword userID = new UserIDPassword();
        // Create a new LoginPage and pass the login information to it
        new LoginPage(userID.getLoginInfo());
    }


}
