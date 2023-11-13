package src.users_code;

import src.Inventory.Product;

import java.util.ArrayList;

public class Seller extends User{
    private ArrayList<Product> sellerProducts;
    private double costs;
    private double revenues;
    private double profits;

    public Seller(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password, email);
        this.sellerProducts = new ArrayList<>();
    }

    public int getProductSize(){
        return this.sellerProducts.size();
    }

    public ArrayList<Product> getSellerProducts(){
        return sellerProducts;
    }
}
