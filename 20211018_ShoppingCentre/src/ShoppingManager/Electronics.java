package ShoppingManager;

// Electronics class extends Product
public class Electronics extends Product {
    private String brand;
    private int warrantyPeriod;

    // Constructor for Electronics class
    public Electronics(String type, String productId, String productName, int availableItems, double price, String brand, int warrantyPeriod) {
        super(type,productId, productName, availableItems, price);
        this.brand = brand;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and setters
    public String getBrand() {
        return brand;
    }

    public int getWarrantyPeriod() {
        return warrantyPeriod;
    }

    public void setWarrantyPeriod(int warrantyPeriod) {
        this.warrantyPeriod = warrantyPeriod;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }


    // Override saveData method to save Electronics-specific data
    @Override
    public String saveData() {
        return type +", "+ productId + ", " + productName + ", " + price + ", " + availableItems + ", " + brand + ", " +warrantyPeriod;
    }

    // Override toString method to display Electronics-specific details
    @Override
    public String toString(){
        return  " Type ("+type+")\n"+
                " ID                : "+productId+"\n"+
                " Name              : "+productName+"\n"+
                " Price             : $" + price+"\n"+
                " Available Items   : "+availableItems+"\n"+
                " Brand             : "+brand+ "\n"+
                " Warranty Period   : "+warrantyPeriod+" month(s)";
    }

    // Override getInfo method to get Electronics-specific information
    @Override
    public String getInfo() {
        return brand + ", " + warrantyPeriod;
    }



}