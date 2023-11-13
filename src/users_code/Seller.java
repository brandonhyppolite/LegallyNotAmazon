package src.users_code;

import src.Inventory.Product;

import java.util.ArrayList;

public class Seller extends User{
    ArrayList<Product> sellerProducts;
    public Seller(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password, email);
        this.sellerProducts = new ArrayList<>();
        addProductsTesting();
    }

    private void addProductsTesting(){
        for (int i = 1; i <= 30; i++) {
            String productName = "Product" + i;
            double productPrice = 10.0 * i; // Replace this with your desired logic for price
            int productQuantity = 20; // Replace this with your desired quantity

            Product product = new Product(productName, productPrice, productQuantity);
            sellerProducts.add(product);
        }
    }

    public int getProductSize(){
        return this.sellerProducts.size();
    }

    public ArrayList<Product> getSellerProducts(){
        return sellerProducts;
    }
}
