package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.ViewManager;
import src.Product.Product;
import src.users_code.Buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

public class BuyerPageView implements ActionListener{
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


        this.searchButton.setActionCommand("search product");
        this.searchButton.addActionListener(this::actionPerformed);


        this.searchField.setActionCommand("search product");
        this.searchField.addActionListener(this::actionPerformed);


        this.updateInformationButton.setActionCommand("update information");
        this.updateInformationButton.addActionListener(this::actionPerformed);

        this.cartButton.setActionCommand("view cart");
        this.cartButton.addActionListener(this::actionPerformed);

        this.goToCheckoutButton.setActionCommand("go to checkout");
        this.goToCheckoutButton.addActionListener(this::actionPerformed);

        this.logOutButton.setActionCommand("log out");
        this.logOutButton.addActionListener(this::actionPerformed);


        setUpMainView();

    }

    public JPanel getBuyerPageMainPanel(){
        return buyerPageMainPanel;
    }


    private void setUpMainView() {
        welcomeLabel.setText("Welcome, " + buyer.getUsername() + "!");
        showProductsForSale("");

    }

    //-------- START FOR METHODS FOR DISPLAYING THE PANELS ------------------------------------------------
    private void showProductsForSale(String field){
        ArrayList<Product> productsForSale;
        if(field.isEmpty()){
             productsForSale = this.userManager.getProductsManager().getAllItemsForSale();
        }else{
            productsForSale = this.userManager.getProductsManager().getAvailableItems(field);
        }

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
        int quantity = product.getQuantity() - this.userManager.getProductsManager().
                getCurrentQuantityOfProductsInCart(this.buyer,product);
        detailsPanel.add(new JLabel("In stock: " + quantity));

        productBox.add(detailsPanel, BorderLayout.CENTER);

        // Add mouse listener to show more details on click
        productBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showProductDetails(product);
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

    private void showProductDetails(Product product){
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.add(createProductDetailsPanel(product));
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }

    private JPanel createProductDetailsPanel(Product product) {
        // Create a panel to hold detailed information
        JPanel moreDetails = new JPanel(new GridLayout(0, 1));
        String searchedField = searchField.getText();
        // Format the price using NumberFormat for currency formatting
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedPrice = currencyFormat.format(product.getSellingPrice());
        int quantity = product.getQuantity() - this.userManager.getProductsManager()
                .getCurrentQuantityOfProductsInCart(this.buyer,product);
        JButton addToCart = createAddToCartButton(product,moreDetails,quantity);
        JButton goBack = new JButton("Back");
        goBack.setActionCommand("go back");
        goBack.addActionListener(this::actionPerformed);
        // Use HTML for better formatting (line breaks)
        JLabel nameLabel = new JLabel("<html><b>Name:</b> " + product.getName() + "</html>");
        JLabel priceLabel = new JLabel("<html><b>Price:</b> " + formattedPrice + "</html>");
        JLabel stockLabel = new JLabel("<html><b>In stock:</b> " + quantity+ "</html>");
        JLabel descriptionLabel = new JLabel("<html><b>Description:</b> " + product.getDescription() + "</html>");
        JLabel sellerLabel = new JLabel("<html><b>Seller:</b> " + product.getSellerUserName() + "</html>");

        moreDetails.add(nameLabel);
        moreDetails.add(priceLabel);
        moreDetails.add(stockLabel);
        moreDetails.add(descriptionLabel);
        moreDetails.add(sellerLabel);
        moreDetails.add(addToCart);
        moreDetails.add(goBack);
        return moreDetails;
    }

    private JButton createAddToCartButton(Product product, JPanel moreDetails, int quantity) {
        JButton addToCart = new JButton();
        if (quantity <= 0) {
            addToCart.setText("Out of Stock");
            addToCart.setEnabled(false);  // Disable button if out of stock
        } else {
            addToCart.setText("Add to Cart");
            addToCart.addActionListener(e -> {
                onAddToCartClicked(product,moreDetails);
            });
        }
        addToCart.setActionCommand("add to cart");
        return addToCart;
    }


    private void onAddToCartClicked(Product product, JPanel moreDetails){
            this.buyer.addProductToCart(product);
            moreDetails.revalidate();
            String message = "Added to cart: " + product.getName();
            JOptionPane.showMessageDialog(moreDetails, message, "Cart", JOptionPane.INFORMATION_MESSAGE);
            showProductsForSale(searchField.getText());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "log out":
                vm.showEntryView();
                break;
            case "go back":
                showProductsForSale(searchField.getText());
                break;
            case "search product":
                String field = searchField.getText();
                showProductsForSale(field);
                break;
            default:
                System.out.println("Unknown button was clicked");
        }
    }
}