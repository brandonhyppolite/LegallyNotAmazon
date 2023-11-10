package src.GUI_code.All_Views;

import src.Backend.ShoppingSystem;
import src.Inventory.Product;
import src.users_code.Seller;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SellerProductsPanel extends JPanel{
    private JTable productTable;
    private ShoppingSystem system;
    private String[] columnNames =new String[]{"Name", "ID", "Quantity", "Price ($)", "Selling Price ($)"};;
    private JPanel productRemovalPanel;
    public SellerProductsPanel(){
        this.system =ShoppingSystem.getInstance();
        this.setLayout(new BorderLayout());
        this.productRemovalPanel = new JPanel();
        drawProductRemoval();
        this.add(productRemovalPanel, BorderLayout.SOUTH);
    }


//    public void displayProducts(Seller seller) {
//        if(this.productTable != null){
//            this.productTable.removeAll();
//        }
//
//        ArrayList<Product> products = seller.getSellerProducts();
//        int numRows = products.size();
//        if (numRows != 0) {
//            int numCols = 5;
//
//            // Creating the 2D array dynamically
//            String[][] productData = new String[numRows][numCols];
//
//            for (int i = 0; i < numRows; i++) {
//                Product product = products.get(i);
//                productData[i][0] = product.getName();
//                productData[i][1] = product.getID();
//                productData[i][2] = String.valueOf(product.getQuantity());
//                productData[i][3] = String.valueOf(product.getPrice());
//                productData[i][4] = String.valueOf(product.getPrice());
//            }
//
//            productTable = new JTable(productData, columnNames);
//            JScrollPane sp = new JScrollPane(productTable);
//            this.add(sp); // Add the scroll pane, not the table directly
//        } else {
//            // Draw an empty table with column names
//
//            // Column Names
//            String[] columnNames = {"Name", "ID", "Quantity", "Price", "Selling Price"};
//
//            // Empty Data (2D array with 0 rows)
//            String[][] emptyData = new String[0][columnNames.length];
//
//            // Creating the JTable
//            productTable = new JTable(emptyData, columnNames);
//
//            // Set up JScrollPane to display the empty table
//            JScrollPane sp = new JScrollPane(productTable);
//
//            // Add the scroll pane to the panel
//            this.add(sp);
//        }
//    }

    public void displayProducts(Seller seller){
        this.productTable = getProductTable(seller);
    }
    public void drawProductRemoval(){
        this.productRemovalPanel.removeAll();
        JLabel removalLabel = new JLabel("Enter product ID to remove");
        JTextField textField = new JTextField();
        textField.setSize(200,20);
        JButton removeButton = new JButton("Remove Product");
        removeButton.setSize(100,20);
        this.productRemovalPanel.setLayout(new GridLayout(1,3));
        this.productRemovalPanel.add(removalLabel);
        this.productRemovalPanel.add(textField);
        this.productRemovalPanel.add(removeButton);
    }
    public JTable getProductTable(Seller seller){
        ArrayList<Product> products = seller.getSellerProducts();
        int numRows = products.size();
        if (numRows != 0) {
            int numCols = 5;

            // Creating the 2D array dynamically
            String[][] productData = new String[numRows][numCols];

            for (int i = 0; i < numRows; i++) {
                Product product = products.get(i);
                productData[i][0] = product.getName();
                productData[i][1] = product.getID();
                productData[i][2] = String.valueOf(product.getQuantity());
                productData[i][3] = String.valueOf(product.getPrice());
                productData[i][4] = String.valueOf(product.getPrice());
            }

            JTable table = new JTable(productData, columnNames);
            JScrollPane sp = new JScrollPane(productTable);
            this.add(sp); // Add the scroll pane, not the table directly
            return table;
        } else {
            // Draw an empty table with column names

            // Column Names
            String[] columnNames = {"Name", "ID", "Quantity", "Price", "Selling Price"};

            // Empty Data (2D array with 0 rows)
            String[][] emptyData = new String[0][columnNames.length];

            // Creating the JTable
            JTable table = new JTable(emptyData, columnNames);

            // Set up JScrollPane to display the empty table
            JScrollPane sp = new JScrollPane(productTable);

            // Add the scroll pane to the panel
            this.add(sp);
            return table;
        }
    }

}
