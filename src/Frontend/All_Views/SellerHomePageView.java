package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.SellerTableViewUtility;
import src.Frontend.UserActionCallBack;
import src.Frontend.ViewManager;
import src.Product.Product;
import src.users_code.Seller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;


/**
 * The `SellerHomePageView` class represents the graphical user interface for the home page of a Seller.
 * It allows Sellers to view, edit, and add products, as well as view sales data.
 */
public class SellerHomePageView implements ActionListener,UserActionCallBack {
    private JPanel sellerHomePageMainPanel;
    private JLabel welcomeUserLabel;
    private JPanel sellerHomePageInfoPanel;
    private JPanel buttonPanel;
    private JButton salesDataButton;
    private JButton addANewProductButton;
    private JButton logOutButton;
    private JPanel mainDataPanel;
    private JButton viewCurrentProductInfoButton;
    private JButton updateInformation;
    private final ViewManager vm;
    private final UserManager userManager;
    private final Seller seller;
    private final SellerTableViewUtility tableViewUtility;

    /**
     * Constructs a `SellerHomePageView` with the given `ViewManager` and `Seller`.
     *
     * @param v      The `ViewManager` instance.
     * @param seller The `Seller` for whom the home page is displayed.
     */
    public SellerHomePageView(ViewManager v, Seller seller){
        this.vm = v;
        this.userManager = UserManager.getInstance();
        this.seller = seller;
        this.seller.setSalesData();
        this.tableViewUtility = new SellerTableViewUtility(this.seller, this);


        logOutButton.setActionCommand("log out");
        logOutButton.addActionListener(this);


        addANewProductButton.setActionCommand("add new product");
        addANewProductButton.addActionListener(this);


        viewCurrentProductInfoButton.setActionCommand("view products");
        viewCurrentProductInfoButton.addActionListener(this);

        updateInformation.setActionCommand("update info");
        updateInformation.addActionListener(this);
        salesDataButton.setActionCommand("sales data");
        salesDataButton.addActionListener(this);


        setUpMainView();
        showSellerProducts();
    }


    /**
     * Gets the main panel of the Seller home page.
     *
     * @return The main panel.
     */
    public JPanel getMainPanel(){
        return sellerHomePageMainPanel;
    }


    /**
     * Sets up the main view by configuring the layout and displaying the welcome message.
     */
    public void setUpMainView() {
        sellerHomePageInfoPanel.setLayout(new BorderLayout());
        sellerHomePageInfoPanel.add(buttonPanel,BorderLayout.NORTH);
        sellerHomePageInfoPanel.add(mainDataPanel,BorderLayout.CENTER);
        welcomeUserLabel.setText("Welcome, " + seller.getUsername() + "!");

    }

