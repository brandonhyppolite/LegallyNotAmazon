package src.Backend;

import src.Inventory.Product;
import src.users_code.Buyer;
import src.users_code.Seller;
import src.users_code.User;

import java.io.*;

public class ProductFileHandler {
    private static final String INVENTORY_FILE_PATH = "src/Database/inventory.txt";


    public static void loadProductFromFile(UserManager userManager, ProductsManager productsManager) {
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(";");
                if (productData.length == 6) {
                    User user =  userManager.getUserByUsername(productData[0]);

                    Product product = new Product(
                            productData[1],     // Product Name
                            productData[2], //Product ID
                            Double.parseDouble(productData[3]), //Product invoice price
                            Double.parseDouble(productData[4]),  //Product selling Price
                            Integer.parseInt(productData[5])           //Product Quantity
                    );

                    productsManager.addProductToUser(user,product);
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
    public static void writeInventoryToFile(UserManager userManager) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE_PATH, false))) {
            for(User u: userManager.getUsers()){
                if(u instanceof Seller){
                    for(Product p: ((Seller) u).getProductsForSale()){
                        writeProductToFile(writer, u.getUsername(), p);
                    }
                }else if(u instanceof Buyer){
                    for(Product p: ((Buyer) u).getShoppingCart()){
                        writeProductToFile(writer, u.getUsername(), p);
                    }
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
    private static void writeProductToFile(BufferedWriter writer, String username, Product product) throws IOException {
        String line = String.format("%s;%s;%s;%s;%s;%s\n",
                username,
                product.getName(),
                product.getID(),
                product.getInvoicePrice(),
                product.getSellingPrice(),
                product.getQuantity());
        writer.write(line);
    }
}