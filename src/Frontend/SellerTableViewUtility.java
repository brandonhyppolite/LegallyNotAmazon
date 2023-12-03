package src.Frontend;

import src.Product.Product;
import src.users_code.Seller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class SellerTableViewUtility {
    private Seller seller;
    private UserActionCallBack callback;

    public SellerTableViewUtility(Seller seller, UserActionCallBack callback) {
        this.seller = seller;
        this.callback = callback;
    }

    public JScrollPane createTable(ArrayList<Product> products, String[] columnNames) {
        // Check if products is null or empty
        if (products == null || products.isEmpty()) {
            // Handle the case where there are no products
            // Create an empty table
            String[][] emptyData = new String[][]{{"", "", "", "", ""}};
            DefaultTableModel emptyModel = new DefaultTableModel(emptyData, columnNames);
            JTable emptyTable = new JTable(emptyModel);
            return new JScrollPane(emptyTable);
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
                        productData[i][j] = String.valueOf(product.getInvoicePrice());
                        break;
                    case 4:
                        productData[i][j] = String.valueOf(product.getSellingPrice());
                        break;
                }
            }
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
        JPopupMenu popupMenu = new JPopupMenu();
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {

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
            callback.saveAndRefresh();
        }
    }

    private void removeProduct(int selectedRow) {
        Product product = getProductForRow(selectedRow);
        this.seller.getProductsForSale().remove(product);
        callback.saveAndRefresh();
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
            }

            callback.saveAndRefresh();
        }
    }
    private Product getProductForRow(int row) {
        return this.seller.getProductsForSale().get(row);
    }
}