    /**
     * Displays the product panel, allowing Sellers to view and edit their current products.
     */
    private void showSellerProducts() {
        String[] columnNames = new String[]{"Name", "ID", "Quantity", "Invoice Price ($)", "Selling Price ($)"};
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainDataPanel.setLayout(new BorderLayout());
            JLabel label = new JLabel("View/Edit your current product(s) by right-clicking below:");
            label.setHorizontalAlignment(JLabel.CENTER);
            mainDataPanel.add(label, BorderLayout.NORTH);
            mainDataPanel.add(tableViewUtility.createTable(this.seller.getProductsForSale()));
            mainDataPanel.add(drawProductRemoval(), BorderLayout.SOUTH);
            mainDataPanel.revalidate();
            mainDataPanel.repaint();
        });
    }

    /**
     * Displays the panel for adding a new product.
     */
    private void showAddNewProductPanel() {
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainDataPanel.setLayout(new BorderLayout());
            mainDataPanel.add(drawAddNewProductPanel(), BorderLayout.CENTER);
            mainDataPanel.revalidate();
            mainDataPanel.repaint();
        });
    }

    private void showUpdateInfo(){
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainDataPanel.setLayout(new BorderLayout());
            mainDataPanel.add(drawUpdateInformationPanel(), BorderLayout.CENTER);
            mainDataPanel.revalidate();
            mainDataPanel.repaint();
        });
    }
    /**
     * Displays the sales data panel, showing costs, revenue, and profits.
     */
    private void showSalesDataPanel(){
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainDataPanel.setLayout(new BorderLayout());
            mainDataPanel.add(drawSalesDataPanel(), BorderLayout.NORTH);
            mainDataPanel.revalidate();
            mainDataPanel.repaint();
        });
    }

    /**
     * Clears the main data panel.
     */

    private void clearPanels(){
        mainDataPanel.removeAll();
    }



    /**
     * Draws the panel for product removal.
     *
     * @return The product removal panel.
     */
    private JPanel drawProductRemoval(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));
        JLabel label = new JLabel("Enter Product ID to remove:");
        JTextField textField = new JTextField();
        JButton removeButton = new JButton("Remove");

        removeButton.addActionListener(e -> {
            String productID = textField.getText().toUpperCase(Locale.ROOT);
            Iterator<Product> iterator = seller.getProductsForSale().iterator();

            while (iterator.hasNext()) {
                Product p = iterator.next();
                if (p.getID().equals(productID)) {
                    iterator.remove();
                    JOptionPane.showMessageDialog(mainDataPanel, "Product removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
            }

            // If the loop completes and the product is not found
            JOptionPane.showMessageDialog(mainDataPanel, "Product not Found!", "Failure", JOptionPane.INFORMATION_MESSAGE);
        });

        panel.add(label);
        panel.add(textField);
        panel.add(removeButton);
        return panel;
    }


    /**
     * Draws the panel for adding a new product.
     *
     * @return The add new product panel.
     */
    private JPanel drawAddNewProductPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        int width = 140 ,height = 25;

        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productName = new JTextField();
        productName.setPreferredSize(new Dimension(width,height));

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(width,height));

        JLabel priceLabel = new JLabel("Invoice Price:");
        JTextField invoicePrice = new JTextField();
        invoicePrice.setPreferredSize(new Dimension(width,height));

        JLabel sellingPriceLabel = new JLabel("Selling Price:");
        JTextField sellingPrice = new JTextField();
        sellingPrice.setPreferredSize(new Dimension(width,height));

        JLabel typeLabel = new JLabel("Type:");

        // Predefined values for the dropdown
        String[] typeChoices = {"Grocery", "Electronic", "Apparel", "Misc"};

        // Create JComboBox with predefined values
        JComboBox<String> typeDropdown = new JComboBox<>(typeChoices);

        // Set preferred size
        typeDropdown.setPreferredSize(new Dimension(width, height));

        // Access the selected value
        AtomicReference<String> selectedType = new AtomicReference<>((String) typeDropdown.getSelectedItem());

        // Add an ActionListener if you want to perform actions when an item is selected
        typeDropdown.addActionListener(e -> {
            selectedType.set((String) typeDropdown.getSelectedItem());
            // Perform actions based on the selected item
            System.out.println("Selected: " + selectedType);
        });


        JButton addProductButton = new JButton("Add Product");



        addProductButton.addActionListener(e -> {
            try {
                // Create a new Product using the values from text fields
                Product p = new Product(
                        productName.getText(),
                        Double.parseDouble(invoicePrice.getText()),
                        Double.parseDouble(sellingPrice.getText()),
                        Integer.parseInt(quantity.getText())
                );
                p.setSellerUserName(seller.getUsername());
                p.setType(selectedType.get());
                seller.addNewProductForSale(p);


                JOptionPane.showMessageDialog(mainDataPanel, "Product added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                productName.setText("");
                invoicePrice.setText("");
                sellingPrice.setText("");
                quantity.setText("");
            } catch (NumberFormatException ex) {
                // Handle the case where conversion from text to numbers fails
                JOptionPane.showMessageDialog(mainDataPanel, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(productNameLabel);
        panel.add(productName);
        panel.add(quantityLabel);
        panel.add(quantity);
        panel.add(priceLabel);
        panel.add(invoicePrice);
        panel.add(sellingPriceLabel);
        panel.add(sellingPrice);
        panel.add(typeLabel);
        panel.add(typeDropdown);
        panel.add(addProductButton);

        return panel;
    }

    /**
     * Draws the panel for displaying sales data.
     *
     * @return The sales data panel.
     */
    private JPanel drawSalesDataPanel() {
        this.seller.setSalesData();
        JPanel panel = new JPanel(new GridLayout(0, 1));
        int gap = 25;
        JLabel totalCosts = new JLabel("Total Costs of Acquired Products: $" + String.format("%.2f",this.seller.getTotalAcquiredCosts()));
        totalCosts.setHorizontalAlignment(JLabel.CENTER);
        totalCosts.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        JLabel costs = new JLabel("Costs of goods currently for sale: $" + String.format("%.2f",this.seller.getCostsOfProductsForSale()));
        costs.setHorizontalAlignment(JLabel.CENTER);
        costs.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        JLabel revenue = new JLabel("Revenue: $" + String.format("%.2f",this.seller.getRevenues()));
        revenue.setHorizontalAlignment(JLabel.CENTER);
        revenue.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        JLabel profits = new JLabel("Profits: $" + String.format("%.2f",this.seller.getProfits()));
        profits.setHorizontalAlignment(JLabel.CENTER);
        profits.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        panel.add(totalCosts);
        panel.add(costs);
        panel.add(revenue);
        panel.add(profits);

        return panel;
    }




    private JPanel drawUpdateInformationPanel(){
        JPanel updateInfo = new JPanel();
        updateInfo.setLayout(new FlowLayout()); // Adjust the layout as needed

        int width = 150 ,height = 25;

        // Labels
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel emailLabel = new JLabel("Email:");


        JLabel bankNameLabel = new JLabel("Bank Name:");
        JLabel accountNumberLabel = new JLabel("Account Number:");
        JLabel routingNumberLabel = new JLabel("Routing Number:");

        // TextFields
        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(width,height));
        usernameField.setText(this.userManager.getSellerAccountInformation(this.seller,"username"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(width,height));

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(width,height));
        emailField.setText(this.userManager.getSellerAccountInformation(this.seller,"email"));


        JTextField bankNameField = new JTextField();
        bankNameField.setPreferredSize(new Dimension(width + 50,height));
        bankNameField.setText(this.userManager.getSellerAccountInformation(this.seller,"bank name"));

        JTextField accountNumberField = new JTextField();
        accountNumberField.setPreferredSize(new Dimension(width + 50,height));
        accountNumberField.setText(this.userManager.getSellerAccountInformation(this.seller,"account number"));

        JTextField routingNumberField = new JTextField();
        routingNumberField.setPreferredSize(new Dimension(width + 50,height));
        routingNumberField.setText(this.userManager.getSellerAccountInformation(this.seller,"routing number"));


        // Update Button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle the update logic here
                String newUsername = usernameField.getText();
                String newPassword =    String.valueOf(passwordField.getPassword());
                String email = emailField.getText();
                String bankName = bankNameField.getText();
                String accountNumber = accountNumberField.getText();
                String routingNumber = routingNumberField.getText();
                if(!newUsername.equals(userManager.getSellerAccountInformation(seller,"username"))){
                    userManager.updateSellerAccountInformation(seller,"username",newUsername);
                }
                if(!newPassword.isEmpty()){
                    userManager.updateSellerAccountInformation(seller,"password",newPassword);
                }
                if(!email.equals(userManager.getSellerAccountInformation(seller,"email"))){
                    userManager.updateSellerAccountInformation(seller,"email",email);
                }

                if(!bankName.equals(userManager.getSellerAccountInformation(seller,"bank name"))){
                    userManager.updateSellerAccountInformation(seller,"bank name",bankName);
                }
                if(!accountNumber.equals(userManager.getSellerAccountInformation(seller,"account number"))){
                    userManager.updateSellerAccountInformation(seller,"account number",accountNumber);
                }
                if(!routingNumber.equals(userManager.getSellerAccountInformation(seller,"routing number"))){
                    userManager.updateSellerAccountInformation(seller,"routing number",routingNumber);
                }



                // Clear fields after update if needed
                usernameField.setText("");
                passwordField.setText("");
                emailField.setText("");
                bankNameField.setText("");
                accountNumberField.setText("");
                routingNumberField.setText("");
            }
        });

        // Add components to the panel
        updateInfo.add(usernameLabel);
        updateInfo.add(usernameField);
        updateInfo.add(passwordLabel);
        updateInfo.add(passwordField);
        updateInfo.add(emailLabel);
        updateInfo.add(emailField);
        updateInfo.add(bankNameLabel);
        updateInfo.add(bankNameField);
        updateInfo.add(accountNumberLabel);
        updateInfo.add(accountNumberField);
        updateInfo.add(routingNumberLabel);
        updateInfo.add(routingNumberField);

        updateInfo.add(new JLabel()); // Placeholder for spacing
        updateInfo.add(updateButton);

        return updateInfo;
    }
    @Override
    public void saveAndRefresh() {
        this.userManager.writeUserDataToFile();
        userManager.getProductsManager().saveInventory();
    }

    @Override
    public void saveAndRefresh(String ID) {
        userManager.getProductsManager().removeProductsFromOtherBuyers(ID);
        userManager.getProductsManager().saveInventory();
        showSellerProducts();
    }

    @Override
    public void refreshTable() {
        showSellerProducts();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command){
            case "add new product":
                showAddNewProductPanel();
                break;
            case "view products":
                showSellerProducts();
                break;
            case "sales data":
                showSalesDataPanel();
                break;
            case "update info":
                showUpdateInfo();
                break;
            case "log out":
                this.seller.setSalesData();
                saveAndRefresh();
                this.vm.showEntryView();
                break;
            default:
                System.out.println("Unknown button was clicked");
        }
    }
}
