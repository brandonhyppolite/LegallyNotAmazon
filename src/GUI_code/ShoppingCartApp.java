package src.GUI_code;

import src.Backend.ShoppingSystem;

import javax.swing.*;

public class ShoppingCartApp extends JFrame {
    private ShoppingSystem system;
    private ViewManager viewManager;

    public ShoppingCartApp() {
        this.system = ShoppingSystem.getInstance();
        this.setTitle("Not Amazon");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        JPanel mainPanel = new JPanel();
        this.add(mainPanel);
        this.viewManager = ViewManager.getInstance(this);
        this.viewManager.showEntryView();
        this.setVisible(true);

    }
}
