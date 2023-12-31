package src.Frontend;

import src.Backend.UserManager;
import src.Frontend.All_Views.SellerHomePageView;
import src.Product.Product;
import src.users_code.Seller;
import src.users_code.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * The SellerTableViewUtility class is responsible for creating and managing the seller's table view in the user interface.
 */
public class SellerTableViewUtility {
    private final Seller seller;
    private final UserManager userManager;
    private JTable table;
    String[] columnNames;
    private final UserActionCallBack callBack;
    /**
     * Constructs a SellerTableViewUtility object with the specified seller and callback.
     *
     * @param seller   The seller object.`
     */
    public SellerTableViewUtility(Seller seller, UserActionCallBack callBack) {
        this.seller = seller;
        this.userManager = UserManager.getInstance();
        this.callBack = callBack;
        this.columnNames = new String[]{"Name", "ID", "Quantity", "Invoice Price ($)", "Selling Price ($)", "Type"};
    }
    /**
     * Creates a scroll pane with a table view of the products.
     *
     * @param products    The list of products.
     *
     * @return The scroll pane with the table view.
     */
    public JScrollPane createTable(ArrayList<Product> products) {
        // Check if products is null or empty
        if (products == null || products.isEmpty()) {
            // Handle the case where there are no products
            // Create an empty table
            String[][] emptyData = new String[][]{{"", "", "", "", ""}};
            DefaultTableModel emptyModel = new DefaultTableModel(emptyData, columnNames);
            this.table= new JTable(emptyModel);
            return new JScrollPane(this.table);
        }

        int numRows = products.size();
        int numCols = columnNames.length;

        String[][] productData = new String[numRows][numCols];

        for (int i = 0; i < numRows; i++) {
            Product product = products.get(i);
            for (int j = 0; j < numCols; j++) {
                switch (j) {
                    case 0:
                        productData[i][j] = product.getName();
                        break;
                    case 1:
                        productData[i][j] = product.getID();
                        break;
                    case 2:
                        productData[i][j] = String.valueOf(product.getQuantity());
                        break;
                    case 3:
                        productData[i][j] = String.format("%.2f", product.getInvoicePrice());
                        break;
                    case 4:
                        productData[i][j] = String.format("%.2f", product.getSellingPrice());
                        break;
                    case 5:
                        productData[i][j] = product.getType();
                }
            }
        }

       this.table = createSellerTable(productData, columnNames);
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
        JPopupMenu popupMenu = new JPopupMenu();
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {

                JMenuItem removeItem = new JMenuItem("Remove Product");
                JMenuItem editNameItem = new JMenuItem("Edit Name");
                JMenuItem addQuantityItem = new JMenuItem("Add Quantity");
                JMenuItem lowerQuantityItem = new JMenuItem("Lower Quantity");
                JMenuItem editInvoicePriceItem = new JMenuItem("Edit Invoice Price");
                JMenuItem editSellingPriceItem = new JMenuItem("Edit Selling Price");
                JMenuItem editProductDescription = new JMenuItem("Edit Description");
                JMenuItem editType = new JMenuItem("Edit Type");

                removeItem.addActionListener(e -> removeProduct(selectedRow));
                editNameItem.addActionListener(e -> editProductAttribute(selectedRow, "Name"));
                addQuantityItem.addActionListener(e -> editProductAttribute(selectedRow, "Add Quantity"));
                lowerQuantityItem.addActionListener(e -> editProductAttribute(selectedRow, "Lower Quantity"));
                editInvoicePriceItem.addActionListener(e -> editProductAttribute(selectedRow, "Invoice Price"));
                editSellingPriceItem.addActionListener(e -> editProductAttribute(selectedRow, "Selling Price"));
                editProductDescription.addActionListener(e -> showEditProductDescriptionPopup(getProductForRow(selectedRow)));
                editType.addActionListener(e ->editProductAttribute(selectedRow,"Type"));
                popupMenu.add(removeItem);
                popupMenu.addSeparator(); // Add separator between remove and edit options
                popupMenu.add(editNameItem);
                popupMenu.add(addQuantityItem);
                popupMenu.add(lowerQuantityItem);
                popupMenu.add(editInvoicePriceItem);
                popupMenu.add(editSellingPriceItem);
                popupMenu.add(editProductDescription);
                popupMenu.add(editType);
            }


        // Show the popup menu at the specified location
        popupMenu.show(table, x, y);
    }

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
        }
    }

    private void removeProduct(int selectedRow) {
        Product product = getProductForRow(selectedRow);
        this.seller.removeProductFromSale(product);
        userManager.getProductsManager().removeProductsFromOtherBuyers(product.getID());
        this.callBack.refreshTable();
    }

    private void editProductAttribute(int selectedRow, String attributeName) {
        Product product = getProductForRow(selectedRow);

        switch (attributeName) {
            case "Name":
            case "Quantity":
            case "Invoice Price":
            case "Selling Price":
            case "Add Quantity":
            case "Lower Quantity":
                // Show the initial input dialog for other cases
                String inputValue = JOptionPane.showInputDialog("Enter new " + attributeName + ":");
                if (inputValue != null && !inputValue.isEmpty()) {
                    handleOtherCases(product, attributeName, inputValue);
                    this.callBack.refreshTable();
                }
                break;
            case "Type":
                // Show a JComboBox with predefined choices for the product type
                String[] typeChoices = {"Grocery", "Electronic", "Apparel", "Misc"};
                JComboBox<String> typeDropdown = new JComboBox<>(typeChoices);

                int result = JOptionPane.showConfirmDialog(
                        this.table,
                        typeDropdown,
                        "Select Type",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.PLAIN_MESSAGE
                );

                if (result == JOptionPane.OK_OPTION) {
                    String selectedType = (String) typeDropdown.getSelectedItem();
                    product.setType(selectedType);
                    this.callBack.refreshTable();
                }
                break;
        }
    }

    private void handleOtherCases(Product product, String attributeName, String inputValue) {
        switch (attributeName) {
            case "Name":
                product.setName(inputValue);
                break;
            case "Quantity":
                int oldQuantity = product.getQuantity();
                int newQuantity = Integer.parseInt(inputValue);

                // Update the quantity of the product
                product.setQuantity(newQuantity);

                int difference = newQuantity - oldQuantity;

                // Update totalAcquiredCosts based on the difference in quantity
                double costChange = product.getInvoicePrice() * difference;
                this.seller.setTotalAcquiredCosts(this.seller.getTotalAcquiredCosts() + costChange);
                break;
            case "Invoice Price":
                double oldPrice = product.getInvoicePrice();
                product.setInvoicePrice(Double.parseDouble(inputValue));
                double differencePrice = product.getInvoicePrice() - oldPrice;
                this.seller.setTotalAcquiredCosts(this.seller.getTotalAcquiredCosts() + (differencePrice * product.getQuantity()));
                break;
            case "Selling Price":
                product.setSellingPrice(Double.parseDouble(inputValue));
                break;
            case "Add Quantity":
                int quantityToAdd = Integer.parseInt(inputValue);
                this.seller.addQuantityToProduct(product, quantityToAdd);
                break;
            case "Lower Quantity":
                int quantityToSubtract = Integer.parseInt(inputValue);
                this.seller.lowerQuantityOfProduct(product, quantityToSubtract);
                break;
        }
    }

    private Product getProductForRow(int row) {
        return this.seller.getProductsForSale().get(row);
    }
}
