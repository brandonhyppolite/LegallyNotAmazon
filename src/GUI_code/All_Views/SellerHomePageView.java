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

public class SellerHomePageView {
    private JPanel sellerHomePageMainPanel;
    private JLabel welcomeUserLabel;
    private JPanel sellerHomePageInfoPanel;
    private JPanel buttonPanel;
    private JButton salesDataButton;
    private JButton addANewProductButton;
    private JButton logOutButton;
    private SellerProductsPanel productsPanel;
    private ViewManager vm;
    private ShoppingSystem system;

    public SellerHomePageView(ViewManager v){
        this.vm = v;
        this.system = ShoppingSystem.getInstance();

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vm.showEntryView();
            }
        });
    }

    public JPanel getMainPanel(){
        return sellerHomePageMainPanel;
    }

    public void displaySellerProducts(String username) {
        Seller seller = (Seller) this.system.getUserByUsername(username);
        sellerHomePageInfoPanel.setLayout(new GridLayout(0, 1));
        productsPanel = new SellerProductsPanel();
        productsPanel.displayProducts(seller);
        sellerHomePageInfoPanel.add(productsPanel);
        welcomeUserLabel.setText("Welcome, " + seller.getUsername() + "!");

    }

}
