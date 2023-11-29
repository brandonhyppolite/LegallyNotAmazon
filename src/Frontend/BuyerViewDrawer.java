package src.Frontend;

import src.Product.Product;
import src.users_code.Buyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.ArrayList;

public class BuyerViewDrawer {

    public static void drawProductListing(JPanel mainPanel, ArrayList<Product> productsForSale, Buyer buyer) {
        SwingUtilities.invokeLater(() -> {
            clearPanels(mainPanel);

            if (productsForSale.isEmpty()) {
                mainPanel.setLayout(new BorderLayout());
                mainPanel.add(createNoProductsPanel(), BorderLayout.CENTER);
            } else {
                mainPanel.setLayout(new BorderLayout());
                mainPanel.add(drawProductsForSale(productsForSale, buyer), BorderLayout.CENTER);
            }

            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    public static void drawBuyerCart(JPanel mainPanel, Buyer buyer){

    }

    public static void showProductDetailsDialog(JPanel mainPanel){

    }
    public static void clearPanels(JPanel mainPanel){
        mainPanel.removeAll();
    }

    private static JScrollPane drawProductsForSale(ArrayList<Product> products, Buyer buyer){
        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0,3,10,10));
        for(Product p: products){
            productsPanel.add(createProductBox(p,buyer));
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        return scrollPane;
    }

    private static JPanel createProductBox(Product product, Buyer buyer) {
        JPanel productBox = new JPanel(new BorderLayout());
        productBox.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JPanel detailsPanel = new JPanel(new GridLayout(0, 1));
        detailsPanel.add(new JLabel("Name: " + product.getName()));
        detailsPanel.add(new JLabel("Price: $" + product.getSellingPrice()));
        detailsPanel.add(new JLabel("In stock: " + product.getQuantity()));

        productBox.add(detailsPanel, BorderLayout.CENTER);

        // Add mouse listener to show more details on click
        productBox.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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

    private static JPanel createProductDetailsPanel(Product product, Buyer buyer) {
        // Create a panel to hold detailed information
        JPanel moreDetails = new JPanel(new GridLayout(0, 1));

        // Format the price using NumberFormat for currency formatting
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        String formattedPrice = currencyFormat.format(product.getSellingPrice());

        JButton addToCart = new JButton();
        if (product.getQuantity() <= 0) {
            addToCart.setText("Out of Stock");
            addToCart.setEnabled(false);  // Disable button if out of stock
        } else {
            addToCart.setText("Add to Cart");
            addToCart.addActionListener(e -> {
                Product copy = new Product(product);
                copy.setQuantity(1);
                product.setQuantity(product.getQuantity() - 1);
                buyer.getShoppingCart().add(copy);
                moreDetails.revalidate();  // Refresh the panel after adding to cart
            });
        }

        // Use HTML for better formatting (line breaks)
        JLabel nameLabel = new JLabel("<html><b>Name:</b> " + product.getName() + "</html>");
        JLabel priceLabel = new JLabel("<html><b>Price:</b> " + formattedPrice + "</html>");
        JLabel stockLabel = new JLabel("<html><b>In stock:</b> " + product.getQuantity() + "</html>");
        JLabel descriptionLabel = new JLabel("<html><b>Description:</b> " + product.getDescription() + "</html>");
        JLabel sellerLabel = new JLabel("<html><b>Seller:</b> " + product.getSellerUserName() + "</html>");

        moreDetails.add(nameLabel);
        moreDetails.add(priceLabel);
        moreDetails.add(stockLabel);
        moreDetails.add(descriptionLabel);
        moreDetails.add(sellerLabel);
        moreDetails.add(addToCart);

        return moreDetails;
    }






}