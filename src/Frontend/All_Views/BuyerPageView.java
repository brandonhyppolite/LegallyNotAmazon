package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.BuyerTableViewUtility;
import src.Frontend.UserActionCallBack;
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

/**
 * The BuyerPageView class represents the view for the buyer's page.
 */
public class BuyerPageView implements ActionListener, UserActionCallBack {
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

    private final BuyerTableViewUtility tableViewUtility;
    /**
     * Constructs a BuyerPageView object with the specified ViewManager and Buyer.
     *
     * @param vm    The ViewManager object.
     * @param buyer The Buyer object.
     */
    public BuyerPageView(ViewManager vm, Buyer buyer){
        this.userManager = UserManager.getInstance();
        this.vm = vm;
        this.buyer = buyer;
        this.tableViewUtility = new BuyerTableViewUtility(this.buyer,this);

        this.searchButton.setActionCommand("search product");
        this.searchButton.addActionListener(this);


        this.searchField.setActionCommand("search product");
        this.searchField.addActionListener(this);


        this.updateInformationButton.setActionCommand("update information");
        this.updateInformationButton.addActionListener(this);

        this.cartButton.setActionCommand("view cart");
        this.cartButton.addActionListener(this);
        this.cartButton.setText("Cart: $" + String.format("%.2f",this.buyer.getTotalOnCart()));

        this.goToCheckoutButton.setActionCommand("go to checkout");
        this.goToCheckoutButton.addActionListener(this);

        this.logOutButton.setActionCommand("log out");
        this.logOutButton.addActionListener(this);


        setUpMainView();

    }
    /**
     * Gets the buyer page main panel.
     *
     * @return The buyer page main panel.
     */
    public JPanel getBuyerPageMainPanel(){
        return buyerPageMainPanel;
    }

    /**
     * Sets up the main view.
     */
    private void setUpMainView() {
        welcomeLabel.setText("Welcome, " + buyer.getUsername() + "!");
        showProductsForSale("");

    }

    //-------- START FOR METHODS FOR DISPLAYING THE PANELS ------------------------------------------------
    /**
     * Shows the products for sale panel.
     *
     * @param field The search field.
     */
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

    /**
     * Shows the buyer's cart panel.
     */
    private void showBuyerCart(){
        this.cartButton.setText("Cart: $" + String.format("%.2f",this.buyer.getTotalOnCart()));
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.setLayout(new BorderLayout());
            JLabel label = new JLabel("View/Remove your current product(s) in cart by right-clicking below:");
            label.setHorizontalAlignment(JLabel.CENTER);
            mainInfoPanel.add(label, BorderLayout.NORTH);
            mainInfoPanel.add(tableViewUtility.createTable(this.buyer.getShoppingCart()));
            mainInfoPanel.add(drawAddProductPanel(),BorderLayout.SOUTH);
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }
    /**
     * Shows the checkout panel.
     */
    private void showCheckout(){
        String[] columnNames = new String[]{"Name", "ID", "Quantity", "Price ($)", "Seller"};
        this.cartButton.setText("Cart: $" + String.format("%.2f",this.buyer.getTotalOnCart()));
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.setLayout(new BorderLayout());
            JLabel label = new JLabel("View your cart below:");
            label.setHorizontalAlignment(JLabel.CENTER);
            mainInfoPanel.add(label, BorderLayout.NORTH);
            mainInfoPanel.add(tableViewUtility.createCheckoutTable(this.buyer.getShoppingCart(), columnNames));
            mainInfoPanel.add(drawCheckoutPanel(), BorderLayout.SOUTH);
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }
    /**
     * Shows the update information panel.
     */
    private void showUpdateInfo(){
        this.cartButton.setText("Cart: $" + String.format("%.2f",this.buyer.getTotalOnCart()));
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.setLayout(new BorderLayout());
            mainInfoPanel.add(drawUpdateInformationPanel(), BorderLayout.CENTER);
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }
    /**
     * Clears the panels.
     */
    private void clearPanels(){
        mainInfoPanel.removeAll();
    }



    //-------- END FOR METHODS FOR DISPLAYING THE PANELS ---------------------------------------------------



