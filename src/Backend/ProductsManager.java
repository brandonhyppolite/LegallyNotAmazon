package src.Backend;

import src.Inventory.Product;
import src.users_code.Buyer;
import src.users_code.Seller;
import src.users_code.User;

import java.io.*;
import java.util.ArrayList;

/**
 * The `ProductsManager` class manages product-related operations, including loading and saving inventory to a file,
 * setting up lists of sellers and buyers, and adding products to users.
 */
public class ProductsManager {
    private static final String INVENTORY_FILE_PATH = "src/Saved_Files/inventory.txt";
    private final ArrayList<Seller> sellers;
    private final ArrayList<Buyer> buyers;
    private final UserManager userManager;


    /**
     * Constructs a `ProductsManager` with the given `UserManager` and a list of users.
     *
     * @param s     The `UserManager` instance.
     * @param users The list of users.
     */
    public ProductsManager(UserManager s, ArrayList<User> users) {
        this.sellers = new ArrayList<>();
        this.buyers = new ArrayList<>();
        this.userManager = s;
        this.setUpLists(users);
        loadInventoryFromFile();
    }



    /**
     * Loads inventory data from the specified file and adds products to users.
     */
    public void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(";");
                if (productData.length == 6) {
                    User user =  this.userManager.getUserByUsername(productData[0]);

                    Product product = new Product(
                            productData[1],     // Product Name
                            productData[2], //Product ID
                            Double.parseDouble(productData[3]), //Product invoice price
                            Double.parseDouble(productData[4]),  //Product selling Price
                            Integer.parseInt(productData[5])           //Product Quantity
                    );

                    addProductToUser(user,product);
                } else {
                    System.out.println("Skipping invalid product data: " + line);
                }
            }
            System.out.println("Product Manager successfully loaded inventory from file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /**
     * Saves inventory data to the specified file based on the products owned by sellers and in buyers' shopping carts.
     */
    public void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE_PATH, false))) {
            for (Seller seller : sellers) {
                for (Product product : seller.getProductsForSale()) {
                    writeProductToFile(writer, seller.getUsername(), product);
                }
            }

            for (Buyer buyer : buyers) {
                for (Product product : buyer.getShoppingCart()) {
                    writeProductToFile(writer, buyer.getUsername(), product);
                }
            }
            System.out.println("Product Manager successfully saved inventory to file");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Writes product data to the specified writer, including the username of the owner and product details.
     *
     * @param writer  The writer to use for writing.
     * @param username The username of the owner of the product.
     * @param product  The product to write.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    private void writeProductToFile(BufferedWriter writer, String username, Product product) throws IOException {
        String line = String.format("%s;%s;%s;%s;%s;%s\n",
                username,
                product.getName(),
                product.getID(),
                product.getInvoicePrice(),
                product.getSellingPrice(),
                product.getQuantity());
        writer.write(line);
    }




    /**
     * Sets up lists of sellers and buyers based on the provided list of users.
     *
     * @param users The list of users.
     */
    public void setUpLists(ArrayList<User> users){
        for(User user: users){
            if(user instanceof Seller){
                if(!this.sellers.contains(user)){
                    this.sellers.add((Seller) user);
                }
            }else if(user instanceof Buyer){
                if(!this.buyers.contains(user)){
                    this.buyers.add((Buyer) user);
                }
            }
        }
    }



    /**
     * Retrieves a list of products associated with a user (either products for sale or in the shopping cart).
     *
     * @param u The user for whom to retrieve the list of products.
     * @return The list of products associated with the user.
     */
    public ArrayList<Product> getItemsFromUser(User u){
        if(u instanceof Seller && sellers.contains(u)){
            return ((Seller) u).getProductsForSale();
        }else if(u instanceof Buyer && buyers.contains(u)){
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
}
