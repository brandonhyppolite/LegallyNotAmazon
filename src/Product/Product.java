package src.Product;



import java.util.Objects;
import java.util.Random;

public class Product{
    private String name;
    private double sellingPrice;
    private int quantity;
    private String ID;
    private double invoicePrice;
    private String sellerUserName;
    private String description;


    //Deep Copy Constructor
    public Product(Product original) {
        this.name = original.getName();
        this.sellingPrice = original.getSellingPrice();
        this.quantity = original.getQuantity();
        this.ID = original.getID();
//        this.invoicePrice = original.getInvoicePrice();
        this.sellerUserName = original.getSellerUserName();
        this.description = original.getDescription();
    }

    // Constructor for creating a new Product
    public Product(String name, double invoicePrice,double sellingPrice, int quantity) {
        this.name = name;
        this.invoicePrice = invoicePrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.ID = generateProductID();
    }

    //Constructor for reading an existing product from the text file
    public Product(String name, String ID,double invoicePrice, double sellingPrice, int quantity) {
        this.name = name;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.ID = ID;
        this.invoicePrice = invoicePrice;
    }

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
        this.invoicePrice = invoicePrice;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
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

    public void setID(String ID) {
        this.ID = ID;
    }

    public Product sell(int amount) {
        Product updatedProduct = new Product(this);  // Create a deep copy
        updatedProduct.setQuantity(updatedProduct.getQuantity() - amount);
        if (updatedProduct.getQuantity() < 0) {
            updatedProduct.setQuantity(0);
        }
        return updatedProduct;
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

    private String generateProductID(){
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
