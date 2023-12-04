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
        this.tableViewUtility = new SellerTableViewUtility(this.seller,this);


        logOutButton.setActionCommand("log out");
        logOutButton.addActionListener(this);


        addANewProductButton.setActionCommand("add new product");
        addANewProductButton.addActionListener(this);


        viewCurrentProductInfoButton.setActionCommand("view products");
        viewCurrentProductInfoButton.addActionListener(this);


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
            JLabel label = new JLabel("View/Edit your current product(s) below:");
            label.setHorizontalAlignment(JLabel.CENTER);
            mainDataPanel.add(label, BorderLayout.NORTH);
            mainDataPanel.add(tableViewUtility.createTable(this.seller.getProductsForSale(), columnNames));
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
                    saveAndRefresh();
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
                seller.getProductsForSale().add(p);
                userManager.getProductsManager().saveInventory();

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
        JLabel costs = new JLabel("Costs: $" + this.seller.getCosts());
        costs.setHorizontalAlignment(JLabel.CENTER);
        costs.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        JLabel revenue = new JLabel("Revenue: $" + this.seller.getRevenues());
        revenue.setHorizontalAlignment(JLabel.CENTER);
        revenue.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        JLabel profits = new JLabel("Profits: $" + this.seller.getProfits());
        profits.setHorizontalAlignment(JLabel.CENTER);
        profits.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        panel.add(costs);
        panel.add(revenue);
        panel.add(profits);

        return panel;
    }
    @Override
    public void saveAndRefresh() {
        userManager.getProductsManager().saveInventory();
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
            case "log out":
                this.seller.setSalesData();
                this.userManager.writeUserDataToFile();
                this.vm.showEntryView();
            default:
                System.out.println("Unknown button was clicked");
        }
    }
}
