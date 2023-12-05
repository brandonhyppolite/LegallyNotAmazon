package src.Product;


import java.util.Objects;
import java.util.Random;

/**
 * The `Product` class represents a product with its attributes and methods.
 */
public class Product {
    private String name;
    private double sellingPrice;
    private int quantity;
    private final String ID;
    private double invoicePrice;
    private String sellerUserName;
    private String description;

    /**
     * Semi-Deep Copy Constructor
     *
     * @param original The original `Product` object to be copied.
     */
    public Product(Product original) {
        this.name = original.getName();
        this.sellingPrice = original.getSellingPrice();
        this.quantity = original.getQuantity();
        this.ID = original.getID();
        this.sellerUserName = original.getSellerUserName();
        this.description = original.getDescription();
    }

    /**
     * Constructor for creating a new Product.
     *
     * @param name         The name of the product.
     * @param invoicePrice The invoice price of the product.
     * @param sellingPrice The selling price of the product.
     * @param quantity     The quantity of the product.
     */
    public Product(String name, double invoicePrice, double sellingPrice, int quantity) {
        this.name = name;
        this.invoicePrice = roundToTwoDecimalPlaces(invoicePrice);
        this.sellingPrice = roundToTwoDecimalPlaces(sellingPrice);
        this.quantity = quantity;
        this.ID = generateProductID();
    }

    /**
     * Constructor for reading an existing product from the text file.
     *
     * @param name         The name of the product.
     * @param ID           The ID of the product.
     * @param invoicePrice The invoice price of the product.
     * @param sellingPrice The selling price of the product.
     * @param quantity     The quantity of the product.
     */
    public Product(String name, String ID, double invoicePrice, double sellingPrice, int quantity) {
        this.name = name;
        this.sellingPrice = roundToTwoDecimalPlaces(sellingPrice);
        this.quantity = quantity;
        this.ID = ID;
        this.invoicePrice = roundToTwoDecimalPlaces(invoicePrice);
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(double invoicePrice) {
        this.invoicePrice = roundToTwoDecimalPlaces(invoicePrice);
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = roundToTwoDecimalPlaces(sellingPrice);
    }

    public int getQuantity() {
        return quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getID() {
        return ID;
    }

    public String getSellerUserName() {
        return sellerUserName;
    }

    public void setSellerUserName(String sellerUserName) {
        this.sellerUserName = sellerUserName;
    }

    /**
     * Creates a new `Product` object with updated quantity after a sale.
     *
     * @param amount The amount of the product to be sold.
     * @return A new `Product` object with updated quantity.
     */
    public Product sell(int amount) {
        Product updatedProduct = new Product(this);  // Create a deep copy
        updatedProduct.setQuantity(updatedProduct.getQuantity() - amount);
        if (updatedProduct.getQuantity() < 0) {
            updatedProduct.setQuantity(0);
        }
        return updatedProduct;
    }

    /**
     * Rounds a double value to two decimal places.
     *
     * @param value The value to be rounded.
     * @return The rounded value.
     */
    private double roundToTwoDecimalPlaces(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", sellingPrice=" + sellingPrice +
                ", quantity=" + quantity +
                ", ID='" + ID + '\'' +
                ", invoicePrice=" + invoicePrice +
                '}';
    }
    /**
     * Generates a random product ID.
     *
     * @return The generated product ID.
     */
    private String generateProductID() {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
    /**
     * Checks if this `Product` is equal to another `Product` based on their name, selling price, and ID.
     *
     * @param obj The object to compare with.
     * @return True if the objects are equal, false otherwise.
     */
    public boolean equalsByNameIdSellingPrice(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return Objects.equals(name, other.name) &&
                Double.compare(other.sellingPrice, sellingPrice) == 0 &&
                Objects.equals(ID, other.ID);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Product other = (Product) obj;
        return Objects.equals(name, other.name) &&
                Double.compare(other.sellingPrice, sellingPrice) == 0 &&
                quantity == other.quantity &&
                Objects.equals(ID, other.ID) &&
                Double.compare(other.invoicePrice, invoicePrice) == 0;
    }

}
