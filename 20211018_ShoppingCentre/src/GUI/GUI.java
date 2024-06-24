package GUI;

import ShoppingManager.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import static GUI.UserIDPassword.users;

public class GUI implements ActionListener, ListSelectionListener {
    private User currentUser;
    private ArrayList<Product> products;

    private JFrame mainFrame;
    private JLabel productDetailsLabel, selectProductLabel;
    private JButton viewCartBtn, addToCart, checkOut, sortButton;
    private ArrayList<Product> sortedProducts;
    private UserIDPassword userIDPassword;
    private boolean didSort = false;
    private JComboBox<String> selectionBox;
    private ShoppingCart shoppingCart;
    private JTable table;
    private DefaultTableModel tableModel;
    private int currentUserPurchaseCount;

    // Constructor to initialize the main graphical user interface
    public GUI(ArrayList<Product> products, User currentUser, int currentUserPurchaseCount) {
        // Initialization of instance variables
        this.products = products;
        this.currentUser = currentUser;
        this.currentUserPurchaseCount = currentUserPurchaseCount;
        shoppingCart = new ShoppingCart();

        // Creating the main frame
        mainFrame = new JFrame("Westminster Shopping Center");
        mainFrame.setSize(600, 550);

        // Creating a panel to hold GUI components
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Creating and configuring labels, buttons, and table
        JLabel selectProductCategoryLabel = new JLabel("Select Product Category");
        selectProductCategoryLabel.setBounds(80, 20, 160, 25);

        selectionBox = new JComboBox(new String[]{"All", "Electronics", "Clothing"});
        selectionBox.setSelectedItem("All");
        selectionBox.addActionListener(this);
        selectionBox.setBounds(240, 20, 160, 25);

        // Creating and configuring the table model
        tableModel = new DefaultTableModel(new String[]{"Product ID", "Name", "Category", "Price", "Info"}, 0);
        for (Product product : products) {
            // Adding rows to the table model based on product data
            Object[] productArray = {product.getProductId(), product.getProductName(), product.getType(), product.getPrice(), product.getInfo()};
            tableModel.addRow(productArray);
        }

        // Creating and configuring the table
        table = new JTable(tableModel);
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer());
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(4).setPreferredWidth(150);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(25, 70, 550, 250);

        productDetailsLabel = new JLabel("Selected Product - Details");
        productDetailsLabel.setFont(new Font("POPPINS", Font.BOLD, 12));
        productDetailsLabel.setBounds(30, 320, 200, 25);

        selectProductLabel = new JLabel("Select a product to view details");
        selectProductLabel.setBounds(30, 340, 200, 125);

        // Adding a selection listener to the table
        ListSelectionModel listModel = table.getSelectionModel();
        listModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listModel.addListSelectionListener(this);

        // Creating and configuring buttons
        viewCartBtn = new JButton("Shopping Cart");
        viewCartBtn.setBounds(430, 10, 150, 25);
        viewCartBtn.addActionListener(this);

        sortButton = new JButton("Sort by ProductID");
        sortButton.setBounds(424, 330, 150, 25);
        sortButton.addActionListener(this);


        addToCart = new JButton("Add To Shopping Cart");
        addToCart.setBounds(200, 470, 170, 25);
        addToCart.addActionListener(this);

        // Adding components to the panel
        panel.add(selectProductCategoryLabel);
        panel.add(selectionBox);
        panel.add(scrollPane);
        panel.add(sortButton);
        panel.add(productDetailsLabel);
        panel.add(selectProductLabel);
        panel.add(viewCartBtn);
        panel.add(addToCart);

        // Adding the panel to the main frame
        mainFrame.add(panel);

