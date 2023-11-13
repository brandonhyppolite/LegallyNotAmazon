package src.Inventory;

import src.users_code.Seller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreInventory {
    private HashMap<Seller,ArrayList<Product>> inventory;
    private static StoreInventory instance;
    private StoreInventory() {
        this.inventory = new HashMap<>();
    }


    public static StoreInventory getInstance() {
        if (instance == null) {
            instance = new StoreInventory();
        }
        return instance;
    }

    public HashMap<Seller,ArrayList<Product>> getInventory(){
        return this.inventory;
    }
}
