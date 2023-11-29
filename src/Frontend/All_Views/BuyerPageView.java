package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.BuyerViewDrawer;
import src.Frontend.ViewManager;
import src.Product.Product;
import src.users_code.Buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
                BuyerViewDrawer.drawProductListing(mainInfoPanel,availableItems, buyer);
            }
        });
        updateInformationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        cartButton.addActionListener(new ActionListener() {
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

                    public void actionPerformed(ActionEvent e) {
                        userManager.writeUserDataToFile();
                        vm.showEntryView();
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

    //-------- START FOR METHODS FOR DISPLAYING THE PANELS ------------------------------------------------
    private void showProductsForSale(){
        ArrayList<Product> productsForSale= this.userManager.getProductsManager().getAllItemsForSale();
        SwingUtilities.invokeLater(() -> {
            clearPanels();

            if (productsForSale.isEmpty()) {
                mainInfoPanel.setLayout(new BorderLayout());
                mainInfoPanel.add(createNoProductsPanel(), BorderLayout.CENTER);
            } else {
                mainInfoPanel.setLayout(new BorderLayout());
                mainInfoPanel.add(drawProductsForSale(productsForSale), BorderLayout.CENTER);
            }

            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }


    private void clearPanels(){
        mainInfoPanel.removeAll();
    }



    //-------- END FOR METHODS FOR DISPLAYING THE PANELS ---------------------------------------------------



    //-------- START FOR METHODS FOR CREATING/DRAWING THE PANELS ---------------------------------------------------
    private JScrollPane drawProductsForSale(ArrayList<Product> products){
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0,3,10,10));
        for(Product p: products){
            productsPanel.add(createProductBox(p));
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    private JPanel createNoProductsPanel(){
        JPanel noProductsPanel = new JPanel(new BorderLayout());
        JLabel noProductsLabel = new JLabel("Product(s) unavailable");
        noProductsLabel.setHorizontalAlignment(JLabel.CENTER);
        noProductsPanel.add(noProductsLabel, BorderLayout.CENTER);
        return noProductsPanel;
    }
    private JPanel createProductBox(Product product){
        JPanel productBox = new JPanel(new BorderLayout());
        productBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.add(new JLabel("Name: " + product.getName()));
        detailsPanel.add(new JLabel("Price: $" + product.getSellingPrice()));
        detailsPanel.add(new JLabel("In stock: " + product.getQuantity()));

        productBox.add(detailsPanel, BorderLayout.CENTER);

        // Add mouse listener to show more details on click
        productBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            } @Override
            public void mouseEntered(MouseEvent e) {
                productBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                productBox.setCursor(Cursor.getDefaultCursor());
            }
        });

        return productBox;
    }


}