package src.Backend;

import src.Inventory.Product;
import src.users_code.Buyer;
import src.users_code.Seller;
import src.users_code.User;

import java.io.*;
import java.util.ArrayList;


public class ProductsManager {
    private static final String INVENTORY_FILE_PATH = "src/Saved_Files/inventory.txt";
    private final ArrayList<Seller> sellers;
    private final ArrayList<Buyer> buyers;
    private final UserManager system;
    public ProductsManager(UserManager s, ArrayList<User> users) {
        this.sellers = new ArrayList<>();
        this.buyers = new ArrayList<>();
        this.system = s;
        this.setUpLists(users);
        loadInventoryFromFile();
    }

    public void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(";");
                if (productData.length == 6) {
                    User user =  this.system.getUserByUsername(productData[0]);

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
    public ArrayList<Product> getItemsFromUser(User u){
        if(u instanceof Seller && sellers.contains(u)){
            return ((Seller) u).getProductsForSale();
        }else if(u instanceof Buyer && buyers.contains(u)){
            return (((Buyer) u).getShoppingCart());
        }
        System.out.println("User was unknown class");
        return null;
    }

    public void addProductToUser(User u, Product p){
        getItemsFromUser(u).add(p);
    }
}
