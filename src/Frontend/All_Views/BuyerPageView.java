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
import java.text.NumberFormat;
import java.util.ArrayList;

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
    public BuyerPageView(ViewManager vm, Buyer buyer){
        this.userManager = UserManager.getInstance();
        this.vm = vm;
        this.buyer = buyer;
        this.tableViewUtility = new BuyerTableViewUtility(this.buyer,this);

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

    private void showBuyerCart(){
        String[] columnNames = new String[]{"Name", "ID", "Quantity", "Selling Price ($)"};
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.setLayout(new BorderLayout());
            JLabel label = new JLabel("View/Remove your current product(s) below:");
            label.setHorizontalAlignment(JLabel.CENTER);
            mainInfoPanel.add(label, BorderLayout.NORTH);
            mainInfoPanel.add(tableViewUtility.createTable(this.buyer.getShoppingCart(),columnNames));
            mainInfoPanel.revalidate();
            mainInfoPanel.repaint();
        });
    }

    private void showUpdateInfo(){
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainInfoPanel.setLayout(new BorderLayout());
            mainInfoPanel.add(drawUpdateInformationPanel(), BorderLayout.CENTER);
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
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(width,height));
        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(width,height));
        JTextField addressField = new JTextField();
        addressField.setPreferredSize(new Dimension(width + 50,height));
        JTextField creditCardAccountField = new JTextField();
        creditCardAccountField.setPreferredSize(new Dimension(width + 50,height));
        JTextField creditCardCVVField = new JTextField();
        creditCardCVVField.setPreferredSize(new Dimension(width + 50,height));
        JTextField creditCardExpirationField = new JTextField();
        creditCardExpirationField.setPreferredSize(new Dimension(width + 50,height));


        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the update logic here
                String newUsername = usernameField.getText();
                char[] newPassword = passwordField.getPassword();
                String email = emailField.getText();
                String address = addressField.getText();
                String creditCardAccount = creditCardAccountField.getText();
                String creditCardCVV = creditCardCVVField.getText();
                String creditCardExpiration = creditCardExpirationField.getText();

                // Perform actions with the updated information
                // ...

                // Clear fields after update if needed
                usernameField.setText("");
                passwordField.setText("");
                creditCardAccountField.setText("");
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
                this.userManager.getProductsManager().saveInventory();
                this.vm.showEntryView();
            case "update information":
                showUpdateInfo();
                break;
            default:
                System.out.println("Unknown button was clicked");
        }
    }

    @Override
    public void saveAndRefresh() {
        this.userManager.getProductsManager().saveInventory();
        showBuyerCart();
    }
}