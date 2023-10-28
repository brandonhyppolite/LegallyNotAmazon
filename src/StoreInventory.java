package src;

import java.util.ArrayList;

public class StoreInventory {
    ArrayList<Product> inventory;

    public StoreInventory() {
        this.inventory = new ArrayList<>();
    }

    public StoreInventory(ArrayList<Product> inventory) {
        this.inventory = inventory;
    }
    public void addProduct(Product p){
        this.inventory.add(p);
    }

    public void sellProduct(Product p, int q){

    }
}
