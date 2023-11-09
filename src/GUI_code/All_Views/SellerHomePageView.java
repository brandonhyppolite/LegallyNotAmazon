package src.GUI_code.All_Views;

import src.Backend.ShoppingSystem;
import src.GUI_code.ViewManager;
import src.Inventory.Product;

import javax.swing.*;
import java.awt.*;

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

    public SellerHomePageView(ViewManager v){
        this.vm = v;
        this.system = ShoppingSystem.getInstance();
        setSellerValues();
    }

    public JPanel getMainPanel(){
        return sellerHomePageMainPanel;
    }

    private void setSellerValues() {

        productsPanel.removeAll();
//        productsPanel.setLayout(new GridLayout());

        // Iterate through the list of products
        for (Product product : this.system.getStoreInventory()) {
            // Create a visual representation for each product

            JLabel productLabel = new JLabel(product.toString());

            // Add the visual representation to the productsPanel
            productsPanel.add(productLabel);
        }

        // Repaint the productsPanel to reflect the changes
        productsPanel.revalidate();
        productsPanel.repaint();
    }
}
