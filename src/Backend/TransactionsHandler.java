package src.Backend;

import src.Product.Product;
import src.users_code.Buyer;
import src.users_code.Seller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TransactionsHandler {
    private final UserManager userManager;
    private double totalPaid;
    List<Product> productsToRemove;
    public TransactionsHandler(){
        this.userManager = UserManager.getInstance();
        this.productsToRemove = new ArrayList<>();
        this.totalPaid = 0.00;
    }

    public void handleTransactionsOnBuyer(Buyer buyer) {

        for (Product p : buyer.getShoppingCart()) {
            Seller seller = (Seller) this.userManager.getUserByUsername(p.getSellerUserName());
            Product original = this.userManager.getProductsManager().getProductByID(p.getID());
            handleTransaction(seller, original, p);

        }

        // Remove the products outside the iteration
        buyer.getShoppingCart().removeAll(productsToRemove);

        this.userManager.writeUserDataToFile();
        this.userManager.getProductsManager().saveInventory();
    }



    private void handleTransaction(Seller seller,Product original, Product fromBuyer) {
        if(original.getQuantity() > 0){
            seller.getProductsForSale().set(seller.getProductsForSale().indexOf(original),original.sell(1));
            double revenue = original.getSellingPrice();
            seller.setRevenues(seller.getRevenues() + revenue);
            productsToRemove.add(fromBuyer);
            totalPaid += revenue;
        }
    }
    public double getTotalPaid(){
        return totalPaid;
    }
}
