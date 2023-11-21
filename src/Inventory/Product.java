package src.Inventory;



import java.util.Random;

public class Product{
    private String name;
    private double sellingPrice;
    private int quantity;
    private String ID;
    private double invoicePrice;

    public Product(String name) {
        this.name = name;
    }

    public Product(String name, double invoicePrice,double sellingPrice, int quantity) {
        this.name = name;
        this.invoicePrice = invoicePrice;
        this.sellingPrice = sellingPrice;
        this.quantity = quantity;
        this.ID = generateProductID();
    }

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void sell(int amount){
        setQuantity(getQuantity() - amount);
        if(getQuantity() < 0){
            setQuantity(0);
        }
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
        StringBuilder stringBuilder = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }

        return stringBuilder.toString();
    }
}
