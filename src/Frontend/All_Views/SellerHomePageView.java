package src.Frontend.All_Views;

import src.Backend.UserManager;
import src.Frontend.ViewManager;
import src.Inventory.Product;
import src.users_code.Seller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                clearPanels();
                mainDataPanel.setLayout(new BorderLayout());
                JLabel label = new JLabel("View/Edit your current product(s) below:");
                label.setHorizontalAlignment(JLabel.CENTER);
                mainDataPanel.add(label, BorderLayout.NORTH);
                mainDataPanel.add(drawProductTable(), BorderLayout.CENTER);
                mainDataPanel.add(drawProductRemoval(), BorderLayout.SOUTH);
                mainDataPanel.revalidate();
                mainDataPanel.repaint();
            }
        });
    }

    /**
     * Displays the panel for adding a new product.
     */
    private void showAddNewProductPanel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                clearPanels();
                mainDataPanel.setLayout(new BorderLayout());
                mainDataPanel.add(drawAddNewProductPanel(), BorderLayout.CENTER);
                mainDataPanel.revalidate();
                mainDataPanel.repaint();
            }
        });
    }


    /**
     * Displays the sales data panel, showing costs, revenue, and profits.
     */
    private void showSalesDataPanel(){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                clearPanels();
                mainDataPanel.setLayout(new BorderLayout());
                mainDataPanel.add(drawSalesDataPanel(), BorderLayout.NORTH);
                mainDataPanel.revalidate();
                mainDataPanel.repaint();
            }
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
            //Create an empty table
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

        DefaultTableModel tableModel = new DefaultTableModel(productData, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Make all cells editable except the ID column
                return column != 1; // 1 is the index of the ID column
            }
        };

        JTable table = new JTable(tableModel);

        table.getModel().addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            TableModel model = (TableModel) e.getSource();
            String columnName = model.getColumnName(column);
            Object data = model.getValueAt(row, column);


            Product product = getProductForRow(row);
            updateProductData(product, columnName, data);
            this.userManager.getProductsManager().saveInventoryToFile();
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    int column = table.columnAtPoint(e.getPoint());

                    // Select the row under the right-clicked point
                    table.setRowSelectionInterval(row, row);

                    // Show the popup menu
                    showPopupMenu(table, e.getX(), e.getY());
                }
            }
        });

        return new JScrollPane(table);
    }

    private void showPopupMenu(JTable table, int x, int y) {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem deleteItem = new JMenuItem("Remove Product");

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Remove the selected row
                    Product product = getProductForRow(selectedRow);
                    seller.getProductsForSale().remove(product);
                    userManager.getProductsManager().saveInventoryToFile();
                    showProductPanel();
                }
            }
        });

        popupMenu.add(deleteItem);

        // Show the popup menu at the specified location
        popupMenu.show(table, x, y);
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
     * Updates the data of a product based on changes in the product table.
     *
     * @param product    The `Product` object to update.
     * @param columnName The name of the column being updated.
     * @param data       The new data for the column.
     */
    private void updateProductData(Product product, String columnName, Object data) {
        switch(columnName){
            case "Name":
                product.setName((String) data);
                break;
            case "Quantity":
                product.setQuantity((Integer) data);
                break;
            case "Invoice Price ($)" :
                product.setInvoicePrice(Double.parseDouble((String) data));
                break;
            case "Selling Price ($)":
                product.setSellingPrice(Double.parseDouble((String) data));
                break;
        }

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

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
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

        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
