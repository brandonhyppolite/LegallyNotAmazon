package src.users_code;

import src.Product.Product;

import java.util.ArrayList;
/**
 * The `Buyer` class represents a buyer user.
 */
public class Buyer extends User {
    private ArrayList<Product> shoppingCart;
    private CreditCard card;
    private String address;

    /**
     * Constructor for creating a new `Buyer` object.
     *
     * @param firstName The first name of the buyer.
     * @param lastName  The last name of the buyer.
     * @param username  The username of the buyer.
     * @param password  The password of the buyer.
     * @param email     The email of the buyer.
     */
    public Buyer(String firstName, String lastName, String username, String password, String email) {
        super(firstName, lastName, username, password, email);
        this.shoppingCart = new ArrayList<>();
        this.card = new CreditCard();
    }

    /**
     * Constructor for creating a new `Buyer` object with additional information.
     *
     * @param firstName           The first name of the buyer.
     * @param lastName            The last name of the buyer.
     * @param username            The username of the buyer.
     * @param password            The password of the buyer.
     * @param email               The email of the buyer.
     * @param address             The address of the buyer.
     * @param creditAccountNumber The credit card account number of the buyer.
     * @param creditCVV           The credit card CVV of the buyer.
     * @param creditExpiration    The credit card expiration date of the buyer.
     */
    public Buyer(String firstName, String lastName, String username, String password, String email, String address, String creditAccountNumber, String creditCVV, String creditExpiration) {
        super(firstName, lastName, username, password, email);
        this.shoppingCart = new ArrayList<>();
        this.setAddress(address);
        this.card = new CreditCard();
        this.card.setCreditCardNumber(creditAccountNumber);
        this.card.setCvv(creditCVV);
        this.card.setExpirationDate(creditExpiration);
    }

    /**
     * Gets the credit card of the buyer.
     *
     * @return The credit card of the buyer.
     */
    public CreditCard getCard() {
        return this.card;
    }

    /**
     * Gets the shopping cart of the buyer.
     *
     * @return The shopping cart of the buyer.
     */
    public ArrayList<Product> getShoppingCart() {
        return shoppingCart;
    }

    /**
     * Gets the address of the buyer.
     *
     * @return The address of the buyer.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the buyer.
     *
     * @param address The address of the buyer.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Adds a product to the shopping cart of the buyer.
     *
     * @param product The product to be added to the shopping cart.
     */
    public void addProductToCart(Product product) {
        Product copy = new Product(product);
        copy.setQuantity(1);
        this.shoppingCart.add(copy);
    }

    /**
     * Calculates the total price of all products in the shopping cart.
     *
     * @return The total price of all products in the shopping cart.
     */
    public double getTotalOnCart() {
        double total = 0.0;
        for (Product p : this.shoppingCart) {
            total += p.getSellingPrice();
        }
        return total;
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
