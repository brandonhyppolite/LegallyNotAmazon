package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.ViewManager;
import src.Inventory.Product;
import src.users_code.Seller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;


/**
 * The `SellerHomePageView` class represents the graphical user interface for the home page of a Seller.
 * It allows Sellers to view, edit, and add products, as well as view sales data.
 */
public class SellerHomePageView {
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

        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seller.setSalesData();
                userManager.writeUserDataToFile();
                vm.showEntryView();
                //More functions for saving any changes Seller made

            }
        });
        addANewProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddNewProductPanel();
            }
        });
        viewCurrentProductInfoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showProductPanel();
            }
        });

        salesDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSalesDataPanel();
            }
        });


        setUpMainView();
        showProductPanel();
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
    private void showProductPanel() {
        SwingUtilities.invokeLater(() -> {
            clearPanels();
            mainDataPanel.setLayout(new BorderLayout());
            JLabel label = new JLabel("View/Edit your current product(s) below:");
            label.setHorizontalAlignment(JLabel.CENTER);
            mainDataPanel.add(label, BorderLayout.NORTH);
            mainDataPanel.add(drawProductTable(), BorderLayout.CENTER);
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
     * Draws the product table to display the Seller's products.
     *
     * @return The scroll pane containing the product table.
     */
    private JScrollPane drawProductTable() {
        ArrayList<Product> products = seller.getProductsForSale();

        // Check if products is null or empty
        if (products == null || products.isEmpty()) {
            // Handle the case where there are no products
            // Create an empty table
            String[] columnNames = new String[]{"Name", "ID", "Quantity", "Invoice Price ($)", "Selling Price ($)"};
            String[][] emptyData = new String[][]{{"", "", "", "", ""}};
            DefaultTableModel emptyModel = new DefaultTableModel(emptyData, columnNames);
            JTable emptyTable = new JTable(emptyModel);
            return new JScrollPane(emptyTable);
        }

        int numRows = products.size();
        int numCols = 5;

        String[][] productData = new String[numRows][numCols];
        String[] columnNames = new String[]{"Name", "ID", "Quantity", "Invoice Price ($)", "Selling Price ($)"};

        for (int i = 0; i < numRows; i++) {
            Product product = products.get(i);
            productData[i][0] = product.getName();
            productData[i][1] = product.getID();
            productData[i][2] = String.valueOf(product.getQuantity());
            productData[i][3] = String.valueOf(product.getInvoicePrice());
            productData[i][4] = String.valueOf(product.getSellingPrice());
        }

        JTable table = createSellerTable(productData, columnNames);

        return new JScrollPane(table);
    }

    private JTable createSellerTable(String[][] productData, String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel(productData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        JTable table = new JTable(tableModel);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());

                    // Select the row under the right-clicked point
                    table.setRowSelectionInterval(row, row);

                    // Show the popup menu with options to remove, edit name, quantity, invoice price, and selling price
                    showPopupMenu(table, e.getX(), e.getY());
                }
            }
        });
        return table;
    }

    private void showPopupMenu(JTable table, int x, int y) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem removeItem = new JMenuItem("Remove Product");
            JMenuItem editNameItem = new JMenuItem("Edit Name");
            JMenuItem editQuantityItem = new JMenuItem("Edit Quantity");
            JMenuItem editInvoicePriceItem = new JMenuItem("Edit Invoice Price");
            JMenuItem editSellingPriceItem = new JMenuItem("Edit Selling Price");

            removeItem.addActionListener(e -> removeProduct(selectedRow));
            editNameItem.addActionListener(e -> editProductAttribute(selectedRow, "Name"));
            editQuantityItem.addActionListener(e -> editProductAttribute(selectedRow, "Quantity"));
            editInvoicePriceItem.addActionListener(e -> editProductAttribute(selectedRow, "Invoice Price"));
            editSellingPriceItem.addActionListener(e -> editProductAttribute(selectedRow, "Selling Price"));

            popupMenu.add(removeItem);
            popupMenu.addSeparator(); // Add separator between remove and edit options
            popupMenu.add(editNameItem);
            popupMenu.add(editQuantityItem);
            popupMenu.add(editInvoicePriceItem);
            popupMenu.add(editSellingPriceItem);

            // Show the popup menu at the specified location
            popupMenu.show(table, x, y);
        }
    }

    private void removeProduct(int selectedRow) {
        Product product = getProductForRow(selectedRow);
        seller.getProductsForSale().remove(product);
        userManager.getProductsManager().saveInventoryToFile();
        showProductPanel(); // Refresh the product panel after changes
    }

    private void editProductAttribute(int selectedRow, String attributeName) {
        String inputValue = JOptionPane.showInputDialog("Enter new " + attributeName + ":");
        if (inputValue != null && !inputValue.isEmpty()) {
            Product product = getProductForRow(selectedRow);

            switch (attributeName) {
                case "Name":
                    product.setName(inputValue);
                    break;
                case "Quantity":
                    product.setQuantity(Integer.parseInt(inputValue));
                    break;
                case "Invoice Price":
                    product.setInvoicePrice(Double.parseDouble(inputValue));
                    break;
                case "Selling Price":
                    product.setSellingPrice(Double.parseDouble(inputValue));
                    break;
                // Add more cases for other attributes if needed
            }

            // Save changes to the file or update the table accordingly
            userManager.getProductsManager().saveInventoryToFile();
            showProductPanel(); // Refresh the product panel after changes
        }
    }

    

    /**
     * Gets the `Product` object associated with the specified row in the product table from Seller.
     *
     * @param row The row index.
     * @return The `Product` object.
     */
    private Product getProductForRow(int row) {

        return this.seller.getProductsForSale().get(row); 
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
                    iterator.remove(); // Use iterator's remove method to avoid ConcurrentModificationException
                    JOptionPane.showMessageDialog(mainDataPanel, "Product removed successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    userManager.getProductsManager().saveInventoryToFile();
                    showProductPanel();
                    return; // Exit the loop once the product is found and removed
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

                seller.getProductsForSale().add(p);
                userManager.getProductsManager().saveInventoryToFile();

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

}
