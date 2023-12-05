package src.Frontend;

import javax.swing.*;

/**
 * The `ShoppingCartApp` class represents the main application window for the shopping cart app.
 */
public class ShoppingCartApp extends JFrame {
    private ViewManager viewManager;
    /**
     * Constructs a `ShoppingCartApp` object.
     */
    public ShoppingCartApp() {
        this.setTitle("Not Amazon");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        JPanel mainPanel = new JPanel();
        this.add(mainPanel);
        this.viewManager = ViewManager.getInstance(this);
        this.viewManager.showEntryView();
        this.setVisible(true);

    }

    public void shutDown(){
        this.dispose();
        System.exit(0);
    }
}
