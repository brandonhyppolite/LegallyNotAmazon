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

    /**
     * Retrieves a list of products associated with a user (either products for sale or in the shopping cart).
     *
     * @param u The user for whom to retrieve the list of products.
     * @return The list of products associated with the user.
     */
    public ArrayList<Product> getItemsFromUser(User u){
        if(u instanceof Seller && this.userManager.getUsers().contains(u)){
            return ((Seller) u).getProductsForSale();
        }else if(u instanceof Buyer && userManager.getUsers().contains(u)){
            return (((Buyer) u).getShoppingCart());
        }
        System.out.println("User was unknown class");
        return null;
    }


    /**
     * Adds a product to the list of products associated with a user.
     *
     * @param u The user to whom the product will be added.
     * @param p The product to add.
     */
    public void addProductToUser(User u, Product p){
        getItemsFromUser(u).add(p);
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

}
