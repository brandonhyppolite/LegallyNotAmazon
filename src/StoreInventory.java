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
}
