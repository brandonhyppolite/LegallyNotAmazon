package src.Inventory;

import src.Backend.ShoppingSystem;
import src.users_code.Seller;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class StoreInventory {
    private static final String INVENTORY_FILE_PATH = "src/inventory.txt";
    private HashMap<Seller,ArrayList<Product>> inventory;

    private ShoppingSystem system;
    private static StoreInventory instance;
    private StoreInventory() {
        this.inventory = new HashMap<>();
        this.system = ShoppingSystem.getInstance();
        loadInventoryFromFile();
    }


    public static StoreInventory getInstance() {
        if (instance == null) {
            instance = new StoreInventory();
        }
        return instance;
    }

    public HashMap<Seller,ArrayList<Product>> getFullInventory(){
        return this.inventory;
    }

    public ArrayList<Product> getSellerInventory(Seller s){
        return this.inventory.get(s);
    }
    private void loadInventoryFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(INVENTORY_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] productData = line.split(";");
                if (productData.length == 6) {
                    Seller seller = (Seller) this.system.getUserByUsername(productData[0]);
                    Product product = new Product(
                            productData[1],     // Product Name
                            productData[2], //Product ID
                            Double.parseDouble(productData[3]), //Product invoice price
                            Double.parseDouble(productData[4]),  //Product selling Price
                            Integer.parseInt(productData[5])           //Product Quantity
                    );
                    addProductToInventory(seller, product);
                } else {
                    System.out.println("Skipping invalid product data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addProductToInventory(Seller seller, Product product) {
        inventory.computeIfAbsent(seller, k -> new ArrayList<>()).add(product);
        saveInventoryToFile();
    }

    private void saveInventoryToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(INVENTORY_FILE_PATH))) {
            for (Seller seller : inventory.keySet()) {
                for (Product product : this.getSellerInventory(seller)) {
                    String line = String.format("%s;%s;%s;%s;%s;%s\n",
                            seller.getUsername(),
                            product.getName(),
                            product.getID(),
                            product.getInvoicePrice(),
                            product.getSellingPrice(),
                            product.getQuantity());
                    writer.write(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
