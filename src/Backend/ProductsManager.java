package src.Backend;

import src.Product.Product;
import src.users_code.Buyer;
import src.users_code.Seller;
import src.users_code.User;

import java.util.ArrayList;

/**
 * The `ProductsManager` class manages product-related operations, including loading and saving inventory to a file,
 * setting up lists of sellers and buyers, and adding products to users.
 */
public class ProductsManager {
    private final UserManager userManager;


    /**
     * Constructs a `ProductsManager` with the given `UserManager`
     *
     * @param u     The `UserManager` instance.
     */
    public ProductsManager(UserManager u) {
        this.userManager = u;
        loadInventory();
    }



    /**
     * Loads inventory data from the specified file and adds products to users.
     */
    public void loadInventory() {
        ProductFileHandler.loadProductFromFile(this.userManager, this);
    }



    /**
     * Saves inventory data to the specified file based on the products owned by sellers and in buyers' shopping carts.
     */
    public void saveInventory() {
        ProductFileHandler.writeInventoryToFile(this.userManager);
    }


    public ArrayList<Product> getAllItemsForSale() {
        ArrayList<Product> allItemsForSale = new ArrayList<>();

        for (User u : this.userManager.getUsers()) {
            if (u instanceof Seller) {
                allItemsForSale.addAll(((Seller) u).getProductsForSale());
            }
        }

        return allItemsForSale;
    }

    public ArrayList<Product> getAvailableItems(String searchPhrase) {
        ArrayList<Product> availableItems = new ArrayList<>();

        for (User u : this.userManager.getUsers()) {
            if (u instanceof Seller) {
                for (Product product : ((Seller) u).getProductsForSale()) {
                    if (product.getName().toLowerCase().contains(searchPhrase.toLowerCase())) {
                        availableItems.add(product);
                    }
                }
            }
        }

        return availableItems;
    }

    public int getCurrentQuantityOfProductsInCart(Buyer buyer, Product product){
        int count = 0;
        for(Product p: buyer.getShoppingCart()){
            if(p.equalsByNameIdSellingPrice(product)){
                count++;
            }
        }
        return count;
    }

    public Product getProductByID(String id){
        for(User u: this.userManager.getUsers()){
            if(u instanceof Seller){
                for(Product p: ((Seller) u).getProductsForSale()){
                    if(p.getID().equals(id)){
                        return p;
                    }
                }
            }
        }
        return null;
    }

   public void removeProductsFromOtherBuyers(String ID){
        for(User u: this.userManager.getUsers()){
            if(u instanceof Buyer){
                ((Buyer) u).getShoppingCart().removeIf(p -> p.getID().equals(ID));
            }
        }
   }

}