    //-------- START FOR METHODS FOR CREATING/DRAWING THE PANELS ---------------------------------------------------
    /**
     * Draws the products for sale panel.
     *
     * @param products The list of products for sale.
     * @return The scroll pane with the products for sale.
     */
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
    /**
     * Creates the panel for displaying "no products available" message.
     *
     * @return The panel with the "no products available" message.
     */
    private JPanel createNoProductsPanel(){
        JPanel noProductsPanel = new JPanel(new BorderLayout());
        JLabel noProductsLabel = new JLabel("Product(s) unavailable");
        noProductsLabel.setHorizontalAlignment(JLabel.CENTER);
        noProductsPanel.add(noProductsLabel, BorderLayout.CENTER);
        return noProductsPanel;
    }
    /**
     * Creates a product box panel for displaying product details.
     *
     * @param product The product to display.
     * @return The product box panel.
     */
    private JPanel createProductBox(Product product){
        JPanel productBox = new JPanel(new BorderLayout());
        productBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.add(new JLabel("Name: " + product.getName()));
        detailsPanel.add(new JLabel("Price: $" + String.format("%.2f", product.getSellingPrice())));
        int quantity = product.getQuantity() - this.userManager.getProductsManager().
                getCurrentQuantityOfProductsInCart(this.buyer,product);
        detailsPanel.add(new JLabel("In stock: " + quantity));

        productBox.add(detailsPanel, BorderLayout.CENTER);

        // Add mouse listener to show more details on click
        productBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showProductDetails(product);
            }
            @Override
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
    /**
     * Shows the detailed information of a product.
     *
     * @param product The product to display details of.
     */
    private void showProductDetails(Product product){
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.add(createProductDetailsPanel(product));
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }
    /**
     * Creates a panel to display the detailed information of a product.
     *
     * @param product The product to display details of.
     * @return The panel with the product details.
     */
    private JPanel createProductDetailsPanel(Product product) {
        // Create a panel to hold detailed information
        JPanel moreDetails = new JPanel(new GridLayout(0, 1));

        String formattedPrice = String.format("%.2f", product.getSellingPrice());
        int quantity = product.getQuantity() - this.userManager.getProductsManager()
                .getCurrentQuantityOfProductsInCart(this.buyer,product);
        JButton addToCart = createAddToCartButton(product,moreDetails,quantity);
        JButton goBack = new JButton("Back");
        goBack.setActionCommand("go back");
        goBack.addActionListener(this);
        // Use HTML for better formatting (line breaks)
        JLabel nameLabel = new JLabel("<html><b>Name:</b> " + product.getName() + "</html>");
        JLabel priceLabel = new JLabel("<html><b>Price:</b> $" + formattedPrice + "</html>");
        JLabel stockLabel = new JLabel("<html><b>In stock:</b> " + quantity+ "</html>");
        JLabel descriptionLabel = new JLabel("<html><b>Description:</b> " + product.getDescription() + "</html>");
        JLabel typeLabel = new JLabel("<html><b>Type:</b> " + product.getType() + "</html>");
        JLabel sellerLabel = new JLabel("<html><b>Seller:</b> " + product.getSellerUserName() + "</html>");

        moreDetails.add(nameLabel);
        moreDetails.add(priceLabel);
        moreDetails.add(stockLabel);
        moreDetails.add(descriptionLabel);
        moreDetails.add(typeLabel);
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
            this.cartButton.setText("Cart: $" + String.format("%.2f",this.buyer.getTotalOnCart()));
            showProductsForSale(searchField.getText());
    }

    /**
     * Draws the update information panel.
     *
     * @return The update information panel.
     */
    private JPanel drawUpdateInformationPanel(){
        JPanel updateInfo = new JPanel();
        updateInfo.setLayout(new FlowLayout()); // Adjust the layout as needed

        int width = 150 ,height = 25;

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");
        JLabel addressLabel = new JLabel("Address:");


        JLabel creditCardAccount = new JLabel("Credit Card Account Number:");
        JLabel creditCardCVV = new JLabel("Credit Card CVV:");
        JLabel creditCardExpiration = new JLabel("Credit Card Expiration Date:");

        // TextFields
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(width,height));
        usernameField.setText(this.userManager.getBuyerAccountInformation(this.buyer,"username"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(width,height));

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(width,height));
        emailField.setText(this.userManager.getBuyerAccountInformation(this.buyer,"email"));

