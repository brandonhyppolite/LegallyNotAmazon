package src.Inventoy;



public class Product {
    private String name;
    private double price;
    private int quantity;
    private String ID;


    public Product(String name) {
        this.name = name;
    }

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
}