        // Configuring main frame properties
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);

        // Creating and loading user information
        userIDPassword = new UserIDPassword();
        userIDPassword.loadUsers(users);
    }

    // Method to display the shopping cart frame
    // Method to display the shopping cart frame
    public void shoppingCartFrame() {

        // Creating a new frame for the shopping cart
        JFrame frame = new JFrame("Shopping Cart");
        frame.setSize(600, 450);
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Creating the table model for the shopping cart
        DefaultTableModel model = new DefaultTableModel(new String[]{"Product", "Quantity", "Price"}, 0);

        // Creating a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(new JTable(model));
        scrollPane.setBounds(12, 10, 560, 200);

        // Retrieving products and quantities from the shopping cart
        HashMap<Product, Integer> productCart = shoppingCart.getProducts();

        // Clearing the table model
        model.setRowCount(0);

        // Initializing variables for total, counts, and discounts
        double total = 0;
        int electronicsCount = 0;
        int clothingCount = 0;
        double discount = 0;

// Iterating through the products in the shopping cart
        for (Map.Entry<Product, Integer> entry : productCart.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            // Adding product details to the table model
            double productTotal = quantity * product.getPrice();
            Object[] productArray = {product.getProductId() + ", " + product.getProductName() + ", " + product.getInfo(), quantity, String.format("%.2f", productTotal)};
            model.addRow(productArray);
            total += productTotal;

            // Updating counts based on product category
            if (product.getType().equalsIgnoreCase("Electronics")) {
                electronicsCount += entry.getValue();
            } else if (product.getType().equalsIgnoreCase("Clothing")) {
                clothingCount += entry.getValue();
            }

            // Applying category discount if applicable
            if (electronicsCount >= 3 || clothingCount >= 3) {
                discount = (total * 0.20);
                JLabel discountLbl20 = new JLabel("Three items in the same Category Discount (20%) :  £ " + String.format("%.2f", discount));
                discountLbl20.setBounds(130, 275, 400, 25);
                panel.add(discountLbl20);
            }

            // Applying first purchase discount if applicable
            if (currentUserPurchaseCount == 0) {
                discount = (total * 0.10);
                JLabel discountLbl10 = new JLabel("First Purchase Discount (10%) :  £ " + String.format("%.2f", discount));
                discountLbl10.setBounds(237, 305, 400, 25);
                panel.add(discountLbl10);
            }
        }


        // Displaying total and final total labels
        JLabel totalL = new JLabel("Total :  £ " + String.format(String.valueOf(total)));
        totalL.setBounds(380, 240, 200, 30);

        JLabel finalL = new JLabel("Final Total :  $ " + String.format("%.2f", (total - discount)));
        finalL.setFont(new Font("", Font.BOLD, 12));
        finalL.setBounds(348, 335, 400, 25);

        // Creating and configuring the "Purchase" button
        checkOut = new JButton("Purchase");
        checkOut.addActionListener(this);
        checkOut.setBounds(430, 380, 100, 25);
        checkOut.setFont(new Font("", Font.BOLD, 12));

        // Adding components to the panel
        panel.add(scrollPane);
        panel.add(totalL);
        panel.add(finalL);
        panel.add(checkOut);

        // Adding the panel to the frame
        frame.add(panel);

        // Adding the panel to the frame
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }


    // ActionListener implementation for handling button clicks and events
    @Override
    public void actionPerformed(ActionEvent e) {

        // Handle the action event based on the source
        if (e.getSource() == viewCartBtn) {
            // Display the shopping cart frame
            shoppingCartFrame();
        } else if (e.getSource() == addToCart) {
            // If the "Add To Shopping Cart" button is clicked
            int index = table.getSelectedRow();
            if (index != -1) {
                Product product = products.get(index);

                // Show input dialog to get quantity
                String input = JOptionPane.showInputDialog("Please Enter Quantity: ");

                // Check if the input is not empty
                if (input != null && !input.isEmpty()) {
                    try {
                        // Parse the input quantity
                        int quantity = Integer.parseInt(input);

                        // Check if there are enough available items
                        if (products.get(index).getAvailableItems() > 0 && quantity <= products.get(index).getAvailableItems()) {
                            // Decrease available items, add to cart, and update table
                            products.get(index).decreaseQuantity(quantity);
                            shoppingCart.addProduct(product, quantity);

                            JOptionPane.showMessageDialog(null, product.getProductName() + " Added To Cart!");
                            updateTable();
                        } else {
                            JOptionPane.showMessageDialog(null, "Not Enough Products");
                        }

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(null, "Please enter a valid number.");
                    }
                }
            }
        } else if (e.getSource() == checkOut) {
            // If the "Purchase" button is clicked
            shoppingCart.clearCart();
            LoginPage.saveCounter();
//            UserIDPassword.incrementPurchaseCount(LoginPage.userID);

            JOptionPane.showMessageDialog(null, "Purchase Made!");
        }

        // Handle combo box selection change event
        if ("comboBoxChanged".equalsIgnoreCase(e.getActionCommand())) {
            // Get the selected category from the combo box
            String category = (String) selectionBox.getSelectedItem();
            tableModel.setRowCount(0);

            // Populate the table based on the selected category
            for (Product product : products) {
                if (category.equalsIgnoreCase("All")) {
                    Object[] arr = {product.getProductId(), product.getProductName(), product.getType(), product.getPrice(), product.getInfo()};
                    tableModel.addRow(arr);
                } else if (product.getType().equalsIgnoreCase(category)) {
                    Object[] arr = {product.getProductId(), product.getProductName(), product.getType(), product.getPrice(), product.getInfo()};
                    tableModel.addRow(arr);

                }
            }
        }
        // If the "Sort by ProductID" button is clicked
        if (e.getSource() == sortButton) {
            // Sort the products and update the table
            sortProducts();
            updateTable();
        }
    }

    //Method to sort product List
    private void sortProducts() {
        // Create a duplicate of the original products for sorting
        sortedProducts = new ArrayList<>(products);
        didSort = true;

        // Sort the duplicate products by Product ID
        Collections.sort(sortedProducts, Comparator.comparing(Product::getProductId));

    }

    //Method to update table
    private void updateTable() {
        // Update the tableModel with sortedProducts
        tableModel.setRowCount(0);
        ArrayList<Product> productsToDisplay;

        // Check if sorting was applied and sortedProducts is not null
        if (didSort && sortedProducts != null) {
            // Display the sorted products
            productsToDisplay = sortedProducts;
        } else {
            // Display the original products without sorting
            productsToDisplay = products;
        }

        // Populate the table with product details
        for (Product product : productsToDisplay) {
            Object[] arr = {product.getProductId(), product.getProductName(), product.getType(), product.getPrice(), product.getInfo()};
            tableModel.addRow(arr);
        }

        // Get the selected row in the table
        int selectedRow = table.getSelectedRow();

        if (selectedRow != -1) {
            // If a product is selected, update the product details label
            Product selectedProduct = productsToDisplay.get(selectedRow);
            String productDetails = generateProductDetails(selectedProduct);
            selectProductLabel.setText(productDetails);
        } else {
            // If no product is selected, clear the product details label
            selectProductLabel.setText("Select a product to view details");
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // Get the selected row in the table
        int selectedRow = table.getSelectedRow();

        // Check if a row is selected
        if (selectedRow != -1) {
            // Get the selected product ID object from the table
            Object selectedProductIdObj = table.getValueAt(selectedRow, 0);

            // Check if the selectedProductIdObj is a String
            if (selectedProductIdObj instanceof String) {
                // Convert the selected product ID object to a String
                String selectedProductId = (String) selectedProductIdObj;

                // Find the product with the selected product ID
                Product selectedProduct = findProductById(selectedProductId);

                if (selectedProduct != null) {
                    // Display information relevant to the selected product
                    String productDetails = generateProductDetails(selectedProduct);
                    selectProductLabel.setText(productDetails);
                }
            }

        }

    }

    // Helper method to find a product by its ID
    private Product findProductById(String productId) {
        for (Product product : products) {
            if (product.getProductId().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    // Method to generate product details as HTML-formatted text
    public String generateProductDetails(Product product) {
        String category = product.getType();
        StringBuilder stringBuilder = new StringBuilder(
                "<html>"
                        + "<b>Product ID:</b> " + product.getProductId() + "<br/>"
                        + "<b>Name:</b> " + product.getProductName() + "<br/>"
                        + "<b>Category:</b> " + category + "<br/>"
                        + "<b>Price: </b> $ " + String.format("%.2f", product.getPrice()) + "<br/>");

        // Check the category to include specific details
        if (category.equalsIgnoreCase("Electronics")) {
            Electronics electronics = (Electronics) product;
            stringBuilder.append("<b>Brand:</b> ").append(electronics.getBrand()).append("<br/>")
                    .append("<b>Warranty Period:</b> ")
                    .append(electronics.getWarrantyPeriod()).append(" months<br/>");
        } else if (category.equalsIgnoreCase("Clothing")) {
            Clothing clothing = (Clothing) product;
            stringBuilder.append("<b>Size:</b> ").append(clothing.getSize()).append("<br/>")
                    .append("<b>Colour:</b> ")
                    .append(clothing.getColor()).append("<br/>");
        }
        // Include general product details
        stringBuilder.append("<b>Items Available:</b> ").append(product.getAvailableItems())
                .append("</html>");
        return stringBuilder.toString();

    }
    public class CustomTableCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            // Call the superclass method to get the default rendering component
            Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Check the available items for the product in the current row
            if (products.get(row).getAvailableItems() < 3) {
                // If available items are less than 3, set background to red and text color to white
                component.setBackground(Color.red);
                component.setForeground(Color.white);
            } else {
                // If available items are 3 or more, set background to white and text color to black
                component.setBackground(Color.white);
                component.setForeground(Color.black);
            }
            // Check if the cell is currently selected
            if (isSelected) {
                // If selected, use the table's selection background and foreground colors
                component.setBackground(table.getSelectionBackground());
                component.setForeground(table.getSelectionForeground());
            }
            return component;
        }
    }

}

