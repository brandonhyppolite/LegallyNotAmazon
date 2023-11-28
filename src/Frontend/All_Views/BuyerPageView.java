package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.BuyerViewDrawer;
import src.Frontend.ViewManager;
import src.Product.Product;
import src.users_code.Buyer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class BuyerPageView {
    private JPanel buyerPageMainPanel;
    private JLabel welcomeLabel;
    private JPanel buyerInfoPanel;
    private JPanel navBarPanel;
    private JPanel mainInfoPanel;
    private JButton cartButton;
    private JButton goToCheckoutButton;
    private JPanel searchPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton logOutButton;
    private JButton updateInformationButton;

    private final ViewManager vm;
    private final Buyer buyer;
    private final UserManager userManager;
    public BuyerPageView(ViewManager vm, Buyer buyer){
        this.userManager = UserManager.getInstance();
        this.vm = vm;
        this.buyer = buyer;


        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String field = searchField.getText();
                ArrayList<Product> availableItems = userManager.getProductsManager().getAvailableItems(field);
                BuyerViewDrawer.drawProductListing(mainInfoPanel,availableItems);
            }
        });
        updateInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        goToCheckoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


        setUpMainView();

    }

    public JPanel getBuyerPageMainPanel(){
        return buyerPageMainPanel;
    }


    private void setUpMainView() {
        welcomeLabel.setText("Welcome, " + buyer.getUsername() + "!");
        showProductsForSale();

    }

    private void showProductsForSale(){
        BuyerViewDrawer.drawProductListing(this.mainInfoPanel,this.userManager.getProductsManager().getAllItemsForSale());
    }


}