package ShoppingManager;

// Clothing class extends Product
public class Clothing extends Product {
    private String size;
    private String color;

    // Constructor for Clothing class
    public Clothing(String type,String productId, String productName, int availableItems, double price, String size, String color) {
        super(type,productId, productName, availableItems, price);
        this.size = size;
        this.color = color;
    }

    //Getters and Setters
    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    // Override saveData method to save Clothing-specific data
    @Override
    public String saveData() {
        return type +", "+ productId + ", " + productName + ", " + price + ", " + availableItems + ", " + size + ", " +color;
    }

    // Override toString method to display Clothing-specific details
    @Override
    public String toString() {
        return  " Type ("+type+")\n"+
                " ID                : "+productId+"\n"+
                " Name              : "+productName+"\n"+
                " Price             : " + price+"\n"+
                " Available Items   : "+availableItems+"\n"+
                " Size              : " + size +"\n"+
                " Color             : " + color;
    }
    // Override toString method to display Clothing-specific details
    @Override
    public String getInfo() {
        return size + ", " + color;
    }
}