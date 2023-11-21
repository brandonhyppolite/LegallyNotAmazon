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
import java.util.ArrayList;

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
    private ViewManager vm;
    private UserManager system;
    private Seller seller;
    public SellerHomePageView(ViewManager v, Seller seller){
        this.vm = v;
        this.seller = seller;
        this.system = UserManager.getInstance();

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

    public JPanel getMainPanel(){
        return sellerHomePageMainPanel;
    }

    public void setUpMainView() {
        sellerHomePageInfoPanel.setLayout(new BorderLayout());
        sellerHomePageInfoPanel.add(buttonPanel,BorderLayout.NORTH);
        sellerHomePageInfoPanel.add(mainDataPanel,BorderLayout.CENTER);
        welcomeUserLabel.setText("Welcome, " + seller.getUsername() + "!");

    }

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
    private void clearPanels(){
        mainDataPanel.removeAll();
    }
    private JScrollPane drawProductTable() {
        ArrayList<Product> products = this.system.getProductsManager().getItemsFromUser(seller);

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
            this.system.getProductsManager().saveInventoryToFile();
        });

        return new JScrollPane(table);
    }

    private Product getProductForRow(int row) {
        return this.system.getProductsManager().getItemsFromUser(this.seller).get(row);
    }
    private void updateProductData(Product product, String columnName, Object data) {
        switch(columnName){
            case "Name":
                product.setName((String) data);
                break;
            case "Quantity":
                product.setQuantity((Integer) data);
                break;
            case "Invoice Price ($)" :
                product.setInvoicePrice((Double) data);
                break;
            case "Selling Price ($)":
                product.setSellingPrice((Double) data);
                break;
        }

    }

    private JPanel drawProductRemoval(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1,3));
        JLabel label = new JLabel("Enter Product ID to remove:");
        JTextField textField = new JTextField();
        JButton removeButton = new JButton("Remove");
        panel.add(label);
        panel.add(textField);
        panel.add(removeButton);
        return panel;
    }

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


                    system.getProductsManager().addProductToUser(seller,p);
                    system.getProductsManager().saveInventoryToFile();

                    JOptionPane.showMessageDialog(null, "Product added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                    productName.setText("");
                    invoicePrice.setText("");
                    sellingPrice.setText("");
                    quantity.setText("");
                } catch (NumberFormatException ex) {
                    // Handle the case where conversion from text to numbers fails
                    JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.", "Error", JOptionPane.ERROR_MESSAGE);
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


    private JPanel drawSalesDataPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1));
        int gap = 25;
        JLabel costs = new JLabel("Costs:");
        costs.setHorizontalAlignment(JLabel.CENTER);
        costs.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        JLabel revenue = new JLabel("Revenue:");
        revenue.setHorizontalAlignment(JLabel.CENTER);
        revenue.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        JLabel profits = new JLabel("Profits:");
        profits.setHorizontalAlignment(JLabel.CENTER);
        profits.setBorder(BorderFactory.createEmptyBorder(gap, 0, gap, 0)); // Add vertical gap

        panel.add(costs);
        panel.add(revenue);
        panel.add(profits);

        return panel;
    }

}