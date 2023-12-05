package src.Backend;

import src.Product.Product;
import src.users_code.Buyer;
import src.users_code.Seller;

public class TransactionsHandler {
    private ProductsManager productsManager;
    private double totalPaid;
    public TransactionsHandler(){
        this.productsManager = UserManager.getInstance().getProductsManager();
        this.totalPaid = 0.00;
    }

    public void handleTransactionsOnBuyer(Buyer buyer){
        for(Product p : buyer.getShoppingCart()){
            Seller seller = this.productsManager.getSellerFromProductID(p.getID());
            Product original = this.productsManager.getProductByID(p.getID());

            handleTransaction(seller,original,p);
        }
    }

    private void handleTransaction(Seller seller, Product original, Product fromBuyer) {

    }
}
