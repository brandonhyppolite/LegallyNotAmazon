package src.GUI_code.All_Views;

import src.Backend.ShoppingSystem;
import src.GUI_code.ViewManager;
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
    private ShoppingSystem system;

    private Seller seller;
    public SellerHomePageView(ViewManager v, Seller seller){
        this.vm = v;
        this.seller = seller;
        this.system = ShoppingSystem.getInstance();

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



        setUpMainView();
        showProductPanel();
    }

    public JPanel getMainPanel(){
        return sellerHomePageMainPanel;
    }

    public void setUpMainView() {
        sellerHomePageInfoPanel.setLayout(new GridLayout(0, 1));
        sellerHomePageInfoPanel.add(mainDataPanel);
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

    private void clearPanels(){
        mainDataPanel.removeAll();
    }
    private JScrollPane drawProductTable() {
        ArrayList<Product> products = this.seller.getSellerProducts();
        int numRows = products.size();
        int numCols = 5;

        // Creating the 2D array dynamically
        String[][] productData = new String[numRows][numCols];
        String[] columnNames = new String[]{"Name", "ID", "Quantity", "Invoice Price ($)", "Selling Price ($)"};

        for (int i = 0; i < numRows; i++) {
            Product product = products.get(i);
            productData[i][0] = product.getName();
            productData[i][1] = product.getID();
            productData[i][2] = String.valueOf(product.getQuantity());
            productData[i][3] = String.valueOf(product.getSellingPrice());
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

            // Add your logic to handle the change, for example, save the data
            updateProductData(row, columnName, data);
        });

        JScrollPane sp = new JScrollPane(table);
        return sp;
    }


    private void updateProductData(int row, String columnName, Object data) {
        // Add your logic here to update the product data, e.g., save it
        // You can use the row, columnName, and data parameters to identify the cell that was edited
        // and update the corresponding Product object in your data model
        // For simplicity, you can print the changes for now
        System.out.println("Row: " + row + ", Column: " + columnName + ", Data: " + data);
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
        JPanel panel = new JPanel(new FlowLayout()); // 0 rows means any number of rows, 2 columns
        int width = 200 ,height = 25;

        JLabel productNameLabel = new JLabel("Product Name:");
        JTextField productName = new JTextField();
        productName.setPreferredSize(new Dimension(width,height));

        JLabel quantityLabel = new JLabel("Quantity:");
        JTextField quantity = new JTextField();
        quantity.setPreferredSize(new Dimension(width,height));

        JLabel priceLabel = new JLabel("Price:");
        JTextField price = new JTextField();
        price.setPreferredSize(new Dimension(width,height));

        JLabel sellingPriceLabel = new JLabel("Selling Price:");
        JTextField sellingPrice = new JTextField();
        sellingPrice.setPreferredSize(new Dimension(width,height));

        JButton addProductButton = new JButton("Add Product");
        panel.add(productNameLabel);
        panel.add(productName);
        panel.add(quantityLabel);
        panel.add(quantity);
        panel.add(priceLabel);
        panel.add(price);
        panel.add(sellingPriceLabel);
        panel.add(sellingPrice);
        panel.add(addProductButton);

        return panel;
    }


    private JPanel drawSalesDataPanel(){
        JPanel panel = new JPanel();


        return panel;
    }
}
