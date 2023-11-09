package src.Inventory;

import java.util.ArrayList;

public class StoreInventory {
    private ArrayList<Product> inventory;
    private static StoreInventory instance;
    private StoreInventory() {
        this.inventory = new ArrayList<>();
        Product laptop = new Product("Laptop", 1200.0, 10);
        Product smartphone = new Product("Smartphone", 800.0, 15);
        Product headphones = new Product("Headphones", 100.0, 20);
        this.inventory.add(laptop);
        this.inventory.add(smartphone);
        this.inventory.add(headphones);
    }

    public StoreInventory(ArrayList<Product> inventory) {
        this.inventory = inventory;
    }

    public static StoreInventory getInstance() {
        if (instance == null) {
            instance = new StoreInventory();
        }
        return instance;
    }
    public void addProduct(Product p){
        if(!this.inventory.contains(p.getID())){
            this.inventory.add(p);
        }else{
            System.out.println("Product already exists");
        }

    }

    public void sellProduct(String ID, int q){
        for(Product p: this.inventory){
            if(p.getID().equals(ID)){
                p.sell(q);
            }
        }
    }
    public Product getProduct(String productId) {
        for (Product product : this.inventory) {
            if (product.getID().equals(productId)) {
                return product;
            }
        }
        return null;
    }

    public ArrayList<Product> getInventory() {
        return inventory;
    }
}
