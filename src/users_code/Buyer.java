package src.users_code;

import src.Product.Product;

import java.util.ArrayList;

public class Buyer extends User{
    private ArrayList<Product> shoppingCart;
    private CreditCard card;
    private String address;
    public Buyer(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password,email);
        this.shoppingCart = new ArrayList<>();
        this.card = new CreditCard();
    }

    public Buyer(String firstName, String lastName, String username, String password, String email,String address,String creditAccountNumber,String creditCVV, String creditExpiration) {
        super(firstName, lastName, username, password,email);
        this.shoppingCart = new ArrayList<>();
        this.setAddress(address);
        this.card = new CreditCard();
        this.card.setCreditCardNumber(creditAccountNumber);
        this.card.setCvv(creditCVV);
        this.card.setExpirationDate(creditExpiration);
    }


    public CreditCard getCard(){
        return this.card;
    }

    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addProductToCart(Product product){
        Product copy = new Product(product);
        copy.setQuantity(1);
        this.shoppingCart.add(copy);
    }

    @Override
    public String toString() {
        return "Buyer{" +
                "username='" + getUsername() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", firstName='" + getFirstName() + '\'' +
                ", lastName='" + getLastName() + '\'' +
                ", creditCard=" + (card != null ? card.toString() : "null") +
                '}';
    }
}