        JTextField addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(width + 50,height));
        addressField.setText(this.userManager.getBuyerAccountInformation(this.buyer,"address"));

        JTextField creditCardAccountField = new JTextField();
        creditCardAccountField.setPreferredSize(new Dimension(width + 50,height));
        creditCardAccountField.setText(this.userManager.getBuyerAccountInformation(this.buyer,"creditCardAccount"));

        JTextField creditCardCVVField = new JTextField();
        creditCardCVVField.setPreferredSize(new Dimension(width + 50,height));
        creditCardCVVField.setText(this.userManager.getBuyerAccountInformation(this.buyer,"creditCardCVV"));

        JTextField creditCardExpirationField = new JTextField();
        creditCardExpirationField.setPreferredSize(new Dimension(width + 50,height));
        creditCardExpirationField.setText(this.userManager.getBuyerAccountInformation(this.buyer,"creditCardExpiration"));


        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the update logic here
                String newUsername = usernameField.getText();
                String newPassword =    String.valueOf(passwordField.getPassword());
                String email = emailField.getText();
                String address = addressField.getText();
                String creditCardAccount = creditCardAccountField.getText();
                String creditCardCVV = creditCardCVVField.getText();
                String creditCardExpiration = creditCardExpirationField.getText();
                if(!newUsername.equals(userManager.getBuyerAccountInformation(buyer,"username"))){
                    userManager.updateBuyerAccountInformation(buyer,"username",newUsername);
                }
                if(!newPassword.isEmpty()){
                    userManager.updateBuyerAccountInformation(buyer,"password",newPassword);
                }
                if(!email.equals(userManager.getBuyerAccountInformation(buyer,"email"))){
                    userManager.updateBuyerAccountInformation(buyer,"email",email);
                }
                if(!address.equals(userManager.getBuyerAccountInformation(buyer,"address"))){
                    userManager.updateBuyerAccountInformation(buyer,"address",address);
                }
                if(!creditCardAccount.equals(userManager.getBuyerAccountInformation(buyer,"creditCardAccount"))){
                    userManager.updateBuyerAccountInformation(buyer,"creditCardAccount",creditCardAccount);
                }
                if(!creditCardCVV.equals(userManager.getBuyerAccountInformation(buyer,"creditCardCVV"))){
                    userManager.updateBuyerAccountInformation(buyer,"creditCardCVV",creditCardCVV);
                }
                if(!creditCardExpiration.equals(userManager.getBuyerAccountInformation(buyer,"creditCardExpiration"))){
                    userManager.updateBuyerAccountInformation(buyer,"creditCardExpiration",creditCardExpiration);
                }



                // Clear fields after update if needed
                usernameField.setText("");
                passwordField.setText("");
                emailField.setText("");
                addressField.setText("");
                creditCardAccountField.setText("");
                creditCardCVVField.setText("");
                creditCardExpirationField.setText("");
            }
        });

        // Add components to the panel
        updateInfo.add(usernameLabel);
        updateInfo.add(usernameField);
        updateInfo.add(passwordLabel);
        updateInfo.add(passwordField);
        updateInfo.add(emailLabel);
        updateInfo.add(emailField);
        updateInfo.add(addressLabel);
        updateInfo.add(addressField);
        updateInfo.add(creditCardAccount);
        updateInfo.add(creditCardAccountField);
        updateInfo.add(creditCardCVV);
        updateInfo.add(creditCardCVVField);
        updateInfo.add(creditCardExpiration);
        updateInfo.add(creditCardExpirationField);

        updateInfo.add(new JLabel()); // Placeholder for spacing
        updateInfo.add(updateButton);

        return updateInfo;
    }

    private JPanel drawAddProductPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JLabel enterIDLabel = new JLabel("Enter a product ID to add to cart:");
        JTextField enterIDField = new JTextField();
        enterIDField.setPreferredSize(new Dimension(100,25));
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID = enterIDField.getText().toUpperCase();
                Product product = userManager.getProductsManager().getProductByID(ID);
                if(product != null){
                    buyer.addProductToCart(product);
                    String message = "Added to cart: " + product.getName();
                    JOptionPane.showMessageDialog(mainInfoPanel, message, "Cart", JOptionPane.INFORMATION_MESSAGE);
                    cartButton.setText("Cart: $" + String.format("%.2f",buyer.getTotalOnCart()));
                    showBuyerCart();
                }else{
                    String message = "Failed to add to cart! No Product with ID " + ID;
                    JOptionPane.showMessageDialog(mainInfoPanel, message, "Cart", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(enterIDLabel);
        panel.add(enterIDField);
        panel.add(addButton);
        return panel;
    }

    private JPanel drawCheckoutPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton checkout = new JButton("Checkout");
        checkout.setActionCommand("checkout");
        checkout.addActionListener(this);

        panel.add(checkout);
        return panel;
    }


    //-------- END FOR METHODS FOR CREATING/DRAWING THE PANELS ---------------------------------------------------
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "go back":
                showProductsForSale(searchField.getText());
                break;
            case "search product":
                String field = searchField.getText();
                showProductsForSale(field);
                break;
            case "view cart":
                showBuyerCart();
                break;
            case "log out":
                saveAndRefresh();
//                this.vm.showEntryView();
                this.vm.closeApp();
            case "update information":
                showUpdateInfo();
                break;
            case "go to checkout":
                showCheckout();
                break;

            case "checkout":
                this.vm.showBuyerCheckoutView(this.buyer);
                break;
            default:
                System.out.println("Unknown button was clicked");
        }
    }

    @Override
    public void saveAndRefresh() {
        this.userManager.writeUserDataToFile();
        this.userManager.getProductsManager().saveInventory();
    }

    @Override
    public void saveAndRefresh(String ID) {
    }

    @Override
    public void refreshTable() {
        showBuyerCart();
    }
}