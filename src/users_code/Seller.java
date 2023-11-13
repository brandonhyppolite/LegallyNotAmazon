package src.users_code;

import src.Inventory.Product;

import java.util.ArrayList;

public class Seller extends User{
    ArrayList<Product> sellerProducts;
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
