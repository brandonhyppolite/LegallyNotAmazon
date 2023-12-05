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
    /**
     * The `UserManager` instance that this `ProductsManager` is associated with.
     */
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

    /**
     * Gets all products that are for sale.
     *
     * @return An `ArrayList` of all products that are for sale.
     */
    public ArrayList<Product> getAllItemsForSale() {
        ArrayList<Product> allItemsForSale = new ArrayList<>();

        for (User u : this.userManager.getUsers()) {
            if (u instanceof Seller) {
                allItemsForSale.addAll(((Seller) u).getProductsForSale());
            }
        }

        return allItemsForSale;
    }
    /**
     * Gets all products that match the given search phrase.
     *
     * @param searchPhrase The search phrase to match products against.
     * @return An `ArrayList` of all products that match the given search phrase.
     */
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
    /**
     * Gets the current quantity of a product in a buyer's shopping cart.
     *
     * @param buyer  The buyer whose shopping cart to check.
     * @param product The product to check the quantity of.
     * @return The current quantity of the product in the buyer's shopping cart.
     */
    public int getCurrentQuantityOfProductsInCart(Buyer buyer, Product product){
        int count = 0;
        for(Product p: buyer.getShoppingCart()){
            if(p.equalsByNameIdSellingPrice(product)){
                count++;
            }
        }
        return count;
    }
    /**
     * Gets a product by its ID.
     *
     * @param id The ID of the product to get.
     * @return The product with the given ID, or `null` if no product with the given ID exists.
     */
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
    /**
     * Gets the seller who owns a product by its ID.
     *
     * @param id The ID of the product to get the seller of.
     * @return The seller who owns the product with the given ID, or `null` if no product with the given ID exists.
     */
    public Seller getSellerFromProductID(String id){
        for(User u: this.userManager.getUsers()){
            if(u instanceof Seller){
                for(Product p: ((Seller) u).getProductsForSale()){
                    if(p.getID().equals(id)){
                        return (Seller) u;
                    }
                }
            }
        }
        return null;
    }

}
