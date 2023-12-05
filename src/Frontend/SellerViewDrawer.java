package src.Frontend;

import src.Backend.UserManager;
import src.Product.Product;
import src.users_code.Seller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class SellerViewDrawer {
    /**
     * The `SellerViewDrawer` class handles the display and management of the seller's view in the user interface.
     */
    private final Seller seller;
    private final JPanel mainDataPanel;
    private final UserManager userManager;
    /**
     * Constructs a `SellerViewDrawer` object with the specified seller and main data panel.
     *
     * @param seller        The seller object.
     * @param mainDataPanel The main data panel.
     */
    public SellerViewDrawer(Seller seller, JPanel mainDataPanel){
        this.seller = seller;
        this.mainDataPanel = mainDataPanel;
        this.userManager = UserManager.getInstance();
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
    /**
     * Creates a JTable for the seller's product data.
     *
     * @param productData  The product data.
     * @param columnNames  The column names.
     * @return The JTable.
     */
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
    /**
     * Shows the popup menu for the selected row in the product table.
     *
     * @param table The JTable.
     * @param x     The x-coordinate of the mouse click.
     * @param y     The y-coordinate of the mouse click.
     */
    private void showPopupMenu(JTable table, int x, int y) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem removeItem = new JMenuItem("Remove Product");
            JMenuItem editNameItem = new JMenuItem("Edit Name");
            JMenuItem editQuantityItem = new JMenuItem("Edit Quantity");
            JMenuItem editInvoicePriceItem = new JMenuItem("Edit Invoice Price");
            JMenuItem editSellingPriceItem = new JMenuItem("Edit Selling Price");
            JMenuItem editProductDescription = new JMenuItem("Edit Description");

            removeItem.addActionListener(e -> removeProduct(selectedRow));
            editNameItem.addActionListener(e -> editProductAttribute(selectedRow, "Name"));
            editQuantityItem.addActionListener(e -> editProductAttribute(selectedRow, "Quantity"));
            editInvoicePriceItem.addActionListener(e -> editProductAttribute(selectedRow, "Invoice Price"));
            editSellingPriceItem.addActionListener(e -> editProductAttribute(selectedRow, "Selling Price"));
            editProductDescription.addActionListener(e -> showEditProductDescriptionPopup(getProductForRow(selectedRow)));

            popupMenu.add(removeItem);
            popupMenu.addSeparator(); // Add separator between remove and edit options
            popupMenu.add(editNameItem);
            popupMenu.add(editQuantityItem);
            popupMenu.add(editInvoicePriceItem);
            popupMenu.add(editSellingPriceItem);
            popupMenu.add(editProductDescription);

            // Show the popup menu at the specified location
            popupMenu.show(table, x, y);
        }
    }
    /**
     * Removes the selected product from the seller's products.
     *
     * @param selectedRow The selected row in the product table.
     */
    private void removeProduct(int selectedRow) {
        Product product = getProductForRow(selectedRow);
        seller.getProductsForSale().remove(product);
        saveAndRefresh();
    }
    /**
     * Edits the specified attribute of the product at the selected row in the product table.
     *
     * @param selectedRow   The selected row in the product table.
     * @param attributeName The name of the attribute to edit.
     */
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
            }

            saveAndRefresh();
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
     * Shows a popup dialog for editing the product description.
     *
     * @param product The product to edit the description for.
     */
    private void showEditProductDescriptionPopup(Product product) {
        // Create a text area for user input
        JTextArea textArea = new JTextArea();
        textArea.setText(product.getDescription());
        // Create a scroll pane for the text area
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(300, 150));

        // Show the input dialog with the text area
        int result = JOptionPane.showOptionDialog(
                null,
                scrollPane,
                "Edit Product Description",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                null);

        // Handle the user's choice
        if (result == JOptionPane.OK_OPTION) {
            String text = textArea.getText();
            product.setDescription(text);
            System.out.println("Edited Description: " + product.getDescription());
            saveAndRefresh();
        }
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
        JLabel costs = new JLabel("Costs: $" + this.seller.getCostsOfProductsForSale());
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
    /**
     * Saves changes and refreshes the view.
     */
    private void saveAndRefresh() {
        userManager.getProductsManager().saveInventory();
        drawProductTable();
    }

}
