package src.users_code;

import src.Product.Product;

import java.util.ArrayList;

public class Buyer extends User{
    private ArrayList<Product> shoppingCart;
    private CreditCard card;

    public Buyer(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password,email);
        this.shoppingCart = new ArrayList<>();
    }


    public CreditCard getCard(){
        return this.card;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public void addProductToCart(Product product){
        Product copy = new Product(product);
        copy.setQuantity(1);
        this.shoppingCart.add(copy);
    }
}
