package src.Frontend;

import src.Product.Product;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

public class BuyerViewDrawer {

    public static void drawProductListing(JPanel mainPanel, ArrayList<Product> productsForSale) {
        SwingUtilities.invokeLater(() -> {
            clearPanels(mainPanel);

            if (productsForSale.isEmpty()) {
                mainPanel.setLayout(new BorderLayout());
                mainPanel.add(createNoProductsPanel(), BorderLayout.CENTER);
            } else {
                mainPanel.setLayout(new BorderLayout());
                mainPanel.add(drawProductsForSale(productsForSale), BorderLayout.CENTER);
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    public static void clearPanels(JPanel mainPanel){
        mainPanel.removeAll();
    }

    private static JScrollPane drawProductsForSale(ArrayList<Product> products){
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0,3,10,10));
        for(Product p: products){
            productsPanel.add(createProductBox(p));
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    private static JPanel createProductBox(Product product) {
        JPanel productBox = new JPanel(new GridLayout());
        productBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.add(new JLabel("Name: " + product.getName()));
        detailsPanel.add(new JLabel("Price: $" + product.getSellingPrice()));
        detailsPanel.add(new JLabel("In stock: " + product.getQuantity()));

        productBox.add(detailsPanel, BorderLayout.EAST);

        // Add mouse listener to show more details on click
        productBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showProductDetailsDialog(product);
            } @Override
            public void mouseEntered(MouseEvent e) {
                productBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                productBox.setCursor(Cursor.getDefaultCursor());
            }
        });

        return productBox;
    }

    private static JPanel createNoProductsPanel() {
        JPanel noProductsPanel = new JPanel(new BorderLayout());
        JLabel noProductsLabel = new JLabel("Product(s) unavailable");
        noProductsLabel.setHorizontalAlignment(JLabel.CENTER);
        noProductsPanel.add(noProductsLabel, BorderLayout.CENTER);
        return noProductsPanel;
    }

    private static void showProductDetailsDialog(Product product) {
        // Create a panel to display detailed information
        JPanel moreDetails = new JPanel(new GridLayout(0, 1));

        // Format the price using NumberFormat for currency formatting
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedPrice = currencyFormat.format(product.getSellingPrice());

        // Use HTML for better formatting (line breaks)
        moreDetails.add(new JLabel("<html><b>Name:</b> " + product.getName() + "</html>"));
        moreDetails.add(new JLabel("<html><b>Price:</b> " + formattedPrice + "</html>"));
        moreDetails.add(new JLabel("<html><b>In stock:</b> " + product.getQuantity() + "</html>"));
        moreDetails.add(new JLabel("<html><b>Description:</b> " + product.getDescription() + "</html>"));
        moreDetails.add(new JLabel("<html><b>Seller:</b> " + product.getSellerUserName() + "</html>"));

        // Show a JOptionPane with detailed information
        JOptionPane.showMessageDialog(null, moreDetails, "Product Details", JOptionPane.INFORMATION_MESSAGE);
    }




}