package src.GUI_code.All_Views;

import src.Backend.ShoppingSystem;
import src.GUI_code.ViewManager;
import src.Inventory.Product;
import src.users_code.Seller;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SellerHomePageView {
    private JPanel sellerHomePageMainPanel;
    private JLabel welcomeUserLabel;
    private JPanel sellerHomePageInfoPanel;
    private JPanel buttonPanel;
    private JButton salesDataButton;
    private JButton addANewProductButton;
    private JButton logOutButton;
    private JPanel productsPanel;
    private ViewManager vm;
    private ShoppingSystem system;

    private Seller seller;
    public SellerHomePageView(ViewManager v, Seller seller){
        this.vm = v;
        this.seller = seller;
        this.system = ShoppingSystem.getInstance();

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.showEntryView();
                //More functions for saving any changes Seller made
            }
        });

        displaySellerProducts();
    }

    public JPanel getMainPanel(){
        return sellerHomePageMainPanel;
    }

    public void displaySellerProducts() {
        sellerHomePageInfoPanel.setLayout(new GridLayout(0, 1));
        setUpProductPanel();
        sellerHomePageInfoPanel.add(productsPanel);
        welcomeUserLabel.setText("Welcome, " + seller.getUsername() + "!");

    }

    private void setUpProductPanel(){
        productsPanel.setLayout(new GridLayout(2,1));
        productsPanel.add(drawProductTable());
        productsPanel.add(drawProductRemoval());
    }

    private JTable drawProductTable(){
        ArrayList<Product> products = this.seller.getSellerProducts();
        int numRows = products.size();
        int numCols = 5;

        // Creating the 2D array dynamically
        String[][] productData = new String[numRows][numCols];
        String[] columnNames =new String[]{"Name", "ID", "Quantity", "Price ($)", "Selling Price ($)"};

        for (int i = 0; i < numRows; i++) {
            Product product = products.get(i);
            productData[i][0] = product.getName();
            productData[i][1] = product.getID();
            productData[i][2] = String.valueOf(product.getQuantity());
            productData[i][3] = String.valueOf(product.getPrice());
            productData[i][4] = String.valueOf(product.getPrice());
        }

        JTable table = new JTable(productData, columnNames);
        JScrollPane sp = new JScrollPane(table);
        productsPanel.add(sp);
        return table;
    }
    private JPanel drawProductRemoval(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));
        JLabel label = new JLabel("Enter Product ID to remove:");
        JTextField textField = new JTextField();
        JButton removeButton = new JButton("Remove");
        panel.add(label);
        panel.add(textField);
        panel.add(removeButton);
        return panel;
    }
}
