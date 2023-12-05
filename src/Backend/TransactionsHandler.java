package src.Backend;

import src.Product.Product;
import src.users_code.Buyer;
import src.users_code.Seller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The `TransactionsHandler` class handles transactions between buyers and sellers.
 */
public class TransactionsHandler {
    private final UserManager userManager;
    private double totalPaid;
    List<Product> productsToRemove;

    /**
     * Constructs a `TransactionsHandler` object.
     */
    public TransactionsHandler(){
        this.userManager = UserManager.getInstance();
        this.productsToRemove = new ArrayList<>();
        this.totalPaid = 0.00;
    }
    /**
     * Handles transactions on a buyer, updating product quantities, seller revenues, and buyer shopping cart.
     *
     * @param buyer The buyer involved in the transactions.
     */
    public void handleTransactionsOnBuyer(Buyer buyer) {

        for (Product p : buyer.getShoppingCart()) {
            Seller seller = (Seller) this.userManager.getUserByUsername(p.getSellerUserName());
            Product original = this.userManager.getProductsManager().getProductByID(p.getID());
            handleTransaction(seller, original, p);

        }

        // Remove the products from the buyer's shopping cart
        buyer.getShoppingCart().removeAll(productsToRemove);

        // Save the updated user data and inventory
        this.userManager.writeUserDataToFile();
        this.userManager.getProductsManager().saveInventory();
    }


    /**
     * Handles a single transaction between a buyer and a seller.
     *
     * @param seller      The seller in the transaction.
     * @param original    The original product being sold.
     * @param fromBuyer   The product being purchased by the buyer.
     */
    private void handleTransaction(Seller seller,Product original, Product fromBuyer) {
        if(original.getQuantity() > 0){
            // Update the seller's product quantity
            seller.getProductsForSale().set(seller.getProductsForSale().indexOf(original),original.sell(1));
            // Update the seller's revenue
            double revenue = original.getSellingPrice();
            seller.setRevenues(seller.getRevenues() + revenue);
            // Add the product to the list of products to remove from the buyer's shopping cart
            productsToRemove.add(fromBuyer);
            // Update the total amount paid
            totalPaid += revenue;
        }
    }
    /**
     * Gets the total amount paid in transactions.
     *
     * @return The total amount paid.
     */
    public double getTotalPaid(){
        return totalPaid;
    }
}
