package src.users_code;

import src.Inventory.Product;

import java.util.ArrayList;

public class Cart {
    private ArrayList<Product> items;

    public Cart() {
        this.items = new ArrayList<>();
    }

    public void addItem(Product product) {
        items.add(product);
    }

    // Remove a product from the cart
    public void removeItem(Product product) {
        items.remove(product);
    }

    // Get the total number of items in the cart
    public int getItemCount() {
        return items.size();
    }

    // Calculate the total price of items in the cart
    public double getTotalPrice() {
        double total = 0.0;
        for (Product product : items) {
            total += product.getSellingPrice();
        }
        return total;
    }
}
