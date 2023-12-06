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
        clearUsersProductDatabase();
        ProductFileHandler.loadProductFromFile(this.userManager, this);
    }


    /**
     * Saves inventory data to the specified file based on the products owned by sellers and in buyers' shopping carts.
     */
    public void saveInventory() {
        ProductFileHandler.writeInventoryToFile(this.userManager);
    }

    private void clearUsersProductDatabase(){
        for(User u: this.userManager.getUsers()){
            u.clearProducts();
        }
    }

    /**
     * Retrieves all items available for sale from all sellers.
     *
     * @return An ArrayList of all items available for sale.
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
     * Retrieves available items that match the given search phrase.
     *
     * @param searchPhrase The search phrase to match.
     * @return An ArrayList of available items that match the search phrase.
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
     * Gets the current quantity of a product in the shopping cart of a buyer.
     *
     * @param buyer   The buyer object.
     * @param product The product object.
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
     * Retrieves a product by its ID.
     *
     * @param id The ID of the product.
     * @return The product with the specified ID, or null if not found.
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
     * Removes products from the shopping carts of all buyers except the one who purchased the product.
     *
     * @param ID The ID of the product to be removed.
     */
   public void removeProductsFromOtherBuyers(String ID){
        for(User u: this.userManager.getUsers()){
            if(u instanceof Buyer){
                ((Buyer) u).getShoppingCart().removeIf(p -> p.getID().equals(ID));
            }
        }
   }

   public void updateProductSellerUsernameInBuyers(String old,String newName){
       for(User u: this.userManager.getUsers()){
           if(u instanceof Buyer){
               for(Product p: ((Buyer) u).getShoppingCart()){
                   if(p.getSellerUserName().equals(old)){
                       p.setSellerUserName(newName);
                   }
               }
           }
       }
   }

}
