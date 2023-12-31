package src.Frontend;

import src.Product.Product;
import src.users_code.Buyer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


/**
 * The BuyerTableViewUtility class is responsible for creating and managing the buyer's table view in the user interface.
 */
public class BuyerTableViewUtility {
    private Buyer buyer;
    private UserActionCallBack callBack;
    String[] columnNames;
    /**
     * Constructs a BuyerTableViewUtility object with the specified buyer and callback.
     *
     * @param buyer   The buyer object.
     * @param callBack The callback for user actions.
     */
    public BuyerTableViewUtility(Buyer buyer, UserActionCallBack callBack){
        this.buyer = buyer;
        this.callBack = callBack;
        this.columnNames = new String[]{"Name", "ID", "Quantity","Price ($)", "Type"};
    }
    /**
     * Creates a scroll pane with a table view of the products.
     *
     * @param products    The list of products.
     * @return The scroll pane with the table view.
     */
    public JScrollPane createTable(ArrayList<Product> products) {
        // Check if products is null or empty
        if (products == null || products.isEmpty()) {
            // Handle the case where there are no products
            // Create an empty table
            String[][] emptyData = new String[][]{{"", "", "", "", ""}};
            DefaultTableModel emptyModel = new DefaultTableModel(emptyData, columnNames);
            JTable emptyTable = new JTable(emptyModel);
            return new JScrollPane(emptyTable);
        }

        int numRows = products.size() + 1; // Additional row for totals
        int numCols = columnNames.length;

        String[][] productData = new String[numRows][numCols];

        double totalQuantity = 0;
        double totalPrice = 0;

        for (int i = 0; i < numRows - 1; i++) {
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
                        totalQuantity += product.getQuantity();
                        break;
                    case 3:
                        productData[i][j] = String.format("%.2f",product.getSellingPrice());
                        totalPrice += product.getSellingPrice() * product.getQuantity();
                        break;
                    case 4:
                        productData[i][j] = product.getType();
                        break;
                }
            }
        }

        // Populate the last row with total quantity and total price
        productData[numRows - 1][1] = "In Total";
        productData[numRows - 1][2] = String.valueOf(totalQuantity);
        productData[numRows - 1][3] = String.format("%.2f",totalPrice);

        JTable table = createBuyerTable(productData, columnNames);

        return new JScrollPane(table);
    }


    private JTable createBuyerTable(String[][] productData, String[] columnNames) {
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

            removeItem.addActionListener(e -> removeProduct(selectedRow));

            popupMenu.add(removeItem);

        }


        // Show the popup menu at the specified location
        popupMenu.show(table, x, y);
    }



    private void removeProduct(int selectedRow) {
        Product product = getProductForRow(selectedRow);
        this.buyer.getShoppingCart().remove(product);
        this.callBack.refreshTable();
    }

    private Product getProductForRow(int row) {
        return this.buyer.getShoppingCart().get(row);
    }

    /**
     * Creates a scroll pane with a table view of the products in the checkout view.
     *
     * @param products    The list of products.
     * @param columnNames The column names for the table.
     * @return The scroll pane with the table view.
     */
    public JScrollPane createCheckoutTable(ArrayList<Product> products, String[] columnNames) {
        // Check if products is null or empty
        if (products == null || products.isEmpty()) {
            // Handle the case where there are no products
            // Create an empty table
            String[][] emptyData = new String[][]{{"", "", "", "", ""}};
            DefaultTableModel emptyModel = new DefaultTableModel(emptyData, columnNames);
            JTable emptyTable = new JTable(emptyModel);
            return new JScrollPane(emptyTable);
        }

        int numRows = products.size() + 1;
        int numCols = columnNames.length;
        String[][] productData = new String[numRows][numCols];

        double totalQuantity = 0;
        double totalPrice = 0;

        for (int i = 0; i < numRows - 1; i++) {
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
                        totalQuantity += product.getQuantity();
                        break;
                    case 3:
                        productData[i][j] = String.format("%.2f", product.getSellingPrice());
                        totalPrice += product.getSellingPrice() * product.getQuantity();
                        break;
                    case 4:
                        productData[i][j] = product.getSellerUserName();
                        break;
                }
            }
        }

        // Populate the last row with total quantity and total price
        productData[numRows - 1][1] = "In Total";
        productData[numRows - 1][2] = String.valueOf(totalQuantity);
        productData[numRows - 1][3] = String.format("%.2f",totalPrice);

        JTable table = createBuyerCheckoutTable(productData, columnNames);

        return new JScrollPane(table);
    }

    private JTable createBuyerCheckoutTable(String[][] productData, String[] columnNames) {
        DefaultTableModel tableModel = new DefaultTableModel(productData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells non-editable
                return false;
            }
        };

        return new JTable((tableModel));
    }

}
